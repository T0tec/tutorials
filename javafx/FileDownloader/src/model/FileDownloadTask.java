package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;

public class FileDownloadTask extends Task<File> {

  private final static Logger log = Logger.getLogger(FileDownloadTask.class.getName());

  private static final int DEFAULT_BUFFER_SIZE = 1024;

  private String url;
  private File file;
  private String downloadDirectory;
  private int bufferSize;

  private HttpURLConnection httpURLConnection;
  private String fileName;
  private int contentLength;

  public FileDownloadTask(String url, String downloadDirectory) {
    this(url, downloadDirectory, DEFAULT_BUFFER_SIZE);
  }

  public FileDownloadTask(String url, String downloadDirectory,
                          int bufferSize) {
    this.url = url;
    this.downloadDirectory = downloadDirectory;
    this.bufferSize = bufferSize;

    stateProperty().addListener(new ChangeListener<State>() {
      public void changed(ObservableValue<? extends State> source, State oldState, State newState) {
        if (newState.equals(State.SUCCEEDED)) {
          succeeded();
        } else if (newState.equals(State.FAILED)) {
          failed();
        }
      }
    });
  }

  protected File call() throws Exception {
    InputStream remoteContentStream = getContent();
    log.info(String.format("Downloading file from %s to %s", url, fileName));
    FileOutputStream outputStream =
        new FileOutputStream(downloadDirectory + File.separator + fileName);
    file = new File(downloadDirectory + File.separator + fileName);
    try {
      long fileSize = contentLength;
      log.fine(String.format("Size of file to download is %s", fileSize));

      byte[] buffer = new byte[bufferSize];
      int bytesRead = 0;
      int totalBytesRead = 0;
      while ((bytesRead = remoteContentStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
        totalBytesRead += bytesRead;
        updateProgress(totalBytesRead, fileSize);
        log.info(String.format("Downloaded %s of %s bytes (%d) for file",
                               totalBytesRead, fileSize,
                               (int) ((double) totalBytesRead / (double) fileSize * 100.0)));
        log.info(String.format("Percent completed = " + (int) (totalBytesRead * 100 / fileSize)));
      }
      log.info(String.format("Downloading of file %s to %s completed successfully", url,
                             file.getAbsolutePath()));
      return file;
    } finally {
      remoteContentStream.close();
      if (outputStream != null) {
        outputStream.close();
      }
    }
  }

  private InputStream getContent() throws Exception {
    URL url = new URL(this.url);
    httpURLConnection = (HttpURLConnection) url.openConnection();
    int responseCode = httpURLConnection.getResponseCode();

    // Always check HTTP response code first
    if (responseCode == HttpURLConnection.HTTP_OK) {
      String disposition = httpURLConnection.getHeaderField("Content-Disposition");
      String contentType = httpURLConnection.getContentType();
      contentLength = httpURLConnection.getContentLength();

      if (disposition != null) {
        // Extracts file name from header field
        int index = disposition.indexOf("filename=");
        if (index > 0) {
          fileName = disposition.substring(index + 9,
                                           disposition.length());
        }
      } else {
        // Extracts file name from URL
        fileName = this.url.substring(this.url.lastIndexOf("/") + 1,
                                      this.url.length());
      }

      log.info(String.format("Content-Type = %s ", contentType));
      log.info(String.format("Content-Disposition = %s", disposition));
      log.info(String.format("Content-Length = ", contentLength));
      log.info(String.format("fileName = %s ", fileName));

      // Opens input stream from the HTTP connection
      InputStream inputStream = httpURLConnection.getInputStream();
      return inputStream;
    } else {
      throw new IOException(
          "No file to download. Server replied HTTP code: "
          + responseCode);
    }
  }

  @Override
  protected void failed() {
    log.log(Level.SEVERE, "File download failed: " + getException().getMessage(), getException());
  }

  @Override
  protected void succeeded() {
    log.log(Level.FINEST, "File download successfully!");
  }
}

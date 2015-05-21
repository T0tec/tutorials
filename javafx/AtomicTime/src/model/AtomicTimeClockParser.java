package model;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class AtomicTimeClockParser {

  public AtomicTimeClockParser() {
  }

  public String getAtomicTime() throws IOException {
    try {
      Connection.Response response = Jsoup.connect("http://time.is/just")
          .timeout(100000)
          .ignoreHttpErrors(true)
          .execute();
      Element content = response.parse().getElementById("twd");
      return content.text();
    } catch (IOException ex) {
      throw new IOException(ex);
    }
  }
}

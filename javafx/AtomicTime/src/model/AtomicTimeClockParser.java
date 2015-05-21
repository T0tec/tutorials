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

  private Element content;

  public AtomicTimeClockParser() {
    try {
      Connection.Response response = Jsoup.connect("http://time.is/just")
          .timeout(100000)
          .ignoreHttpErrors(true)
          .execute();
      content = response.parse().getElementById("twd");
    } catch (IOException e) {
      System.out.println("io - " + e);
    }
  }

  public String getAtomicTime() {
    return this.content.text();
  }
}

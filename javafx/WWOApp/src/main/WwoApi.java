package main;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLEncoder;

public class WwoApi {

  public static final boolean LOG_DEBUG = false;

  // Refer for documentation to following links:
  // - http://www.worldweatheronline.com/api/docs/local-city-town-weather-api.aspx
  // - http://www.worldweatheronline.com/api/docs/search-api.aspx
  //
  // Replace the key with your own key
  // Obtain key by registering on World Weather Online website
  public static final String FREE_API_KEY = "6f81839c19ed37a2aff40be6a0b59";
  public static final String PREMIUM_API_KEY = "69eb7e06c07b4900093a4eac61082";

  private String key;
  protected String apiEndPoint;

  public WwoApi(boolean freeAPI) {
    if (freeAPI) {
      setKey(FREE_API_KEY);
    } else {
      setKey(PREMIUM_API_KEY);
    }
  }

  public WwoApi setKey(String key) {
    this.key = key;
    return this;
  }

  public WwoApi setApiEndPoint(String apiEndPoint) {
    this.apiEndPoint = apiEndPoint;
    return this;
  }

  public String getKey() {
    return key;
  }

  class RootParams {

    public String getQueryString(Class cls) {
      String query = null;

      Field[] fields = cls.getDeclaredFields();

      try {
        for (Field field : fields) {
          Object f = field.get(this);
          if (f != null) {
            if (query == null) {
              query = "?" + URLEncoder.encode(field.getName(), "UTF-8") + "="
                      + URLEncoder.encode((String) f, "UTF-8");
            } else {
              query += "&" + URLEncoder.encode(field.getName(), "UTF-8") + "="
                       + URLEncoder.encode((String) f, "UTF-8");
            }
          }
        }
      } catch (Exception e) {

      }
      return query;
    }
  }

  static InputStream getInputStream(String url) {
    InputStream is = null;
    try {
      is = (new URL(url)).openConnection().getInputStream();
    } catch (Exception e) {

    }
    return is;
  }
}

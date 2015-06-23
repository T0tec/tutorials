package main;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class LocalWeather extends WwoApi {

  public static final String FREE_API_ENDPOINT =
      "http://api.worldweatheronline.com/free/v2/weather.ashx";
  public static final String PREMIUM_API_ENDPOINT =
      "http://api.worldweatheronline.com/premium/v1/weather.ashx";

  public LocalWeather(boolean freeAPI) {
    super(freeAPI);
    if (freeAPI) {
      apiEndPoint = FREE_API_ENDPOINT;
    } else {
      apiEndPoint = PREMIUM_API_ENDPOINT;
    }
  }

  public Data callAPI(String query, MediaType mediaType) {
    return getLocalWeatherData(mediaType, getInputStream(apiEndPoint + query));
  }

  Data getLocalWeatherData(MediaType mediaType, InputStream is) {
    Data weather = null;

    try {
      // create JAXB context and initializing Marshaller
      JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);

      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

      jaxbUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, mediaType);

      // this will create Java object - Data from the XML/JSON response
      weather = (Data) jaxbUnmarshaller.unmarshal(is);

      if (LOG_DEBUG) {
        CurrentCondition cc = weather.currentCondition;
        System.out.println(cc.tempC);
        System.out.println(cc.weatherIconUrl);
        System.out.println(cc.weatherIconUrl);
      }
    } catch (JAXBException e) {
      // some exception occured
      e.printStackTrace();
    }

    return weather;
  }

  public class Params extends RootParams {

    String query;               // required
    String extra;
    String numOfDays;           // required
    String date;
    String fx;                  // "no" or "yes"
    String cc;                  // default "yes"
    String includeLocation;     // default "no"
    String format;              // default "xml"
    String showComments;
    String callback;
    String key;                 // required

    public Params(String key, String query) {
      if (key == null || query == null) {
        throw new IllegalArgumentException(
            "key and query can not be null");
      }
      this.key = key;
      this.query = query;
    }

    public Params withExtra(String extra) {
      this.extra = extra;
      return this;
    }

    public Params withNumOfDays(String num_of_days) {
      this.numOfDays = num_of_days;
      return this;
    }

    public Params withDate(String date) {
      this.date = date;
      return this;
    }

    public Params withFx(String fx) {
      this.fx = fx;
      return this;
    }

    public Params withCc(String cc) {
      this.cc = cc;
      return this;
    }

    public Params withIncludeLocation(String includeLocation) {
      this.includeLocation = includeLocation;
      return this;
    }

    public Params withFormat(String format) {
      this.format = format;
      return this;
    }

    public Params withShowComments(String showComments) {
      this.showComments = showComments;
      return this;
    }

    public Params withCallback(String callback) {
      this.callback = callback;
      return this;
    }
  }

  @XmlRootElement
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Data {

    Request request;
    @XmlElement(name = "current_condition")
    CurrentCondition currentCondition;
    Weather weather;

    Data() {
    }

    ;
  }

  @XmlRootElement(namespace = "main.LocalWeather.Data")
  @XmlAccessorType(XmlAccessType.FIELD)
  static class Request {

    String type;
    String query;

    Request() {
    }
  }

  @XmlRootElement(namespace = "main.LocalWeather.Data")
  @XmlAccessorType(XmlAccessType.FIELD)
  static class CurrentCondition {

    @XmlElement(name = "observation_time")
    String observationTime;
    @XmlElement(name = "temp_C")
    String tempC;
    @XmlElement(name = "temp_F")
    String tempF;
    String weatherCode;
    String weatherIconUrl;
    String weatherDesc;
    String windspeedMiles;
    String windspeedKmph;
    String winddirDegree;
    String winddir16Point;
    String precipMM;
    String humidity;
    String visibility;
    String pressure;
    @XmlElement(name = "cloudcover")
    String cloudCover;
    @XmlElement(name = "FeelsLikeC")
    String feelsLikeC;
    @XmlElement(name = "FeelsLikeF")
    String feelsLikeF;

    CurrentCondition() {
    }


  }

  @XmlRootElement(namespace = "main.LocalWeather.Data")
  @XmlAccessorType(XmlAccessType.FIELD)
  static class Weather {

    String date;
    String tempMaxC;
    String tempMaxF;
    String tempMinC;
    String tempMinF;
    String windspeedMiles;
    String windspeedKmph;
    String winddirection;
    String weatherCode;
    String weatherIconUrl;
    String weatherDesc;
    String precipMM;

    Weather() {
    }
  }
}


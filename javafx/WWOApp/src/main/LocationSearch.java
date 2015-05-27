package main;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

public class LocationSearch extends WwoApi {

  public static final String FREE_API_ENDPOINT =
      "http://api.worldweatheronline.com/free/v2/search.ashx";
  public static final String PREMIUM_API_ENDPOINT =
      "http://api.worldweatheronline.com/premium/v1/search.ashx";

  public LocationSearch(boolean freeAPI) {
    super(freeAPI);

    if (freeAPI) {
      apiEndPoint = FREE_API_ENDPOINT;
    } else {
      apiEndPoint = PREMIUM_API_ENDPOINT;
    }
  }

  public Data callAPI(String query, MediaType mediaType) {
    System.out.println(apiEndPoint + query);
    return getLocationSearchData(mediaType, getInputStream(apiEndPoint + query));
  }

  Data getLocationSearchData(MediaType mediaType, InputStream is) {
    Data location = null;

    try {
      // create JAXB context and initializing Marshaller
      JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);

      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

      jaxbUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, mediaType);

      // this will create Java object - Data from the XML/JSON response
      location = (Data) jaxbUnmarshaller.unmarshal(is);

      if (LOGD) {
        System.out.println(location.result.areaName);
        System.out.println(location.result.country);
        System.out.println(location.result.region);
      }

    } catch (JAXBException e) {
      // some exception occured
      e.printStackTrace();
    }

    return location;
  }

  public class Params extends RootParams {

    String query;                 // required
    String num_of_results = "1";
    String timezone;
    String popular;
    String format;                // default "xml"
    String key;                   // required

    public Params(String key, String query) {
      if (key == null || query == null) {
        throw new IllegalArgumentException(
            "key and query can not be null");
      }
      this.key = key;
      this.query = query;
    }

    public Params withNumOfResults(String num_of_results) {
      this.num_of_results = num_of_results;
      return this;
    }

    public Params withTimezone(String timezone) {
      this.timezone = timezone;
      return this;
    }

    public Params withPopular(String popular) {
      this.popular = popular;
      return this;
    }

    public Params withFormat(String format) {
      this.format = format;
      return this;
    }

    public Params withKey(String key) {
      this.key = key;
      return this;
    }
  }

  @XmlRootElement(name = "search_api")
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Data {

    Result result;

    Data() {
    }
  }

  @XmlRootElement(namespace = "main.LocalWeather.Data")
  @XmlAccessorType(XmlAccessType.FIELD)
  static class Result {

    String areaName;
    String country;
    String region;
    String latitude;
    String longitude;
    String population;
    String weatherUrl;
    TimeZone timezone;

    Result() {
    }
  }

  @XmlRootElement(namespace = "main.LocalWeather.Data")
  @XmlAccessorType(XmlAccessType.FIELD)
  static class TimeZone {

    String offset;

    TimeZone() {
    }
  }
}


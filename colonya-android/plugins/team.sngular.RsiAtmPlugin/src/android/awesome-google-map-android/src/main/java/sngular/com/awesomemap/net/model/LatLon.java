package sngular.com.awesomemap.net.model;

/**
 * Created by Fabio on 26/3/15.
 * fabio.santana@medianet.es
 */
public class LatLon {
  private double latitude;
  private double longitude;

  public LatLon() {}

  public LatLon(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }
}

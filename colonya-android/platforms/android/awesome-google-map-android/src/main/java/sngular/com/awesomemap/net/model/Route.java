package sngular.com.awesomemap.net.model;

import java.util.List;

/**
 * Created by Fabio on 1/4/15.
 * fabio.santana@medianet.es
 */
public class Route {
  private int distance;
  private int time;
  private List<LatLon> steps;

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public void setSteps(List<LatLon> steps) {
    this.steps = steps;
  }

  public List<LatLon> getSteps() {
    return steps;
  }
}

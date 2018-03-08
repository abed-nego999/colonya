
package sngular.com.awesomemap.net.dto.google.directions;

import com.google.gson.annotations.Expose;

public class PolylineDTO {

    @Expose
    private String points;

    /**
     * 
     * @return
     *     The points
     */
    public String getPoints() {
        return points;
    }

    /**
     * 
     * @param points
     *     The points
     */
    public void setPoints(String points) {
        this.points = points;
    }

}


package sngular.com.awesomemap.net.dto.google.directions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StepDTO {

    @Expose
    private DistanceDTO distance;
    @Expose
    private DurationDTO duration;
    @SerializedName("end_location")
    @Expose
    private EndLocationDTO endLocation;
    @SerializedName("html_instructions")
    @Expose
    private String htmlInstructions;
    @Expose
    private PolylineDTO polyline;
    @SerializedName("start_location")
    @Expose
    private StartLocationDTO startLocation;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;
    @Expose
    private String maneuver;

    /**
     * 
     * @return
     *     The distance
     */
    public DistanceDTO getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    public void setDistance(DistanceDTO distance) {
        this.distance = distance;
    }

    /**
     * 
     * @return
     *     The duration
     */
    public DurationDTO getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration
     *     The duration
     */
    public void setDuration(DurationDTO duration) {
        this.duration = duration;
    }

    /**
     * 
     * @return
     *     The endLocation
     */
    public EndLocationDTO getEndLocation() {
        return endLocation;
    }

    /**
     * 
     * @param endLocation
     *     The end_location
     */
    public void setEndLocation(EndLocationDTO endLocation) {
        this.endLocation = endLocation;
    }

    /**
     * 
     * @return
     *     The htmlInstructions
     */
    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    /**
     * 
     * @param htmlInstructions
     *     The html_instructions
     */
    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    /**
     * 
     * @return
     *     The polyline
     */
    public PolylineDTO getPolyline() {
        return polyline;
    }

    /**
     * 
     * @param polyline
     *     The polyline
     */
    public void setPolyline(PolylineDTO polyline) {
        this.polyline = polyline;
    }

    /**
     * 
     * @return
     *     The startLocation
     */
    public StartLocationDTO getStartLocation() {
        return startLocation;
    }

    /**
     * 
     * @param startLocation
     *     The start_location
     */
    public void setStartLocation(StartLocationDTO startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * 
     * @return
     *     The travelMode
     */
    public String getTravelMode() {
        return travelMode;
    }

    /**
     * 
     * @param travelMode
     *     The travel_mode
     */
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    /**
     * 
     * @return
     *     The maneuver
     */
    public String getManeuver() {
        return maneuver;
    }

    /**
     * 
     * @param maneuver
     *     The maneuver
     */
    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

}

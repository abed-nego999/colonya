
package sngular.com.awesomemap.net.dto.google.directions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LegDTO {

    @Expose
    private DistanceDTO distance;
    @Expose
    private DurationDTO duration;
    @SerializedName("end_address")
    @Expose
    private String endAddress;
    @SerializedName("end_location")
    @Expose
    private EndLocationDTO endLocation;
    @SerializedName("start_address")
    @Expose
    private String startAddress;
    @SerializedName("start_location")
    @Expose
    private StartLocationDTO startLocation;
    @Expose
    private List<StepDTO> steps = new ArrayList<StepDTO>();
    @SerializedName("via_waypoint")
    @Expose
    private List<Object> viaWaypoint = new ArrayList<Object>();

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
     *     The endAddress
     */
    public String getEndAddress() {
        return endAddress;
    }

    /**
     * 
     * @param endAddress
     *     The end_address
     */
    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
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
     *     The startAddress
     */
    public String getStartAddress() {
        return startAddress;
    }

    /**
     * 
     * @param startAddress
     *     The start_address
     */
    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
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
     *     The steps
     */
    public List<StepDTO> getSteps() {
        return steps;
    }

    /**
     * 
     * @param steps
     *     The steps
     */
    public void setSteps(List<StepDTO> steps) {
        this.steps = steps;
    }

    /**
     * 
     * @return
     *     The viaWaypoint
     */
    public List<Object> getViaWaypoint() {
        return viaWaypoint;
    }

    /**
     * 
     * @param viaWaypoint
     *     The via_waypoint
     */
    public void setViaWaypoint(List<Object> viaWaypoint) {
        this.viaWaypoint = viaWaypoint;
    }

}

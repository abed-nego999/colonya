
package sngular.com.awesomemap.net.dto.google.directions;

import com.google.gson.annotations.Expose;


public class BoundsDTO {

    @Expose
    private NortheastDTO northeast;
    @Expose
    private SouthwestDTO southwest;

    /**
     * 
     * @return
     *     The northeast
     */
    public NortheastDTO getNortheast() {
        return northeast;
    }

    /**
     * 
     * @param northeast
     *     The northeast
     */
    public void setNortheast(NortheastDTO northeast) {
        this.northeast = northeast;
    }

    /**
     * 
     * @return
     *     The southwest
     */
    public SouthwestDTO getSouthwest() {
        return southwest;
    }

    /**
     * 
     * @param southwest
     *     The southwest
     */
    public void setSouthwest(SouthwestDTO southwest) {
        this.southwest = southwest;
    }

}

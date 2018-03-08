
package sngular.com.awesomemap.net.dto.google.directions;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class GoogleDirectionRouteDTO {

    @Expose
    private List<RouteDTO> routes = new ArrayList<RouteDTO>();
    @Expose
    private String status;

    /**
     * 
     * @return
     *     The routes
     */
    public List<RouteDTO> getRoutes() {
        return routes;
    }

    /**
     * 
     * @param routes
     *     The routes
     */
    public void setRoutes(List<RouteDTO> routes) {
        this.routes = routes;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}

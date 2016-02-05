package offers.restaurant.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("data")
    @Expose
    private List<RestaurantData> data = new ArrayList<RestaurantData>();

    /**
     * @return The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return The data
     */
    public List<RestaurantData> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<RestaurantData> data) {
        this.data = data;
    }

}

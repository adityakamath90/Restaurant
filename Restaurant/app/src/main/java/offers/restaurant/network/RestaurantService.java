package offers.restaurant.network;

import offers.restaurant.dto.Data;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestaurantService {

    @GET("task.txt")
    Call<Data> getRestaurantData();
}

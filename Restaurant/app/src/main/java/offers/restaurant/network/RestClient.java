package offers.restaurant.network;

import offers.restaurant.utilities.Config;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class RestClient {


    public static RestaurantService getRestaurantService() {
        RestaurantService api = getRetrofit().create(RestaurantService.class);
        return api;
    }


    private static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
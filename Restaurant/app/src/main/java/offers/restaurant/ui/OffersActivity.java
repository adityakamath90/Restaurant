package offers.restaurant.ui;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import offers.restaurant.R;
import offers.restaurant.adpater.RestaurantAdapter;
import offers.restaurant.dto.Data;
import offers.restaurant.dto.RestaurantData;
import offers.restaurant.network.RestClient;
import offers.restaurant.utilities.NetworkUtils;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersActivity extends AppCompatActivity implements GoogleApiClient
        .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private RecyclerView mRestaurantList;
    private ProgressBar mProgressBar;
    private GoogleApiClient mGoogleApiClient;
    private double mCurrentLatitude;
    private double mCurrentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offfers);
        initView();
        initializeGoogleApiClient();

    }

    private void initializeGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(OffersActivity.this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void initiateRestaurantListingApi() {
        showProgress();
        RestClient.getRestaurantService().getRestaurantData().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Response<Data> response) {
                if (response != null && response.isSuccess()) {
                    List<RestaurantData> restaurantDataList = response.body().getData();
                    sortRestaurantListBasedOnCurrentLocation(restaurantDataList);
                    mRestaurantList.setAdapter(new RestaurantAdapter(OffersActivity.this,
                            restaurantDataList));
                }
                hideProgress();
            }

            @Override
            public void onFailure(Throwable t) {
                hideProgress();
            }
        });
    }

    private void sortRestaurantListBasedOnCurrentLocation(List<RestaurantData> restaurantDataList) {
        Collections.sort(restaurantDataList, new Comparator<RestaurantData>() {
            @Override
            public int compare(RestaurantData o1, RestaurantData o2) {
                float[] result1 = new float[3];
                Location.distanceBetween(mCurrentLatitude,
                        mCurrentLongitude, Double.valueOf(o1
                                .getLatitude()), Double.valueOf(o1.getLongitude()),
                        result1);
                Float distance1 = result1[0];

                float[] result2 = new float[3];
                Location.distanceBetween(mCurrentLatitude,
                        mCurrentLongitude, Double.valueOf(o2
                                .getLatitude()), Double.valueOf(o2.getLongitude()),
                        result2);
                Float distance2 = result2[0];

                return distance1.compareTo(distance2);
            }
        });
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }


    private void initView() {
        mRestaurantList = (RecyclerView) findViewById(R.id.restaurant_list);
        mRestaurantList.setLayoutManager(new LinearLayoutManager(OffersActivity.this));
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (location != null) {
            mCurrentLatitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
        }
        if (NetworkUtils.isAvailable(OffersActivity.this)) {
            initiateRestaurantListingApi();
        } else {
            NetworkUtils.displayNetworkDialog(OffersActivity.this);
        }
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

}

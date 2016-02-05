package offers.restaurant.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import offers.restaurant.R;
import offers.restaurant.dto.RestaurantData;
import offers.restaurant.utilities.MathUtils;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<RestaurantData> mRestaurantDataList;
    private LayoutInflater mInflater;
    private double mCurrentLatitude;
    private double mCurrentLongitude;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mRestaurantImage;
        private TextView mRestaurantName;
        private TextView mDistanceAway;

        public ViewHolder(View itemView) {
            super(itemView);
            mRestaurantImage = (ImageView) itemView.findViewById(R.id.restaurant_thumbnail);
            mRestaurantName = (TextView) itemView.findViewById(R.id.restaurant_name);
            mDistanceAway = (TextView) itemView.findViewById(R.id.distance_away);
        }
    }

    public RestaurantAdapter(Context context, List<RestaurantData> restaurantData, double
            currentLatitude, double currentLongitude) {
        mRestaurantDataList = restaurantData;
        mInflater = LayoutInflater.from(context);
        mCurrentLatitude = currentLatitude;
        mCurrentLongitude = currentLongitude;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.restaurant_item, parent, false);
        ViewHolder movieViewHolder = new ViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RestaurantData data = mRestaurantDataList.get(position);
        if (data != null) {
            holder.mRestaurantName.setText(data.getBrandName());
            Picasso.with(mInflater.getContext()).load(data.getLogoURL())
                    .resize(150, 75).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                    .into(holder.mRestaurantImage);
            holder.mDistanceAway.setText(mInflater.getContext().getString(R.string.location,
                    String.valueOf(MathUtils.convertMeterToKm(MathUtils.distance
                            (mCurrentLatitude, mCurrentLongitude, Double
                            .valueOf(data.getLatitude()), Double.valueOf(data.getLongitude())))),
                    data
                            .getNeighbourhoodName()));
        }
    }

    @Override
    public int getItemCount() {
        return (mRestaurantDataList != null && !mRestaurantDataList.isEmpty()) ?
                mRestaurantDataList.size() : 0;
    }

}

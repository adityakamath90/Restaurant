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

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<RestaurantData> mRestaurantDataList;
    private LayoutInflater mInflater;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mRestaurantImage;
        private TextView mRestaurantName;

        public ViewHolder(View itemView) {
            super(itemView);
            mRestaurantImage = (ImageView) itemView.findViewById(R.id.restaurant_thumbnail);
            mRestaurantName = (TextView) itemView.findViewById(R.id.restaurant_name);
        }
    }

    public RestaurantAdapter(Context context, List<RestaurantData> restaurantData) {
        mRestaurantDataList = restaurantData;
        mInflater = LayoutInflater.from(context);
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
            Picasso.with(mInflater.getContext()).load(data.getLogoURL()).resize(75, 75).centerCrop()
                    .placeholder(R
                            .mipmap
                            .ic_launcher).error(R.mipmap.ic_launcher).into(holder.mRestaurantImage);
        }
    }

    @Override
    public int getItemCount() {
        return (mRestaurantDataList != null && !mRestaurantDataList.isEmpty()) ?
                mRestaurantDataList.size() : 0;
    }

}

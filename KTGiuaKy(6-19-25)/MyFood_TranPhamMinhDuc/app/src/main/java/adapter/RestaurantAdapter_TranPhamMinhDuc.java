package adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfood_tranphamminhduc.FoodList_TranPhamMinhDuc;
import com.example.myfood_tranphamminhduc.R;
import com.example.myfood_tranphamminhduc.model.Restaurant_TranPhamMinhDuc;

import java.util.List;
    public class RestaurantAdapter_TranPhamMinhDuc extends RecyclerView.Adapter<RestaurantAdapter_TranPhamMinhDuc.RestaurantViewHolder> {

        private Context context;
        private List<Restaurant_TranPhamMinhDuc> restaurantList;

        public RestaurantAdapter_TranPhamMinhDuc(Context context, List<Restaurant_TranPhamMinhDuc> restaurantList) {
            this.context = context;
            this.restaurantList = restaurantList;
        }

        @NonNull
        @Override
        public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_tpmd, parent, false);
            return new RestaurantViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
            Restaurant_TranPhamMinhDuc res = restaurantList.get(position);
            holder.txtName.setText(res.getName());
            holder.txtAddress.setText(res.getAddress());

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, FoodList_TranPhamMinhDuc.class);
                intent.putExtra("res_id", res.getResID());
                context.startActivity(intent);
            });
            int resId = context.getResources().getIdentifier(res.getImage(), "drawable", context.getPackageName());
            holder.imgRes.setImageResource(resId);

        }

        @Override
        public int getItemCount() {
            return restaurantList.size();
        }

        public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
            ImageView imgRes;
            TextView txtName, txtAddress;

            public RestaurantViewHolder(@NonNull View itemView) {
                super(itemView);
                imgRes = itemView.findViewById(R.id.imgRes);
                txtName = itemView.findViewById(R.id.txtRestaurantName);
                txtAddress = itemView.findViewById(R.id.txtRestaurantAddress);
            }
        }
    }



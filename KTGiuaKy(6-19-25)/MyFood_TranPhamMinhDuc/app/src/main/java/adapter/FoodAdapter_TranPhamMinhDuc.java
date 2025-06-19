package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfood_tranphamminhduc.R;
import com.example.myfood_tranphamminhduc.model.Food_TranPhamMinhDuc;

import java.util.List;

public class FoodAdapter_TranPhamMinhDuc extends RecyclerView.Adapter<FoodAdapter_TranPhamMinhDuc.FoodViewHolder> {

    private Context context;
    private List<Food_TranPhamMinhDuc> foodList;

    public FoodAdapter_TranPhamMinhDuc(Context context, List<Food_TranPhamMinhDuc> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_tpmd, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food_TranPhamMinhDuc food = foodList.get(position);
        holder.txtName.setText(food.getName());
        holder.txtType.setText(food.getType());
        holder.txtDesc.setText(food.getDescription());

        int resId = context.getResources().getIdentifier(food.getImage(), "drawable", context.getPackageName());

        holder.imgFood.setImageResource(resId); // đơn giản và nhanh
        //Glide.with(context).load(food.getImage()).into(holder.imgFood);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtType, txtDesc;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            txtName = itemView.findViewById(R.id.txtFoodName);
            txtType = itemView.findViewById(R.id.txtFoodType);
            txtDesc = itemView.findViewById(R.id.txtFoodDesc);
        }
    }
}

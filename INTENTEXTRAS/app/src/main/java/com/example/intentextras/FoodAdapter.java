package com.example.intentextras;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foodList;
    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }
    public class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImageView;
        TextView foodNameTextView;
        public FoodViewHolder(View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.foodImageView);
            foodNameTextView = itemView.findViewById(R.id.foodNameTextView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Toast.makeText(v.getContext(), "Bạn chọn: " + foodList.get(position).getName(),
                            Toast.LENGTH_SHORT).show();
                }
            });}
    }
    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }
    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.foodNameTextView.setText(food.getName());
        holder.foodImageView.setImageResource(food.getImageResId());
        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            Food clickedFood = foodList.get(position);

            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("foodName", clickedFood.getName());
            intent.putExtra("foodImage", clickedFood.getImageResId());
            intent.putExtra("foodDescription", clickedFood.getDescription());
            intent.putExtra("FoodPrice", clickedFood.getPrice());

            v.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return foodList.size();
    }
    public void removeItem(int position) {
        foodList.remove(position);
        notifyItemRemoved(position);
    }
}
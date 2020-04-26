package org.example.foodie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import org.example.foodie.models.Food;
import org.example.foodie.models.Foodid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.CustomViewHolder> {

    private static int pos;

    List<Food> items;
    private OnItemCLickListener mOnItemCLickListener;
    private FragmentManager f_manager;
    private Context context;
    public SharedPreferences sharedPreferences;




    public FoodAdapter(Context context) {
        this.context = context;

        // this.items = items;
    }

    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.mOnItemCLickListener = onItemCLickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.foods_view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Log.i("afnan", String.valueOf(items.get(position).getFoodid()));
        Foodid foodid = items.get(position).getFoodid();
        holder.foodName.setText(foodid.getName());


        sharedPreferences = context.getSharedPreferences("org.example.foodie", Context.MODE_PRIVATE);

        CartActivity.getPrefernce(sharedPreferences);
        Food currItem = items.get(position);


       /* if(!CartActivity.cartItems.isEmpty()){
            if(isPresent(CartActivity.cartItems,currItem.getFoodid().getName())){
                holder.countFood.setText(CartActivity.cartItems.get(pos).getCount());
            }
        }*/


        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isPresent(CartActivity.cartItems, currItem.getFoodid().getName())) {
                    Toast.makeText(context, "Already in cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show();

                    items.get(position).addCount();
                    CartActivity.cartItems.add(items.get(position));
                    CartActivity.saveData(sharedPreferences);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setFood(List<Food> foods) {
        this.items = foods;
        notifyDataSetChanged();
    }

    public interface OnItemCLickListener {
        void OnItemClick(int position);
    }

    public Boolean isPresent(List<Food> foods, String name) {

        for (int i = 0; i < foods.size(); i++) {
            if (name.equals(foods.get(i).getFoodid().getName())) {
                pos = i;
                return true;
            }
        }
        return false;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private Button addToCart;
        private TextView foodName;
        private ImageView addfood;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            addfood = itemView.findViewById(R.id.addFood);
            addToCart = itemView.findViewById(R.id.addToCart);
        }
    }

}

package com.example.shopping.productList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopping.R;
import com.example.shopping.databaase.Product;
import com.example.shopping.manager.ProductManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Product> products = new ArrayList<>();

    public void updateProductData(ArrayList<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.updateView(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        Product bindProduct;
        TextView name;
        TextView price;
        TextView description;
        TextView inventory;
        Button buy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
            inventory = itemView.findViewById(R.id.inventory);
            buy = itemView.findViewById(R.id.buy);

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("test123", "receive click");
                    if(bindProduct == null){
                        return;
                    }

                    Log.e("test123", "receive id: " + bindProduct.getId());
                    ProductManager.getInstance().buyProductWithNumber(
                            bindProduct.getId(), bindProduct.getInventory(),
                            1, new Runnable() {
                                @Override
                                public void run() {
                                    ProductManager.getInstance().getProductsFromRemote(new ProductManager.GetProductsCallback() {
                                        @Override
                                        public void success(ArrayList<Product> products) {
                                            Adapter.this.updateProductData(products);
                                        }

                                        @Override
                                        public void fail(Exception e) {
                                            Log.e("hi",e.getMessage());
                                        }
                                    });
                                }
                            });
                }
            });
        }

        public void updateView(Product product){
            if(product == null){
                return;
            }
            name.setText(product.getName());
            price.setText(""+product.getPrice());
            description.setText(product.getDescription());
            inventory.setText(""+product.getInventory());
            bindProduct = product;
        }
    }
}

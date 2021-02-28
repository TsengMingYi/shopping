package com.example.shopping.productList;

import android.os.Bundle;
import android.util.Log;

import com.example.shopping.R;
import com.example.shopping.databaase.Product;
import com.example.shopping.manager.ProductManager;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter = new Adapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        findView();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        ProductManager.getInstance().getProductsFromRemote(new ProductManager.GetProductsCallback() {
            @Override
            public void success(ArrayList<Product> products) {
               adapter.updateProductData(products);
            }

            @Override
            public void fail(Exception e) {
                Log.e("hi",e.getMessage());
            }
        });

        // Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);
//
//// Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("test", "DocumentSnapshot added with ID: " +
//                                documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(  Exception e) {
//                        Log.w("test", "Error adding document", e);
//                    }
//                });
    }
    private void findView(){
        recyclerView = findViewById(R.id.recyclerView);
    }
}

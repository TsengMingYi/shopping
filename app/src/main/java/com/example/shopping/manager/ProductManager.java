package com.example.shopping.manager;


import android.util.Log;

import com.example.shopping.databaase.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class ProductManager {
    private static final String TAG = ProductManager.class.getSimpleName();
    private static ProductManager instance = new ProductManager();

    public static ProductManager getInstance() {
        return instance;
    }

    private ProductManager() {
    }

    public void getProductsFromRemote(final GetProductsCallback callback) {
        FirebaseFirestore.getInstance().collection("product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        ArrayList<Product> products = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals("about")) {
                                    continue;
                                }

                                Map data = document.getData();
                                Log.e("test", document.getId());
                                products.add(new Product(document.getId(),
                                        (Long) data.get("price"), (String) data.get("name")
                                        , (String) data.get("description"), (Long) data.get("inventory")));
                            }
                            callback.success(products);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            callback.fail(task.getException());
                        }
                    }
                });
    }

    public void buyProductWithNumber(String id, long inventory, long buyNumber, final Runnable completeRunnable) {
        FirebaseFirestore.getInstance().collection("product")
                .document(id)
                .update("inventory", FieldValue.increment(-buyNumber))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("test123", "receive task");
                        if (completeRunnable != null) {
                            completeRunnable.run();
                        }
                    }
                });
    }


    public interface GetProductsCallback {
        void success(ArrayList<Product> products);

        void fail(Exception e);
    }
}

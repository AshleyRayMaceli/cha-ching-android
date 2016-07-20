package com.epicodus.chaching.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.epicodus.chaching.Constants;
import com.epicodus.chaching.R;
import com.epicodus.chaching.adapters.FirebasePurchaseViewHolder;
import com.epicodus.chaching.models.Purchase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PurchaseListActivity extends AppCompatActivity {
    private DatabaseReference mPurchaseReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private ArrayList<Purchase> mPurchases = new ArrayList<>();

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String categorySelected = intent.getStringExtra("categorySelected");

        if (categorySelected.equals("All")) {
            mPurchaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_PURCHASES);

        } else {
            mPurchaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_PURCHASES)
                    .child(categorySelected);
        }
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Purchase, FirebasePurchaseViewHolder>
                (Purchase.class, R.layout.purchase_list_item, FirebasePurchaseViewHolder.class, mPurchaseReference) {
            @Override
            protected void populateViewHolder(FirebasePurchaseViewHolder viewHolder,
                                              Purchase model, int position) {
                viewHolder.bindPurchase(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}

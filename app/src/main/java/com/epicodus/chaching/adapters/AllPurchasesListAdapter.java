package com.epicodus.chaching.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.chaching.R;
import com.epicodus.chaching.models.Purchase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AllPurchasesListAdapter extends RecyclerView.Adapter<AllPurchasesListAdapter.AllPurchasesViewHolder> {
    private ArrayList<Purchase> mAllPurchases = new ArrayList<>();
    private Context mContext;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public AllPurchasesListAdapter(Context context, ArrayList<Purchase> allPurchases) {
        mContext = context;
        mAllPurchases = allPurchases;
    }

    @Override
    public AllPurchasesListAdapter.AllPurchasesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_list_item, parent, false);
        AllPurchasesViewHolder viewHolder = new AllPurchasesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AllPurchasesListAdapter.AllPurchasesViewHolder holder, int position) {
        holder.bindAllPurchases(mAllPurchases.get(position));
    }

    @Override
    public int getItemCount() {
        return mAllPurchases.size();
    }

    public class AllPurchasesViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.purchaseCostTextView) TextView mPurchaseCostTextView;
        @Bind(R.id.purchaseNameTextView) TextView mPurchaseNameTextView;

        private Context mContext;

        public AllPurchasesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindAllPurchases(Purchase purchase) {
            mPurchaseCostTextView.setText(formatter.format(purchase.getCost()));
            mPurchaseNameTextView.setText(purchase.getName());
        }
    }
}

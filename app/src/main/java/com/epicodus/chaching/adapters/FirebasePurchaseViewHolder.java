package com.epicodus.chaching.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.epicodus.chaching.R;
import com.epicodus.chaching.models.Purchase;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FirebasePurchaseViewHolder extends RecyclerView.ViewHolder {
    NumberFormat formatter = new DecimalFormat("#0.00");

    View mView;
    Context mContext;

    public FirebasePurchaseViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindPurchase(Purchase purchase) {
        TextView purchaseCostTextView = (TextView) mView.findViewById(R.id.purchaseCostTextView);
        TextView purchaseNameTextView = (TextView) mView.findViewById(R.id.purchaseNameTextView);

        purchaseCostTextView.setText(formatter.format(purchase.getCost()));
        purchaseNameTextView.setText(purchase.getName());
    }
}

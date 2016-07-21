package com.epicodus.chaching.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.epicodus.chaching.Constants;
import com.epicodus.chaching.R;
import com.epicodus.chaching.models.Purchase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mPurchaseReference;
    private ArrayList<Purchase> mPurchases = new ArrayList<>();
    private Double[] categoryTotals = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private String[] colors = {"#ff5252", "#00BCD4", "#FFC107", "#673AB7", "#8BC34A", "#CDDC39", "#607D8B", "#FF4081", "#FF4081", "#FFEB3B"};
    private int purchasesTotal = 0;

    @Bind(R.id.specificCategoriesSpinner) Spinner mSpecificCategoriesSpinner;
    @Bind(R.id.categorizedPurchasesButton) Button mCategorizedPurchasesButton;
    @Bind(R.id.dynamicArcView) DecoView mDynamicArcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ButterKnife.bind(this);

        mCategorizedPurchasesButton.setOnClickListener(this);

        mPurchaseReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_PURCHASES);

        mPurchaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot categoriesSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot purchaseSnapshot : categoriesSnapshot.getChildren()) {
                        Purchase purchase = purchaseSnapshot.getValue(Purchase.class);
                        mPurchases.add(purchase);

                        purchasesTotal += purchase.getCost();
                        Log.v("Purchase Total:", purchasesTotal + "");
                    }
                }

                drawChart();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void onClick(View view) {
        if (view == mCategorizedPurchasesButton) {
            String categorySelected = mSpecificCategoriesSpinner.getSelectedItem().toString();
            Intent intent = new Intent(ChartActivity.this, PurchaseListActivity.class);
            intent.putExtra("categorySelected", categorySelected);
            startActivity(intent);
        }
    }

    public void drawChart() {

        Float insetAmount = 0f;
        int incrementingAnimationDelay = 400;

        mDynamicArcView.configureAngles(360, 270);

        mDynamicArcView.addSeries(new SeriesItem.Builder(Color.argb(0, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(true)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                .build());

        for (int i = 0; i < mPurchases.size(); i++) {

            SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor
                    (assignCategoryColor(mPurchases.get(i).getCategory())))
                    .setRange(0, 100, 0)
                    .setLineWidth(40f)
                    .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                    .setInset(new PointF(insetAmount, insetAmount))
                    .build();

            int seriesIndex = mDynamicArcView.addSeries(seriesItem);

            mDynamicArcView.addEvent(new DecoEvent.Builder
                    (calculateBudgetPercentage(mPurchases.get(i).getCost()))
                    .setIndex(seriesIndex)
                    .setDelay(incrementingAnimationDelay)
                    .build());

            insetAmount += 45f;
            incrementingAnimationDelay += 20;

        }
    }

//    cost should change to total category cost
    public int calculateBudgetPercentage(double cost) {
        for (int i = 0; i < mPurchases.size(); i++) {

        }

        int budgetPercentage = (int) ((cost / purchasesTotal) * 100);

        Log.v("BudgetPercentage", budgetPercentage + "");

        return budgetPercentage;
    }

    public String assignCategoryColor(String category) {
        switch (category) {
            case "Food": {
                return colors[0];
            }
            case "Entertainment": {
                return colors[1];
            }
            case "Housing": {
                return colors[2];
            }
            case "Family": {
                return colors[3];
            }
            case "Clothing": {
                return colors[4];
            }
            case "Bribes": {
                return colors[5];
            }
            case "Medical": {
                return colors[6];
            }
            case "Education": {
                return colors[7];
            }
            case "Travel": {
                return colors[8];
            }
            case "Savings": {
                return colors[9];
            }
            default: {
                return "#000000";
            }
        }
    }
}

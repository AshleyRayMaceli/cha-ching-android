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
    private int[] categoryTotals = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
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
                    }
                }

                calculateCategoryTotals();
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

        for (int i = 0; i < categoryTotals.length; i++) {

            SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor
                    (assignCategoryColor(i)))
                    .setRange(0, 100, 0)
                    .setLineWidth(50f)
                    .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                    .setInset(new PointF(insetAmount, insetAmount))
                    .build();

            int seriesIndex = mDynamicArcView.addSeries(seriesItem);

            mDynamicArcView.addEvent(new DecoEvent.Builder
                    (calculateBudgetPercentage(categoryTotals[i]))
                    .setIndex(seriesIndex)
                    .setDelay(incrementingAnimationDelay)
                    .build());

            insetAmount += 60f;
            incrementingAnimationDelay += 20;

        }
    }

    public void calculateCategoryTotals() {
        for (int i = 0; i < mPurchases.size(); i++) {
            double purchaseCost = mPurchases.get(i).getCost();
            String purchaseCategory = mPurchases.get(i).getCategory();

            if (purchaseCategory.equals("Bribes")) {
                categoryTotals[0] += purchaseCost;
            } else if (purchaseCategory.equals("Clothing")) {
                categoryTotals[1] += purchaseCost;
            } else if (purchaseCategory.equals("Education")) {
                categoryTotals[2] += purchaseCost;
            } else if (purchaseCategory.equals("Entertainment")) {
                categoryTotals[3] += purchaseCost;
            } else if (purchaseCategory.equals("Family")) {
                categoryTotals[4] += purchaseCost;
            } else if (purchaseCategory.equals("Food")) {
                categoryTotals[5] += purchaseCost;
            } else if (purchaseCategory.equals("Housing")) {
                categoryTotals[6] += purchaseCost;
            } else if (purchaseCategory.equals("Medical")) {
                categoryTotals[7] += purchaseCost;
            } else if (purchaseCategory.equals("Savings")) {
                categoryTotals[8] += purchaseCost;
            } else if (purchaseCategory.equals("Travel")) {
                categoryTotals[9] += purchaseCost;
            } else {
                Log.e("Error", "Invalid Category");
            }
        }

    }

    public int calculateBudgetPercentage(int categoryCost) {

        double budgetPercentage = (((double) categoryCost / (double) purchasesTotal) * 100.00);

        return (int) budgetPercentage;
    }

    public String assignCategoryColor(int categoryIndex) {
        String[] colors = {"#cddc39", "#8bc34a", "#ff4081", "#00bcd4", "#673ab7", "#ff5253", "#ffc107", "#607d8b", "#ffeb3b", "#ff4081"};
        switch (categoryIndex) {
            case 0: {
                return colors[0];
            }
            case 1: {
                return colors[1];
            }
            case 2: {
                return colors[2];
            }
            case 3: {
                return colors[3];
            }
            case 4: {
                return colors[4];
            }
            case 5: {
                return colors[5];
            }
            case 6: {
                return colors[6];
            }
            case 7: {
                return colors[7];
            }
            case 8: {
                return colors[8];
            }
            case 9: {
                return colors[9];
            }
            default: {
                return "#000000";
            }
        }
    }
}

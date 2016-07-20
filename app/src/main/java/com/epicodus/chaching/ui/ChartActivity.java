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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mPurchaseReference;
    private ArrayList<Purchase> mPurchases = new ArrayList<>();

    @Bind(R.id.specificCategoriesSpinner) Spinner mSpecificCategoriesSpinner;
    @Bind(R.id.categorizedPurchasesButton) Button mCategorizedPurchasesButton;
    @Bind(R.id.dynamicArcView) DecoView mDynamicArcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ButterKnife.bind(this);

        mCategorizedPurchasesButton.setOnClickListener(this);

        drawChart();

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
                        Log.v("List of Purchases: ", purchase.getCost() + "");
                    }
                }
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
        mDynamicArcView.configureAngles(360, 0);

        mDynamicArcView.addSeries(new SeriesItem.Builder(Color.argb(0, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(true)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                .build());

        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.parseColor("#ff5252"))
                .setRange(0, 100, 0)
                .setLineWidth(50f)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                .build();

        SeriesItem seriesItem2 = new SeriesItem.Builder(Color.argb(255, 50, 120, 120))
                .setRange(0, 100, 0)
                .setLineWidth(50f)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                .setInset(new PointF(50f, 50f))
                .build();

        SeriesItem seriesItem3 = new SeriesItem.Builder(Color.parseColor("#689f38"))
                .setRange(0, 100, 0)
                .setLineWidth(50f)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                .setInset(new PointF(100f, 100f))
                .build();

        int series1Index = mDynamicArcView.addSeries(seriesItem1);
        int series2Index = mDynamicArcView.addSeries(seriesItem2);
        int series3Index = mDynamicArcView.addSeries(seriesItem3);

        mDynamicArcView.addEvent(new DecoEvent.Builder(60).setIndex(series1Index).build());
        mDynamicArcView.addEvent(new DecoEvent.Builder(50).setIndex(series2Index).build());
        mDynamicArcView.addEvent(new DecoEvent.Builder(70).setIndex(series3Index).build());
    }
}

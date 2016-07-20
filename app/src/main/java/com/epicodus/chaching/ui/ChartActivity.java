package com.epicodus.chaching.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.epicodus.chaching.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.specificCategoriesSpinner) Spinner mSpecificCategoriesSpinner;
    @Bind(R.id.categorizedPurchasesButton) Button mCategorizedPurchasesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ButterKnife.bind(this);

        mCategorizedPurchasesButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == mCategorizedPurchasesButton) {
            String categorySelected = mSpecificCategoriesSpinner.getSelectedItem().toString();
            Intent intent = new Intent(ChartActivity.this, PurchaseListActivity.class);
            intent.putExtra("categorySelected", categorySelected);
            startActivity(intent);
        }
    }
}

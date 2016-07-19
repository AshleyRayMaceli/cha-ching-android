package com.epicodus.chaching.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.epicodus.chaching.Constants;
import com.epicodus.chaching.R;
import com.epicodus.chaching.models.Category;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.purchaseNameEditText) EditText mPurchaseNameEditText;
    @Bind(R.id.costEditText) EditText mCostEditText;
    @Bind(R.id.categorySpinner) Spinner mCategorySpinner;
    @Bind(R.id.purchaseButton) ImageButton mPurchaseButton;
    @Bind(R.id.viewBudgetButton) Button mViewBudgetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPurchaseButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mPurchaseButton) {
            String purchaseName = mPurchaseNameEditText.getText().toString();
            double cost = Double.parseDouble(mCostEditText.getText().toString());

            //TODO: add conditional statement for preventing duplicate Categories
            Category category = new Category(mCategorySpinner.getSelectedItem().toString());

            DatabaseReference categoriesRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_CATEGORIES);

            DatabaseReference pushRef = categoriesRef.push();
            String pushId = pushRef.getKey();
            category.setPushId(pushId);
            pushRef.setValue(category);

            Toast.makeText(this, "Cha-CHING!", Toast.LENGTH_SHORT).show();
        }
    }
}

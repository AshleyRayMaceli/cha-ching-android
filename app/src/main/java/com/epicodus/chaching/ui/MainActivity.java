package com.epicodus.chaching.ui;

import android.content.Intent;
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
import com.epicodus.chaching.models.Purchase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
        mViewBudgetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mPurchaseButton) {

            // Grab the user's input to create a new Purchase object
            String purchaseName = mPurchaseNameEditText.getText().toString();
            double cost = Double.parseDouble(mCostEditText.getText().toString());
            String category = mCategorySpinner.getSelectedItem().toString();

            if (!purchaseName.equals("") && cost != 0 && !category.equals("Choose Category")) {

                Purchase purchase = new Purchase(purchaseName, cost, category);

                // Set up Database Reference to the New Purchase
                DatabaseReference purchasesRef = FirebaseDatabase
                        .getInstance()
                        .getReference(Constants.FIREBASE_CHILD_PURCHASES);

                // Save the purchase along with its PushID
                DatabaseReference pushRef = purchasesRef.push();
                String pushId = pushRef.getKey();
                purchase.setPushId(pushId);
                pushRef.setValue(purchase);

                // Notify success and clear form fields
                Toast.makeText(this, "Cha-CHING!", Toast.LENGTH_SHORT).show();
                mPurchaseNameEditText.setText("");
                mCostEditText.setText("0.00");
                mCategorySpinner.setSelection(0);

            } else {
                Toast.makeText(this, "Please ensure all fields are filled.", Toast.LENGTH_SHORT).show();
            }
        }
        if (v == mViewBudgetButton) {
            Intent intent = new Intent(MainActivity.this, PurchaseListActivity.class);
            startActivity(intent);
        }
    }
}

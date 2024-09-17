package com.example.myapplication.BMI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class BmiActivity extends AppCompatActivity {
    TextView bmiValue, bmiCategory, bmiTips;
    String category;
    String bmiValOutput;
    Button calculateAgainBtn;
    String[] bmiTipsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        bmiValue = findViewById(R.id.bmi_value);
        bmiCategory = findViewById(R.id.bmi_category);
        bmiTips = findViewById(R.id.bmi_tips);
        bmiTipsArray = getResources().getStringArray(R.array.tips_array);
        calculateAgainBtn = findViewById(R.id.calculate_again_btn);

        bmiValOutput = getIntent().getStringExtra("bmiVal");

        if (bmiValOutput != null && !bmiValOutput.isEmpty()) {
            try {
                double result = Double.parseDouble(bmiValOutput.trim());
                bmiValue.setText(bmiValOutput);
                findCategory(result);
                categoryTips(result);
            } catch (NumberFormatException e) {
                bmiValue.setText("Invalid BMI");
                bmiCategory.setText("Error");
                bmiTips.setText("Please enter a valid BMI value.");
            }
        } else {
            bmiValue.setText("No BMI data");
            bmiCategory.setText("No data");
            bmiTips.setText("No BMI data available.");
        }

        calculateAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void categoryTips(double result) {
        if (result < 15) {
            bmiTips.setText(bmiTipsArray[0]);
        } else if (result >= 15 && result <= 16) {
            bmiTips.setText(bmiTipsArray[0]);
        } else if (result >= 16 && result <= 18.5) {
            bmiTips.setText(bmiTipsArray[1]);
        } else if (result >= 18.5 && result <= 25) {
            bmiTips.setText(bmiTipsArray[2]);
        } else if (result >= 25 && result <= 30) {
            bmiTips.setText(bmiTipsArray[3]);
        } else {
            bmiTips.setText(bmiTipsArray[4]);
        }
    }

    private void findCategory(double result) {
        if (result < 15) {
            category = "Very Severely Underweight";
        } else if (result >= 15 && result <= 16) {
            category = "Severely Underweight";
        } else if (result >= 16 && result <= 18.5) {
            category = "Underweight";
        } else if (result >= 18.5 && result <= 25) {
            category = "Normal (Healthy weight)";
        } else if (result >= 25 && result <= 30) {
            category = "Overweight";
        } else if (result >= 30 && result <= 35) {
            category = "Moderately Obese";
        } else if (result > 35) {
            category = "Severely Obese";
        } else {
            category = "Invalid BMI";
        }
        bmiCategory.setText(category);
    }
}

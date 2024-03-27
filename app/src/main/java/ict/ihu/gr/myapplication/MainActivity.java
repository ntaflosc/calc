package ict.ihu.gr.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private double memory = 0.0; // Stores current memory value
    private boolean memorySet = false; // Flag to indicate if memory has a value

    private EditText expressionInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        expressionInput = findViewById(R.id.et);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnCalc = findViewById(R.id.btnCalc);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onCalculateClick();

            }
        });

        Button btnMC = findViewById(R.id.btnMC);

        btnMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buttonId = view.getId();
                onMemoryClick(buttonId);
            }
        });


        Button btnMS = findViewById(R.id.btnMS);

        btnMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buttonId = view.getId();
                onMemoryClick(buttonId);
            }
        });
        Button btnMR = findViewById(R.id.btnMR);

        btnMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buttonId = view.getId();
                onMemoryClick(buttonId);
            }
        });
        Button btnMAdd = findViewById(R.id.btnMAdd);

        btnMAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buttonId = view.getId();
                onMemoryClick(buttonId);
            }
        });

// Similar click listeners for other memory buttons (btnMR, btnMS, btnMAdd)

    }

    private void onCalculateClick() {
        //EditText etNumber1 = findViewById(R.id.expressionInput);
//        double number1;
//        try {
//            number1 = Double.parseDouble(etNumber1.getText().toString());
//        } catch (Exception e) {
//            number1 = 0.0;
//        }
//        EditText etNumber2 = findViewById(R.id.etNumber2);
//        double number2;
//        try {
//            number2 = Double.parseDouble(etNumber2.getText().toString());
//        } catch (Exception e) {
//            number2 = 0.0;
//        }
        //RadioGroup rgOperations = findViewById(R.id.rgOperations);

       // TextView tvMem = findViewById(R.id.tvMem);
        //int selectedId = rgOperations.getCheckedRadioButtonId();
        TextView tvResult = findViewById(R.id.tvResult);
        String expression = expressionInput.getText().toString().trim();
        Log.d("Calculator", "User Input: " + expression);
        //ShuntingYardCalculator calculator = new ShuntingYardCalculator();


        // Error handling for empty input
        if (expression.isEmpty()) {
            Toast.makeText(this, "Please enter an expression", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] operands = expression.split("\\*");
        if (operands.length != 2) {
            // Handle invalid expression with multiple or missing asterisks
            Toast.makeText(this, "Invalid expression format!", Toast.LENGTH_SHORT).show();
            return;
        }
        //String processedExpression = operands[0].trim() + "*" + operands[1].trim();
        String operand1 = operands[0].trim();
        String operand2 = operands[1].trim();
        Log.d("Calculator", "Split Operands: " + operand1 + ", " + operand2);

        String processedExpression = operand1 + "*" + operand2;
        Log.d("Calculator", "Processed Expression: " + processedExpression);
        //Toast.makeText(MainActivity.this,"Result: "+processedExpression +"Opernad1:"+ operand1 +"Operand2:"+ operand2, Toast.LENGTH_SHORT ).show();
        ShuntingYardCalculator calculator = new ShuntingYardCalculator();
        double result = 0;
        try {
            result = calculator.evaluate(processedExpression);
            Toast.makeText(MainActivity.this, "Result: " + result, Toast.LENGTH_SHORT).show();
        } catch (InvalidNumberFormatException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Generic number format error!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Handle other potential exceptions (optional)
            Toast.makeText(MainActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
        }



// Display the result
        tvResult.setText(String.valueOf(result));

    }


    private void onMemoryClick(int buttonId) {
        EditText etNumber1 = findViewById(R.id.et);
        TextView tvResult = findViewById(R.id.tvResult);
        if (buttonId == R.id.btnMC) {
            memory = 0.0;
            memorySet = false;
            Toast.makeText(getApplicationContext(), "Memory Cleared", Toast.LENGTH_SHORT).show();
        } else if (buttonId == R.id.btnMR) {
            if (memorySet) {
                etNumber1.setText(String.valueOf(memory));
            } else {
                Toast.makeText(getApplicationContext(), "Memory Empty", Toast.LENGTH_SHORT).show();
            }
        } else if (buttonId == R.id.btnMS) {
            try {
                double currentNumber = Double.parseDouble(tvResult.getText().toString());
                memory = currentNumber;
                memorySet = true;
                Toast.makeText(getApplicationContext(), "Value Stored in Memory", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
            }
        } else if (buttonId == R.id.btnMAdd) {
            try {
                double currentNumber = Double.parseDouble(tvResult.getText().toString());
                memory += currentNumber;
                memorySet = true;
                Toast.makeText(getApplicationContext(), "Value Added to Memory", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
            }
        }
    }

}



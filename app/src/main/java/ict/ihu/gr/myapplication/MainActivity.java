package ict.ihu.gr.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
    }
    private void onCalculateClick() {
        EditText etNumber1 = findViewById(R.id.etNumber1);
        double number1;
        try{
            number1 = Double.parseDouble(etNumber1.getText().toString());
        }catch (Exception e){
            number1 = 0.0;
        }
        EditText etNumber2 = findViewById(R.id.etNumber2);
        double number2;
        try{
            number2 = Double.parseDouble(etNumber2.getText().toString());
        }catch (Exception e){
            number2 = 0.0;
        }
        RadioGroup rgOperations = findViewById(R.id.rgOperations);
        TextView tvResult = findViewById(R.id.tvResult);
        int selectedId = rgOperations.getCheckedRadioButtonId();

        if (selectedId == R.id.rbPlus) {
            tvResult.setText(String.valueOf(number1 + number2));
        } else if (selectedId == R.id.rbMinus) {
            tvResult.setText(String.valueOf(number1 - number2));
        } else if (selectedId == R.id.rbMulti) {
            tvResult.setText(String.valueOf(number1 * number2));
        } else if (selectedId == R.id.rbDiv) {
            if (number2 != 0.0)
                tvResult.setText(String.valueOf(number1 / number2));
            else
                tvResult.setText("Can't Divide With 0");
        } else {
            tvResult.setText("Operation Not Selected");
        }

    }

}
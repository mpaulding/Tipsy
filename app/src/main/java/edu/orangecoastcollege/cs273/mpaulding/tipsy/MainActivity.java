package edu.orangecoastcollege.cs273.mpaulding.tipsy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
    private static final NumberFormat percent = NumberFormat.getPercentInstance(Locale.US);

    private Bill currentBill = new Bill();
    private EditText amountEditText;
    private TextView amountTextView;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;
    private SeekBar percentSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to each of the views used in this app
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);

        // Add listener events to each view that receives user input
        // EditText relies on TextWatcher
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // SeekBar relies on seekBarListener (OnSeekBarChangeListener)
        percentSeekBar.setOnSeekBarChangeListener(percentSeekBarListener);

        currentBill.setTipPercent(0.15);
        updateViews();
    }

    private void updateViews()
    {
        amountTextView.setText(currency.format(currentBill.getAmount()));
        percentTextView.setText(percent.format(currentBill.getTipPercent()));
        tipTextView.setText(currency.format(currentBill.getTipAmount()));
        totalTextView.setText(currency.format(currentBill.getTotalAmount()));
    }

    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                double amount = Double.parseDouble(s.toString()) / 100.0;
                currentBill.setAmount(amount);
            }
            catch (NumberFormatException e)
            {
                amountTextView.setText("");
            }
            updateViews();
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Do nothing
        }
    };

    private final SeekBar.OnSeekBarChangeListener percentSeekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            currentBill.setTipPercent(progress / 100.0);
            updateViews();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

}

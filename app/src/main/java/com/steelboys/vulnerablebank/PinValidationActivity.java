package com.steelboys.vulnerablebank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.steelboys.vulnerablebank.utils.PinValidator;

public class PinValidationActivity extends AppCompatActivity {

	EditText editText_pin;
	Button button_validatePin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_pin_validation);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		Toast.makeText(PinValidationActivity.this, "Insert the PIN you received on email", Toast.LENGTH_SHORT).show();

		Intent intent = getIntent();
		String IBAN = intent.getStringExtra("IBAN");
		Integer amount = intent.getIntExtra("amount", 0);

		editText_pin = findViewById(R.id.app_pin_validation_editText_pin);
		button_validatePin = findViewById(R.id.app_pin_validation_button_validate);

		button_validatePin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String editText_pin_value = editText_pin.getText().toString();

					if (editText_pin_value.length() != 6) {
						Toast.makeText(PinValidationActivity.this, "Invalid PIN. PIN should have 6 characters.", Toast.LENGTH_SHORT).show();
						return;
					}

					Integer pin = Integer.valueOf(editText_pin.getText().toString());
					if (PinValidator.validatePin(pin)) {
						Toast.makeText(PinValidationActivity.this, "Congratulations. You have successfully completed this challenge!", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(PinValidationActivity.this, "Wrong PIN. Please enter the correct PIN.", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					Toast.makeText(PinValidationActivity.this, "An error occured. Please try again...", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
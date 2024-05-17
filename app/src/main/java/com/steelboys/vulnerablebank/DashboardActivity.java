package com.steelboys.vulnerablebank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.steelboys.vulnerablebank.database.DatabaseHelper;
import com.steelboys.vulnerablebank.domain.User;
import com.steelboys.vulnerablebank.utils.Constants;

public class DashboardActivity extends AppCompatActivity {

	private TextView textView_name;
	private TextView textView_balance;

	private Button button_transfer;
	private EditText editText_IBAN;
	private EditText editText_amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_dashboard);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		textView_name = findViewById(R.id.app_dashboard_activity_card_name);
		textView_balance = findViewById(R.id.app_dashboard_activity_card_balance);

		button_transfer = findViewById(R.id.app_dashboard_activity_button_transfer);
		editText_IBAN = findViewById(R.id.app_dashboard_activity_editText_IBAN);
		editText_amount = findViewById(R.id.app_dashboard_activity_editText_amount);

		Intent intent = getIntent();
		String username = intent.getStringExtra("username");

		//we should send a fetch data info to the server to get info about the user.

		String fullName = null;
		Integer balance = null;

		textView_name.setText(null != fullName ? fullName : "Unknown");
		textView_balance.setText("$" + (null != balance ? balance : 0));

		button_transfer.setOnClickListener(v -> {
			try {
				String IBAN = editText_IBAN.getText().toString();
				int amount = Integer.parseInt(editText_amount.getText().toString());

				Log.d(Constants.TAG_INFO, "IBAN: " + IBAN);
				Log.d(Constants.TAG_INFO, "amount: " + amount);
			} catch (Exception e) {
				Toast.makeText(DashboardActivity.this, "Unexpected error occurred.", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
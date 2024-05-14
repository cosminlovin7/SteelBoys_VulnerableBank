package com.steelboys.vulnerablebank;

import android.content.Intent;
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

import com.steelboys.vulnerablebank.database.DatabaseHelper;
import com.steelboys.vulnerablebank.domain.User;
import com.steelboys.vulnerablebank.utils.Constants;

public class DashboardActivity extends AppCompatActivity {

	private TextView textView_name;
	private TextView textView_balance;

	private Button button_transfer;
	private EditText editText_IBAN;
	private EditText editText_amount;

	private DatabaseHelper databaseHelper;

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

		databaseHelper = new DatabaseHelper(this);

		Intent intent = getIntent();
		String username = intent.getStringExtra("username");

		User user = databaseHelper.getUserByUsername(username);

		String fullName = null;
		Integer balance = null;
		if (null != user) {
			Log.d(Constants.TAG_INFO, user.getFirst_name() + ' ' + user.getLast_name());

			fullName = user.getFirst_name() + " " + user.getLast_name();
			balance = user.getBalance();
		} else {
			fullName = "Unknown";
			balance = 0;
		}

		textView_name.setText(fullName);
		textView_balance.setText("$" + balance);

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
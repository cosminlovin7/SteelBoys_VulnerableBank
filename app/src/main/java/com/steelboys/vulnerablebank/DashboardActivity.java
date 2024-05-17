package com.steelboys.vulnerablebank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.steelboys.vulnerablebank.dto.AccountInfoDto;
import com.steelboys.vulnerablebank.requests.ResponseAccountInfoObj;
import com.steelboys.vulnerablebank.utils.Constants;

import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

	private TextView textView_name;
	private TextView textView_balance;

	private Button button_transfer;
	private EditText editText_IBAN;
	private EditText editText_amount;
	private ProgressBar progressBar;

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
		progressBar = findViewById(R.id.app_dashboard_activity_progress_bar_login);

		Intent intent = getIntent();
		String username = intent.getStringExtra("username");

		//we should send a fetch data info to the server to get info about the user.
		RequestQueue queue = Volley.newRequestQueue(this);
		String url = "http://10.0.2.2:9191/user/" + username;

		progressBar.setVisibility(View.VISIBLE);
		JsonObjectRequest accountInfoRequest = new JsonObjectRequest(
			Request.Method.GET,
			url,
			null,
			new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject responseJsonObject) {
					progressBar.setVisibility(View.GONE);
					ResponseAccountInfoObj responseAccountInfoObj = null;

					if (null != responseJsonObject) {
						String response = responseJsonObject.toString();
						Gson gson = new Gson();

						responseAccountInfoObj = gson.fromJson(response, ResponseAccountInfoObj.class);
					}
					handleSuccessfulAccountInfoRequest(responseAccountInfoObj);
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError volleyError) {
					progressBar.setVisibility(View.GONE);
					Log.d(Constants.TAG_INFO, "Error occurred");

					ResponseAccountInfoObj responseAccountInfoObj = null;

					if (null != volleyError) {
						if (null != volleyError.networkResponse) {
							if (null != volleyError.networkResponse.data) {
								String errorResponse = new String(volleyError.networkResponse.data);
								Log.d(Constants.TAG_ERROR, errorResponse);
								Gson gson = new Gson();

								responseAccountInfoObj = gson.fromJson(errorResponse, ResponseAccountInfoObj.class);
							}
						}
					}
					handleErrorAccountInfo(responseAccountInfoObj);
				}
			}
		);

		queue.add(accountInfoRequest);

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

	private void handleSuccessfulAccountInfoRequest(ResponseAccountInfoObj responseAccountInfoObj) {
		if (null == responseAccountInfoObj) {
			Log.d(Constants.TAG_ERROR, "Account info response is null");
		} else {
			String fullName = null;
			Integer balance = null;

			AccountInfoDto accountInfoDto = responseAccountInfoObj.getAccountInfo();
			if (
				null != accountInfoDto.getFirstName()
				&& null != accountInfoDto.getLastName()
			) {
				fullName = accountInfoDto.getFirstName() + " " + accountInfoDto.getLastName();
			} else if (
				null != accountInfoDto.getFirstName()
			) {
				fullName = accountInfoDto.getFirstName();
			} else if (
				null != accountInfoDto.getLastName()
			) {
				fullName = accountInfoDto.getLastName();
			} else {
				fullName = "Unknown";
			}

			if (null != accountInfoDto.getBalance()) {
				balance = accountInfoDto.getBalance();
			} else {
				balance = 0;
			}

			String balanceInfo = "$" + balance;
			textView_name.setText(fullName);
			textView_balance.setText(balanceInfo);
		}
	}

	private void handleErrorAccountInfo(ResponseAccountInfoObj responseAccountInfoObj) {
		StringBuilder message = new StringBuilder();
		if (null != responseAccountInfoObj) {
			int statusCode = responseAccountInfoObj.getStatus();
			switch (statusCode) {
				case 400:
					message.append("Bad request").append("-");
					break;
				case 404:
					message.append("Not found").append("-");
					break;
				case 403:
					message.append("Unauthorized").append("-");
					break;
				default:
					message.append(statusCode).append("-");
					break;
			}
			message.append(responseAccountInfoObj.getMessage());
		} else {
			message.append("Unknown error occurred while trying to login");
		}

		Toast.makeText(DashboardActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
	}
}
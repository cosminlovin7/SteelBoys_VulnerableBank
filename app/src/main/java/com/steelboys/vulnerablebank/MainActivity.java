package com.steelboys.vulnerablebank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.steelboys.vulnerablebank.database.DatabaseHelper;
import com.steelboys.vulnerablebank.requests.RequestLoginObj;
import com.steelboys.vulnerablebank.requests.ResponseLoginObj;
import com.steelboys.vulnerablebank.utils.Constants;
import com.steelboys.vulnerablebank.utils.RootDetectorUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText editText_username;
    private EditText editText_password;
    private Button button_login;
    private Button button_register;
    private ProgressBar progressBar;
    private DatabaseHelper databaseHelper;
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

        if (true == RootDetectorUtils.isDeviceRooted()) {
            Toast.makeText(this, "Device is rooted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Device is not rooted!", Toast.LENGTH_SHORT).show();
        }

        try {
            databaseHelper = new DatabaseHelper(this);

            if (null != databaseHelper.getUserByUsername("admin")) {
                //noop
                Log.d(Constants.TAG_INFO, "Admin user exists!");
            } else {
                databaseHelper.addUser("admin", "admin");

                Log.d(Constants.TAG_INFO, "Admin user added successfully!");
            }
        } catch (Exception e) {
            Log.d(Constants.TAG_DATABASE_ERROR, e.getMessage());
            Log.d(Constants.TAG_DATABASE_ERROR, "Error while inserting default user");
        }
        editText_username = findViewById(R.id.app_first_page_editText_username);
        editText_password = findViewById(R.id.app_first_page_editText_password);
        button_login = findViewById(R.id.app_first_page_button_login);
        button_register = findViewById(R.id.app_first_page_button_register);
        progressBar = findViewById(R.id.app_first_page_progress_bar_login);

        RequestQueue queue = Volley.newRequestQueue(this);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText_username.getText().toString();
                String password = editText_password.getText().toString();

                Log.d(Constants.TAG_CREDENTIALS, "username: " + username);
                Log.d(Constants.TAG_CREDENTIALS, "password: " + password);

                //special IP address: 10.0.2.2, alias to the host loopback interface. This allows the emulator
                //to access services running on the host machine.
                String url = "http://10.0.2.2:9191/login";

                // Create your request object
                RequestLoginObj requestLoginObj = new RequestLoginObj(username, password);

                // Convert the request object to a JSON object
                Gson gson = new Gson();
                String jsonString = gson.toJson(requestLoginObj);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

				progressBar.setVisibility(View.VISIBLE);
                JsonObjectRequest loginRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject responseJsonObject) {
                            progressBar.setVisibility(View.INVISIBLE);
                            ResponseLoginObj responseLoginObj = null;

                            if (null != responseJsonObject) {
                                String response = responseJsonObject.toString();
                                Gson gson = new Gson();

                                responseLoginObj = gson.fromJson(response, ResponseLoginObj.class);
                            }
                            handleSuccessfulLogin(username, password, responseLoginObj);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.d(Constants.TAG_INFO, "Error occurred");

                            ResponseLoginObj responseLoginObj = null;

                            if (null != volleyError) {
                                if (null != volleyError.networkResponse) {
                                    if (null != volleyError.networkResponse.data) {
                                        String errorResponse = new String(volleyError.networkResponse.data);
                                        Log.d(Constants.TAG_ERROR, errorResponse);
                                        Gson gson = new Gson();

                                        responseLoginObj = gson.fromJson(errorResponse, ResponseLoginObj.class);
                                    }
                                }
                            }
                            handleErrorLogin(responseLoginObj);
                        }
                    }
                );

                loginRequest.setRetryPolicy(new DefaultRetryPolicy(
                    20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));

                queue.add(loginRequest);
            }
        });
    }

    private void handleSuccessfulLogin(
        final String username,
        final String password,
        final ResponseLoginObj responseLoginObj
    ) {
        if (null != databaseHelper.getUserByUsername(username)) {
            //noop
            Log.d(Constants.TAG_INFO, username + " is already cached!");
        } else {
            databaseHelper.addUser(username, password);

            Log.d(Constants.TAG_INFO, username + " cached successfully!");
        }

        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void handleErrorLogin(ResponseLoginObj responseLoginObj) {
        StringBuilder message = new StringBuilder();
        if (null != responseLoginObj) {
            int statusCode = responseLoginObj.getStatus();
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
            message.append(responseLoginObj.getMessage());
        } else {
            message.append("Unknown error occurred while trying to login");
        }

        Toast.makeText(MainActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
    }
}
package com.steelboys.vulnerablebank;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.steelboys.vulnerablebank.database.DatabaseHelper;
import com.steelboys.vulnerablebank.domain.User;
import com.steelboys.vulnerablebank.utils.Constants;
import com.steelboys.vulnerablebank.utils.RootDetectorUtils;

public class MainActivity extends AppCompatActivity {

    private EditText editText_username;
    private EditText editText_password;
    private Button button_login;
    private Button button_register;
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
                databaseHelper.addUser(
                    "admin",
                    "admin",
                    "admin",
                    "root",
                    "0000",
                    999999);

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

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText_username.getText().toString();
                String password = editText_password.getText().toString();

                Log.d(Constants.TAG_CREDENTIALS, "username: " + username);
                Log.d(Constants.TAG_CREDENTIALS, "password: " + password);

                User userFetched = databaseHelper.getUserByUsername(username);

                if (null != userFetched) {
                    if (username.contentEquals(userFetched.getUsername())) {
                        if (password.contentEquals(userFetched.getPassword())) {
                            Toast.makeText(MainActivity.this, "Correct username & password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(Constants.TAG_ERROR, "Bad logic.");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
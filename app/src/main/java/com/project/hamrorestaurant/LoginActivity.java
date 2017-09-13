package com.project.hamrorestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button logInButton, registerButton;
    private EditText email, password;
    private ImageButton facebook, google;
    private TextView signInWith, noAccount, forgotPassword;
    private String userName, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        logInButton = (Button) findViewById(R.id.logInButton);


        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = email.getText().toString().trim();
                pass = password.getText().toString().trim();
                if (userName.isEmpty() || pass.isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                } else if (Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
                    if (userName.equals("a@b.com") && pass.equals("a")) {

                        Toast.makeText(LoginActivity.this, "welcome", Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(LoginActivity.this, RestaurantActivity.class);
                        home.putExtra("username", userName);
                        startActivity(home);

                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    email.setError("Invalid Email Address");
                }
            }
        });


//                Toast.makeText(LoginActivity.this, "Enter Valid Password", Toast.LENGTH_SHORT).show();


    }
}

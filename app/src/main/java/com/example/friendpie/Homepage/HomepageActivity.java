package com.example.friendpie.Homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.friendpie.R;
import com.example.friendpie.SignIn.SignInActivity;
import com.example.friendpie.SignUp.A_SignUpActivity;
import com.example.friendpie.UserHomeScreen.UserHomeScreen_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomepageActivity extends AppCompatActivity {

    Button signup_button, login_button;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser = fAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        if(fUser!=null){
            startActivity(new Intent(HomepageActivity.this, UserHomeScreen_Activity.class));
            finish();
        }

        signup_button = findViewById(R.id.signup_button);
        login_button = findViewById(R.id.login_button);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepageActivity.this, A_SignUpActivity.class));
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomepageActivity.this, SignInActivity.class));
            }
        });

    }

}
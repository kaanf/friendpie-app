package com.example.friendpie.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendpie.R;
import com.example.friendpie.SignIn.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class A_SignUpActivity extends AppCompatActivity {

    private  Button signup_button;
    private ImageView back_arrow;
    private Toast toastError;
    private EditText email_signup, password_signup, password_dogrula_signup;
    private TextView signup_girisyap_text, toastText;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        assignItems();
        toastMessage();

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signup_girisyap_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(A_SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateEmail() || !validatePassword() || !validateSecondPassword() || !validateSamePassword()) { return; }
                else{
                    final String emailUser = email_signup.getText().toString();
                    final String passwordUser = password_signup.getText().toString();

                    firebaseAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(A_SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(A_SignUpActivity.this, B_PhoneActivity.class);
                                intent.putExtra("UserEmail", emailUser);
                                intent.putExtra("UserPassword", passwordUser);
                                startActivity(intent);
                            }
                            else{
                                toastText.setText("Kullanıcı oluşturulamadı");
                                toastError.show();
                                return;
                            }
                        }
                    });


                }

            }
        });

    }

    private void assignItems() {

        signup_button = findViewById(R.id.signup_button);
        back_arrow = findViewById(R.id.back_arrow);
        email_signup = findViewById(R.id.email_signup);
        password_signup = findViewById(R.id.password_signup);
        password_dogrula_signup = findViewById(R.id.password_dogrula_signup);
        signup_girisyap_text = findViewById(R.id.signup_girisyap_text);


    }

    private boolean validateEmail() {

        String val = email_signup.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            toastText.setText(R.string.email_error_toast);
            toastError.show();
            return false;
        }
        else if(!val.matches(checkEmail)) {
            toastText.setText(R.string.email_error_toast);
            toastError.show();
            return false;
        }
        else{
            email_signup.setError(null);
            return true;
        }

    }

    private boolean validatePassword() {

        String val = password_signup.getText().toString().trim();

        if(val.isEmpty()){
            toastText.setText(R.string.password_error_toast);
            toastError.show();
            return false;
        }else if(val.length()<6){
            toastText.setText(R.string.password_error_toast_2);
            toastError.show();
            return false;
        }
        else{
            password_signup.setError(null);
            return true;
        }

    }

    private boolean validateSecondPassword() {

        String val = password_dogrula_signup.getText().toString().trim();

        if(val.isEmpty()){
            toastText.setText(R.string.password_dogrula_error_toast);
            toastError.show();
            return false;
        }else if(val.length()<6){
            toastText.setText(R.string.password_dogrula_error_toast);
            toastError.show();
            return false;
        }
        else{
            password_dogrula_signup.setError(null);
            return true;
        }

    }

    private boolean validateSamePassword() {

        if(password_signup.getText().toString().equals(password_dogrula_signup.getText().toString())){
            return true;
        }
        else{
            toastText.setText(R.string.validate_password_error_toast);
            toastError.show();
            return false;
        }

    }

    private void toastMessage() {

        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.error_toast));
        toastText = layout.findViewById(R.id.error_toast_text);
        toastError = new Toast(getApplicationContext());
        toastError.setGravity(Gravity.TOP, 0, 40);
        toastError.setDuration(Toast.LENGTH_SHORT);
        toastError.setView(layout);

    }

}



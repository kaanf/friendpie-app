package com.example.friendpie.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendpie.OtherClasses.Friendpie_ProgressBar;
import com.example.friendpie.R;
import com.hbb20.CountryCodePicker;
public class B_PhoneActivity extends AppCompatActivity {

    Button onay_kodu_gonder;
    ImageView back_arrow_2;
    CountryCodePicker country_code;
    EditText phone_signup;
    Toast toastError;
    TextView toastText;
    private InputMethodManager input;
    String UserEmail, UserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        assignItems();
        toastMessage();

        back_arrow_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        onay_kodu_gonder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!validatePhone()) {
                    return;
                }
                new getPhoneCode().execute();
            }
        });

    }

    private void assignItems() {

        input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        back_arrow_2 = findViewById(R.id.back_arrow_2);
        onay_kodu_gonder = findViewById(R.id.onay_kodu_gonder);
        country_code = findViewById(R.id.country_code);
        phone_signup = findViewById(R.id.phone_signup);

        //User values from the previous activity
        UserEmail = getIntent().getStringExtra("UserEmail");
        UserPassword = getIntent().getStringExtra("UserPassword");

    }

    private boolean validatePhone() {
        String val = phone_signup.getText().toString();
        if(val.isEmpty()) {
            toastText.setText(getString(R.string.phone_error_isempty));
            toastError.show();
            return false;
        }else if(val.length()<10){
            toastText.setText(getString(R.string.phone_error_isempty));
            toastError.show();
            return false;
        }else{
            phone_signup.setError(null);
            return true;
        }
    }

    private class getPhoneCode extends AsyncTask<Void, Void, Void> {

        String phone_val = phone_signup.getText().toString();
        String total_phone = country_code.getSelectedCountryCodeWithPlus() + phone_val;
        Friendpie_ProgressBar friendpieProgressBar =new Friendpie_ProgressBar(B_PhoneActivity.this, "Onay kodu gönderiliyor.");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            input.hideSoftInputFromWindow(phone_signup.getWindowToken(), 0);

            friendpieProgressBar.show();
            friendpieProgressBar.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(B_PhoneActivity.this, C_OTPActivity.class);
            intent.putExtra("UserPhone", total_phone);
            intent.putExtra("UserEmail", UserEmail);
            intent.putExtra("UserPassword", UserPassword);
            startActivity(intent);

            friendpieProgressBar.dismiss();
        }

    }

    private void toastMessage() {

        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.error_toast));
        toastText = (TextView) layout.findViewById(R.id.error_toast_text);
        toastError = new Toast(getApplicationContext());
        toastError.setGravity(Gravity.TOP, 0, 40);
        toastError.setDuration(Toast.LENGTH_SHORT);
        toastError.setView(layout);

    }


}
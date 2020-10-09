package com.example.friendpie.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendpie.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class D_PersonalInfoActivity extends AppCompatActivity {

    private Button personalActivity_button;
    private EditText ad_soyad_signup, date_signup;
    private RadioGroup gender_signup;
    private RadioButton selected_gender;
    private Toast toastError;
    private TextView toastText;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener setListener;
    private String UserEmail, UserPhone, UserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        assignItems();
        toastMessage();

        personalActivity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateName() || !validateDate() || !validateGender()) {
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, datePickerDialog.getDatePicker().getYear());
                calendar.set(Calendar.MONTH, datePickerDialog.getDatePicker().getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, datePickerDialog.getDatePicker().getDayOfMonth());
                String UserBirthdate = new SimpleDateFormat("dd/MM/YYYY").format(calendar.getTime());
                if(calculateAge(calendar.getTimeInMillis())<18){
                        toastText.setText(R.string.yas_error_toast);
                        toastError.show();
                        return;
                } else{
                        selected_gender = findViewById(gender_signup.getCheckedRadioButtonId());
                        String UserGender = selected_gender.getText().toString();
                        String UserName = ad_soyad_signup.getText().toString();
                        Intent intent = new Intent(D_PersonalInfoActivity.this, E_HobbyActivity.class);
                        intent.putExtra("UserPhone", UserPhone);
                        intent.putExtra("UserEmail", UserEmail);
                        intent.putExtra("UserPassword", UserPassword);
                        intent.putExtra("UserBirthdate", UserBirthdate);
                        intent.putExtra("UserGender", UserGender);
                        intent.putExtra("UserName", UserName);
                        startActivity(intent);
                }
            }
        });

        date_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Date assignments
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(D_PersonalInfoActivity.this, R.style.DatePickerCustomFriendpie,
                        setListener, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                String date = new SimpleDateFormat("dd MMMM YYYY").format(calendar.getTime());
                date_signup.setText(date);
            }
        };

    }

    int calculateAge (long date) {

        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;

    }

    private void assignItems() {

        personalActivity_button = findViewById(R.id.personalActivity_button);
        ad_soyad_signup = findViewById(R.id.ad_soyad_signup);
        date_signup = findViewById(R.id.date_signup);
        gender_signup = findViewById(R.id.gender_signup);

        // Passed datas of user
        UserEmail = getIntent().getStringExtra("UserEmail");
        UserPhone = getIntent().getStringExtra("UserPhone");
        UserPassword = getIntent().getStringExtra("UserPassword");

    }

    private boolean validateName() {
        String val = ad_soyad_signup.getText().toString();
        if(val.isEmpty()||val.length()<3) {
            toastText.setText(R.string.ad_soyad_error_toast);
            toastError.show();
            ad_soyad_signup.requestFocus();
            return false;
        }else{
            ad_soyad_signup.setError(null);
            return true;
        }
    }

    private boolean validateDate() {
        String val = date_signup.getText().toString();
        if(val.isEmpty()){
            toastText.setText(R.string.date_error_toast);
            toastError.show();
            return false;
        }
        else{
            date_signup.setError(null);
            return true;
        }
    }

    private boolean validateGender() {
        if(gender_signup.getCheckedRadioButtonId()!=-1){
            return true;
        }else{
            toastText.setText(R.string.gender_error_toast);
            toastError.show();
            return false;
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
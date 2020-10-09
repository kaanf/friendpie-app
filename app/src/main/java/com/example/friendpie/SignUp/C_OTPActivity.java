package com.example.friendpie.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.chaos.view.PinView;
import com.example.friendpie.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class C_OTPActivity extends AppCompatActivity {

    private Button otp_button;
    private PinView register_otpCode;
    private TextView tekrar_gonder_text, toastText;
    private ImageView back_arrow_3;
    private Toast toastError;
    LinearLayout error_toast_bg;
    private String UserPhone, UserEmail, UserPassword, codeBySystem;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        assignItems();
        toastMessage();
        sendVerificationCodeToUser(UserPhone);

        back_arrow_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tekrar_gonder_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCodeToUser(UserPhone);
                error_toast_bg.setBackgroundResource(R.drawable.success_toast_background);
                toastText.setText("Kod gönderildi");
                toastError.show();
            }
        });

        otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = register_otpCode.getText().toString().trim();
                if(code.isEmpty() || code.length() < 6)
                {
                    register_otpCode.requestFocus();
                    toastText.setText("6 haneli onay kodunu giriniz.");
                    toastError.show();
                    return;
                }
                else{ verifyCode(code);
                }
            }
        });

    }

    private void assignItems() {

        otp_button = findViewById(R.id.otp_button);
        register_otpCode = findViewById(R.id.register_otpCode);
        tekrar_gonder_text = findViewById(R.id.tekrar_gonder_text);
        back_arrow_3 = findViewById(R.id.back_arrow_3);

        //User values from the previous activity

        UserEmail = getIntent().getStringExtra("UserEmail");
        UserPhone = getIntent().getStringExtra("UserPhone");
        UserPassword = getIntent().getStringExtra("UserPassword");

    }

    private void sendVerificationCodeToUser(String phoneNo) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
                        linkCredential(credential);
                    }

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    toastText.setText(R.string.otp_error_toast);
                    toastError.show();
                }
            };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        linkCredential(credential);

    }

    public void linkCredential(AuthCredential credential) {
        firebaseAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("success", "linkWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(C_OTPActivity.this, "Merged", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(C_OTPActivity.this, D_PersonalInfoActivity.class);
                            intent.putExtra("UserPhone", UserPhone);
                            intent.putExtra("UserEmail", UserEmail);
                            intent.putExtra("UserPassword", UserPassword);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            Log.w("failed", "linkWithCredential:failure", task.getException());
                            Toast.makeText(C_OTPActivity.this, "Failed to merge" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void toastMessage() {

        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.error_toast));
        error_toast_bg = layout.findViewById(R.id.error_toast);
        toastText = (TextView) layout.findViewById(R.id.error_toast_text);
        toastError = new Toast(getApplicationContext());
        toastError.setGravity(Gravity.TOP, 0, 40);
        toastError.setDuration(Toast.LENGTH_SHORT);
        toastError.setView(layout);

    }

}
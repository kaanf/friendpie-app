package com.example.friendpie.SignIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendpie.OtherClasses.SessionManager;
import com.example.friendpie.R;
import com.example.friendpie.UserHomeScreen.UserHomeScreen_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    private ImageView back_arrow_login;
    private EditText email_login, password_login;
    private Toast toastError;
    private TextView toastText;
    private Button login_button;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        assignItems();
        toastMessage();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateEmail() || !validatePassword()){
                    return;
                }else if(!checkInternet()){
                    return;
                }
                else{
                    letTheUserLoggedIn();
                }
            }
        });

        back_arrow_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void assignItems() {
        back_arrow_login = findViewById(R.id.back_arrow_login_page);
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        login_button = findViewById(R.id.login_button);
    }

    private boolean validateEmail() {
        String val = email_login.getText().toString().trim();
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
            email_login.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {

        String val = password_login.getText().toString().trim();

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
            password_login.setError(null);
            return true;
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

    private void letTheUserLoggedIn() {

        final String userEmail = email_login.getText().toString();
        final String userPassword = password_login.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Query checkUser = firestore.collection("Users").whereEqualTo("userEmail", userEmail);
                    checkUser.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot ds: Objects.requireNonNull(task.getResult())){
                                    String emailS = ds.getString("userEmail");
                                    assert emailS != null;
                                    if(emailS.equals(userEmail)){
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        assert user != null;
                                        final String userId = user.getUid();
                                        final Intent intent = new Intent(SignInActivity.this, UserHomeScreen_Activity.class);
                                        DocumentReference rootNode = firestore.collection("Users").document(userId);
                                        rootNode.get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        String UserName = documentSnapshot.getString("userName");
                                                        String UserEmail = documentSnapshot.getString("userEmail");
                                                        String UserGender = documentSnapshot.getString("userGender");
                                                        String UserBirthdate = documentSnapshot.getString("userBirthdate");
                                                        String UserPhone = documentSnapshot.getString("userPhone");
                                                        String UserPassword = documentSnapshot.getString("userPassword");

                                                        ArrayList<String> UserHobbies;
                                                        ArrayList<String> UserMusicGenres;

                                                        UserHobbies = (ArrayList<String>) documentSnapshot.get("userHobbies");
                                                        UserMusicGenres = (ArrayList<String>) documentSnapshot.get("userMusicGenres");

                                                        SessionManager sessionManager = new SessionManager(SignInActivity.this);
                                                        sessionManager.createLoginSession(UserName, UserPhone, UserPassword, UserEmail, UserGender, UserBirthdate, UserHobbies, UserMusicGenres);

                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                toastText.setText(R.string.login_internet_connection_error_toast);
                                                toastError.show();
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    });

                }else{
                    toastText.setText(R.string.login_user_error_toast);
                    toastError.show();
                }
            }
        });

    }

    private boolean checkInternet() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConnection = manager.getNetworkInfo(manager.TYPE_WIFI);
        NetworkInfo mobileConnection = manager.getNetworkInfo(manager.TYPE_MOBILE);

        if(wifiConnection.isConnected()){
            toastText.setText(null);
            return true;
        }else if(mobileConnection.isConnected()){
            toastText.setText(null);
            return true;
        }else{
            toastText.setText(R.string.login_internet_connection_error_toast);
            toastError.show();
            return false;
        }

    }

}
package com.example.friendpie.SignUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendpie.HelperAdapters.MusicGenre.MusicGenre;
import com.example.friendpie.HelperAdapters.MusicGenre.MusicGenreAdapter;
import com.example.friendpie.OtherClasses.Friendpie_ProgressBar;
import com.example.friendpie.OtherClasses.SessionManager;
import com.example.friendpie.OtherClasses.UserHelperClass;
import com.example.friendpie.R;
import com.example.friendpie.UserHomeScreen.UserHomeScreen_Activity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class F_MusicGenreActivity extends AppCompatActivity {

    private ArrayList<MusicGenre> musicGenres;
    private MusicGenreAdapter musicGenreAdapter;
    private Button music_genre_button;
    private String UserEmail, UserPhone, UserPassword, UserBirthdate, UserName, UserGender;
    private ArrayList<String> UserHobbies;
    private Toast toastError;
    private TextView toastText;
    private ImageView back_arrow_6;
    private ArrayList<String> UserMusicGenres;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = firebaseAuth.getCurrentUser();
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_genre);
        assignItems();
        assignMusicGenre();
        toastMessage();

        back_arrow_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        music_genre_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(view);
                if(UserMusicGenres.isEmpty()) {
                    toastText.setText("Dinlediğiniz müzik tarzlarını seçin.");
                    toastError.show();
                    return;
                }else{
                    new signUpUser().execute();
                }
            }
        });
    }

    private void assignItems() {
        RecyclerView music_genre_recyclerview = findViewById(R.id.music_genre_recyclerview);
        music_genre_button = findViewById(R.id.music_genre_button);
        musicGenres = new ArrayList<>();
        back_arrow_6 = findViewById(R.id.back_arrow_6);
        firestore = FirebaseFirestore.getInstance();
        musicGenreAdapter = new MusicGenreAdapter(musicGenres);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        music_genre_recyclerview.setAdapter(musicGenreAdapter);
        music_genre_recyclerview.setLayoutManager(layoutManager);

        //Pass datas of user
        Bundle bundle = getIntent().getExtras();
        UserHobbies = bundle.getStringArrayList("UserHobbies");
        UserEmail = getIntent().getStringExtra("UserEmail");
        UserPhone = getIntent().getStringExtra("UserPhone");
        UserPassword = getIntent().getStringExtra("UserPassword");
        UserBirthdate = getIntent().getStringExtra("UserBirthdate");
        UserName = getIntent().getStringExtra("UserName");
        UserGender = getIntent().getStringExtra("UserGender");

    }

    private void assignMusicGenre() {

        String [] TitlesOfGenres = {
                "Akustik",
                "Arabesk",
                "Asya",
                "Caz",
                "Chill",
                "Country",
                "Çocuk",
                "Dizi/Film",
                "Elektronik",
                "Enstrümental",
                "Folk",
                "Funk",
                "Hip Hop",
                "Indie",
                "Klasik",
                "Latin",
                "Metal",
                "Parti",
                "Pop",
                "Progressive",
                "Punk",
                "R&B",
                "Reggae",
                "Rock",
        };

        for(int i = 0; i<TitlesOfGenres.length; i++) {
            musicGenres.add(new MusicGenre(TitlesOfGenres[i]));
        }


    }

    private void getData(View v) {
        UserMusicGenres = musicGenreAdapter.listOfMusicGenres();
        Log.d("List", UserMusicGenres.toString());
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

    private class signUpUser extends AsyncTask<Void, Void, Void> {

        Friendpie_ProgressBar friendpieProgressBar =new Friendpie_ProgressBar(F_MusicGenreActivity.this, "Hesap oluşturuluyor.");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            storeNewUserData();
            Intent intent = new Intent(F_MusicGenreActivity.this, UserHomeScreen_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

            friendpieProgressBar.dismiss();
        }

    }

    private void storeNewUserData() {

        final String UID = mUser.getUid();
        DocumentReference documentReference = firestore.collection("Users").document(UID);
        UserHelperClass addNewUser = new UserHelperClass(UserEmail, UserPhone, UserPassword, UserBirthdate, UserName, UserGender, UserHobbies, UserMusicGenres);
        documentReference.set(addNewUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("UserCreated", "User successfully created" + UID);
            }
        });

        SessionManager sessionManager = new SessionManager(F_MusicGenreActivity.this);
        sessionManager.createLoginSession(UserName, UserPhone, UserPassword, UserEmail, UserGender, UserBirthdate, UserHobbies, UserMusicGenres);

    }

}
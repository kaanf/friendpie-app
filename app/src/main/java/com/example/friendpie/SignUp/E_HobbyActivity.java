package com.example.friendpie.SignUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendpie.HelperAdapters.Hobby.Hobby;
import com.example.friendpie.HelperAdapters.Hobby.HobbyAdapter;
import com.example.friendpie.R;
import java.util.ArrayList;

public class E_HobbyActivity extends AppCompatActivity {

    private ArrayList<Hobby> hobby;
    private EditText search_hobby;
    private HobbyAdapter hobbyAdapter;
    private Button hobby_button;
    private ImageView back_arrow_5;
    private ArrayList<String> selectedHobbies;
    private String UserEmail, UserPhone, UserPassword, UserName, UserGender, UserBirthdate;
    private Toast toastError;
    private TextView toastText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);

        //Initialize of functions
        assignItems();
        toastMessage();
        assignHobbyList();
        hobbyAdapter.notifyDataSetChanged();
        searchInList();

        back_arrow_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        hobby_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(view);
                if(selectedHobbies.isEmpty())
                {
                    toastText.setText(R.string.hobby_error_toast);
                    toastError.show();
                    return;
                }
                else{
                    Intent intent = new Intent(E_HobbyActivity.this, F_MusicGenreActivity.class);
                    intent.putExtra("UserPhone", UserPhone);
                    intent.putExtra("UserEmail", UserEmail);
                    intent.putExtra("UserPassword", UserPassword);
                    intent.putExtra("UserBirthdate", UserBirthdate);
                    intent.putExtra("UserGender", UserGender);
                    intent.putExtra("UserName", UserName);
                    intent.putExtra("UserHobbies", selectedHobbies);
                    startActivity(intent);
                }

            }
        });

    }

    private void assignItems() {

        RecyclerView hobby_recyclerview = findViewById(R.id.hobby_recyclerview);
        search_hobby = findViewById(R.id.search_hobby);
        hobby_button = findViewById(R.id.hobby_button);
        hobby = new ArrayList<>();
        hobbyAdapter = new HobbyAdapter(hobby);
        back_arrow_5 = findViewById(R.id.back_arrow_5);
        hobby_recyclerview.setAdapter(hobbyAdapter);
        hobby_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        // Pass datas of user

        UserEmail = getIntent().getStringExtra("UserEmail");
        UserPhone = getIntent().getStringExtra("UserPhone");
        UserPassword = getIntent().getStringExtra("UserPassword");
        UserBirthdate = getIntent().getStringExtra("UserBirthdate");
        UserName = getIntent().getStringExtra("UserName");
        UserGender = getIntent().getStringExtra("UserGender");

    }

    private void assignHobbyList() {

        String [] TitlesOfHobby = {
                "Alışveriş",
                "Araba",
                "Bahçe İşleri",
                "Bisiklet",
                "Blog Yazmak",
                "Çizim",
                "Dans",
                "Dizi",
                "Doğa Yürüyüşü",
                "Dövüş Sanatları",
                "E-Ticaret",
                "El Sanatları",
                "Enstrüman Aletleri",
                "Film",
                "Fotoğrafçılık",
                "Kamp",
                "Kitap",
                "Koleksiyon",
                "Masa Oyunları",
                "Motosiklet",
                "Müzik",
                "Oyun",
                "Piknik",
                "Psikoloji",
                "Seyahat",
                "Sinema",
                "Spor",
                "Şiir",
                "Tiyatro",
                "Vokal",
                "Yabancı Dil",
                "Yazılım",
                "Yemek"
        };

        int [] ImagesOfHobby = {
                R.drawable.ic_alisveris,
                R.drawable.ic_araba,
                R.drawable.ic_garden,
                R.drawable.ic_bisiklet,
                R.drawable.ic_blog,
                R.drawable.ic_cizim,
                R.drawable.ic_dance,
                R.drawable.ic_popcorn,
                R.drawable.ic_doga_yuruyusu,
                R.drawable.ic_dovus_sanatlari,
                R.drawable.ic_eticaret,
                R.drawable.ic_el_sanatlari,
                R.drawable.ic_enstruman,
                R.drawable.ic_movie,
                R.drawable.ic_fotografcilik,
                R.drawable.ic_kamp,
                R.drawable.ic_book,
                R.drawable.ic_koleksiyon,
                R.drawable.ic_masa_oyunlari,
                R.drawable.ic_motosiklet,
                R.drawable.ic_music,
                R.drawable.ic_oyun,
                R.drawable.ic_piknik,
                R.drawable.ic_psikoloji,
                R.drawable.ic_seyahat,
                R.drawable.ic_sinema,
                R.drawable.ic_spor,
                R.drawable.ic_siir,
                R.drawable.ic_tiyatro,
                R.drawable.ic_vokal,
                R.drawable.ic_yabanci_dil,
                R.drawable.ic_laptop,
                R.drawable.ic_yemek
        };

        for(int i = 0; i<TitlesOfHobby.length; i++) {
            hobby.add(new Hobby(TitlesOfHobby[i], ImagesOfHobby[i]));
        }


    }

    private void searchInList() {
        search_hobby.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<Hobby> filteredList = new ArrayList<>();
        for(Hobby item: hobby){
            if(item.getHobbyTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        hobbyAdapter.filterList(filteredList);
    }

    private void getData(View v){
        selectedHobbies = hobbyAdapter.listOfHobbies();
        Log.d("List", selectedHobbies.toString());
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
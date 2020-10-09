package com.example.friendpie.UserHomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.friendpie.HelperAdapters.Screens.NavigationViewAdapter;
import com.example.friendpie.Homepage.HomepageActivity;
import com.example.friendpie.OtherClasses.SessionManager;
import com.example.friendpie.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;


public class UserHomeScreen_Activity extends AppCompatActivity {

    private ImageView logout_button;
    private ViewPager fragmentContainer;
    private TabLayout navigationBar;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen);
        assignItems();
        setNavigationBar();

        if(firebaseUser==null){
            Intent intent = new Intent(UserHomeScreen_Activity.this, HomepageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        /* SESSIONMANAGER - RETRIEVE OF USER'S DATA
        SessionManager sessionManager = new SessionManager(UserHomeScreen_Activity.this);
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailFromSession();
        ArrayList<String> userHob = sessionManager.getUserHobbies();
        ArrayList<String> uuserM = sessionManager.getUserMusicGenres();
        String name = usersDetails.get(SessionManager.KEY_FULLNAME);
        String gender = usersDetails.get(SessionManager.KEY_GENDER);
        */

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(UserHomeScreen_Activity.this, HomepageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void assignItems() {
        logout_button = findViewById(R.id.logout_button);
        navigationBar = findViewById(R.id.navigationBar);
        fragmentContainer = findViewById(R.id.fragmentContainer);
    }

    private void setNavigationBar() {
        TabLayout.Tab profileScreen = navigationBar.newTab();
        TabLayout.Tab matchScreen = navigationBar.newTab();
        TabLayout.Tab messageScreen = navigationBar.newTab();
        navigationBar.addTab(profileScreen);
        navigationBar.addTab(matchScreen);
        navigationBar.addTab(messageScreen);
        NavigationViewAdapter adapter = new NavigationViewAdapter(getSupportFragmentManager());
        adapter.AddFragment(new ProfileFragment(), "");
        adapter.AddFragment(new MatchFragment(), "");
        adapter.AddFragment(new MessageFragment(), "");
        fragmentContainer.setAdapter(adapter);
        navigationBar.setupWithViewPager(fragmentContainer);
        navigationBar.getTabAt(0).setIcon(R.drawable.tab_profile);
        navigationBar.getTabAt(1).setIcon(R.drawable.tab_match);
        navigationBar.getTabAt(2).setIcon(R.drawable.tab_message);
        fragmentContainer.setCurrentItem(1);
    }

}
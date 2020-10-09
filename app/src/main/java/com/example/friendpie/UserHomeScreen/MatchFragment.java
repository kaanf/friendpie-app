package com.example.friendpie.UserHomeScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.friendpie.HelperAdapters.MatchUser.User;
import com.example.friendpie.Interfaces.MainCallback;
import com.example.friendpie.OtherClasses.SessionManager;
import com.example.friendpie.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MatchFragment extends Fragment {

    private MainCallback callback = null;
    private View view;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private CollectionReference cRef;
    private DocumentReference dRef;
    private SessionManager sessionManager;
    private HashMap<String, String> usersDetails;
    private ArrayList<String> userHob;
    private String userID;
    private ArrayAdapter<User> cardsAdapter = null;
    private ArrayList<User> rowItems;
    private DocumentReference uDatabase;

    public MatchFragment() {
        // Required empty public constructor
    }

    public void setCallback(MainCallback callback){
        this.callback = callback;
        this.userID = callback.onGetUserId();
        this.uDatabase = callback.getUserDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_match, container, false);
        assignItems();
        for(int i=0; i<userHob.size(); i++) {

            Log.i("Userg", userHob.get(i));
        }
        matchItems();



        return view;
    }

    private void assignItems() {
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        String UID = fAuth.getCurrentUser().getUid();
        dRef = fStore.collection("Users").document(UID);
        cRef = fStore.collection("Users");
        sessionManager = new SessionManager(getActivity());
        usersDetails = sessionManager.getUsersDetailFromSession();
        userHob = sessionManager.getUserHobbies();
    }

    private void matchItems() {
        cRef.whereArrayContainsAny("userHobbies", userHob)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for ( QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Log.i("userTag", documentSnapshot.toString());
                }

            }
        });
    }





}
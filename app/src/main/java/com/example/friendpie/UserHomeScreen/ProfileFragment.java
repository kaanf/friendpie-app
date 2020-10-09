package com.example.friendpie.UserHomeScreen;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendpie.HelperAdapters.ProfileInterests.ProfileHobby;
import com.example.friendpie.HelperAdapters.ProfileInterests.ProfileHobbyAdapter;
import com.example.friendpie.HelperAdapters.ProfileMusicGenres.ProfileMusic;
import com.example.friendpie.HelperAdapters.ProfileMusicGenres.ProfileMusicGenreAdapter;
import com.example.friendpie.OtherClasses.SessionManager;
import com.example.friendpie.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ProfileFragment extends Fragment {

    private ArrayList<ProfileHobby> profileHobbies = new ArrayList<>();
    private ArrayList<ProfileMusic> profileMusics = new ArrayList<>();
    private ImageView profile_picture;
    private BottomSheetDialog bottomSheetDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String UID;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;

    public ProfileFragment(){}

    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.fragment_profile, container, false);
            hobbyProfileList();
            musicProfileList();

            //AssignItems
            RecyclerView profil_ilgiAlani = view.findViewById(R.id.profil_ilgiAlani);
            RecyclerView muzik_ilgiAlani = view.findViewById(R.id.muzik_ilgiAlani);
            TextView profile_UserName = view.findViewById(R.id.profile_UserName);
            profile_picture = view.findViewById(R.id.profile_picture);
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
            FlexboxLayoutManager layoutMusicManager = new FlexboxLayoutManager(getActivity());

            //Name assign
            SessionManager sManager = new SessionManager(Objects.requireNonNull(getActivity()));
            HashMap<String, String> usersDetails = sManager.getUsersDetailFromSession();
            String UserProfileName = usersDetails.get(SessionManager.KEY_FULLNAME);
            profile_UserName.setText(UserProfileName);

            //Hobby & Music Cards
            layoutMusicManager.setFlexDirection(FlexDirection.ROW);
            layoutMusicManager.setJustifyContent(JustifyContent.CENTER);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.CENTER);
            muzik_ilgiAlani.setLayoutManager(layoutMusicManager);
            muzik_ilgiAlani.setNestedScrollingEnabled(false);
            profil_ilgiAlani.setLayoutManager(layoutManager);
            profil_ilgiAlani.setNestedScrollingEnabled(false);
            ProfileHobbyAdapter profileHobbyAdapter = new ProfileHobbyAdapter(profileHobbies);
            ProfileMusicGenreAdapter profileMusicGenreAdapter = new ProfileMusicGenreAdapter(profileMusics);
            profil_ilgiAlani.setAdapter(profileHobbyAdapter);
            muzik_ilgiAlani.setAdapter(profileMusicGenreAdapter);
        }

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(Objects.requireNonNull(getActivity()), R.style.BottomSheetTheme);
                View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.profile_picture_bottom_sheet, (LinearLayout) view.findViewById(R.id.bottom_sheet_pp));
                bottomSheetView.findViewById(R.id.galeriden_sec).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGallery();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        UID = firebaseAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        documentReference = firestore.collection("Users").document(UID);
        StorageReference profileReference = storageReference.child(UID).child("profile_picture");
        profileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profile_picture);
            }
        });

        return view;
    }

    private void openGallery() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri = data.getData();
                bottomSheetDialog.dismiss();
                uploadToFirebase(imageUri);
            }
        }
    }

    private void uploadToFirebase(Uri uri) {
        final StorageReference fileRef = storageReference.child(UID).child("profile_picture");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(profile_picture);
                        Map<String, Object> map = new HashMap<>();
                        map.put("profile_picture", uri.toString());
                        documentReference.update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("UserC", "Success");
                                    }
                                });

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Image upload failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hobbyProfileList() {

        SessionManager sessionManager = new SessionManager(Objects.requireNonNull(getActivity()));
        ArrayList<String> userHobbySession = sessionManager.getUserHobbies();
        Log.i("UserHob", userHobbySession.toString());
        for(int i = 0; i<userHobbySession.size(); i++){
            profileHobbies.add(new ProfileHobby(userHobbySession.get(i)));
        }
    }

    private void musicProfileList() {

        SessionManager sessionManager = new SessionManager(Objects.requireNonNull(getActivity()));
        ArrayList<String> userMusicGenres = sessionManager.getUserMusicGenres();
        Log.i("UserHob", userMusicGenres.toString());
        for(int i = 0; i<userMusicGenres.size(); i++){
            profileMusics.add(new ProfileMusic(userMusicGenres.get(i)));
        }
    }

}
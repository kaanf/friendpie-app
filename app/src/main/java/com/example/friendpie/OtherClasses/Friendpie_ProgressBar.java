package com.example.friendpie.OtherClasses;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.friendpie.R;

public class Friendpie_ProgressBar extends Dialog {

    TextView progress_bar_textview;

    public Friendpie_ProgressBar(@NonNull Context context, String s) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_bar_friendpie);
        progress_bar_textview = findViewById(R.id.progress_bar_textview);
        progress_bar_textview.setText(s);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


}

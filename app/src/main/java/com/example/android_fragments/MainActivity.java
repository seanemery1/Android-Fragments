package com.example.android_fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private final String path_logo = "file:///android_asset/logo.png";
    private ImageView imageView_logo;
    private Button button_char;
    private Button button_ep;
    private Button button_loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageView_logo = findViewById(R.id.imageView_mainLogo);
        this.button_char = findViewById(R.id.button_mainChar);
        this.button_ep = findViewById(R.id.button_mainEp);
        this.button_loc = findViewById(R.id.button_mainLoc);

        Picasso.get().load(path_logo).into(imageView_logo);

        this.button_char.setOnClickListener(v -> loadFragment(new CharFragment(), R.id.fragContainer));

        this.button_ep.setOnClickListener(v -> loadFragment(new EpFragment(), R.id.fragContainer));

        this.button_loc.setOnClickListener(v -> loadFragment(new LocFragment(), R.id.fragContainer));
    }

    private void loadFragment(Fragment fragment, int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }
}
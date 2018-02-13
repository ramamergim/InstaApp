package com.mergimrama.instaapp.UIActivity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.mergimrama.instaapp.AppPreferences;
import com.mergimrama.instaapp.R;
import com.mergimrama.instaapp.fragments.HomeFragment;
import com.mergimrama.instaapp.fragments.ProfileFragment;
import com.mergimrama.instaapp.model.User;

public class MainActivity extends AppCompatActivity {

    /*ListView listView;
    ListAdapter listAdapter;*/

    ImageButton homeFragmenntButton;
    ImageButton addPostFragmenntButton;
    ImageButton profileFragmenntButton;

    HomeFragment homeFragment;
    ProfileFragment profileFragment;

    public static String userId;
    public static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragmenntButton = (ImageButton) findViewById(R.id.fragment_home);
        addPostFragmenntButton = (ImageButton) findViewById(R.id.fragment_add_post);
        profileFragmenntButton = (ImageButton) findViewById(R.id.fragment_profile);

        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();


        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_holder, homeFragment);
            fragmentTransaction.commit();
            selectButton(0);
        }

        AppPreferences.init(getApplicationContext());
        if (!AppPreferences.getUserId().equals("")) {

        } else {
            userId = AppPreferences.getUserId();
        }

        user = (User) getIntent().getSerializableExtra("userObj");

//        userId = getIntent().getStringExtra("userId");

        /*listView = (ListView) findViewById(R.id.list_view);
        listAdapter = new ListAdapter(getLayoutInflater());
        listView.setAdapter(listAdapter);*/

        homeFragmenntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                } else {
                    addFragment(homeFragment);
                    selectButton(0);
                }
            }
        });

        addPostFragmenntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostFeedActivity.class);
                startActivity(intent);
            }
        });

        profileFragmenntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileFragment == null) {
                    profileFragment = new ProfileFragment();
                } else {
                    addFragment(profileFragment);
                    selectButton(2);
                }
            }
        });
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_holder, fragment);
        ft.commit();
    }

    private void selectButton(int position) {
        homeFragmenntButton.setImageResource(R.drawable.ic_home);
        addPostFragmenntButton.setImageResource(R.drawable.ic_add);
        profileFragmenntButton.setImageResource(R.drawable.ic_profile);

        switch (position) {
            case 0:
                homeFragmenntButton.setImageResource(R.drawable.ic_home_selected);
                break;
            case 1:
                addPostFragmenntButton.setImageResource(R.drawable.ic_add_selected);
                break;
            case 2:
                profileFragmenntButton.setImageResource(R.drawable.ic_profile_selected);
                break;
        }
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_button) {
            Intent intent = new Intent(MainActivity.this, PostFeedActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.ahadabd.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SharedPreferences theme_pref ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theme_pref = getSharedPreferences("THEME_PREF", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new Tab1Fragment(), "Match Preference");
        adapter.addFragment(new Tab2Fragment(), "App Preference");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        setThemeView();

    }

    private void setThemeView() {
        if(theme_pref.getBoolean("DARK",false)){
            setDarkTheme();
        }
        else{
            setLightTheme();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.viewMode) {
            if(!theme_pref.getBoolean("DARK",false)){
               setDarkTheme();
            }
            else{
                setLightTheme();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void setLightTheme() {
        SharedPreferences.Editor editor= theme_pref.edit();
        editor.putBoolean("DARK", true);
        editor.commit();
        tabLayout.setBackgroundColor(Color.TRANSPARENT);
        tabLayout.setTabTextColors(Color.GRAY,Color.BLACK);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.DarkRed));
    }

    private void setDarkTheme() {
        SharedPreferences.Editor editor= theme_pref.edit();
        editor.putBoolean("DARK", false);
        editor.commit();
        tabLayout.setBackgroundColor(Color.BLACK);
        tabLayout.setTabTextColors(Color.GRAY,Color.WHITE);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.DeepPink));
    }



}

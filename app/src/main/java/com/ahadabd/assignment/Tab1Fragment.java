package com.ahadabd.assignment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.jaygoo.widget.SeekBar;

import net.colindodd.toggleimagebutton.ToggleImageButton;

import io.github.tonnyl.light.Light;

import static android.content.Context.MODE_PRIVATE;

public class Tab1Fragment extends Fragment {


    RangeSeekBar range_age_bar;
    ToggleImageButton man_on,woman_on;
    TextView range_txt,txt_gender_desc,txt_age_desc,txt_interested_in,txt_age_range_title;
    ScrollView scrollView;
    LinearLayout linearLayout;
    ConstraintLayout gender_pref_layout,range_layout,man_card,woman_card;
    SharedPreferences theme_pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_one, container, false);

        findbyId(view);
        initialize();
        setThemeView();

        range_age_bar.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                range_txt.setText((int)view.getLeftSeekBar().getProgress()+"-"+(int)view.getRightSeekBar().getProgress()+" years");
                changeSeekBarThumb(view.getLeftSeekBar(),leftValue);
                changeSeekBarThumb(view.getRightSeekBar(),rightValue);
                //setAgeRangeInDatabase
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });


        man_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 man_on.setSelected(!man_on.isSelected());
                if(man_on.isSelected() && woman_on.isSelected()){

                    updateseekbar();
                }
                else if(man_on.isSelected() && !woman_on.isSelected()){
                    updateseekbar();
                    //UpdateInteresetInDatabse
                }
                else if(!man_on.isSelected() && woman_on.isSelected()){
                    updateseekbar();
                    //UpdateInteresetInDatabse
                }
                else{
                    man_on.setSelected(true);
                    man_on.setChecked(true);
                    updateseekbar();
                    Light.error(view,"You can't unselect both", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        woman_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               woman_on.setSelected(!woman_on.isSelected());
                if(woman_on.isSelected() && man_on.isSelected()){
                    updateseekbar();
                    //UpdateInteresetInDatabse
                }
                else if(woman_on.isSelected() && !man_on.isSelected()){
                    updateseekbar();
                    //UpdateInteresetInDatabse
                }
                else if(!woman_on.isSelected() && man_on.isSelected()){
                    updateseekbar();
                    //UpdateInteresetInDatabse
                }
                else{
                    woman_on.setSelected(true);
                    woman_on.setChecked(true);
                    updateseekbar();
                    Light.error(view,"You can't unselect both", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void findbyId(View view) {
        range_age_bar=view.findViewById(R.id.sb_range_3);
        man_on=view.findViewById(R.id.man_on);
        woman_on=view.findViewById(R.id.woman_on);
        range_txt=view.findViewById(R.id.txt_range);
        scrollView= view.findViewById(R.id.scroll_view_1);
        linearLayout=view.findViewById(R.id.layout_linear);
        man_card=view.findViewById(R.id.layout_card_man);
        woman_card=view.findViewById(R.id.layout_card_woman);
        gender_pref_layout=view.findViewById(R.id.gender_pref_layout);
        range_layout=view.findViewById(R.id.range_layout);
        txt_age_desc=view.findViewById(R.id.txt_age_discription);
        txt_gender_desc= view.findViewById(R.id.gender_text_description);
        txt_interested_in=view.findViewById(R.id.txt_interested_in);
        txt_age_range_title= view.findViewById(R.id.txt_age_range);

    }

    private void setThemeView() {
        theme_pref = getActivity().getSharedPreferences("THEME_PREF", MODE_PRIVATE);
        if(!theme_pref.getBoolean("DARK",false)){
            setDarkTheme();
        }
        else{
            setLightTheme();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.viewMode);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!theme_pref.getBoolean("DARK",false)){
            setDarkTheme();
        }
        else{
            setLightTheme();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setLightTheme() {
        SharedPreferences.Editor editor= theme_pref.edit();
        editor.putBoolean("DARK", false);
        editor.commit();
        scrollView.setBackgroundColor(Color.TRANSPARENT);
        linearLayout.setBackgroundColor(Color.TRANSPARENT);
        man_card.setBackgroundResource(R.color.White);
        woman_card.setBackgroundResource(R.color.White);
        txt_gender_desc.setTextColor(Color.parseColor("#827D7D"));
        txt_age_desc.setTextColor(Color.parseColor("#827D7D"));
        gender_pref_layout.setBackgroundResource(R.color.DeepPink);
        range_layout.setBackgroundResource(R.color.DeepPink);
        txt_age_range_title.setTextColor(Color.parseColor("#8B0000"));
        txt_interested_in.setTextColor(Color.parseColor("#8B0000"));
        range_txt.setTextColor(Color.GRAY);
    }

    private void setDarkTheme() {
        SharedPreferences.Editor editor= theme_pref.edit();
        editor.putBoolean("DARK", true);
        editor.commit();
        scrollView.setBackgroundColor(Color.BLACK);
        linearLayout.setBackgroundColor(Color.BLACK);
        man_card.setBackgroundResource(R.color.Silver);
        woman_card.setBackgroundResource(R.color.Silver);
        txt_gender_desc.setTextColor(Color.WHITE);
        txt_age_desc.setTextColor(Color.WHITE);
        gender_pref_layout.setBackgroundResource(R.color.my);
        range_layout.setBackgroundResource(R.color.my);
        txt_age_range_title.setTextColor(Color.parseColor("#FFFFE0"));
        txt_interested_in.setTextColor(Color.parseColor("#FFFFE0"));
        range_txt.setTextColor(Color.WHITE);
    }

    private void updateseekbar() {
        changeSeekBarThumb(range_age_bar.getLeftSeekBar(),range_age_bar.getProgressLeft());
        changeSeekBarThumb(range_age_bar.getLeftSeekBar(),range_age_bar.getProgressLeft());
        range_age_bar.setProgress(range_age_bar.getLeftSeekBar().getProgress(),range_age_bar.getRightSeekBar().getProgress());
    }


    private void initialize() {
        woman_on.setSelected(true);
        woman_on.setChecked(true);
        range_age_bar.setRange(18, 70);
        range_age_bar.setIndicatorTextDecimalFormat("0");
        range_age_bar.setProgress(20,30);
        range_age_bar.getLeftSeekBar().setIndicatorArrowSize(10);
        range_age_bar.getLeftSeekBar().setIndicatorRadius(20);
        range_age_bar.getRightSeekBar().setIndicatorArrowSize(10);
        range_age_bar.getRightSeekBar().setIndicatorRadius(20);
        range_age_bar.getLeftSeekBar().setThumbDrawableId(R.drawable.girlround);
        range_age_bar.getRightSeekBar().setThumbDrawableId(R.drawable.woman_mid);
    }

    private void changeSeekBarThumb(SeekBar SeekBar, float Value) {
        if(man_on.isSelected() && !woman_on.isSelected()){
            if(Value<30){
                SeekBar.setThumbDrawableId(R.drawable.man_young);
            }
            else if(Value<50){
                SeekBar.setThumbDrawableId(R.drawable.man_mid_r);
            }
            else{
                SeekBar.setThumbDrawableId(R.drawable.man_old);
            }
        }
        else if(woman_on.isSelected() && !man_on.isSelected()){
            if(Value<30){
                SeekBar.setThumbDrawableId(R.drawable.girlround);
            }
            else if(Value<50){
                SeekBar.setThumbDrawableId(R.drawable.woman_mid);
            }
            else{
                SeekBar.setThumbDrawableId(R.drawable.woman_old);
            }
        }
        else{
            if(Value<30){
                SeekBar.setThumbDrawableId(R.drawable.man_woman_young);
            }
            else if(Value<50){
                SeekBar.setThumbDrawableId(R.drawable.man_woman_mid);
            }
            else{
                SeekBar.setThumbDrawableId(R.drawable.man_woman_old);
            }
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        if(theme_pref.getBoolean("DARK",false)){
            setDarkTheme();
        }
        else{
            setLightTheme();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(theme_pref.getBoolean("DARK",false)){
            setDarkTheme();
        }
        else{
            setLightTheme();
        }
    }
}
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.rm.rmswitch.RMAbstractSwitch;
import com.rm.rmswitch.RMSwitch;

import io.github.tonnyl.light.Light;

import static android.content.Context.MODE_PRIVATE;

public class Tab2Fragment extends Fragment  {

    RMSwitch btn_visibility,btn_sound,btn_lang;
    TextView txt_alert,txt_visibility,txt_lang,txt_notification;
    TextView txt1_1,txt2_1,txt3_1,txt4;
    ImageView img_sound,img_visible;
    LinearLayout layout;
    ScrollView scrollView;
    ConstraintLayout c1,c2,c3,c4;
    TabLayout tab;
    SharedPreferences theme_pref ;
    Button btn_go;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_two, container, false);
        findById(view);
        setThemeView();
        initialize();

        btn_sound.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if(isChecked){
                    Light.success(view,"Notification sound is enabled", Snackbar.LENGTH_SHORT).show();
                    txt_alert.setText("Alerts are ON");
                    img_sound.setImageResource(R.drawable.sound_on);

                }
                else {
                    Light.warning(view,"Notification sound is disabled", Snackbar.LENGTH_SHORT).show();
                    txt_alert.setText("Alerts are OFF");
                    img_sound.setImageResource(R.drawable.no_sound);
                }
            }
        });

        btn_visibility.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if(isChecked){
                    Light.info(view,"You are in matchmaker-only mode", Snackbar.LENGTH_SHORT).show();
                    txt_visibility.setText("Your Profile is hidden");
                    img_visible.setImageResource(R.drawable.eye);
                }
                else {
                    Light.info(view,"Marchmaker-only mode is disabled", Snackbar.LENGTH_SHORT).show();
                    txt_visibility.setText("Your Profile is visible");
                    img_visible.setImageResource(R.drawable.eye_visible);
                }
            }
        });

        btn_lang.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if(isChecked){
                    Light.info(view,"Language changed to Hindi", Snackbar.LENGTH_SHORT).show();
                    txt_lang.setText("Current language is Hindi");
                }
                else {
                    Light.info(view,"Language changed to English", Snackbar.LENGTH_SHORT).show();
                    txt_lang.setText("Current language is English");
                }
            }
        });

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Light.info(view,"Redirecting to settings...", Snackbar.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initialize() {
        btn_sound.setChecked(true);
        txt_alert.setText("Alerts are ON");
        btn_sound.setSwitchDesign(RMAbstractSwitch.DESIGN_ANDROID);
        btn_visibility.setChecked(false);
        txt_visibility.setText("Your Profile is visible");
        btn_visibility.setSwitchDesign(RMSwitch.DESIGN_ANDROID);
        btn_lang.setChecked(false);
        txt_lang.setText("Current language is English");
        btn_lang.setSwitchDesign(RMSwitch.DESIGN_ANDROID);
    }

    private void findById(View view) {
        layout=view.findViewById(R.id.layout);
        tab=view.findViewById(R.id.tabLayout);
        c1=view.findViewById(R.id.relativeLayout_1);
        c2=view.findViewById(R.id.c2);
        c3=view.findViewById(R.id.c3);
        c4=view.findViewById(R.id.c4);
        scrollView=view.findViewById(R.id.scroll);
        btn_sound =view.findViewById(R.id.sound_btn);
        btn_visibility=view.findViewById(R.id.btn_visiblity);
        btn_lang=view.findViewById(R.id.btn_lang);
        txt_alert=view.findViewById(R.id.sound_status_txt);
        txt_notification=view.findViewById(R.id.txt_enable_noti);
        txt_visibility=view.findViewById(R.id.matchmaker_status_txt);
        txt_lang=view.findViewById(R.id.txt_lang);
        img_sound=view.findViewById(R.id.img_sound);
        img_visible=view.findViewById(R.id.img_visibility);
        txt1_1=view.findViewById(R.id.txt1_1);
        txt2_1=view.findViewById(R.id.txt2_1);
        txt3_1=view.findViewById(R.id.txt3_1);
        txt4=view.findViewById(R.id.txt4_1);
        btn_go=view.findViewById(R.id.btn_go);
    }

    private void setThemeView() {
        theme_pref = getActivity().getSharedPreferences("THEME_PREF", MODE_PRIVATE);
        if(theme_pref.getBoolean("DARK",false)){
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

    private void setDarkTheme() {
        SharedPreferences.Editor editor= theme_pref.edit();
        editor.putBoolean("DARK", true);
        editor.commit();
        scrollView.setBackgroundColor(Color.BLACK);
        layout.setBackgroundColor(Color.BLACK);
        c1.setBackgroundResource(R.color.my);
        c2.setBackgroundResource(R.color.my);
        c3.setBackgroundResource(R.color.my);
        c4.setBackgroundResource(R.color.my);
        txt1_1.setTextColor(Color.parseColor("#FFFFE0"));
        txt2_1.setTextColor(Color.parseColor("#FFFFE0"));
        txt3_1.setTextColor(Color.parseColor("#FFFFE0"));
        txt4.setTextColor(Color.parseColor("#FFFFE0"));
        txt_alert.setTextColor(Color.WHITE);
        txt_lang.setTextColor(Color.WHITE);
        txt_visibility.setTextColor(Color.WHITE);
        txt_notification.setTextColor(Color.WHITE);



    }
    private void setLightTheme() {
        SharedPreferences.Editor editor= theme_pref.edit();
        editor.putBoolean("DARK", false);
        editor.commit();
        scrollView.setBackgroundColor(Color.TRANSPARENT);
        layout.setBackgroundColor(Color.TRANSPARENT);
        c1.setBackgroundResource(R.color.DeepPink);
        c2.setBackgroundResource(R.color.DeepPink);
        c3.setBackgroundResource(R.color.DeepPink);
        c4.setBackgroundResource(R.color.DeepPink);
        txt1_1.setTextColor(Color.parseColor("#8B0000"));
        txt2_1.setTextColor(Color.parseColor("#8B0000"));
        txt3_1.setTextColor(Color.parseColor("#8B0000"));
        txt4.setTextColor(Color.parseColor("#8B0000"));
        txt_alert.setTextColor(Color.parseColor("#827D7D"));
        txt_lang.setTextColor(Color.parseColor("#827D7D"));
        txt_visibility.setTextColor(Color.parseColor("#827D7D"));

        txt_notification.setTextColor(Color.parseColor("#827D7D"));
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
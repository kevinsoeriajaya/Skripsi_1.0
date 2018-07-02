package com.example.spinder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.lang.reflect.Field;

public class Home extends AppCompatActivity {
    private ImageButton btn_chooseCategory;
    private ImageView imageCategory;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.fragmentContainer, new HomeFragment()).commit();
                    return true;
                case R.id.navigation_search:
                    transaction.replace(R.id.fragmentContainer, new SearchFragment()).commit();
                    return true;
                case R.id.navigation_activity:
                    transaction.replace(R.id.fragmentContainer, new ActivityFragment()).commit();
                    return true;
                case R.id.navigation_chat:
                    transaction.replace(R.id.fragmentContainer, new ChatFragment()).commit();
                    return true;
                case R.id.navigation_profile:
                    transaction.replace(R.id.fragmentContainer, new ProfileFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, new HomeFragment()).commit();

        //untuk button category
        btn_chooseCategory = (ImageButton) findViewById(R.id.button_category);
        btn_chooseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //=====PAKE POP UP MENU======
                PopupMenu popup = new PopupMenu(Home.this, btn_chooseCategory);
                popup.getMenuInflater()
                        .inflate(R.menu.sport_type_menu, popup.getMenu());

                //popup.setGravity(Gravity.END);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        imageCategory = (ImageView) findViewById(R.id.button_category);
                        switch (item.getItemId()) {
                            case R.id.type_badminton:
                                imageCategory.setImageResource(R.drawable.badminton_logo);
                                return true;
                            case R.id.type_basketball:
                                imageCategory.setImageResource(R.drawable.basket_logo);
                                return true;
                            case R.id.type_futsal:
                                imageCategory.setImageResource(R.drawable.soccer_icon);
                                return true;
                            case R.id.type_volley:
                                imageCategory.setImageResource(R.drawable.volley_icon);
                                return true;
                            case R.id.type_tennis:
                                imageCategory.setImageResource(R.drawable.tennis_logo);
                                return true;
                            case R.id.type_pingpong:
                                imageCategory.setImageResource(R.drawable.pingpong_logo);
                                return true;
                        }
                        return true;
                    }

                    //=================PAKE POP UP WINDOW

                });

                popup.show();
            }
        });



    }

    public static class BottomNavigationViewHelper {
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }
}

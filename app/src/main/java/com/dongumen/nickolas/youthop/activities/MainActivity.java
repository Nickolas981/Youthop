package com.dongumen.nickolas.youthop.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dongumen.nickolas.youthop.R;
import com.dongumen.nickolas.youthop.fragments.FilteredListFragment;
import com.dongumen.nickolas.youthop.fragments.HomeFragment;
import com.dongumen.nickolas.youthop.utils.BookmarkUtil;
import com.dongumen.nickolas.youthop.widgets.enums.OppType;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Integer container = R.id.container;
    HomeFragment homeFragment;
    int notifications = 0;

    private TextView notificationBadge;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        homeFragment = HomeFragment.newInstance();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        bottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.tab_home:
                    changeFragment(homeFragment, false);
                    break;
                case R.id.tab_chatbox:
                case R.id.tab_explode:
                case R.id.tab_sdg:
                case R.id.tab_success_stories:
                    bottomBar.selectTabWithId(R.id.tab_home);
                    showDialog();
                    break;
                default:
                    break;

            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        changeFragment(homeFragment, false);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hand's tight, exciting features coming in next update soon!")
                .setCancelable(true)
                .setPositiveButton("OK", (dialog, id) -> {
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_bell);
        View actionView = MenuItemCompat.getActionView(menuItem);
        notificationBadge = actionView.findViewById(R.id.bell_badge);
        if (notifications != 0) {
            notificationBadge.setVisibility(View.VISIBLE);
            notificationBadge.setText(String.valueOf(Math.min(notifications, 99)));
        } else {
            notificationBadge.setVisibility(View.GONE);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_bookmark){
            Intent intent = new Intent(this, BookmarkActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home){
            bottomBar.setVisibility(View.VISIBLE);
        }else{
            bottomBar.setVisibility(View.GONE);
        }

        switch (id) {
            case R.id.nav_home:
                changeFragment(HomeFragment.newInstance(), false);
                break;
            case R.id.nav_scholarships:
                changeFragment(FilteredListFragment.newInstance(OppType.Scholarships), false);
                break;
            case R.id.nav_fellowships:
                changeFragment(FilteredListFragment.newInstance(OppType.Fellowships), false);
                break;
            case R.id.nav_jobs:
                changeFragment(FilteredListFragment.newInstance(OppType.Jobs), false);
                break;
            case R.id.nav_internships:
                changeFragment(FilteredListFragment.newInstance(OppType.Internships), false);
                break;
            case R.id.nav_conferences:
                changeFragment(FilteredListFragment.newInstance(OppType.Conferences), false);
                break;
            case R.id.nav_competitions:
                changeFragment(FilteredListFragment.newInstance(OppType.Competitions), false);
                break;
            case R.id.nav_grants:
                changeFragment(FilteredListFragment.newInstance(OppType.Grants), false);
                break;
            case R.id.nav_miscellaneous:
                changeFragment(FilteredListFragment.newInstance(OppType.Miscellaneous), false);
                break;
            default: break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment, boolean add) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment);
        if (add)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}

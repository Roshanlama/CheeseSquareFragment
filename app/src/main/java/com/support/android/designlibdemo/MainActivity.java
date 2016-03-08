/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.support.android.designlibdemo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity implements
        CheeseListFragment.OnCheeseListFragmentInteractionListener,
        CheeseDetailFragment.OnCheeseDetailFragmentListener,
        CheeseCategoriesFragment.OnCheeseCategoriesFragmentListener {

    private DrawerLayout mDrawerLayout;
    private ImageView imageView;
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBar;
    private Toolbar toolbar;

    public CollapsingToolbarLayout getCollapsingToolbar() {
        return collapsingToolbar;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        syncFrags();
    }

    private void syncFrags() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof CheeseCategoriesFragment) {
            disableCollapse();
        } else if (fragment instanceof CheeseDetailFragment) {
            enableCollapse();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBar = (AppBarLayout) findViewById(R.id.appbar);
        imageView = (ImageView) findViewById(R.id.backdrop);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        loadBackdrop();
        launchCheeseCategoriesFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

    private void launchCheeseCategoriesFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, CheeseCategoriesFragment.newInstance());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void launchCheeseDetailFragment(String string) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, CheeseDetailFragment.newInstance(string));
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void disableCollapse() {
        imageView.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        collapsingToolbar.setTitleEnabled(false);

    }

    @Override
    public void enableCollapse() {
        imageView.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.GONE);
        collapsingToolbar.setTitleEnabled(true);
    }

    @Override
    public void onCheeseClick(String string) {
        launchCheeseDetailFragment(string);
    }
}

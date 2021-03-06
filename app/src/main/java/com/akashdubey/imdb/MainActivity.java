package com.akashdubey.imdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.akashdubey.imdb.db.Constants;
import com.akashdubey.imdb.network.MyWebService;

import static com.akashdubey.imdb.WebList.myWebService;
import static com.akashdubey.imdb.db.Constants.refreshStatus;
import static com.akashdubey.imdb.db.Constants.runOnce;


// Even thou this class is named MainActivity it is just a holder for Menus and associated actions

public class MainActivity extends AppCompatActivity {

    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebService = new MyWebService();
        if (runOnce == false) {
            myWebService.getUpcomingMovies();
            myWebService.getGuestSessionID();
            runOnce = true;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //disabling refresh menu item on app load
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.itemRefresh);
        if (refreshStatus == false) {
            item.setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.in_app_choices, menu);
        return true;
    }

    //action to be taken on corresponding item selection,mostly opening anotehr activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.itemFavourite:
                Toast.makeText(this, "Favourite movies", Toast.LENGTH_SHORT).show();
                refreshStatus = true;
                intent = new Intent(MainActivity.this, UserMovieList.class);
                bundle = new Bundle();
                bundle.putString("search", "favourites");
                bundle.putString("refresh", "no");
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.itemWatchlist:
                Toast.makeText(this, "Watch bucket list", Toast.LENGTH_SHORT).show();
                refreshStatus = true;
                intent = new Intent(MainActivity.this, UserMovieList.class);
                bundle = new Bundle();
                bundle.putString("search", "watchlater");
                bundle.putString("refresh", "no");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.itemRefresh:
                intent = new Intent(MainActivity.this, UserMovieList.class);
                bundle = new Bundle();
                bundle.putString("refresh", "yes");
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.itemMostPopularMovies:
                intent = new Intent(MainActivity.this, WebList.class);
                bundle = new Bundle();
                bundle.putString("type", "popular");
                intent.putExtras(bundle);
                startActivity(intent);
                break;


            case R.id.itemUpcomingMovies:
                intent = new Intent(MainActivity.this, WebList.class);
                bundle = new Bundle();
                bundle.putString("type", "upcoming");
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.itemLatestMovies:
                intent = new Intent(MainActivity.this, WebList.class);
                bundle = new Bundle();
                bundle.putString("type", "latest");
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.itemNowPlayingMovies:
                intent = new Intent(MainActivity.this, WebList.class);
                bundle = new Bundle();
                bundle.putString("type", "playing");
                intent.putExtras(bundle);
                startActivity(intent);
                break;


            case R.id.itemExit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Exit IMDB App ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //since we open multiple activities, to properly exit from here,
                        // let's go back to our Main Activity which is WebList
                        // and set clear the activity stack for the intent
                        Intent intent = new Intent(getApplicationContext(), WebList.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                        dialogInterface.dismiss();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
                break;

            case R.id.itemTopRatedMovies:
                intent = new Intent(MainActivity.this, WebList.class);
                bundle = new Bundle();
                bundle.putString("type", "toprated");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void exitApp() {

    }
}

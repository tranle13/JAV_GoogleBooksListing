
    // Name: Tran Le
    // JAV1 - 1808
    // File name: MainActivity.java

package com.sunny.android.letran_ce07;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity implements BookProcessTask.OnFinished {

        private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = (LinearLayout)findViewById(R.id.linear);

        // Check connection to start task
        if (isConnected()) {
            BookProcessTask bookTask = new BookProcessTask(MainActivity.this, MainActivity.this);
            bookTask.execute();
            layout.setVisibility(View.INVISIBLE);
        } else {
            layout.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, R.string.no_connectivity, Toast.LENGTH_SHORT).show();
        }
    }

    // Function to check if there is an internet connection
    private boolean isConnected() {
        ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            Log.i(TAG, "It's connected!");
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if (info != null) {
                return info.isConnected();
            }
        }

        return false;
    }

    // Function to populate the ListView after data is done being pulled down
    @Override
    public void onFinished(ArrayList<Book> library) {
        BookAdapter adapter = new BookAdapter(this, library);
        GridView grid = (GridView)findViewById(R.id.gridView);
        grid.setAdapter(adapter);
    }
}

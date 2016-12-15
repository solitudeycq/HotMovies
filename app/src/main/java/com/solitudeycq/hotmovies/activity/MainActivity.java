package com.solitudeycq.hotmovies.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.solitudeycq.hotmovies.R;
import com.solitudeycq.hotmovies.fragment.RecyclerViewMoviesFragment;
import com.solitudeycq.hotmovies.utils.CheckNetWork;

public class MainActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final boolean isSave = (savedInstanceState == null);

        if(!CheckNetWork.isOnline(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.alert_title));
            builder.setIcon(R.drawable.alert_icon);
            builder.setMessage(getString(R.string.alert_message));
            builder.setPositiveButton(getString(R.string.alert_continue), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this,getString(R.string.toast_message),Toast.LENGTH_SHORT).show();
                    if (isSave) {
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, new RecyclerViewMoviesFragment())
                                .commit();
                    }
                }
            });
            builder.setNegativeButton(getString(R.string.alert_quit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else{
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new RecyclerViewMoviesFragment())
                        .commit();
            }
        }
    }
}

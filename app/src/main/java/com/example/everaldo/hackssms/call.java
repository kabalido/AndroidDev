package com.example.everaldo.hackssms;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;


public class call extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar =  getActionBar();

        bar.setTitle("Back");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setHomeButtonEnabled(true);
        bar.setLogo(R.drawable.ic_action_photo);

        //getActionBar().setLogo(R.drawable.ic_action_photo);
        Bundle b = getIntent().getExtras();

        String number = b.get("number").toString();
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(i);
        finish();

    }
}

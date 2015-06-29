package com.example.everaldo.hackssms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button b, btnSelect;
    private Preference prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button)findViewById(R.id.button);

        btnSelect = (Button)findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //selectImage();
            }
        });

        prefs = new Preference(MainActivity.this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Input");
                final EditText input = new EditText(MainActivity.this);

                dialog.setView(input);
                input.setText(prefs.getUrl());

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        prefs.storeUrl(input.getText().toString());
                        Toast.makeText(MainActivity.this,"Saved successfully", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setIcon(R.drawable.ic_action_photo);
                dialog.setTitle("Configure Computer Address");
                dialog.setMessage("Inform the Address or IP");
                dialog.setCancelable(false);
                dialog.show();
            }
        });
    }


    /*public void selectImage(){

        final CharSequence[] items = {"Take Photo", "Choose Photo", "Cancel"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select photo to send");
        dialog.setItems(items, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface d, int i){
                if (items[ i ].equals("Take Photo")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File..."), 200);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 200 && resultCode == RESULT_OK){

            Uri selectedImageUri = data.getData();
            String [] projection = {MediaStore.MediaColumns.DATA};
            Cursor c = getContentResolver().query(selectedImageUri, projection, null, null, null);
            if(c.moveToFirst()){

            }

        }
        //super.onActivityResult(requestCode, resultCode, data);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

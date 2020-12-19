package com.SD.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteView extends AppCompatActivity {

EditText nota;
String fileOpened="";

//TODO:add font options to personalize notes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        Intent intent = getIntent();
        String fileName = intent.getStringExtra("Nombre del archivo");
        fileOpened = fileName;
        setTitle(fileOpened);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteOperations.Save(getApplicationContext(),fileName,nota);
            }
        });
        try {
            nota = (EditText) findViewById(R.id.EditText1);
            nota.setText(NoteOperations.Open(getApplicationContext(),fileName));
        }
        catch(Exception e) {
            Toast.makeText(this,"Error:"+e.toString(),Toast.LENGTH_LONG).show();
        }
    }

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
            Intent myIntent = new Intent(this, NoteManager.class);
            startActivity(myIntent);
            return true;
        }

        if(id == R.id.delete) {
            Intent myIntent = new Intent(this, NoteManager.class);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Borrar "+fileOpened);
            builder.setMessage("¿Está seguro que quiere borrar este archivo?");

            // Botones
            builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                   NoteOperations.Delete(getApplicationContext(),fileOpened);
                   Toast.makeText(getApplicationContext(),"Nota borrada",Toast.LENGTH_SHORT).show();
                   startActivity(myIntent);
                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // create and show
            // the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

}
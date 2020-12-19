package com.SD.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NoteManager extends AppCompatActivity implements NotesAdapter.OnNoteListener {

    private final List<NotesBuilder> notesList = new ArrayList<>();
    NotesAdapter nAdapter = new NotesAdapter(notesList, this::onNoteClick);

    //TODO:add a "last modified" datetime

    //TODO:Refactor if it is possible to do so

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_manager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote();
            }
        });



        RecyclerView notesRecycler = findViewById(R.id.notes);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        notesRecycler.setLayoutManager(mLayoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());
        Loadnotes(notesRecycler);
    }

    @Override
    public void onNoteClick(int position) {
        NotesBuilder note = notesList.get(position);
        Intent intent = new Intent(this,NoteView.class);
        intent.putExtra("Nombre del archivo",note.getTitle());
        startActivity(intent);
    }

    public void Loadnotes(RecyclerView R){
        R.setAdapter(nAdapter);
        NoteOperations.prepareNotes(getApplicationContext(),notesList);
    }

    public void newNote() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crea una nota");


        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);

        // Botones
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
                {
                    EditText editText = customLayout.findViewById(R.id.editText);
                    String name = editText.getText().toString();
                    if(name.equals("")){
                        dialog.cancel();
                        WarningDialog();
                    }
                    else {
                        if (NoteOperations.FileExists(getApplicationContext(), name + ".txt")) {
                            dialog.cancel();
                            overWriteDialog(name);
                        } else addNote(name);
                    }
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

    private void addNote(String data)
    {
        NotesBuilder nuevo= new NotesBuilder(data+".txt","");
        notesList.add(nuevo);
        nAdapter.notifyItemInserted(notesList.size()-1);
        NoteOperations.Create(getApplicationContext(),nuevo.getTitle());
    }

    private void overWriteNote(String data) {
        NotesBuilder nuevo = new NotesBuilder(data + ".txt", "");
        notesList.add(nuevo);
        finish();
        startActivity(getIntent());
        nAdapter.notifyItemInserted(notesList.size() - 1);
        NoteOperations.Create(getApplicationContext(), nuevo.getTitle());
    }

    public void overWriteDialog(String fileName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(fileName+".txt ya existe");
        builder.setMessage("¿Quiere sobre-escribir este archivo?");

        // Botones
        builder.setPositiveButton("Sobre-escribir", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                overWriteNote(fileName);
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

    public void WarningDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("No puede crear notas sin nombre");

        // Boton
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        // create and show
        // the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


}
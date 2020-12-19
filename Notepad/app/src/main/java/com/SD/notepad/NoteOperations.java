package com.SD.notepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class NoteOperations extends AppCompatActivity {

    //Create a File
    public static void Create(Context C, String fileName) {
        try{
            OutputStreamWriter out =
                    new OutputStreamWriter(C.openFileOutput(fileName,0));
            out.write("");
            out.close();
            Toast.makeText(C.getApplicationContext(),"¡Nota Creada!",Toast.LENGTH_SHORT).show();
        }
        catch (Throwable t){
            Toast.makeText(C.getApplicationContext(),"Error:"+t.toString(),Toast.LENGTH_LONG).show();
        }
    }

    //Deletes a File

    public static void Delete(Context C,String fileName) {

        if(FileExists(C.getApplicationContext(),fileName)) {
            File file = C.getFileStreamPath(fileName);
            file.delete();
        }
    }

    //Saves a File with the fileName and a editText content
    public static void Save(Context C,String fileName, EditText nota) {
        try{
            OutputStreamWriter out =
                    new OutputStreamWriter(C.openFileOutput(fileName,0));
            out.write(nota.getText().toString());
            out.close();
            Toast.makeText(C.getApplicationContext(),"¡Nota Guardada!",Toast.LENGTH_SHORT).show();
        }
        catch (Throwable t){
            Toast.makeText(C.getApplicationContext(),"Error:"+t.toString(),Toast.LENGTH_LONG).show();
        }
    }

    //Verifies if the File exists
    public static boolean FileExists(Context C,String fname){
        File file = C.getApplicationContext().getFileStreamPath(fname);
        return file.exists();
    }

    //Opens a File
    public static String Open(Context C,String fileName){
        String content="";
        if(FileExists(C,fileName)) {
            try {
                InputStream in = C.openFileInput(fileName);
                if(in!=null) {
                    InputStreamReader tmp = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(tmp);
                    String str;
                    StringBuilder buf= new StringBuilder();
                    while((str = reader.readLine())!=null) buf.append(str + "\n");
                    in.close();
                    content = buf.toString();
                }
            }
            catch(java.io.FileNotFoundException e){} catch (Throwable t){
                Toast.makeText(C.getApplicationContext(), "Error: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }
        else return "";
        return content;
    }

    //TODO:include the last modified field in the method
    //sets the Adapter´s List
    public static void prepareNotes(Context C, List<NotesBuilder> notesList) {
        File directory;
        directory = C.getFilesDir();
        File[] files = directory.listFiles();
        int len = files.length;
        if (len != 0) {
            for (int f = 0; f < len; f++) {
                String fileName = files[f].getName();
                NotesBuilder note = new NotesBuilder(fileName, Open(C.getApplicationContext(), fileName));
                notesList.add(note);
            }
        }
    }

}

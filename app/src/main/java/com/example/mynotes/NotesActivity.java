package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NotesActivity extends AppCompatActivity {
EditText editText;
int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        editText = (EditText)findViewById(R.id.editText);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteid",-1);

        if(noteId != -1){
            editText.setText(MainActivity.notes.get(noteId));
        }else{
            MainActivity.notes.add(" ");
            noteId = MainActivity.notes.size() - 1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    MainActivity.notes.set(noteId,String.valueOf(charSequence));
                    MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);
                HashSet<String> hashSet = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", hashSet).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





    }
}

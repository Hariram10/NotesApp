package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    ListView listView;
   static  ArrayList<String> notes = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        sharedPreferences= getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);

        HashSet<String> set =(HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if(set == null){
            notes.add("Example Notes");
        }else {
            notes = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),NotesActivity.class);
                intent.putExtra("noteid",i);
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDel = i;

                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure?")
                        .setTitle("Do you want to delete this note?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                            notes.remove(itemToDel);
                            arrayAdapter.notifyDataSetChanged();

                        HashSet<String> hashSet = new HashSet<>(MainActivity.notes);
                        sharedPreferences.edit().putStringSet("notes", hashSet).apply();

                    }
                })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.notes_add){
            Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
            startActivity(intent);

            return true;
        }
        return false;
    }
}

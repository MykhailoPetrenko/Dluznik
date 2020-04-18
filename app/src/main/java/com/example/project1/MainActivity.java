package com.example.project1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Button addButton;
    public ListView listView;
    ArrayAdapter<Person> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStartDataPerson();
        addListenerOnButton();
        fullList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    private void fullList() {
        listView = (ListView) findViewById(R.id.listOwe);
        adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, Person.personList);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Do you want to delete person?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Person.personList.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alet = builder.create();
                        alet.setTitle("Edit Person");
                        alet.show();
                        return true;
                    }
                  /*  @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Person person = (Person) listView.getItemAtPosition(position);
                        Intent intent = new Intent("com.example.project1.PersonActiviti");
                        intent.putExtra("person",person);
                        intent.putExtra("index", position);
                        startActivity(intent);
                    }*/
                }
        );
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Person person = (Person) listView.getItemAtPosition(position);
                        Intent intent = new Intent("com.example.project1.PersonActiviti");
                        intent.putExtra("person",person);
                        intent.putExtra("index", position);
                        startActivity(intent);
                    }
                }
        );

    }


    private void addListenerOnButton(){
        addButton = (Button)findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.project1.PersonActiviti");
                startActivity(intent);
            }
        });
    }
    private void setStartDataPerson() {
        Person.addPerson(new Person("Mykhailo","Petrenko",30.5));
        Person.addPerson(new Person("Volodymir","Chaplykin",17.5));
        Person.addPerson(new Person("Mateusz","Przewodek",81.8));
        Person.addPerson(new Person("Roman","Zhurba",22.0));

    }
}

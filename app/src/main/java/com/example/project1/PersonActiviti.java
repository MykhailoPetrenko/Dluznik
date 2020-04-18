package com.example.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonActiviti extends AppCompatActivity {
    EditText editImie, editNazwisko, editDlug;
    Button button, simulation;
    Intent intent;
    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_activiti);
        editImie = (EditText) findViewById(R.id.editImie);
        editNazwisko = (EditText) findViewById(R.id.editNazwisko);
        editDlug = (EditText) findViewById(R.id.editDlug);
        button = (Button) findViewById(R.id.actionButton);
        intent = getIntent();
        person = intent.getParcelableExtra("person");
        if(person!=null){
            String str = getString(R.string.buttonEdit);
            button.setText(str);
            editImie.setText(person.getImie());
            editNazwisko.setText(person.getNazwisko());
            editDlug.setText(String.valueOf(person.getDlug()));
        }
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        simulation = (Button) findViewById(R.id.buttonSimulation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (person == null) {
                        if(editImie.getText().toString().equals("") || editNazwisko.getText().toString().equals(""))
                            throw new NumberFormatException();
                        Person newPerson = new Person(editImie.getText().toString(), editNazwisko.getText().toString(), Double.parseDouble(editDlug.getText().toString()));
                        Person.addPerson(newPerson);
                        Toast.makeText(
                                PersonActiviti.this,
                                "Success!",
                                Toast.LENGTH_SHORT
                        ).show();
                        finish();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PersonActiviti.this);
                        builder.setMessage("Do you want edit person?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int index = intent.getIntExtra("index", 0);
                                        Person.personList.set(index, new Person(editImie.getText().toString(), editNazwisko.getText().toString(), Double.parseDouble(editDlug.getText().toString())));
                                        finish();
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
                    }
                }catch (NumberFormatException n){
                    Toast.makeText(
                            PersonActiviti.this,
                            "Dane niepoprawne!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        simulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                if(editImie.getText().toString().equals("") && editNazwisko.getText().toString().equals("")){
                    Toast.makeText(
                            PersonActiviti.this,
                            "Imie i nazwisko nie moga byc puste",
                            Toast.LENGTH_SHORT
                    ).show();
                }else {
                    Intent intentNew = new Intent("com.example.project1.SimulationActiviti");
                    Person newPerson = new Person(editImie.getText().toString(), editNazwisko.getText().toString(), Double.parseDouble(editDlug.getText().toString()));
                    int tmp = Person.personList.size();
                    if(person == null)
                    Person.personList.add(newPerson);
                    int index = intent.getIntExtra("index", tmp);
                    intentNew.putExtra("personSimulation", newPerson);
                    intentNew.putExtra("id", index);
                    startActivity(intentNew);
                    finish();
                }
                }catch (NumberFormatException n){
                    Toast.makeText(
                            PersonActiviti.this,
                            "Dlug musi byc liczba!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
}

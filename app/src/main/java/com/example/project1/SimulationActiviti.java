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
import android.widget.TextView;
import android.widget.Toast;

public class SimulationActiviti extends AppCompatActivity {
    private Button start, stop, reset;
    private TextView personText, dlugText;
    private EditText predkosc, prowizja;
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    private Intent intent;
    private double odcetkiSuma = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_activiti);
        addListenerOnButton();

        personText = (TextView)findViewById(R.id.simulationPerson);
        dlugText = (TextView)findViewById(R.id.simulationDlug);
        predkosc = (EditText) findViewById(R.id.predkosc);
        prowizja = (EditText) findViewById(R.id.prowizja);

        intent = getIntent();
        Person person = intent.getParcelableExtra("personSimulation");
        personText.setText(person.getImie() + " " + person.getNazwisko());
        dlugText.setText(String.valueOf(person.getDlug()));

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
    }

    private void addListenerOnButton() {
        start = (Button)findViewById(R.id.buttonStart);
        stop = (Button)findViewById(R.id.buttonStop);
        reset = (Button)findViewById(R.id.buttonReset);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predkosc.setEnabled(false);
                prowizja.setEnabled(false);
                runSimulation();
                running = true;
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SimulationActiviti.this);
                builder.setMessage("Chces skonczyc symulacje?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int index = intent.getIntExtra("id", 0);
                                String strPerson = personText.getText().toString();
                                String str[] = strPerson.split("\\s");
                                Person.personList.set(index, new Person(str[0], str[1], Double.parseDouble(dlugText.getText().toString())));
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
                running = false;
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                //dialog fragment
                AlertDialog.Builder builder = new AlertDialog.Builder(SimulationActiviti.this);
                builder.setMessage("Chces anulowac dlug?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int index = intent.getIntExtra("id", 0);
                                Person.personList.remove(index);
                                Toast.makeText(
                                        SimulationActiviti.this,
                                        "Dlug zostal splacony! Odsetki:" + odcetkiSuma,
                                        Toast.LENGTH_LONG
                                ).show();
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
        });
    }
    private void runSimulation(){
        final TextView timeView = (TextView)findViewById(R.id.timerView);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                double dlug = Double.parseDouble(dlugText.getText().toString());
                double odcetki = 0;
                if(running) {
                    odcetki= ((Double.parseDouble(dlugText.getText().toString()) *  Double.parseDouble(prowizja.getText().toString()))/100);
                    odcetkiSuma+= odcetki;
                    dlug = (Double.parseDouble(dlugText.getText().toString()) - Double.parseDouble(predkosc.getText().toString())) + odcetki;
                    if(dlug < 0) {
                        running = false;
                        dlugText.setText("0.0");
                        Toast.makeText(
                                SimulationActiviti.this,
                                "Dlug zostal splacony! Odsetki:" + odcetkiSuma,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                    seconds++;}
                if(dlug > 0)
                dlugText.setText(String.valueOf(dlug));
                timeView.setText(time);
                handler.postDelayed(this, 1000);
            }
        });

    }
    @Override
    protected void onStop(){
        super.onStop();
        wasRunning= running;
        running = false;
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(wasRunning) running = true;
    }
}

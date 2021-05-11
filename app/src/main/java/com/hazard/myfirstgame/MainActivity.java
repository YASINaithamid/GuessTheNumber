package com.hazard.myfirstgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button buttonOk;
    private EditText editTextNumber;
    private TextView textViewIndication;
    private int secret;
    private TextView textViewNumber;
    private ProgressBar progressBarTentatives;
    private TextView textViewTentatives;
    private ListView listViewHistorique;
    private int counter;
    private int score;
    private List<String> listHistorique=new ArrayList<>();
    private int maxTentatives=7;
    private ArrayAdapter<String> model;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOk=findViewById(R.id.buttonOK);
        editTextNumber=findViewById(R.id.editTextNumber);
        textViewIndication=findViewById(R.id.textViewIndication);
        progressBarTentatives=findViewById(R.id.progressBarTentatives);
        textViewTentatives=findViewById(R.id.textViewTentatives);
        listViewHistorique=findViewById(R.id.listViewHistorique);
        textViewNumber=findViewById(R.id.textViewNumber);



        model=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listHistorique);
        listViewHistorique.setAdapter(model);
        initialisation();
        buttonOk.setOnClickListener((evt)->{
            int number=Integer.parseInt(editTextNumber.getText().toString());

            if(number>secret){
                textViewIndication.setText(R.string.sup_valeur);


            }
            else if (number<secret){
                textViewIndication.setText(R.string.inf_valeur);

            }
            else {
                textViewIndication.setText(R.string.str_bravo);
                score+=5;
                textViewNumber.setText(String.valueOf(score));
                rejouer();

            }
            listHistorique.add(counter+"=>"+number);
            model.notifyDataSetChanged();
            ++counter;
            textViewTentatives.setText(String.valueOf(counter));
            progressBarTentatives.setProgress(counter);
            if(counter>maxTentatives){
                rejouer();
            }
        });
    }

    private void rejouer() {
        Log.i("MyLog","Rejouer ....");
        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.str_rejouer));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.oui), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initialisation();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.quiter), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();

    }

    private void initialisation() {
        secret=1+((int)(Math.random()*100));
        counter=0;
        listHistorique.clear();model.notifyDataSetChanged();
        textViewTentatives.setText(String.valueOf(counter));
        progressBarTentatives.setProgress(counter);
        progressBarTentatives.setMax(maxTentatives);
        textViewNumber.setText(String.valueOf(score));

    }
}
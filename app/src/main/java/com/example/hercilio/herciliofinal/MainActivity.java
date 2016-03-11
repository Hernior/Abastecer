package com.example.hercilio.herciliofinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    String name;
    String tipoCombustivel;
    float gasolinePrice = 0;
    float ethanolPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button shareButton = (Button) findViewById(R.id.shareSocialNetwork);
        shareButton.setVisibility(View.INVISIBLE);
    }

    public void calculateCostBenefit(View v) {
        String resultTitle = "Melhor abastecer com";
        String resultText;
        String resultColor;

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Atenção");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        EditText inputName = (EditText)findViewById(R.id.nameInput);
        name = inputName.getText().toString();
        if(name.length() < 1){
            alertDialog.setMessage("Preencha o nome do posto de combustíveis antes de calcular.");
            alertDialog.show();
        }
        else {
            EditText inputGasoline = (EditText)findViewById(R.id.gasolineInput);
            EditText inputEthanol = (EditText)findViewById(R.id.ethanolInput);

            if(inputGasoline.getText().length() == 0 || inputEthanol.getText().length() == 0) {
                alertDialog.setMessage("Você precisa preencher os preços antes de calcular.");
                alertDialog.show();
            }
            else {
                gasolinePrice = Float.parseFloat(inputGasoline.getText().toString());
                ethanolPrice = Float.parseFloat(inputEthanol.getText().toString());

                if(gasolinePrice*0.7 > ethanolPrice) {
                    tipoCombustivel = "ETANOL";
                    resultColor = "#00BFA5";
                    resultText = "A gasolina só valeria a pena se o etanol custar acima de "+NumberFormat.getCurrencyInstance().format(gasolinePrice * 0.7);
                }
                else {
                    tipoCombustivel = "GASOLINA";
                    resultColor = "#FFD600";
                    resultText = "O etanol só valeria a pena por um preço abaixo de "+NumberFormat.getCurrencyInstance().format(gasolinePrice*0.7);
                }
                displayResult(resultTitle, tipoCombustivel, resultColor, resultText);
            }
        }
    }

    private void displayResult(String intro, String result, String color, String message) {
        TextView resultTextViewIntro = (TextView) findViewById(R.id.resultTitle);
        resultTextViewIntro.setText(intro);

        TextView resultTextViewValue = (TextView) findViewById(R.id.resultValue);
        resultTextViewValue.setText(result);
        resultTextViewValue.setTextColor(Color.parseColor(color));

        TextView resultTextViewMessage= (TextView) findViewById(R.id.resultText);
        resultTextViewMessage.setText(message);

        Button shareButton = (Button) findViewById(R.id.shareSocialNetwork);
        shareButton.setVisibility(View.VISIBLE);
    }

    public void onClickWhatsApp(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "No posto: " + name + "\n" +
                "Vale mais a pena abastecer com " + tipoCombustivel + " hoje.\n" +
                "Gasolina: "+NumberFormat.getCurrencyInstance().format(gasolinePrice)+"\n" +
                "Etanol: "+NumberFormat.getCurrencyInstance().format(ethanolPrice));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
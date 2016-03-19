package com.example.hercilio.herciliofinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    public static final String RESULT_NAME = "com.example.hercilio.herciliofinal.RESULT";
    public static final String RESULT_COLOR = "com.example.hercilio.herciliofinal.COLOR";
    public static final String RESULT_TEXT = "com.example.hercilio.herciliofinal.TEXT";
    public static final String SHARE_GAS_STATION = "com.example.hercilio.herciliofinal.GAS_STATION";
    public static final String SHARE_GASOLINE_PRICE = "com.example.hercilio.herciliofinal.GASOLINE_PRICE";
    public static final String SHARE_ETHANOL_PRICE = "com.example.hercilio.herciliofinal.ETHANOL_PRICE";
    String name;
    String tipoCombustivel;
    float gasolinePrice = 0;
    float ethanolPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calculateCostBenefit(View v) {
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
                displayResult(tipoCombustivel, resultColor, resultText);
            }
        }
    }

    private void displayResult(String result, String color, String message) {
        Intent intent = new Intent(this, Result.class);

        intent.putExtra(RESULT_NAME, result);
        intent.putExtra(RESULT_COLOR, color);
        intent.putExtra(RESULT_TEXT, message);

        intent.putExtra(SHARE_GAS_STATION, name);
        intent.putExtra(SHARE_GASOLINE_PRICE, NumberFormat.getCurrencyInstance().format(gasolinePrice));
        intent.putExtra(SHARE_ETHANOL_PRICE, NumberFormat.getCurrencyInstance().format(ethanolPrice));
        startActivity(intent);
    }
}
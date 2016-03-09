package com.example.hercilio.herciliofinal;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String name;
    String tipoCombustivel;
    double gasolinePrice = 0;
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

        EditText inputName = (EditText)findViewById(R.id.nameInput);
        name = inputName.getText().toString();

        EditText inputGasoline = (EditText)findViewById(R.id.gasolineInput);
        gasolinePrice = Float.parseFloat(inputGasoline.getText().toString());

        EditText inputEthanol = (EditText)findViewById(R.id.ethanolInput);
        ethanolPrice = Float.parseFloat(inputEthanol.getText().toString());

        if(gasolinePrice*0.7 > ethanolPrice) {
            tipoCombustivel = "ETANOL";
            resultColor = "#00BFA5";
            resultText = "A gasolina só valeria a pena se o etanol custar acima de "+String.format("%.2f", gasolinePrice*0.7);
        }
        else {
            tipoCombustivel = "GASOLINA";
            resultColor = "#FFD600";
            resultText = "O etanol só valeria a pena por um preço abaixo de "+String.format("%.2f", gasolinePrice*0.7);
        }
        displayResult(resultTitle, tipoCombustivel, resultColor, resultText);
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
        sendIntent.putExtra(Intent.EXTRA_TEXT, "No posto de combustíveis " + name + " vale mais a pena abastecer com " + tipoCombustivel + " hoje.\n(Gasolina: "+String.format("%.2f", gasolinePrice)+" Etanol: "+ethanolPrice+")");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}

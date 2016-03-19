package com.example.hercilio.herciliofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Color;
import android.view.View;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String result = intent.getStringExtra(MainActivity.RESULT_NAME);
        String color = intent.getStringExtra(MainActivity.RESULT_COLOR);
        String text = intent.getStringExtra(MainActivity.RESULT_TEXT);

        // Defining result name
        TextView resultTextViewValue = (TextView) findViewById(R.id.resultValue);
        resultTextViewValue.setText(result);

        // Defining result color
        resultTextViewValue.setTextColor(Color.parseColor(color));

        // Defining result phrase
        TextView resultTextViewMessage = (TextView) findViewById(R.id.resultText);
        resultTextViewMessage.setText(text);
    }

    public void shareResult(View view) {
        Intent intent = getIntent();
        String result = intent.getStringExtra(MainActivity.RESULT_NAME);
        String gasStation = intent.getStringExtra(MainActivity.SHARE_GAS_STATION);
        String gasoline = intent.getStringExtra(MainActivity.SHARE_GASOLINE_PRICE);
        String ethanol = intent.getStringExtra(MainActivity.SHARE_ETHANOL_PRICE);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "No posto: " + gasStation + "\n" +
                "Vale mais a pena abastecer com " + result + " hoje.\n" +
                "Gasolina: " + gasoline + "\n" +
                "Etanol: " + ethanol);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}

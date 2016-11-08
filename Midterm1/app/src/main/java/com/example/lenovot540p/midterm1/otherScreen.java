package com.example.lenovot540p.midterm1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by LenovoT540p on 3/9/2015.
 */

public class otherScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other);
        TextView textview = (TextView) findViewById(R.id.textView);
        Button exit = (Button) findViewById(R.id.exit);
        Button callthedealer = (Button) findViewById(R.id.callthedealer);
        Button viewmore = (Button) findViewById(R.id.viewmore);
        Intent intent = getIntent();
        String info = intent.getStringExtra("textinput");
        textview.setTextColor(Color.parseColor("#D00D0D"));
        textview.setText(info);

        exit.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home= new Intent(Intent.ACTION_MAIN);

                home.addCategory(Intent.CATEGORY_HOME);

                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(home);

            }
        });

        callthedealer.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Dealer Called", Toast.LENGTH_LONG).show();

            }
        });

        viewmore.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

package com.example.lenovot540p.midterm1;


import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends ListActivity
{

    private ArrayList<String> cars;
    private ArrayAdapter<String> listContent;
    //private  String[] carname = new String[] { "Gallant", "PT Cruiser", "Camry", "Yaris", "DTS", "300C", "Town And Country", "Navigator", "Escalade", "Wrangler", "Silverado", "Jetta", "Malibu", "F150", "Corvette" };

    private  String[] carprice = new String[] { "$10,000", "$5,000", "$12,000", "$32,000", "$35,000", "$12,500", "$13,000", "$20,000", "$40,000", "$21,000", "$43,000", "$18,000", "$32,000", "$41,000", "$47,000" };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        String[] cars = new String[] { "Gallant", "PT Cruiser", "Camry", "Yaris", "DTS", "300C", "Town And Country", "Navigator", "Escalade", "Wrangler", "Silverado", "Jetta", "Malibu", "F150", "Corvette" };

        ArrayAdapter<String> listContent = new ArrayAdapter<String>(this, R.layout.example, cars);
        setListAdapter(listContent);



    }

    protected void onListItemClick(ListView t, View v, int position, long id)
    {
        String item = (String) getListAdapter().getItem(position);

        Intent nextScreen = new Intent(getApplicationContext(), otherScreen.class);
        String sendinfo = "Dealer Contact: 999-999-9999\nPrice: " + carprice[position]+"\nFinance Rate: 2.9% \nLease Rate: 3.9%";
        nextScreen.putExtra("textinput", sendinfo);
        startActivityForResult(nextScreen, 0);

    }


}
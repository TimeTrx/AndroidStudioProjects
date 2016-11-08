package com.example.lenovot540p.finalproject;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;


import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends ListActivity {

    private ArrayList<String> cars;
    private ArrayAdapter<String> listContent;
    //private  String[] carname = new String[] { "Gallant", "PT Cruiser", "Camry", "Yaris", "DTS", "300C", "Town And Country", "Navigator", "Escalade", "Wrangler", "Silverado", "Jetta", "Malibu", "F150", "Corvette" };

    private String[] email = new String[]{"$10,000", "$5,000", "$12,000", "$32,000", "$35,000", "$12,500", "$13,000", "$20,000", "$40,000", "$21,000", "$43,000", "$18,000", "$32,000", "$41,000", "$47,000"};
    private String[] phone = new String[]{"519-123-4567"};
    private String[] city = new String[]{"city"};
    private String[] province = new String[]{"Province"};
    private String[] zip = new String[]{"zipcode"};
    private String[] names = new String[]{"Gallant"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayAdapter<String> listContent = new ArrayAdapter<String>(this, R.layout.example, names);
        setListAdapter(listContent);


    }

    protected void onListItemClick(ListView t, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        Intent nextScreen = new Intent(getApplicationContext(), otherScreen.class);
        String sendinfo = "\n\n\n\nName: "+names[position]+"\nNumber: "+ phone[position]+"\nEmail: " + email[position] + "\nCity: " + city[position] +" \nState/Province: " + province[position] +" \nZip/Postal Codes: " + zip[position];
        nextScreen.putExtra("textinput", sendinfo);
        startActivityForResult(nextScreen, 0);

    }




}

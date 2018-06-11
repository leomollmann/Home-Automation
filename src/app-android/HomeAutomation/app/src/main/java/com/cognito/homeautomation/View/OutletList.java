package com.cognito.homeautomation.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cognito.homeautomation.Adapters.TOutletAdapter;
import com.cognito.homeautomation.Database.TOutlet;
import com.cognito.homeautomation.Database.TOutletDAO;
import com.cognito.homeautomattion.R;

import java.util.ArrayList;

public class OutletList extends AppCompatActivity{
    TOutletAdapter adapter;
    ListView listView;
    TOutletDAO tOutletDAO;
    TextView textView;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_list);
        textView = (TextView) findViewById(R.id.txtEmpty);
        textView.setVisibility(View.INVISIBLE);
        add = (Button) findViewById(R.id.btnAdd);
        listView = (ListView) findViewById(R.id.TOutletList);
        tOutletDAO = new TOutletDAO(OutletList.this);
        adapter = new TOutletAdapter(OutletList.this,new ArrayList<TOutlet>());
        adapter.addAll(tOutletDAO.getAll());
        if(adapter.getCount() == 0){
            textView.setVisibility(View.VISIBLE);
        }else {
            listView.setAdapter(adapter);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putInt("id",adapter.getCount());
                Intent i = new Intent(OutletList.this,AddDevice.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    public void update(){
        adapter = new TOutletAdapter(OutletList.this,new ArrayList<TOutlet>());
        adapter.addAll(tOutletDAO.getAll());
        if(adapter.getCount() == 0){
            textView.setVisibility(View.VISIBLE);
            listView.setAdapter(adapter);
        }else {
            textView.setVisibility(View.INVISIBLE);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }
}

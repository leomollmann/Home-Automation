package com.cognito.homeautomation.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cognito.homeautomation.Database.TOutlet;
import com.cognito.homeautomation.Database.TOutletDAO;
import com.cognito.homeautomattion.R;

public class OutletSettings extends AppCompatActivity {
    private TOutlet tOutlet;
    private TOutletDAO tOutletDAO;
    private boolean isPressed1;
    private boolean isPressed2;
    private boolean isPressed3;
    private ImageButton Outlet1;
    private ImageButton Outlet2;
    private ImageButton Outlet3;
    private EditText name;
    private EditText locale;
    private EditText Plug1Name;
    private EditText Plug2Name;
    private EditText Plug3Name;
    private TextView temp;
    private TextView energy;
    private TextView data;
    private Button Scheulde;
    private Button Histogram;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_settings);
        tOutletDAO = new TOutletDAO(this);
        tOutlet = tOutletDAO.get(getIntent().getIntExtra("DeviceID",0));
        index = tOutlet.getDeviceID();
        name = (EditText) findViewById(R.id.edtSetName);
        locale = (EditText) findViewById(R.id.edtSetLocale);
        Plug1Name = (EditText) findViewById(R.id.edtPlug1Name);
        Plug2Name = (EditText) findViewById(R.id.edtPlug2Name);
        Plug3Name = (EditText) findViewById(R.id.edtPlug3Name);
        data = (TextView) findViewById(R.id.txtData);
        temp = (TextView) findViewById(R.id.txtSetTemp);
        energy = (TextView) findViewById(R.id.txtSetEnergy);
        Scheulde = (Button) findViewById(R.id.btnScheulde);
        Histogram = (Button) findViewById(R.id.btnHistogram);

        name.setText(tOutlet.getName());
        locale.setText(tOutlet.getLocale());
        Plug1Name.setText(tOutlet.getPlug1());
        Plug2Name.setText(tOutlet.getPlug2());
        Plug3Name.setText(tOutlet.getPlug3());
        energy.setText(String.valueOf(tOutlet.getEnergy())+"W");
        temp.setText(String.valueOf(tOutlet.getTemperature())+"C°");

        isPressed1 = tOutlet.isState0();
        isPressed2 = tOutlet.isState1();
        isPressed3 = tOutlet.isState2();

        Outlet1 = (ImageButton) findViewById(R.id.imgbPlug1);
        Outlet2 = (ImageButton) findViewById(R.id.imgbPlug2);
        Outlet3 = (ImageButton) findViewById(R.id.imgbPlug3);

        Outlet1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tOutletDAO.updateState1(isPressed1,index);
                if (isPressed1)
                    Outlet1.setImageResource(R.drawable.ic_on_60);
                else
                    Outlet1.setImageResource(R.drawable.ic_off_60);
                isPressed1 = !isPressed1;
            }
        });
        Outlet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tOutletDAO.updateState2(isPressed2,index);
                if (isPressed2)
                    Outlet2.setImageResource(R.drawable.ic_on_60);
                else
                    Outlet2.setImageResource(R.drawable.ic_off_60);
                isPressed2 = !isPressed2;
            }
        });
        Outlet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tOutletDAO.updateState3(isPressed3,index);
                if (isPressed3)
                    Outlet3.setImageResource(R.drawable.ic_on_60);
                else
                    Outlet3.setImageResource(R.drawable.ic_off_60);
                isPressed3 = !isPressed3;
            }
        });

        if (isPressed1)
            Outlet1.setImageResource(R.drawable.ic_on_60);
        else
            Outlet1.setImageResource(R.drawable.ic_off_60);
        isPressed1 = !isPressed1;

        if (isPressed2)
            Outlet2.setImageResource(R.drawable.ic_on_60);
        else
            Outlet2.setImageResource(R.drawable.ic_off_60);
        isPressed2 = !isPressed2;

        if (isPressed3)
            Outlet3.setImageResource(R.drawable.ic_on_60);
        else
            Outlet3.setImageResource(R.drawable.ic_off_60);
        isPressed3 = !isPressed3;

        Scheulde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OutletSettings.this, TaskScheulde.class));
            }
        });
    }

    @Override
    protected void onPause() {
        String[] names = new String[5];
        names[0] = name.getText().toString();
        names[1] = locale.getText().toString();
        names[2] = Plug1Name.getText().toString();
        names[3] = Plug2Name.getText().toString();
        names[4] = Plug3Name.getText().toString();
        tOutletDAO.updateNames(names,tOutlet.getDeviceID());
        super.onPause();
    }

}

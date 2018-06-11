package com.cognito.homeautomation.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cognito.homeautomation.Database.TOutlet;
import com.cognito.homeautomation.Database.TOutletDAO;
import com.cognito.homeautomation.Holders.IDHolder;
import com.cognito.homeautomattion.R;

public class AddDevice extends AppCompatActivity{

    private EditText edtName;
    private EditText edtLocale;
    private Button btnSave;
    private Button btnRmv;
    private TOutletDAO tOutletDAO;
    private TOutlet tOutlet;
    private Bundle b;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        edtName = (EditText) findViewById(R.id.edtName);
        edtLocale = (EditText) findViewById(R.id.edtLocale);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnRmv = (Button) findViewById(R.id.btnRemove);
        tOutletDAO = new TOutletDAO(AddDevice.this);
        tOutlet = new TOutlet();
        b = getIntent().getExtras();
        index = b.getInt("id");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tOutlet.setName(edtName.getText().toString());
                tOutlet.setLocale(edtLocale.getText().toString());
                tOutlet.setState0(false);
                tOutlet.setState1(false);
                tOutlet.setState2(false);
                tOutlet.setEnergy(Float.parseFloat(String.format("%.2f", (float) Math.random() * 200)));
                tOutlet.setTemperature(Float.parseFloat(String.format("%.2f", (float) Math.random() * 30)));
                tOutlet.setHumidity(Float.parseFloat(String.format("%.2f", (float) Math.random() * 100)));
                tOutlet.setDeviceID(index);
                tOutlet.setPlug1("Left Plug");
                tOutlet.setPlug2("Front Plug");
                tOutlet.setPlug3("Right Plug");
                tOutlet.setUserID(IDHolder.getHolder().ID);
                tOutletDAO.insert(tOutlet);
                index++;
                //.update();
            }
        });

        btnRmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tOutletDAO.excluir(index-1);
            }
        });
    }
}

package com.cognito.homeautomation.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cognito.homeautomation.Database.TOutlet;
import com.cognito.homeautomation.Database.TOutletDAO;
import com.cognito.homeautomattion.R;

public class Menu extends AppCompatActivity {

    private TextView txtLampada, txtTomada, txtRemote, txtServer;
    private ImageButton imgLampada, imgTomada, imgRemote, imgServer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        txtTomada = (TextView) findViewById(R.id.txtTomada);
        imgTomada = (ImageButton) findViewById(R.id.imgTomada);

        txtTomada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, OutletList.class));
            }
        });
        imgTomada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, OutletList.class));
            }
        });

    }
}

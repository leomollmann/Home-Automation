package com.cognito.homeautomation.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.cognito.homeautomattion.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton imgButtonGo, imgButtonTutorial;
    private TextView login, tutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgButtonGo = (ImageButton) findViewById(R.id.imgButtonGo);
        imgButtonTutorial = (ImageButton) findViewById(R.id.imgButtonTutorial);
        login = (TextView)findViewById(R.id.login);
        tutorial = (TextView) findViewById(R.id.tutorial);

        imgButtonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}

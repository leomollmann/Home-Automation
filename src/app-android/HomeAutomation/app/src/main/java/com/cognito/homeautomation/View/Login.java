package com.cognito.homeautomation.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cognito.homeautomation.Holders.IDHolder;
import com.cognito.homeautomation.Database.UserDAO;
import com.cognito.homeautomattion.R;

public class Login extends AppCompatActivity {

    private Button btnEntrar;
    private Button btnSignUp;
    private TextView user;
    private TextView pass;
    private UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnEntrar = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSave);
        user = (TextView) findViewById(R.id.edtLoginUser);
        pass = (TextView) findViewById(R.id.edtLoginPass);
        userDAO = new UserDAO(this);


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = userDAO.check(user.getText().toString(),pass.getText().toString());
                if(id > 0) {
                    IDHolder holder = IDHolder.getHolder();
                    holder.ID = id;
                    startActivity(new Intent(Login.this, Menu.class));
                }else{
                    Toast.makeText(Login.this,"Senha ou usuário incorretos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

    }
}

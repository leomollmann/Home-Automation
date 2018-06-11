package com.cognito.homeautomation.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cognito.homeautomation.Database.UserDAO;
import com.cognito.homeautomattion.R;

public class SignUp extends AppCompatActivity {

    private EditText edtName, edtPassW, edtTelephone;
    private Button btnSave;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (EditText)findViewById(R.id.edtName);
        edtPassW = (EditText)findViewById(R.id.edtPassW);
        edtTelephone = (EditText)findViewById(R.id.edtTelephone);
        btnSave = (Button) findViewById(R.id.btnSave);
        userDAO = new UserDAO(SignUp.this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edtName.getText().toString();
                String senha = edtPassW.getText().toString();
                String telefone = edtTelephone.getText().toString();
                if(nome.length() > 7) {
                    if (senha.length() > 7) {
                        if (telefone.length() > 8) {
                            userDAO.insert(nome,senha,telefone);
                            Toast.makeText(SignUp.this,"Usuário inserido com sucesso",Toast.LENGTH_SHORT).show();
                        }else{Toast.makeText(SignUp.this,"Telefone inválido,  usar 9 caracteres ou mais",Toast.LENGTH_SHORT).show(); }
                    }else{Toast.makeText(SignUp.this,"Senha inválida,  usar 8 caracteres ou mais",Toast.LENGTH_SHORT).show();}
                }else{Toast.makeText(SignUp.this,"Nome inválido, usar 8 caracteres ou mais",Toast.LENGTH_SHORT).show();}
            }
        });
    }
}

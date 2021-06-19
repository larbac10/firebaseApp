package com.andre.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private Button btnCadastrar;
    private Button btnlogin;
    private EditText editEmail, editSenha;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnCadastrar = findViewById(R.id.login_btn_cadastrar);
        btnlogin = findViewById(R.id.login_btn_logar);
        editEmail = findViewById(R.id.login_edit_email);
        editSenha = findViewById(R.id.login_edit_senha);
        //caso usuario logado
        if (auth.getCurrentUser() != null) {
            String email = auth.getCurrentUser().getEmail();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            // passar email p/ mainActivity
            intent.putExtra("email", email);
            startActivity(intent);
        }


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(View -> {
            logar();
        });
    }

    public void logar() {
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        //t -> é uma tarefa para logar
        auth.signInWithEmailAndPassword(email, senha)
                // listener de sucesso
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "bem vindo", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                })
                // listener de falha
                .addOnFailureListener(e -> {
                    // parametro e -> exception
                    Toast.makeText(this, "Erro!" + e.getClass().toString(), Toast.LENGTH_LONG).show();
                    Log.e("Erro", "Mesagem:" + e.getMessage() + "classe:" + e.getClass().toString());
                    try {
                        //disparando a exceçõa
                        throw e;
                    } catch (FirebaseAuthInvalidUserException userException) {
                        //exececão p/ senha incarreta
                        Toast.makeText(this, "E-mail invalido", Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthInvalidCredentialsException credException) {
                        Toast.makeText(this, "senha incorreta", Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(this, "Erro!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
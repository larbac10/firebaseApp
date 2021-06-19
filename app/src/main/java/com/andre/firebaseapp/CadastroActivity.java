package com.andre.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CadastroActivity extends AppCompatActivity {
    private Button btnCadastrar;
    private EditText editEmail, editNome,editSenha;
    //referncia para autendicação
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        btnCadastrar = findViewById(R.id.cadastro_btn_cadastrar);
        editEmail = findViewById(R.id.cadastro_edit_email);
        editNome = findViewById(R.id.cadastr_edit_nome);
        editSenha =findViewById(R.id.cadastro_edit_senha);

        btnCadastrar.setOnClickListener(view ->{
            cadastrar();
        });
    }
    public void cadastrar(){
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String nome = editNome.getText().toString();

        if(email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Preencha os campos",Toast.LENGTH_SHORT).show();
            return;
        }
        //método para criar um usuario com e-mail e senha
        Task<AuthResult> t = auth.createUserWithEmailAndPassword(email,senha);
        t.addOnCompleteListener(task -> {
            // listener executndo com sucesso ou fracasso
            if(task.isSuccessful()){
                Toast.makeText(getApplicationContext(),"Usuario criado com sucesso",Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"Erro!", Toast.LENGTH_LONG).show();

            }
        });

        t.addOnSuccessListener(authResult -> {
            //request para mudar nome usuario
            UserProfileChangeRequest update = new UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(nome).build();


           //setando nome usuario
           authResult.getUser().updateProfile(update);
        });
    }


}



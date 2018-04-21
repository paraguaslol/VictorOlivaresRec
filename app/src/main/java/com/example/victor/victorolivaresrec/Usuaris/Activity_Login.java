package com.example.victor.victorolivaresrec.Usuaris;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victor.victorolivaresrec.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText etLogEmail,etLogPassword;
    String logEmail,logPassword;
    Button btnEntrar,btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        etLogEmail = findViewById(R.id.etLogEmail);
        etLogPassword = findViewById(R.id.etLogPassword);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
    }

    public void loginUsuari(View v){
        logEmail = etLogEmail.getText().toString();
        logPassword = etLogPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(logEmail, logPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            Toast.makeText(Activity_Login.this, "Sessió iniciada :)", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            getIntent().putExtra("userUID",user.getUid());
                            setResult(RESULT_OK,getIntent());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Activity_Login.this, "No se ha pogut iniciar sessió :(",
                                    Toast.LENGTH_SHORT).show();
                        }

                        Activity_Login.this.finish();
                    }
                });
    }

    public void registrarUsuari(View v){
        Intent intent = new Intent(this, Activity_Registrar.class);
        startActivity(intent);
    }
}

package com.example.victor.victorolivaresrec.Usuaris;


import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.victor.victorolivaresrec.Fragments.DadesUsuari_Fragment;
import com.example.victor.victorolivaresrec.R;
import com.example.victor.victorolivaresrec.Model.Usuari;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_Registrar extends AppCompatActivity implements DadesUsuari_Fragment.OnFragmentInteractionListener{

    DatabaseReference dbr;
    EditText etRegNomUsuari,etRegNom,etRegCognoms,etRegEmail,etRegPassword,etRegAdreça;
    String regNomUsuari,regNom,regCognoms,regEmail,regPassword,regAdreça;
    Button btnCancelar, btnOK;
    private FirebaseAuth mAuth;
    FragmentManager fm;
    FragmentTransaction ft;
    DadesUsuari_Fragment dadesUsuari_fragment;

    List<DadesUsuari_Fragment> myFragList = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__registrar);


        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        etRegNomUsuari = findViewById(R.id.etRegNomUsuari);
        etRegNom = findViewById(R.id.etRegNom);
        etRegCognoms = findViewById(R.id.etRegCognoms);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegAdreça = findViewById(R.id.etRegAdreça);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnOK = findViewById(R.id.btnOK);

        dbr = FirebaseDatabase.getInstance().getReference("usuarios");
        callRegisterFragment();
    }
    public void callRegisterFragment(){
        fm = getFragmentManager();
        fm.popBackStack();
        ft = fm.beginTransaction();
        dadesUsuari_fragment = new DadesUsuari_Fragment();
        myFragList.add(dadesUsuari_fragment);
        ft.add(R.id.myFrame,dadesUsuari_fragment,"");
        ft.addToBackStack("DadesUsuari_Fragment");
        ft.commit();
    }

    public void registrarUsuari(View v){

        regNomUsuari = etRegNomUsuari.getText().toString();
        regNom = etRegNom.getText().toString();
        regCognoms = etRegCognoms.getText().toString();
        regEmail = etRegEmail.getText().toString();
        regPassword = etRegPassword.getText().toString();
        regAdreça = etRegAdreça.getText().toString();

        crearCompte();

    }

    public void crearUsuari(String key){
        Usuari usuari = new Usuari(regNomUsuari,regNom,regCognoms,regEmail,regAdreça,"user");
        dbr.child(key).setValue(usuari);
    }

    public void crearCompte(){

        mAuth.createUserWithEmailAndPassword(regEmail, regPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("TAG", "createUserWithEmail:success");
                            Toast.makeText(Activity_Registrar.this, "Registre exitós :)", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            crearUsuari(user.getUid());
                            getIntent().putExtra("userUID",user.getUid());
                            setResult(RESULT_OK,getIntent());
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Activity_Registrar.this, "No se ha pogut crear l'usuari :(", Toast.LENGTH_SHORT).show();
                        }

                        Activity_Registrar.this.finish();
                    }
                });
    }

    public void tornarLogin(View v){
        Intent intent = new Intent(this, Activity_Login.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}

package com.example.victor.victorolivaresrec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.victor.victorolivaresrec.Model.Usuari;
import com.example.victor.victorolivaresrec.Usuaris.Activity_Login;
import com.example.victor.victorolivaresrec.Usuaris.Activity_Registrar;
import com.example.victor.victorolivaresrec.Usuaris.App_main;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String userUID;

    DatabaseReference dbr;

    final static int LOGIN_ACTIVITY_CODE = 2;
    final static int REGISTER_ACTIVITY_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, Activity_Login.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    public void loginActivity(View v){
        Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
        startActivityForResult(intent,LOGIN_ACTIVITY_CODE);
    }

    public void registerActivity(View v){
        Intent intent = new Intent(getApplicationContext(), Activity_Registrar.class);
        startActivityForResult(intent,REGISTER_ACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case LOGIN_ACTIVITY_CODE:
                switch (resultCode){
                    case RESULT_OK:

                        userUID = data.getExtras().getString("userUID");
                        Log.e("USERUID: ",userUID.toString());

                        dbr = FirebaseDatabase.getInstance().getReference("usuarios/"+userUID);
                        Log.e("Firebase: ",dbr.toString());
                        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Usuari usuari = dataSnapshot.getValue(Usuari.class);
                                Log.e("USER: ",usuari.getAuth().toString());
                                if(usuari.getAuth().equals("user")){
                                    Intent i = new Intent(getApplicationContext(), App_main.class);
                                    i.putExtra("userUID", userUID);
                                    startActivity(i);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        break;
                    case RESULT_CANCELED:
                        break;
                }

                break;
            case REGISTER_ACTIVITY_CODE:
                switch (resultCode){
                    case RESULT_OK:
                        Toast.makeText(getApplicationContext(),"Registre completat :)",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), App_main.class);
                        i.putExtra("userUID",data.getExtras().getString("userUID"));
                        startActivity(i);
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }
}


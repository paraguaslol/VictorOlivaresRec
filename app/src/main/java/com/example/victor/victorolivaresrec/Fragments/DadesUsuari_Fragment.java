package com.example.victor.victorolivaresrec.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.victor.victorolivaresrec.Model.Usuari;
import com.example.victor.victorolivaresrec.R;
import com.example.victor.victorolivaresrec.Usuaris.Activity_Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.Activity.RESULT_OK;


public class DadesUsuari_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    DatabaseReference dbr;
    private EditText etRegNomUsuari,etRegNom,etRegCognoms,etRegEmail,etRegPassword,etRegAdreça;
    private String regNomUsuari,regNom,regCognoms,regEmail,regPassword,regAdreça;
    private Button btnCancelar, btnOK;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public DadesUsuari_Fragment() {
        // Required empty public constructor
    }

    public static DadesUsuari_Fragment newInstance(String param1, String param2) {
        DadesUsuari_Fragment fragment = new DadesUsuari_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dades_usuari_, container, false);

        mAuth = FirebaseAuth.getInstance();

        etRegNomUsuari = v.findViewById(R.id.etRegNomUsuari);
        etRegNom = v.findViewById(R.id.etRegNom);
        etRegCognoms = v.findViewById(R.id.etRegCognoms);
        etRegEmail = v.findViewById(R.id.etRegEmail);
        etRegPassword = v.findViewById(R.id.etRegPassword);
        etRegAdreça = v.findViewById(R.id.etRegAdreça);
        btnCancelar = v.findViewById(R.id.btnCancelar);
        btnOK = v.findViewById(R.id.btnOK);

        dbr = FirebaseDatabase.getInstance().getReference("usuarios");

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLogin();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuari();
            }
        });

        return v;
    }

    public void registrarUsuari(){

        regNomUsuari = etRegNomUsuari.getText().toString();
        regNom = etRegNom.getText().toString();
        regCognoms = etRegCognoms.getText().toString();
        regEmail = etRegEmail.getText().toString();
        regPassword = etRegPassword.getText().toString();
        regAdreça = etRegAdreça.getText().toString();

        checkFields();

    }

    public void checkFields(){
        if (regNomUsuari.isEmpty() || regNom.isEmpty() || regCognoms.isEmpty() ||regEmail.isEmpty() ||
                regPassword.isEmpty() || regAdreça.isEmpty()){
            Toast.makeText(getContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            createAccount();
        }
    }

    public void createUser(String key){
        Usuari usuari = new Usuari(regNomUsuari,regNom,regCognoms,regEmail,regAdreça,"user");
        databaseReference.child(key).setValue(usuari);
    }

    public void returnLogin(){
        Intent intent = new Intent(getContext(), Activity_Login.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void createAccount(){
        DatabaseReference ddbb = FirebaseDatabase.getInstance().getReference();
        ddbb.child("usuarios").child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    mAuth.createUserWithEmailAndPassword(regEmail, regPassword)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.e("TAG", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        createUser(user.getUid());
                                        getActivity().getIntent().putExtra("userUID",user.getUid());
                                        getActivity().setResult(RESULT_OK,getActivity().getIntent());
                                        Toast.makeText(getContext(), "Registre exitós :)", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getContext(), Activity_Login.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.e("TAG", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getContext(), "No se ha pogut crear l'usuari :(", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
    @Override
    public void onCancelled(DatabaseError databaseError) {}
            }
        );
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

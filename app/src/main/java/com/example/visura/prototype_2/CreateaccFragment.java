package com.example.visura.prototype_2;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateaccFragment extends Fragment {
public static String sName,sEmail,sPassword,sPostalCode;
    public static SQLiteDatabase DB;
    public static int count=0;
    public CreateaccFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_createacc, container, false);
        DB = Drawer_main.DB;
        final Button create = (Button)v.findViewById(R.id.btnCreate);
        final Button clear = (Button)v.findViewById(R.id.btnClear);
        create.setEnabled(false);
        final EditText name = (EditText)v.findViewById(R.id.txtName);
       final EditText email = (EditText)v.findViewById(R.id.txtEmail);
       final EditText password = (EditText)v.findViewById(R.id.txtPassword);
       final EditText postalCode = (EditText)v.findViewById(R.id.txtPostCode);


        CheckBox terms = (CheckBox)v.findViewById(R.id.chcTerms);

        terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                create.setEnabled(true);}
                else{
                    create.setEnabled(false);
                }
                }
            }
        );

        create.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sName= name.getText().toString();
                sEmail= email.getText().toString();
                sPassword= password.getText().toString();
                sPostalCode= postalCode.getText().toString();
                saveDB(sName,sEmail,sPassword,sPostalCode);
                LoginFragment fragment = new LoginFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment,"Create_Account");
                fragmentTransaction.commit();
            }
        });

        clear.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                name.setText("");
                email.setText("");
                password.setText("");
                postalCode.setText("");

            }
        });
        return v;
    }

    public void  saveDB(String name,String email,String password,String postal){
        count++;
        String c = Integer.toString(count);
        String tablename = "Users";
        DB.execSQL("DROP TABLE IF EXISTS " + tablename);
        DB.execSQL("CREATE TABLE IF NOT EXISTS " + tablename + " (Name VARCHAR, Email VARCHAR, Password VARCHAR,Postal VARCHAR,Id VARCHAR);");
            DB.execSQL("INSERT INTO " + tablename + " (Name, Email, Password, Postal,Id)" + " VALUES ('" + name + "', '" + email + "','" + password + "', '" + postal + "','" + c + "');");
        }


    }



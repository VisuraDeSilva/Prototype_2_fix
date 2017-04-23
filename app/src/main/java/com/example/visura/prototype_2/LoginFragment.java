package com.example.visura.prototype_2;


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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment  implements View.OnClickListener {
    public static String test1;
    public static String test2;
    public static String test3;
    public static String test4;
    public static int count=0;
    public static List<String> Name = new ArrayList<String>();
    public static List<String> Email = new ArrayList<String>();
    public static List<String> Password = new ArrayList<String>();
    public static List<String> Postal = new ArrayList<String>();
    EditText name;
    EditText password;
    public static SQLiteDatabase DB;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_login, container, false);
        DB = Drawer_main.DB;
        Button btnRegister = (Button)v.findViewById(R.id.btnRegister);
        Button btnLogin = (Button)v.findViewById(R.id.btnLogin);
        name = (EditText)v.findViewById(R.id.txtUserName);
        password = (EditText)v.findViewById(R.id.txtPasswordLogin);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnRegister:
                Toast.makeText(getActivity(), "Redirecting to Registation", Toast.LENGTH_LONG).show();
                CreateaccFragment fragment = new CreateaccFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, "Create_Account");
                fragmentTransaction.commit();
                break;

            case R.id.btnLogin:
                viewTable();
                for (int i = 0; i < Name.size(); i++) {
                    if (Name.get(i).equals(name.getText().toString())) {
                        if (Password.get(i).equals(password.getText().toString())) {
                            Toast.makeText(getActivity(), "Logged in!!", Toast.LENGTH_LONG).show();
                            ProfileFragment fragment1 = new ProfileFragment();
                            FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                            fragmentTransaction1.replace(R.id.frame, fragment1, "Profile");
                            fragmentTransaction1.commit();
                        }

                    }
                }
        }
        }




    public void viewTable() {
        String tablename = "Users";
        Cursor c = DB.rawQuery("SELECT * FROM " + tablename, null);
        Name.clear();
        Email.clear();
        Password.clear();
        Postal.clear();

        //get the data from database
        int getName = c.getColumnIndex("Name");
        int getEmail = c.getColumnIndex("Email");
        int getPassword = c.getColumnIndex("Password");
        int getPostal = c.getColumnIndex("Postal");


        if (c != null) {
            if (c.moveToFirst()) {
                // Loop through all Results
                do {

                    test1 = c.getString(getName);
                    test2 = c.getString(getEmail);
                    test3 = c.getString(getPassword);
                    test4 = c.getString(getPostal);
                    //count = Integer.parseInt(test4);

                        Name.add(test1);
                        Email.add(test2);
                        Password.add(test3);
                        Postal.add(test4);

                } while (c.moveToNext());

            }
        }


    }}


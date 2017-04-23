package com.example.visura.prototype_2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    public static String  pName,pAge,pAddress;
    public static String test1;
    public static String test2;
    public static String test3;
    public static String test4;
    public static int count=0;
    public static List<String> Name = new ArrayList<String>();
    public static List<String> Email = new ArrayList<String>();
    public static List<String> Password = new ArrayList<String>();
    public static List<String> Postal = new ArrayList<String>();

    public static SQLiteDatabase DB;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView profileName =  (TextView) v.findViewById(R.id.lblProName);
        final TextView profileAge =  (TextView) v.findViewById(R.id.lblProAge);
        final TextView profileAdress =  (TextView) v.findViewById(R.id.lblProAddress);

        //get the name age address from webservice
      /*  pName = ;
        pAge = ;
        pAddress = ; */

        profileName.setText(pName);
        profileAge.setText(pAge);
        profileAdress.setText(pAddress);



        return v;
    }


    }








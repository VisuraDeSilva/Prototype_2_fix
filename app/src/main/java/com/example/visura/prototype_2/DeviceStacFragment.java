package com.example.visura.prototype_2;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceStacFragment extends Fragment {
    static String split1 =null;
    static String split2 =null;
    static String split3 =null;
    static String split4 =null;
    static String split5 =null;
    static String split6 =null;
    static String [] spiter=new String[6];
    BluetoothAdapter bluetoothAdepter;
    BluetoothSocket Socket;
    BluetoothDevice devicel;
    InputStream Input;
    Thread Thread;
    byte[] readBuffer;
    int readPosition;
    volatile boolean stop;


    public DeviceStacFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_device_stac, container, false);

        Button Connect =  (Button) v.findViewById(R.id.btnConnect);
        final TextView temp =  (TextView) v.findViewById(R.id.lbl2);
        final TextView humidity =  (TextView) v.findViewById(R.id.lbl3);
        final TextView moisture =  (TextView) v.findViewById(R.id.lbl4);
        final TextView ph =  (TextView) v.findViewById(R.id.lbl5);
        final TextView npk =  (TextView) v.findViewById(R.id.lbl6);
        final TextView error =  (TextView) v.findViewById(R.id.lblError);


        Connect.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    findDevice(error);
                    connectPort(temp,humidity,moisture,ph,npk);
                }
                catch (IOException ex) { }
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    void findDevice(TextView error)
    {
        bluetoothAdepter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdepter == null)
        {
            error.setText("Connection Error!!! ");
        }

        if(!bluetoothAdepter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdepter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals("H6-06"))
                {
                    devicel = device;
                    break;
                }
            }
        }
        error.setText("Connected !!");
    }

    void connectPort(TextView temp,TextView humidity,TextView moisture,TextView ph,TextView npk) throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        Socket = devicel.createRfcommSocketToServiceRecord(uuid);
        Socket.connect();
        Input = Socket.getInputStream();

        getData(temp,humidity,moisture,ph,npk);

    }

    void getData(final TextView temp, final TextView humidity, final TextView moisture, TextView ph, TextView npk)
    {
        final Handler handler = new Handler();
        final byte deli = 10;


        stop = false;
        readPosition = 0;
        readBuffer = new byte[1024];
        Thread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stop)
                {
                    try
                    {
                        int bytesAvailable = Input.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packets = new byte[bytesAvailable];
                            Input.read(packets);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packets[i];
                                if(b == deli)
                                {
                                    byte[] bytes = new byte[readPosition];
                                    System.arraycopy(readBuffer, 0, bytes, 0, bytes.length);
                                    final String data = new String(bytes, "US-ASCII");
                                    readPosition = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            spiter = data.split("--");
                                            split1 = spiter[0];
                                            split2= spiter[1];
                                            split3= spiter[2];
                                            split4 = spiter[3];
                                            split5 = spiter[4];
                                            split6 = spiter[5];
                                            //error.setText(split1+split2+split3+split4+split5+split6);
                                            moisture.setText("Moisture - "+split4);
                                            humidity.setText("Humidity - "+split5);
                                            temp.setText("Temparature - "+split6);
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stop = true;
                    }
                }
            }
        });

        Thread.start();
    }

}

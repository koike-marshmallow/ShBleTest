package com.example.ryokun.shbletest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private BluetoothManager btm;
    private BluetoothAdapter bta;
    private BluetoothAdapter.LeScanCallback lesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btm = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        bta = btm.getAdapter();


        lesc = new BluetoothAdapter.LeScanCallback(){
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord){
                String msg = "ADDRESS=" + device.getAddress() + "\nRSSI=" + rssi;
                Log.d("BLE", msg);
            }
        };
    }

    public void onBtnStartBleScanClicked(View view){
        bta.startLeScan(lesc);
    }

    public void onBtnStopBleScanClicked(View view){
        bta.stopLeScan(lesc);
    }
}


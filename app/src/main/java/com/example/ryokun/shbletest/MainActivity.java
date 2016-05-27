package com.example.ryokun.shbletest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        final Button bs = (Button)this.findViewById(R.id.btnstart);
        final Button be = (Button)this.findViewById(R.id.btnstop);
        final TextView tvs = (TextView)this.findViewById(R.id.textstatus);

        setInstanceText();
        addLogText("initialization", true);

        bs.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                tvs.setText("start");
                setInstanceText();
                onBtnStartBleScanClicked(v);
            }
        });
        be.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                tvs.setText("stop");
                setInstanceText();
                onBtnStopBleScanClicked(v);
            }
        });


        lesc = new BluetoothAdapter.LeScanCallback(){
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord){
                String msg = "ADDRESS=" + device.getAddress() + "\nRSSI=" + rssi;
                Log.d("BLE", msg);
                addLogText(msg, false);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void onBtnStartBleScanClicked(View view){
        bta.startLeScan(lesc);
    }

    public void onBtnStopBleScanClicked(View view){
        bta.stopLeScan(lesc);
    }

    private void setInstanceText(){
        String str = "";
        str += "bmanager: " + (btm != null ? btm.toString() : "null") + "\n";
        str += "badaptor: " + (bta != null ? bta.toString() : "null");
        ((TextView)findViewById(R.id.textinstance)).setText(str);
    }

    private void addLogText(String str, boolean refresh){
        TextView tvl = (TextView)findViewById(R.id.textlog);
        String pre = tvl.getText().toString();
        if( refresh || pre.equals("") ){
            tvl.setText(str);
        }else{
            tvl.setText(pre + "\n" + str);
        }
    }
}


package com.example.ryokun.shbletest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
implements BluetoothAdapter.LeScanCallback{
    private BleDetector detector;

    private TextView tvStatus;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detector = new BleDetector(this);
        tvStatus = (TextView)this.findViewById(R.id.textstatus);

        final Button bs = (Button)this.findViewById(R.id.btnstart);
        final Button be = (Button)this.findViewById(R.id.btnstop);

        addLogText("initialization", true);

        bs.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBtnStartBleScanClicked(v);
            }
        });
        be.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBtnStopBleScanClicked(v);
            }
        });
    }

    public void onBtnStartBleScanClicked(View view){
        tvStatus.setText("start");
        detector.startLeScan();
    }

    public void onBtnStopBleScanClicked(View view){
        tvStatus.setText("end");
        detector.stopLeScan();
    }

    public void addLogText(String str, boolean refresh){
        TextView tvl = (TextView)findViewById(R.id.textlog);
        String pre = tvl.getText().toString();
        if( refresh || pre.equals("") ){
            tvl.setText(str);
        }else{
            tvl.setText(pre + "\n" + str);
        }
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord){
        String deviceInfo = "[ADDR=" + device.getAddress() + ",RSSI=" + rssi + "]";
        String records = convertToHexString(scanRecord);
        String msg = deviceInfo + "\n" + records;
        addLogText(msg, false);
    }

    String convertToHexString(byte[] bytes){
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<bytes.length; i++) {
            buffer.append(Integer.toHexString(bytes[i] & 0xff));
        }
        return buffer.toString();
    }
}


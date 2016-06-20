package com.example.android.signalanalyzer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothSettingsActivity extends AppCompatActivity {
//    private ListView listView;
//    private ArrayList<String> mDeviceList = new ArrayList<String>();
//    private BluetoothAdapter mBluetoothAdapter;
private static final int REQUEST_ENABLE_BT = 1;

    BluetoothAdapter bluetoothAdapter;

    ArrayList<BluetoothDevice> pairedDeviceArrayList;

    ListView listViewPairedDevice;
    ArrayAdapter<BluetoothDevice> pairedDeviceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listViewPairedDevice = (ListView) findViewById(R.id.ListView);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        mBluetoothAdapter.startDiscovery();
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        registerReceiver(mReceiver, filter);

    }

//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        String add1;
//        public void onReceive(Context context, final Intent intent) {
//            String action = intent.getAction();
//
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                 BluetoothDevice device = intent
//                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//
//                mDeviceList.add(device.getName() + "\n" + device.getAddress());
//                listView.setAdapter(new ArrayAdapter<String>(context,
//                        android.R.layout.simple_list_item_1, mDeviceList));
//
//
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position,
//                                            long id) {
//
//                      BluetoothDevice  device1 = (BluetoothDevice) parent.getItemAtPosition(position);
//                        add1 = device1.getAddress();
////                        BluetoothDevice device= (BluetoothDevice)parent.getItemAtPosition(position);
////                        add = device.getAddress();
////                        Toast.makeText(this,add,Toast.LENGTH_SHORT).show();
//
//
////                        add1 = "Fcuk off";
//                        Intent intent2 = new Intent();
//                        intent2.putExtra("rssi", add1);
//                        BluetoothSettingsActivity.this.setResult(RESULT_OK,intent2);
//                        finish();
//                    }
//
//                });
//            }
//
//        }
//
//    };
@Override
protected void onStart() {
    super.onStart();

    //Turn ON BlueTooth if it is OFF
    if (!bluetoothAdapter.isEnabled()) {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    setup();
}

    private void setup() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            pairedDeviceArrayList = new ArrayList<BluetoothDevice>();

            for (BluetoothDevice device : pairedDevices) {
                pairedDeviceArrayList.add(device);


            }

            pairedDeviceAdapter = new ArrayAdapter<BluetoothDevice>(this,
                    android.R.layout.simple_list_item_1, pairedDeviceArrayList);
            listViewPairedDevice.setAdapter(pairedDeviceAdapter);

            listViewPairedDevice.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    BluetoothDevice device =
                            (BluetoothDevice)parent.getItemAtPosition(position);
                    Toast.makeText(BluetoothSettingsActivity.this,
                            "Name: " + device.getName() + "\n"
                                    + "Address: " + device.getAddress() + "\n"
                                    + "BondState: " + device.getBondState() + "\n"
                                    + "BluetoothClass: " + device.getBluetoothClass() + "\n"
                                    + "Class: " + device.getClass(),
                            Toast.LENGTH_LONG).show();
                    String add = device.getAddress();
                    Intent intent2 = new Intent();
                        intent2.putExtra("rssi", add);
                        BluetoothSettingsActivity.this.setResult(RESULT_OK,intent2);
                        finish();
                }});
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REQUEST_ENABLE_BT){
            if(resultCode == Activity.RESULT_OK){
                setup();
            }else{
                Toast.makeText(this,
                        "BlueTooth NOT enabled",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}

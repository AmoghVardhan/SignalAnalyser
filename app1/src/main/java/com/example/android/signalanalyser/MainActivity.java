package com.example.android.signalanalyser;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series1;
    private LineGraphSeries<DataPoint> series2;
    private LineGraphSeries<DataPoint> series3;
    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    public int lastX = 0;
    public long a, b, c, d, e, f, g, h;
    public int rssi2;
    private int rm;
    String wName;
    String stredittext;
    GraphView graph;
//    private final static int REQUEST_ENABLE_BT = -1;

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private String connectedDeviceName = null;
    private ArrayAdapter<String> chatArrayAdapter;

    private StringBuffer outStringBuffer;
    private BluetoothAdapter bluetoothAdapter = null;
    private ChatService chatService = null;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case ChatService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to,
                                    connectedDeviceName));
                            chatArrayAdapter.clear();
                            break;
                        case ChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case ChatService.STATE_LISTEN:
                        case ChatService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;

                    String writeMessage = new String(writeBuf);
                    chatArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;

                    String readMessage = new String(readBuf, 0, msg.arg1);
                     rm = Integer.parseInt(readMessage);
                    chatArrayAdapter.add(connectedDeviceName + ":  " + readMessage);
                    break;
                case MESSAGE_DEVICE_NAME:

                    connectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "Connected to " + connectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
            return false;
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        creating a graph instance
         graph = (GraphView) findViewById(R.id.graph);
//        data
        series = new LineGraphSeries<>();
        series1 = new LineGraphSeries<>();
        graph.addSeries(series1);
//        customization of our graph
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(-100);
        viewport.setMaxY(0);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time (Seconds)");
//        gridLabel.setVerticalAxisTitle("Signal Strength (db)");
        gridLabel.setNumHorizontalLabels(5);
        gridLabel.setNumVerticalLabels(11);
        series1.setColor(Color.RED);

        series2 = new LineGraphSeries<>();
        graph.addSeries(series2);
        series2.setColor(Color.BLUE);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
//        series.setCustomPaint(paint);
        series2.setCustomPaint(paint);
        series3 = new LineGraphSeries<>();
        graph.addSeries(series3);
        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(20);
        paint1.setColor(Color.YELLOW);
        paint1.setPathEffect(new CornerPathEffect(10));
        series3.setCustomPaint(paint1);
        if (!BTAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,REQUEST_ENABLE_BT);
        }
//        BTAdapter.startDiscovery();


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);


        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setSelection(1);
        spinner3.setOnItemSelectedListener(this);


        Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setSelection(1);
        spinner4.setOnItemSelectedListener(this);


        Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);
        spinner5.setSelection(2);
        spinner5.setOnItemSelectedListener(this);


        Spinner spinner6 = (Spinner) findViewById(R.id.spinner6);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter6);
        spinner6.setSelection(2);
        spinner6.setOnItemSelectedListener(this);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

//        getWidgetReferences();
//        bindEventHandler();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }


//    private void bindEventHandler() {
//        etMain.setOnEditorActionListener(mWriteListener);
//
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String message = etMain.getText().toString();
//                sendMessage(message);
//            }
//        });
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    setupChat();
                } else {
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        String address = data.getExtras().getString(
                BluetoothSettingsActivity.DEVICE_ADDRESS);
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        chatService.connect(device, secure);
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (chatService != null) {
            if (chatService.getState() == ChatService.STATE_NONE) {
                chatService.start();
            }
        }
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {
            @Override
            public void run() {
//we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
//                    sleep to slow down the addition of the entries
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent serverIntent = null;
        switch (item.getItemId()) {
            case R.id.wifi_settings:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

            case R.id.secure_connect_scan:
                serverIntent = new Intent(this, BluetoothSettingsActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            case R.id.insecure_connect_scan:
                serverIntent = new Intent(this, BluetoothSettingsActivity.class);
                startActivityForResult(serverIntent,
                        REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
            case R.id.discoverable:
                ensureDiscoverable();
                return true;
        }
        return false;
    }
    private void ensureDiscoverable() {
        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(
                    BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
    private final void setStatus(int resId) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(resId);
    }

    private final void setStatus(CharSequence subTitle) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subTitle);
    }
    private void setupChat() {
        chatArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);

        chatService = new ChatService(this, handler);

        outStringBuffer = new StringBuffer("");
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if(resultCode == RESULT_OK){
//                stredittext=data.getExtras().getString("rssi");
//                Toast.makeText(this,stredittext,Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    //    add random data
    private void addEntry() {

//        here we choose a max of 10 points to show up on the Viewport
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        int rssi = wifiManager.getConnectionInfo().getRssi();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String wName = wifiInfo.getSSID();
        series1.setTitle(wName);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getLegendRenderer().setWidth(200);
        graph.getLegendRenderer().setSpacing(15);
//        graph.getLegendRenderer().
        series1.appendData(new DataPoint(lastX++, rssi), true, 10);
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if(BluetoothDevice.ACTION_FOUND.equals(stredittext));
              int  rsssi2 = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MAX_VALUE);
                rssi2 = rsssi2;
                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                series3.setTitle("\""+name+"\"");
//            TextView rssi_msg = (TextView)findViewById(R.id.textView);
//            rssi_msg.setText(rssi_msg.getText() + name + "=>" + rsssi2 +"dbm\n");
            }
        };
        int lastX1 = lastX - 1;
        Random r = new Random();
        int i1 = r.nextInt(-60 - (-70)) + (-70);
        series2.appendData(new DataPoint(lastX1++,i1 ), true, 10);
        int lastX2 = lastX1 - 1;
        Random r1 = new Random();
//        int i2 = r1.nextInt(-50 - (-60)) + (-60);

        BTAdapter.startDiscovery();
        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        series3.appendData(new DataPoint(lastX2++, rm), true, 10);


        if (wifiManager.isWifiEnabled() == false) {
            // If wifi disabled then enable it
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled",
                    Toast.LENGTH_LONG).show();

            wifiManager.setWifiEnabled(true);
        }


//        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
//        Button button = (Button)findViewById(R.id.bluetoothB);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                BTAdapter.startDiscovery();
//            }
//        });

//         final BroadcastReceiver receiver = new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                String action = intent.getAction();
//                if(BluetoothDevice.ACTION_FOUND.equals(action)) {
//                    int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
//                    Toast.makeText(getApplicationContext(),"  RSSI: " + rssi + "dBm", Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
    }
    @Override
    public void onStart() {
        super.onStart();

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (chatService == null)
                setupChat();
        }
    }
    @Override
    public synchronized void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatService != null)
            chatService.stop();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        switch (adapterView.getId()) {
            case R.id.spinner:

                g = adapterView.getItemIdAtPosition(i);

                break;

            case R.id.spinner2:
                h = adapterView.getItemIdAtPosition(i);
//                Toast.makeText(this, "a=" + i, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "b=" + i, Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner3:
//                Toast.makeText(this,"entered", Toast.LENGTH_SHORT).show();
                c = adapterView.getItemIdAtPosition(i);
//                Toast.makeText(this,"c = "+c, Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner4:
                d = adapterView.getItemIdAtPosition(i);
//                Toast.makeText(this, "d = "+d, Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner5:
                e = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.spinner6:
                f = adapterView.getItemIdAtPosition(i);
                break;


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void bWifi(View view) {
        a = g;
        b = h;
        series = series1;
        setStyle(view);
    }

    public void setStyle(View view) {
        if (a == 0 && b == 0) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.RED);
            paint.setPathEffect(new CornerPathEffect(10));
            series.setCustomPaint(paint);
        }
        if (a == 0 && b == 1) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.RED);
            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
            series.setCustomPaint(paint);
        }
        if (a == 0 && b == 2) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(20);
            paint.setColor(Color.RED);

            paint.setPathEffect(new CornerPathEffect(10));
            series.setCustomPaint(paint);
        }

        if (a == 1 && b == 0) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.BLUE);
            paint.setPathEffect(new CornerPathEffect(10));
            series.setCustomPaint(paint);
        }
        if (a == 1 && b == 1) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.BLUE);
            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
            series.setCustomPaint(paint);

        }
        if (a == 1 && b == 2) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(20);
            paint.setColor(Color.BLUE);
            paint.setPathEffect(new CornerPathEffect(10));
            series.setCustomPaint(paint);

        }
        if (a == 2 && b == 0) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.YELLOW);
            paint.setPathEffect(new CornerPathEffect(10));
            series.setCustomPaint(paint);

        }
        if (a == 2 && b == 1) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.YELLOW);
            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
            series.setCustomPaint(paint);

        }
        if (a == 2 && b == 2) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(20);
            paint.setColor(Color.YELLOW);
            paint.setPathEffect(new CornerPathEffect(10));
            series.setCustomPaint(paint);

        }

    }

    public void bUSB(View view) {
        series = series2;
        a = c;
        b = d;
//Toast.makeText(this,"c = "+c + "d = "+d, Toast.LENGTH_SHORT).show();
        setStyle(view);


    }

    public void bBluetooth(View view) {
        series = series3;
        a = e;
        b = f;
        setStyle(view);
    }
}

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
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity  {
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series1;
    private LineGraphSeries<DataPoint> series2;
    private LineGraphSeries<DataPoint> series3;
    private LineGraphSeries<DataPoint> series4;
    private LineGraphSeries<DataPoint> series5;
    private LineGraphSeries<DataPoint> series6;
    private LineGraphSeries<DataPoint> series7;
    private LineGraphSeries<DataPoint> series8;
    private LineGraphSeries<DataPoint> series9;
    private LineGraphSeries<DataPoint> series10;

    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    public int lastX = 0;
    public long a, b, c, d, e, f, g, h;
    public int rssi2;
    private int[] rm = new int[100];
    String wName;
    private int[] me = new int[100];
    String stredittext;
    GraphView graph;
    String s1,s2;
//    private final static int REQUEST_ENABLE_BT = -1;

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int wifiCode = 6;

    Paint paint = new Paint();

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private static final int REQUEST_DATA = 7;

    private String connectedDeviceName = "none" ;
    private ArrayAdapter<String> chatArrayAdapter;

    private StringBuffer outStringBuffer;
    private BluetoothAdapter bluetoothAdapter = null;
    private ChatService chatService = null;
    private IntBuffer intBuffer;
    private int[] lm = null;
    private byte[] readBuf;
    private int[] array;
    private int l = 0;

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
                     readBuf = (byte[]) msg.obj;
//                    intBuffer = ByteBuffer.wrap(readBuf).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
//                  array = new int[intBuffer.remaining()];
//                    intBuffer.get(array);
//                    lm = intBuffer.array();


                    String readMessage = new String(readBuf, 0, msg.arg1);

//                     rm[l] = Integer.parseInt(readMessage);
//                    ++l;
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
        viewport.setMinY(0);
        viewport.setMaxY(10);
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
        series2.setColor(Color.RED);
        series2.setCustomPaint(paint);

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

        series4 = new LineGraphSeries<>();
        graph.addSeries(series4);


//        if (!BTAdapter.isEnabled()) {
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableIntent,REQUEST_ENABLE_BT);
//        }
//        BTAdapter.startDiscovery();





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
        if(resultCode == Activity.RESULT_OK  && requestCode == REQUEST_DATA){
            if(data.hasExtra("spinner1")) {
                s1 = data.getExtras().getString("spinner1");
                if(s1.equals("Blue")==true)
                {
                   cBlue();
                    series1.setCustomPaint(paint);
                    Toast.makeText(this,"Blue",Toast.LENGTH_SHORT).show();

                }

            }

        }
        switch (requestCode) {




            case REQUEST_CONNECT_DEVICE_SECURE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
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
                break;

                }

        }

    private void cBlue() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        int color = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Blue);
        paint.setColor(color);
    }

    private void cYellow() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        int color = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Yellow);
        paint.setColor(color);
    }
    private void cBlack() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        int color = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Black);
        paint.setColor(color);
    }
    private void cRed() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        int color = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Red);
        paint.setColor(color);
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
                        Thread.sleep(1000);
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
            case R.id.Graphconfig:
                serverIntent = new Intent(this,graphConfigAct.class);
                startActivityForResult(serverIntent,REQUEST_DATA);
                return true;
            case R.id.wifi_settings:
                serverIntent = new Intent(this, wifiSettingsActivity.class);
                startActivityForResult(serverIntent, wifiCode);
                return true;

            case R.id.secure_connect_scan:
                serverIntent = new Intent(this, BluetoothSettingsActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
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
        Random r0 = new Random();
        int i0 = r0.nextInt(10 - (0)) + (0);
        series1.appendData(new DataPoint(lastX++, i0), true, 1000);
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if(BluetoothDevice.ACTION_FOUND.equals(stredittext));
              int  rsssi2 = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MAX_VALUE);
                rssi2 = rsssi2;
                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);

                series3.setTitle("\""+connectedDeviceName+"\"");
//            TextView rssi_msg = (TextView)findViewById(R.id.textView);
//            rssi_msg.setText(rssi_msg.getText() + name + "=>" + rsssi2 +"dbm\n");
            }
        };
        int lastX1 = lastX - 1;
//        Random r = new Random();
//        int i1 = r.nextInt(-60 - (-70)) + (-70);
        int i1 = i0 + 1;
        series2.appendData(new DataPoint(lastX1++,i1 ), true, 1000);
        int lastX2 = lastX1 - 1;
        Random r1 = new Random();
//        int i2 = r1.nextInt(-50 - (-60)) + (-60);

        BTAdapter.startDiscovery();
        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
       float lastX3 = (float) lastX2;
//        for(int i=0;i<8;i++) {
//if(readBuf!= null) {
//
//    series3.appendData(new DataPoint(lastX3+0.1, rm), true, 1000);
//}
//else
//{
//    series3.appendData(new DataPoint(lastX3+0.1,0),true,1000);
//
//}
//
//
//
////        }
//        for(int i=0;i<9;i++) {
//            if (rm != null) {
                series3.appendData(new DataPoint(lastX3,0), true, 1000);
//            }
//            else
//                series3.appendData(new DataPoint(lastX3 + 0.1, 0),true,1000);
//        }
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








}

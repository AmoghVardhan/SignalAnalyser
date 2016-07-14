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
    String s,s1,s2,s11,s12;
//    private final static int REQUEST_ENABLE_BT = -1;

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int wifiCode = 6;

    String abc;
    String abcd;

    Paint paint = new Paint();
    Paint paint1 = new Paint();
    Paint paint2 = new Paint();
    Paint paint3 = new Paint();
    Paint paint4 = new Paint();
    Paint paint5 = new Paint();
    Paint paint6 = new Paint();
    Paint paint7 = new Paint();
    Paint paint8 = new Paint();
    Paint paint9 = new Paint();
    Paint paint10 = new Paint();
    Paint paint11 = new Paint();
    Paint paint12 = new Paint();
    Paint paint13 = new Paint();
    Paint paint14= new Paint();
    Paint paint15= new Paint();
    Paint paint16= new Paint();
    Paint paint17= new Paint();
    Paint paint18= new Paint();
    
    
    

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

//        customization of our graph
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(50);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time (Seconds)");
//        gridLabel.setVerticalAxisTitle("Signal Strength (db)");
        gridLabel.setNumHorizontalLabels(5);
        gridLabel.setNumVerticalLabels(11);

        series1 = new LineGraphSeries<>();
        graph.addSeries(series1);
        series1.setColor(Color.BLUE);
//        paint1.setStyle(Paint.Style.STROKE);
//        paint1.setStrokeWidth(10);
//        int color1 = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Red);
//        paint1.setColor(color1);
//        paint1.setPathEffect(new CornerPathEffect(10));
//        series1.setCustomPaint(paint1);


        series2 = new LineGraphSeries<>();
        graph.addSeries(series2);
        series2.setColor(Color.GREEN);
//        paint2.setStyle(Paint.Style.STROKE);
//        paint2.setStrokeWidth(10);
//        int color2 = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Blue);
//        paint2.setColor(color2);
//        paint2.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
//        series2.setCustomPaint(paint2);

        series3 = new LineGraphSeries<>();
        graph.addSeries(series3);
        series3.setColor(Color.BLACK);
//        paint3.setStyle(Paint.Style.STROKE);
//        paint3.setStrokeWidth(20);
//        int color3 = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Yellow);
//        paint3.setColor(color3);
//        paint3.setPathEffect(new CornerPathEffect(10));
//        series3.setCustomPaint(paint3);

        series4 = new LineGraphSeries<>();
        graph.addSeries(series4);
        series4.setColor(Color.RED);
//        paint4.setStyle(Paint.Style.STROKE);
//        paint4.setStrokeWidth(20);
//        int color4 = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Yellow);
//        paint4.setColor(color4);
//        paint4.setPathEffect(new CornerPathEffect(10));
//        series4.setCustomPaint(paint4);

        series5 = new LineGraphSeries<>();
        graph.addSeries(series5);
        series5.setColor(Color.WHITE);
//        paint5.setStyle(Paint.Style.STROKE);
//        paint5.setStrokeWidth(20);
//        int color5 = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Yellow);
//        paint5.setColor(color5);
//        paint5.setPathEffect(new CornerPathEffect(10));
//        series5.setCustomPaint(paint5);

        series6 = new LineGraphSeries<>();
        graph.addSeries(series6);
        series6.setColor(Color.CYAN);
//        paint6.setStyle(Paint.Style.STROKE);
//        paint6.setStrokeWidth(20);
//        int color6 = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Yellow);
//        paint6.setColor(color6);
//        paint6.setPathEffect(new CornerPathEffect(10));
//        series6.setCustomPaint(paint6);

        series7 = new LineGraphSeries<>();
        graph.addSeries(series7);
        series7.setColor(Color.GRAY);
//        paint7.setStyle(Paint.Style.STROKE);
//        paint7.setStrokeWidth(20);
//        int color7 = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Yellow);
//        paint7.setColor(color7);
//        paint7.setPathEffect(new CornerPathEffect(10));
//        series7.setCustomPaint(paint7);

        series8 = new LineGraphSeries<>();
        graph.addSeries(series8);
        series8.setColor(Color.MAGENTA);
//        paint8.setStyle(Paint.Style.STROKE);
//        paint8.setStrokeWidth(20);
//        int color8 = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Yellow);
//        paint8.setColor(color8);
//        paint8.setPathEffect(new CornerPathEffect(10));
//        series8.setCustomPaint(paint8);

        series9 = new LineGraphSeries<>();
        graph.addSeries(series9);
        series9.setColor(Color.DKGRAY);
//        paint9.setStyle(Paint.Style.STROKE);
//        paint9.setStrokeWidth(20);
//        int color9 = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Yellow);
//        paint9.setColor(color9);
//        paint9.setPathEffect(new CornerPathEffect(10));
//        series9.setCustomPaint(paint9);

        series10 = new LineGraphSeries<>();
        graph.addSeries(series10);
        series10.setColor(Color.YELLOW);
//        paint10.setStyle(Paint.Style.STROKE);
//        paint10.setStrokeWidth(20);
//        int color10 = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Yellow);
//        paint10.setColor(color10);
//        paint10.setPathEffect(new CornerPathEffect(10));
//        series10.setCustomPaint(paint10);


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

//            if(data.hasExtra("spinner1")) {
//                s1 = data.getExtras().getString("spinner1");
//                s=s1;
//                series = series1;
//                setPforC();
//            }
            if(data.hasExtra("color1")){
                abc = data.getExtras().getString("color1");
                if(abc != null)
                series1.setColor(Color.parseColor(abc));
            }
            if(data.hasExtra("color2")){
                abc = data.getExtras().getString("color2");
                if(abc != null)
                series2.setColor(Color.parseColor(abc));
            }
            if(data.hasExtra("color3")){
                abc = data.getExtras().getString("color3");
                if(abc != null)
                    series3.setColor(Color.parseColor(abc));
            }
            if(data.hasExtra("color4")){
                abc = data.getExtras().getString("color4");
                if(abc != null)
                    series4.setColor(Color.parseColor(abc));
            }
            if(data.hasExtra("color5")){
                abc = data.getExtras().getString("color5");
                if(abc != null)
                    series5.setColor(Color.parseColor(abc));
            }
            if(data.hasExtra("color6")){
                abc = data.getExtras().getString("color6");
                if(abc != null)
                    series6.setColor(Color.parseColor(abc));
            }
            if(data.hasExtra("color7")){
                abc = data.getExtras().getString("color7");
                if(abc != null)
                    series7.setColor(Color.parseColor(abc));
            }
            if(data.hasExtra("color8")){
                abc = data.getExtras().getString("color8");
                if(abc != null)
                    series8.setColor(Color.parseColor(abc));
            }
            if(data.hasExtra("color9")){
                abc = data.getExtras().getString("color9");
                if(abc != null)
                    series9.setColor(Color.parseColor(abc));
            }
            if(data.hasExtra("color10")){
                abc = data.getExtras().getString("color10");
                if(abc != null)
                    series10.setColor(Color.parseColor(abc));
            }

//            if(data.hasExtra("spinner2")) {
//                s1 = data.getExtras().getString("spinner2");
//                s=s1;
//                series = series2;
//                setPforC();
//            }
//            if(data.hasExtra("spinner3")) {
//                s1 = data.getExtras().getString("spinner3");
//                s=s1;
//                series = series3;
//                setPforC();
//            }
//            if(data.hasExtra("spinner4")) {
//                s1 = data.getExtras().getString("spinner4");
//                s=s1;
//                series = series4;
//                setPforC();
//            }
//            if(data.hasExtra("spinner5")) {
//                s1 = data.getExtras().getString("spinner5");
//                s=s1;
//                series = series5;
//                setPforC();
//            }
//            if(data.hasExtra("spinner6")) {
//                s1 = data.getExtras().getString("spinner6");
//                s=s1;
//                series = series6;
//                setPforC();
//            }
//            if(data.hasExtra("spinner7")) {
//                s1 = data.getExtras().getString("spinner7");
//                s=s1;
//                series = series7;
//                setPforC();
//            }
//            if(data.hasExtra("spinner8")) {
//                s1 = data.getExtras().getString("spinner8");
//                s=s1;
//                series = series8;
//                setPforC();
//            }
//            if(data.hasExtra("spinner9")) {
//                s1 = data.getExtras().getString("spinner9");
//                s=s1;
//                series = series9;
//                setPforC();
//            }
//
//            if(data.hasExtra("spinner10")) {
//                s1 = data.getExtras().getString("spinner10");
//                s=s1;
//                series = series10;
//                setPforC();
//            }
            if(data.hasExtra("spinner11")) {
                s11 = data.getExtras().getString("spinner11");
                s=s11;
                series = series1;
                setPforS();

            }
            if(data.hasExtra("spinner12")) {
                s11 = data.getExtras().getString("spinner12");
                s=s11;
                series = series2;
                setPforS();

            }
            if(data.hasExtra("spinner13")) {
                s11 = data.getExtras().getString("spinner13");
                s=s11;
                series = series3;
                setPforS();

            }
            if(data.hasExtra("spinner14")) {
                s11 = data.getExtras().getString("spinner14");
                s=s11;
                series = series4;
                setPforS();

            }
            if(data.hasExtra("spinner15")) {
                s11 = data.getExtras().getString("spinner15");
                s=s11;
                series = series5;
                setPforS();

            }
            if(data.hasExtra("spinner16")) {
                s11 = data.getExtras().getString("spinner16");
                s=s11;
                series = series6;
                setPforS();

            }
            if(data.hasExtra("spinner17")) {
                s11 = data.getExtras().getString("spinner17");
                s=s11;
                series = series7;
                setPforS();

            }
            if(data.hasExtra("spinner18")) {
                s11 = data.getExtras().getString("spinner18");
                s=s11;
                series = series8;
                setPforS();

            }
            if(data.hasExtra("spinner19")) {
                s11 = data.getExtras().getString("spinner19");
                s=s11;
                series = series9;
                setPforS();

            }
            if(data.hasExtra("spinner20")) {
                s11 = data.getExtras().getString("spinner20");
                s=s11;
                series = series10;
                setPforS();

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

    private void setPforC(){

        if(s.equals("Blue")==true)
        {
            cBlue();
        }
        if(s.equals("Yellow")==true)
        {
            cYellow();
        }
        if(s.equals("Red")==true)
        {
            cRed();
        }
        if(s.equals("Black")==true)
        {
            cBlack();
        }

    }

    private void setPforS(){
        if(s.equals("dashed")==true) {
            sDash();
        }
        if(s.equals("continuous")==true) {
            sCont();
        }
        if(s.equals("thick")==true) {
            sThick();
        }
    }

    private void cBlue() {
        Paint paint11 = new Paint();
        paint11.setStyle(Paint.Style.STROKE);
        paint11.setStrokeWidth(10);
        int color = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Blue);
        paint11.setColor(color);
        series.setCustomPaint(paint11);
    }

    private void cYellow() {
        Paint paint12 = new Paint();
        paint12.setStyle(Paint.Style.STROKE);
        paint12.setStrokeWidth(10);
        int color = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Yellow);
        paint12.setColor(color);
        series.setCustomPaint(paint12);
    }
    private void cRed() {
        Paint paint13 = new Paint();
        paint13.setStyle(Paint.Style.STROKE);
        paint13.setStrokeWidth(10);
        int color = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Red);
        series.setCustomPaint(paint13);
    }
    private void cBlack() {
        Paint paint14 = new Paint();
        paint14.setStyle(Paint.Style.STROKE);
        paint14.setStrokeWidth(10);
        int color = getApplicationContext().getResources().getColor(com.example.android.signalanalyser.R.color.Black);
        paint14.setColor(color);
        series.setCustomPaint(paint14);
    }



    private void sCont(){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setPathEffect(new CornerPathEffect(10));
        series.setCustomPaint(paint);
    }
    private void sDash(){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        series.setCustomPaint(paint);

    }
    private void sThick(){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setPathEffect(new CornerPathEffect(10));
        series.setCustomPaint(paint);
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
        int i1 = i0 + 2;
        series2.appendData(new DataPoint(lastX1++,i1 ), true, 1000);

//        int lastX2 = lastX1 - 1;
//        Random r1 = new Random();
////        int i2 = r1.nextInt(-50 - (-60)) + (-60);

        int lastX3 = lastX1- 1;
        int i2 = i1 + 2;
        series3.appendData(new DataPoint(lastX3++,i2),true,1000);

        int lastX4 = lastX3 - 1;
        int i3 = i2 + 2;
        series4.appendData(new DataPoint(lastX4++,i3),true,1000);

        int lastX5 = lastX4 - 1;
        int i4 = i3 + 2;
        series5.appendData(new DataPoint(lastX5++,i4),true,1000);

        int lastX6 = lastX5 - 1;
        int i5 = i4 + 2;
        series6.appendData(new DataPoint(lastX6++,i5),true,1000);

        int lastX7 = lastX6 - 1;
        int i6 = i5 + 2;
        series7.appendData(new DataPoint(lastX7++,i6),true,1000);

        int lastX8 = lastX7 - 1;
        int i7 = i6 + 2;
        series8.appendData(new DataPoint(lastX8++,i7),true,1000);

        int lastX9 = lastX8 - 1;
        int i8 = i7 + 2;
        series9.appendData(new DataPoint(lastX9++,i8),true,1000);

        int lastX10 = lastX9 - 1;
        int i9 = i8 + 2;
        series10.appendData(new DataPoint(lastX10++,i9),true,1000);



        BTAdapter.startDiscovery();
        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
//       float lastX31 = (float) lastX2;
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
//                series3.appendData(new DataPoint(lastX31,0), true, 1000);
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

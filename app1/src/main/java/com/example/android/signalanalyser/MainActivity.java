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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    int j = 1;

    String[] time = new String[15];
    String[] sig1 = new String[15];
    String[] sig2 = new String[15];
    String[] sig3 = new String[15];
    String[] sig4 = new String[15];
    String[] sig5 = new String[15];
    String[] sig6 = new String[15];
    String[] sig7 = new String[15];
    String[] sig8 = new String[15];
    String[] sig9 = new String[15];
    String[] sig10 = new String[15];
    String[] sep = new String[15];
    
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
        viewport.setMaxY(100);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time (Seconds)");
//        gridLabel.setVerticalAxisTitle("Signal Strength (db)");
        gridLabel.setNumHorizontalLabels(5);
        gridLabel.setNumVerticalLabels(11);

        try {
            StringBuilder buf = new StringBuilder();
            InputStream json = getAssets().open("first.txt");
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String strr;
            String str;
            int z = 0;
            while ((strr = in.readLine()) != null) {
                buf.append(strr);
                str = buf.toString();
                sep = str.split("\\s+");
                time[z] = sep[0];
                sig1[z] = sep[1];
                sig2[z] = sep[2];
                sig3[z] = sep[3];
                sig4[z] = sep[4];
                sig5[z] = sep[5];
                sig6[z] = sep[6];
                sig7[z] = sep[7];
                sig8[z] = sep[8];
                sig9[z] = sep[9];
                sig10[z] = sep[10];
                buf.setLength(0);
                z++;

            }




            in.close();
        }catch (IOException e) {
            e.printStackTrace();
        }



        series1 = new LineGraphSeries<>();
        graph.addSeries(series1);
        series1.setColor(Color.BLUE);
        series1.setTitle(sig1[0]);


        series2 = new LineGraphSeries<>();
        graph.addSeries(series2);
        series2.setColor(Color.GREEN);
        series2.setTitle(sig2[0]);

        series3 = new LineGraphSeries<>();
        graph.addSeries(series3);
        series3.setColor(Color.BLACK);
        series3.setTitle(sig3[0]);

        series4 = new LineGraphSeries<>();
        graph.addSeries(series4);
        series4.setColor(Color.RED);
        series4.setTitle(sig4[0]);

        series5 = new LineGraphSeries<>();
        graph.addSeries(series5);
        series5.setColor(Color.WHITE);
        series5.setTitle(sig5[0]);

        series6 = new LineGraphSeries<>();
        graph.addSeries(series6);
        series6.setColor(Color.CYAN);
        series6.setTitle(sig6[0]);

        series7 = new LineGraphSeries<>();
        graph.addSeries(series7);
        series7.setColor(Color.GRAY);
        series7.setTitle(sig7[0]);

        series8 = new LineGraphSeries<>();
        graph.addSeries(series8);
        series8.setColor(Color.MAGENTA);
        series8.setTitle(sig8[0]);

        series9 = new LineGraphSeries<>();
        graph.addSeries(series9);
        series9.setColor(Color.DKGRAY);
        series9.setTitle(sig9[0]);

        series10 = new LineGraphSeries<>();
        graph.addSeries(series10);
        series10.setColor(Color.YELLOW);
        series10.setTitle(sig10[0]);


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


for(int i=0;i<11;i++)
{
    addEntry();
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

//
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

    private void setPforS(){
        if(s.equals("1")==true) {
            series.setThickness(6);
        }
        if(s.equals("2")==true) {
            series.setThickness(12);
        }
        if(s.equals("3")==true) {
            series.setThickness(18);
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
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////we add 100 new entries
//                for (int i = 0; i < 100; i++) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            addEntry();
//                        }
//                    });
////                    sleep to slow down the addition of the entries
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//
//                    }
//                }
//            }
//        }).start();
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
//        series1.setTitle(wName);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//        graph.getLegendRenderer().setWidth(200);
        graph.getLegendRenderer().setSpacing(15);
//        graph.getLegendRenderer().
        Random r0 = new Random();
        //int i0 = r0.nextInt(10 - (0)) + (0);
        int i0=Integer.parseInt(sig1[j]);
        series1.appendData(new DataPoint(lastX++,i0 ), true, 1000);
        int lastX1 = lastX - 1;

        int i1=Integer.parseInt(sig2[j]);

        series2.appendData(new DataPoint(lastX1++,i1 ), true, 1000);



        int lastX3 = lastX1- 1;
        //int i2 = i1 + 2;
        int i2=Integer.parseInt(sig3[j]);
        series3.appendData(new DataPoint(lastX3++,i2),true,1000);

        int lastX4 = lastX3 - 1;
        int i3 = Integer.parseInt(sig4[j]);
        series4.appendData(new DataPoint(lastX4++,i3),true,1000);

        int lastX5 = lastX4 - 1;
        int i4 = Integer.parseInt(sig5[j]);
        series5.appendData(new DataPoint(lastX5++,i4),true,1000);

        int lastX6 = lastX5 - 1;
        int i5 = Integer.parseInt(sig6[j]);
        series6.appendData(new DataPoint(lastX6++,i5),true,1000);

        int lastX7 = lastX6 - 1;
        int i6 = Integer.parseInt(sig7[j]);
        series7.appendData(new DataPoint(lastX7++,i6),true,1000);

        int lastX8 = lastX7 - 1;
        int i7 = Integer.parseInt(sig8[j]);
        series8.appendData(new DataPoint(lastX8++,i7),true,1000);

        int lastX9 = lastX8 - 1;
        int i8 = Integer.parseInt(sig9[j]);
        series9.appendData(new DataPoint(lastX9++,i8),true,1000);

        int lastX10 = lastX9 - 1;
        int i9 = Integer.parseInt(sig10[j]);
        series10.appendData(new DataPoint(lastX10++,i9),true,1000);

//        Toast.makeText(this,String.valueOf(sig1[1]),Toast.LENGTH_SHORT).show();
//
//                series1.appendData(new DataPoint(lastX++,i0),true,1000);
//        int lastX1 = lastX - 1;
//                series2.appendData(new DataPoint(lastX1++,Integer.parseInt(sig2[1])),true,1000);
//        int lastX2 = lastX1 - 1;
//                series3.appendData(new DataPoint(lastX2++,Integer.parseInt(sig3[1])),true,1000);
//        int lastX3 = lastX2 - 1;
//                series4.appendData(new DataPoint(lastX3++,Integer.parseInt(sig4[1])),true,1000);
//                series5.appendData(new DataPoint(Integer.parseInt(time[j]),Integer.parseInt(sig5[j])),true,1000);
//                series6.appendData(new DataPoint(Integer.parseInt(time[j]),Integer.parseInt(sig6[j])),true,1000);
//                series7.appendData(new DataPoint(Integer.parseInt(time[j]),Integer.parseInt(sig7[j])),true,1000);
//                series8.appendData(new DataPoint(Integer.parseInt(time[j]),Integer.parseInt(sig8[j])),true,1000);
//                series9.appendData(new DataPoint(Integer.parseInt(time[j]),Integer.parseInt(sig9[j])),true,1000);
//                series10.appendData(new DataPoint(Integer.parseInt(time[j]),Integer.parseInt(sig10[j])),true,1000);









        if (wifiManager.isWifiEnabled() == false) {
            // If wifi disabled then enable it
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled",
                    Toast.LENGTH_LONG).show();

            wifiManager.setWifiEnabled(true);
        }
if(j<10)
j++;
        else
    j=1;
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

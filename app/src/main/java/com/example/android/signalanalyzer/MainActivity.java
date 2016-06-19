package com.example.android.signalanalyzer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.hardware.camera2.params.BlackLevelPattern;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.sax.StartElementListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.Random;
import java.util.concurrent.RunnableFuture;

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
    String wName;
    GraphView graph;

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
        if(BTAdapter.disable()== true){
            Toast.makeText(this,"Bluetooth is disabled..making it enabled",Toast.LENGTH_SHORT).show();
            BTAdapter.enable();
        }


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

    }


    @Override
    protected void onResume() {
        super.onResume();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.wifi_settings)
        {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }
        if (id == R.id.bluetooth_settings)
        {
            Intent intent1 = new Intent(this,BluetoothSettingsActivity.class);
            startActivity(intent1);
//            startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
        }
//
        return super.onOptionsItemSelected(item);
    }

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
                if(BluetoothDevice.ACTION_FOUND.equals(action));
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
        series3.appendData(new DataPoint(lastX2++, rssi2), true, 10);

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

package com.example.android.signalanalyzer;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.hardware.camera2.params.BlackLevelPattern;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.Random;
import java.util.concurrent.RunnableFuture;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final Random RANDOM = new Random();
private LineGraphSeries<DataPoint> series1;
    private LineGraphSeries<DataPoint>series2;
    public int lastX = 0;
    public long a ,b ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        creating a graph instance
        GraphView graph = (GraphView)findViewById(R.id.graph);
//        data
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
        series2.setColor(Color.BLACK);

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
//        spinner3.setOnItemSelectedListener(this);


        Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setSelection(1);
//        spinner4.setOnItemSelectedListener(this);


        Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);
        spinner5.setSelection(2);
//        spinner5.setOnItemSelectedListener(this);


        Spinner spinner6 = (Spinner) findViewById(R.id.spinner6);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter6);
        spinner6.setSelection(2);
//        spinner6.setOnItemSelectedListener(this);


    }
    @Override
    protected void onResume(){
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {
            @Override
            public void run() {
//we add 100 new entries
                for(int i = 0; i<100 ; i++){
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
//        if (id == R.id.action_settings) {
//Intent intent = new Intent(this, Personalization.class);
//            startActivity(intent);
//            return true;
//        }
//
        return super.onOptionsItemSelected(item);
    }

    //    add random data
    private void addEntry(){
//        here we choose a max of 10 points to show up on the Viewport
        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        int rssi = wifiManager.getConnectionInfo().getRssi();

        series1.appendData(new DataPoint(lastX++, rssi),true, 10);
int lastX1 = lastX-1;
        Random r = new Random();
        int i1 = r.nextInt(-60-(-70)) + (-70);
        series2.appendData(new DataPoint(lastX1++,i1),true,10);

        if (wifiManager.isWifiEnabled() == false)
        {
            // If wifi disabled then enable it
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled",
                    Toast.LENGTH_LONG).show();

            wifiManager.setWifiEnabled(true);
        }
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

                a = adapterView.getItemIdAtPosition(i);

                break;

            case R.id.spinner2:
                b= adapterView.getItemIdAtPosition(i);
//                Toast.makeText(this, "a=" + i, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "b=" + i, Toast.LENGTH_SHORT).show();

                break;


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
   public void bWifi(View view)
    {

        if(a==0 && b==0)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.RED);
            paint.setPathEffect(new CornerPathEffect(10));
            series1.setCustomPaint(paint);
        }
        if(a==0 && b==1)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.RED);
            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
            series1.setCustomPaint(paint);
        }
        if(a==0 && b==2)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(30);
            paint.setColor(Color.RED);

            paint.setPathEffect(new CornerPathEffect(10));
            series1.setCustomPaint(paint);
        }

        if(a==1 && b==0)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.BLUE);
            paint.setPathEffect(new CornerPathEffect(10));
            series1.setCustomPaint(paint);
        }
        if(a==1 && b==1)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.BLUE);
            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
            series1.setCustomPaint(paint);

        }
        if(a==1 && b==2)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(30);
            paint.setColor(Color.BLUE);
            paint.setPathEffect(new CornerPathEffect(10));
            series1.setCustomPaint(paint);

        }
        if(a==2 && b==0)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.YELLOW);
            paint.setPathEffect(new CornerPathEffect(10));
            series1.setCustomPaint(paint);

        }
        if(a==2 && b==1)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.YELLOW);
            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
            series1.setCustomPaint(paint);

        }
        if(a==2 && b==2)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(30);
            paint.setColor(Color.YELLOW);
            paint.setPathEffect(new CornerPathEffect(10));
            series1.setCustomPaint(paint);

        }

    }
}

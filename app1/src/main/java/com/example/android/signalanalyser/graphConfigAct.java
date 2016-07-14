package com.example.android.signalanalyser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class graphConfigAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public long a, b, c, d, e, f, g, h,i1,j,k,l,m,n,o,p,q,r,s,t,u,v;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20;
    public static final String PREFS_NAME = "MyPrefsFile";
    int color = 0xffff00;
    String color1;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_config);

//        Spinner spinner1 = (Spinner) findViewById(R.id.sp1);
         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner1.setAdapter(adapter);
//        spinner1.setOnItemSelectedListener(this);

        text1 = (TextView) findViewById(R.id.text1);
        Spinner spinner2 = (Spinner) findViewById(R.id.sp2);
         ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        Spinner spinner3 = (Spinner) findViewById(R.id.sp3);
         ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);

        Spinner spinner4 = (Spinner) findViewById(R.id.sp4);
         ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setSelection(1);
        spinner4.setOnItemSelectedListener(this);

        Spinner spinner5 = (Spinner) findViewById(R.id.sp5);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);
        spinner5.setOnItemSelectedListener(this);
        spinner5.setSelection(1);

        Spinner spinner6 = (Spinner) findViewById(R.id.sp6);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter6);
        spinner6.setOnItemSelectedListener(this);
        spinner6.setSelection(1);

        Spinner spinner7 = (Spinner) findViewById(R.id.sp7);
        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner7.setAdapter(adapter7);
        spinner7.setOnItemSelectedListener(this);
        spinner7.setSelection(2);

        Spinner spinner8 = (Spinner) findViewById(R.id.sp8);
        ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner8.setAdapter(adapter8);
        spinner8.setOnItemSelectedListener(this);
        spinner8.setSelection(2);

        Spinner spinner9 = (Spinner) findViewById(R.id.sp9);
        ArrayAdapter<CharSequence> adapter9 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner9.setAdapter(adapter9);
        spinner9.setOnItemSelectedListener(this);
        spinner9.setSelection(2);

        Spinner spinner10 = (Spinner) findViewById(R.id.sp10);
        ArrayAdapter<CharSequence> adapter10 = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner10.setAdapter(adapter10);
        spinner10.setOnItemSelectedListener(this);
        spinner10.setSelection(3);

        Spinner spinner11 = (Spinner) findViewById(R.id.sp11);
        ArrayAdapter<CharSequence> adapter11 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner11.setAdapter(adapter11);
        spinner11.setOnItemSelectedListener(this);

        Spinner spinner12= (Spinner) findViewById(R.id.sp12);
        ArrayAdapter<CharSequence> adapter12 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner12.setAdapter(adapter12);
        spinner12.setSelection(1);
        spinner12.setOnItemSelectedListener(this);

        Spinner spinner13 = (Spinner) findViewById(R.id.sp13);
        ArrayAdapter<CharSequence> adapter13 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner13.setAdapter(adapter13);
        spinner13.setSelection(2);
        spinner13.setOnItemSelectedListener(this);

        Spinner spinner14 = (Spinner) findViewById(R.id.sp14);
        ArrayAdapter<CharSequence> adapter14 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner14.setAdapter(adapter14);
        spinner14.setOnItemSelectedListener(this);

        Spinner spinner15 = (Spinner) findViewById(R.id.sp15);
        ArrayAdapter<CharSequence> adapter15 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner15.setAdapter(adapter15);
        spinner15.setSelection(1);
        spinner15.setOnItemSelectedListener(this);

        Spinner spinner16 = (Spinner) findViewById(R.id.sp16);
        ArrayAdapter<CharSequence> adapter16 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner16.setAdapter(adapter16);
        spinner16.setSelection(2);
        spinner16.setOnItemSelectedListener(this);

        Spinner spinner17 = (Spinner) findViewById(R.id.sp17);
        ArrayAdapter<CharSequence> adapter17 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner17.setAdapter(adapter17);
        spinner17.setOnItemSelectedListener(this);

        Spinner spinner18 = (Spinner) findViewById(R.id.sp18);
        ArrayAdapter<CharSequence> adapter18 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner18.setAdapter(adapter18);
        spinner18.setSelection(1);
        spinner18.setOnItemSelectedListener(this);

        Spinner spinner19 = (Spinner) findViewById(R.id.sp19);
        ArrayAdapter<CharSequence> adapter19 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner19.setAdapter(adapter19);
        spinner19.setSelection(2);
        spinner19.setOnItemSelectedListener(this);

        Spinner spinner20 = (Spinner) findViewById(R.id.sp20);
        ArrayAdapter<CharSequence> adapter20 = ArrayAdapter.createFromResource(this,
                R.array.style_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner20.setAdapter(adapter20);
        spinner20.setOnItemSelectedListener(this);

    }

    public void bt1C(View view){
        openDialog(true);
    }

    void openDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(graphConfigAct.this, color, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                graphConfigAct.this.color = color;
                displayColor();
            }
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    void displayColor() {
        text1.setText(String.format("Current color: 0x%08x", color));
        color1 = String.format("#%06X", (0xFFFFFF & color));

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        switch (adapterView.getId()) {
//            case R.id.sp1:
//
//                s1 = adapterView.getItemAtPosition(i).toString();
//
//                break;

            case R.id.sp2:
                s2 = adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp3:
                s3 = adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp4:
                s4 = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(this, "d = "+d, Toast.LENGTH_SHORT).show();
                break;
            case R.id.sp5:
                s5 = adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp6:
                s6 = adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp7:
                s7 = adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp8:
                s8 = adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp9:
                s9 = adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp10:
                s10 = adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp11:
                s11= adapterView.getItemAtPosition(i).toString();;
                break;
            case R.id.sp12:
                s12= adapterView.getItemAtPosition(i).toString();;
                break;
            case R.id.sp13:
                s13= adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp14:
                s14= adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp15:
                s15= adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp16:
                s16 = adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp17:
                s17= adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp18:
                s18= adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp19:
                s19= adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sp20:
                s20 = adapterView.getItemAtPosition(i).toString();
                break;



        }

    }
    public void applyBtClick(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("spinner1",s1);
        intent.putExtra("color1",color1);
//        intent.putExtra("spinner2",s2);
//        intent.putExtra("spinner3",s3);
//        intent.putExtra("spinner4",s4);
//        intent.putExtra("spinner5",s5);
//        intent.putExtra("spinner6",s6);
//        intent.putExtra("spinner7",s7);
//        intent.putExtra("spinner8",s8);
//        intent.putExtra("spinner9",s9);
//        intent.putExtra("spinner10",s10);
//        intent.putExtra("spinner11",s11);
//        intent.putExtra("spinner12",s12);
//        intent.putExtra("spinner13",s13);
//        intent.putExtra("spinner14",s14);
//        intent.putExtra("spinner15",s15);
//        intent.putExtra("spinner16",s16);
//        intent.putExtra("spinner17",s17);
//        intent.putExtra("spinner18",s18);
//        intent.putExtra("spinner19",s19);
//        intent.putExtra("spinner20",s20);
        setResult(RESULT_OK,intent);
        super.finish();
    }
//    public void setStyle(View view) {
//        if (a == 0 && b == 0) {
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(10);
//            paint.setColor(Color.RED);
//            paint.setPathEffect(new CornerPathEffect(10));
//            series.setCustomPaint(paint);
//        }
//        if (a == 0 && b == 1) {
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(10);
//            paint.setColor(Color.RED);
//            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
//            series.setCustomPaint(paint);
//        }
//        if (a == 0 && b == 2) {
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(20);
//            paint.setColor(Color.RED);
//
//            paint.setPathEffect(new CornerPathEffect(10));
//            series.setCustomPaint(paint);
//        }
//
//        if (a == 1 && b == 0) {
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(10);
//            paint.setColor(Color.BLUE);
//            paint.setPathEffect(new CornerPathEffect(10));
//            series.setCustomPaint(paint);
//        }
//        if (a == 1 && b == 1) {
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(10);
//            paint.setColor(Color.BLUE);
//            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
//            series.setCustomPaint(paint);
//
//        }
//        if (a == 1 && b == 2) {
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(20);
//            paint.setColor(Color.BLUE);
//            paint.setPathEffect(new CornerPathEffect(10));
//            series.setCustomPaint(paint);
//
//        }
//        if (a == 2 && b == 0) {
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(10);
//            paint.setColor(Color.YELLOW);
//            paint.setPathEffect(new CornerPathEffect(10));
//            series.setCustomPaint(paint);
//
//        }
//        if (a == 2 && b == 1) {
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(10);
//            paint.setColor(Color.YELLOW);
//            paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
//            series.setCustomPaint(paint);
//
//        }
//        if (a == 2 && b == 2) {
//            Paint paint = new Paint();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(20);
//            paint.setColor(Color.YELLOW);
//            paint.setPathEffect(new CornerPathEffect(10));
//            series.setCustomPaint(paint);
//
//        }
//
//    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

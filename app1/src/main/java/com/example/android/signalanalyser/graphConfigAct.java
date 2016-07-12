package com.example.android.signalanalyser;

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

public class graphConfigAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public long a, b, c, d, e, f, g, h,i1,j,k,l,m,n,o,p,q,r,s,t,u,v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_config);

        Spinner spinner1 = (Spinner) findViewById(R.id.sp1);
         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.color_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        switch (adapterView.getId()) {
            case R.id.sp1:

                u = adapterView.getItemIdAtPosition(i);

                break;

            case R.id.sp2:
                v = adapterView.getItemIdAtPosition(i);
//                Toast.makeText(this, "a=" + i, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "b=" + i, Toast.LENGTH_SHORT).show();
                break;
            case R.id.sp3:
//                Toast.makeText(this,"entered", Toast.LENGTH_SHORT).show();
                c = adapterView.getItemIdAtPosition(i);
//                Toast.makeText(this,"c = "+c, Toast.LENGTH_SHORT).show();
                break;
            case R.id.sp4:
                d = adapterView.getItemIdAtPosition(i);
//                Toast.makeText(this, "d = "+d, Toast.LENGTH_SHORT).show();
                break;
            case R.id.sp5:
                e = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp6:
                f = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp7:
                g = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp8:
                h = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp9:
                i1 = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp10:
                j = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp11:
                k = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp12:
                l = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp13:
                m = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp15:
                n = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp16:
                o = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp17:
                p = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp18:
                q = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp19:
                r = adapterView.getItemIdAtPosition(i);
                break;
            case R.id.sp20:
                s = adapterView.getItemIdAtPosition(i);
                break;



        }

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

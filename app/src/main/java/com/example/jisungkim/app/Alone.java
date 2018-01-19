package com.example.jisungkim.app;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Alone extends AppCompatActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alone);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent= getIntent();
        String text=intent.getStringExtra("a1");
        String text2=intent.getStringExtra("a2");
        String text3=intent.getStringExtra("a3");
        String text4=intent.getStringExtra("a4");

        ArrayList<String> array=new ArrayList<String>();

        array.add(text);
        array.add(text2);
        array.add(text3);
        array.add(text4);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,array);

        ListView list=(ListView)findViewById(R.id.list_item);
        list.setAdapter(adapter);
    }
    @Override

    public void onMapReady(final GoogleMap map) {

        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }


   public void mOnClick2(View v){
        Intent i=new Intent(this,Date.class);
        startActivity(i);
    }


    public void mOnClick(View v){
        Intent intent=getIntent();
        String mtext=intent.getStringExtra("a3");
        String mtext2=intent.getStringExtra("a2");
        String mtext3=intent.getStringExtra("a1");
        String mtext4=intent.getStringExtra("a4");


        ArrayList<String> array=new ArrayList<String>();
        switch(v.getId()){

            case R.id.rankbtn:
                array.add(mtext);
                array.add(mtext2);
                array.add(mtext3);
                array.add(mtext4);

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,array);

                ListView list=(ListView)findViewById(R.id.list_item);
                list.setAdapter(adapter);
                break;

            case R.id.lengthbtn:

                array.add(mtext2);
                array.add(mtext);
                array.add(mtext4);
                array.add(mtext3);

                ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,array);

                ListView list2=(ListView)findViewById(R.id.list_item);
                list2.setAdapter(adapter2);
                break;

            case R.id.pricebtn:
                array.add(mtext4);
                array.add(mtext2);
                array.add(mtext3);
                array.add(mtext);

                ArrayAdapter<String> adapter3=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,array);

                ListView list3=(ListView)findViewById(R.id.list_item);
                list3.setAdapter(adapter3);
                break;
        }

    }


}

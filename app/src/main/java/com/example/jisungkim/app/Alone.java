package com.example.jisungkim.app;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.BindView;

public class Alone extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener  {

    ArrayList<MyItem> arItem;
    Marker selectedMarker;
    View marker_root_view;
    TextView tv_marker;
    private GoogleMap mMap;
    @BindView(R.id.fab_gps) FloatingActionButton fab_gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alone);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("맛집GO!");

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent= getIntent();
        String text=intent.getStringExtra("a1");
        String text2=intent.getStringExtra("a2");
        String text3=intent.getStringExtra("a3");
        String text4=intent.getStringExtra("a4");

        arItem=new ArrayList<MyItem>();
        MyItem mi;
        mi=new MyItem("A",text,"2인 2만원대");
        arItem.add(mi);
        mi=new MyItem("B",text2,"2인 1만원대");
        arItem.add(mi);
        mi=new MyItem("C",text3,"2인 2만원대");
        arItem.add(mi);
        mi=new MyItem("D",text4,"2인 3만원대");
        arItem.add(mi);



        MyListAdapter MyAdapter = new MyListAdapter(this, R.layout.list,arItem);
        final ListView MyList;
        MyList=(ListView) findViewById(R.id.list_item);
        MyList.setAdapter(MyAdapter);

        MyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageButton btn=(ImageButton)view.findViewById(R.id.btn);
                btn.setVisibility(View.VISIBLE);

            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.537523, 126.96558), 14));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        setCustomMarkerView();
        getSampleMarkerItems();
    }

    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }


    private void getSampleMarkerItems() {
        ArrayList<MarkerItem> sampleList = new ArrayList();


        sampleList.add(new MarkerItem(37.538523, 126.96568, "A"));
        sampleList.add(new MarkerItem(37.527523, 126.96568, "B"));
        sampleList.add(new MarkerItem(37.549523, 126.96568, "C"));
        sampleList.add(new MarkerItem(37.538523, 126.95768, "D"));


        for (MarkerItem markerItem : sampleList) {
            addMarker(markerItem, false);
        }

    }

    private Marker addMarker(MarkerItem markerItem, boolean isSelectedMarker) {


        LatLng position = new LatLng(markerItem.getLat(), markerItem.getLon());
        String mark = markerItem.getMark();

        tv_marker.setText(mark);

        if (isSelectedMarker) {
            tv_marker.setBackgroundResource(R.drawable.marker_red_s);
            tv_marker.setTextColor(Color.WHITE);
        } else {
            tv_marker.setBackgroundResource(R.drawable.marker_navy_s);
            tv_marker.setTextColor(Color.WHITE);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(mark);
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));


        return mMap.addMarker(markerOptions);

    }




    // View를 Bitmap으로 변환
    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

//새로운 마커 생성
    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        String mark = marker.getTitle();
        MarkerItem temp = new MarkerItem(lat, lon, mark);
        return addMarker(temp, isSelectedMarker);

    }


    //마커 클릭이벤트
    @Override
    public boolean onMarkerClick(Marker marker) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);


        changeSelectedMarker(marker);

        return true;
    }


    // 선택했던 마커 되돌리기
    private void changeSelectedMarker(Marker marker) {

        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }


    }


    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }

    public void mmOnClick(View v){
        ImageButton btn=(ImageButton)findViewById(R.id.btn);
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://store.naver.com/restaurants/detail?id=1630366010"));
        startActivity(intent);
    }


    public void mOnClick(View v){
        Intent intent= getIntent();
        String text=intent.getStringExtra("a1");
        String text2=intent.getStringExtra("a2");
        String text3=intent.getStringExtra("a3");
        String text4=intent.getStringExtra("a4");


        arItem=new ArrayList<MyItem>();
        MyItem mi;
        mi=new MyItem("A",text,"2인 2만원대");arItem.add(mi);
        mi=new MyItem("B",text2,"2인 1만원대");arItem.add(mi);
        mi=new MyItem("C",text3,"2인 4만원대");arItem.add(mi);
        mi=new MyItem("D",text4,"2인 6만원대");arItem.add(mi);


        MyListAdapter MyAdapter = new MyListAdapter(this, R.layout.list,arItem);
        ListView MyList;
        MyList=(ListView) findViewById(R.id.list_item);
        MyList.setAdapter(MyAdapter);

        switch(v.getId()){

            case R.id.rankbtn:
                arItem=new ArrayList<MyItem>();
                MyItem mii;
                mii=new MyItem("A",text2,"2인 3만원대");arItem.add(mii);
                mii=new MyItem("B",text,"2인 2만원대");arItem.add(mii);
                mii=new MyItem("C",text4,"2인 1만원대");arItem.add(mii);
                mii=new MyItem("D",text3,"2인 2만원대");arItem.add(mii);

                MyListAdapter MyAdapter1 = new MyListAdapter(this, R.layout.list,arItem);
                ListView MyList1;
                MyList1=(ListView) findViewById(R.id.list_item);
                MyList1.setAdapter(MyAdapter1);
                break;

            case R.id.lengthbtn:
                arItem=new ArrayList<MyItem>();
                MyItem mi2;
                mi2=new MyItem("A",text3,"2인 5만원대");arItem.add(mi2);
                mi2=new MyItem("B",text,"2인 3만원대");arItem.add(mi2);
                mi2=new MyItem("C",text2,"2인 1만원대");arItem.add(mi2);
                mi2=new MyItem("D",text4,"2인 2만원대");arItem.add(mi2);

                MyListAdapter MyAdapter2 = new MyListAdapter(this, R.layout.list,arItem);
                ListView MyList2;
                MyList2=(ListView) findViewById(R.id.list_item);
                MyList2.setAdapter(MyAdapter2);
                break;

            case R.id.pricebtn:
                arItem=new ArrayList<MyItem>();
                MyItem mi3;
                mi3=new MyItem("A",text3,"2인 3만원대");arItem.add(mi3);
                mi3=new MyItem("B",text4,"2인 6만원대");arItem.add(mi3);
                mi3=new MyItem("C",text,"2인 2만원대");arItem.add(mi3);
                mi3=new MyItem("D",text2,"2인 2만원대");arItem.add(mi3);

                MyListAdapter MyAdapter3 = new MyListAdapter(this, R.layout.list,arItem);
                ListView MyList3;
                MyList3=(ListView) findViewById(R.id.list_item);
                MyList3.setAdapter(MyAdapter3);
                break;


        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

package com.example.jisungkim.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.alone) LinearLayout alone;
    @BindView(R.id.date)   LinearLayout date;
    @BindView(R.id.friend)     LinearLayout friend;
    @BindView(R.id.family)     LinearLayout family;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        alone.setOnClickListener(this);
        date.setOnClickListener(this);
        friend.setOnClickListener(this);
        family.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.alone:
                i = new Intent(MainActivity.this, Alone.class);
                startActivity(i);
                break;
            case R.id.date:
                i = new Intent(MainActivity.this, Date.class);
                startActivity(i);
                break;
            case R.id.friend:
                i = new Intent(MainActivity.this, Friend.class);
                startActivity(i);
                break;
            case R.id.family:
                i = new Intent(MainActivity.this, Family.class);
                startActivity(i);
                break;
        }
    }
}

package com.app.searchplaces.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.searchplaces.R;
import com.app.searchplaces.ui.base.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

package com.example.sql;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SQL 연결 및 데이터 가져오기
        ConnectionHelper connectionHelper = new ConnectionHelper();
        connectionHelper.execute();
    }
}

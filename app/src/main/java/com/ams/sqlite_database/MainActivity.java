package com.ams.sqlite_database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText value;
    Button add;
    RecyclerView recycler;
    adapter adapter;
    List<dbHelper> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        value = findViewById(R.id.value);
        add = findViewById(R.id.add);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        final sqlite sqlite = new sqlite(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();

                if(TextUtils.isEmpty(val)){
                    value.setError("This is required");
                    value.requestFocus();
                }
                else{
                    Boolean added = sqlite.add(val);
                    if(added){
                        Toast.makeText(MainActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        list = sqlite.getAllData();
        adapter = new adapter(this, list, sqlite);
        recycler.setAdapter(adapter);
    }
}
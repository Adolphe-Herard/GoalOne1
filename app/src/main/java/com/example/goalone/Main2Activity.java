package com.example.goalone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.goalone.MainActivity.edittext;
import static com.example.goalone.MainActivity.positionitem;

public class Main2Activity extends AppCompatActivity {
    int position;
    Button save;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit item");
        save =(Button)findViewById(R.id.btn_save);
        edit = (EditText)findViewById(R.id.edititem);
        edit.setText(getIntent().getStringExtra(edittext));
        position = getIntent().getIntExtra(positionitem,0);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemupdate = edit.getText().toString();
                if (itemupdate.isEmpty()){
                    Toast.makeText(Main2Activity.this, "Please Enter a new item", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra(edittext,itemupdate);
                    intent.putExtra(positionitem,position);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}

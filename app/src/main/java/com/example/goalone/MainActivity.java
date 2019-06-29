package com.example.goalone;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText edtitem;
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayList;
    Button button_add;
    public static int code = 2;
    public static String edittext = "edittext";
    public static String positionitem = "positionitem";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Simple Todo");
        listView = (ListView)findViewById(R.id.listview);
        edtitem = (EditText)findViewById(R.id.edtadd);
        button_add = (Button)findViewById(R.id.btn_add);

        //Lire les donnees qui stockent dans le fichier listitem.txt
        Readfile();

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = edtitem.getText().toString();
                if (item.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter a new item", Toast.LENGTH_SHORT).show();
                }else{
                    arrayAdapter.add(item);
                    writeitem();
                    Toast.makeText(MainActivity.this, "Added to list", Toast.LENGTH_SHORT).show();
                    edtitem.setText("");
                }
            }
        });

        //Even when click on listener

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra(edittext,arrayList.get(position));
                intent.putExtra(positionitem,position);
                startActivityForResult(intent,code);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                writeitem();
                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == code){
            String itemupdate = data.getExtras().getString(edittext);
            int positionupdate = data.getExtras().getInt(positionitem);
            arrayList.set(positionupdate,itemupdate);
            arrayAdapter.notifyDataSetChanged();
            writeitem();
            Toast.makeText(this, "Updated  item to list", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File getirem(){
        return new File(getFilesDir(),"listitem.txt");
    }
    private void Readfile(){
        try {
            arrayList = new ArrayList<>(FileUtils.readLines(getirem(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            arrayList = new ArrayList<>();
        }
    }
    private void writeitem(){
        try {
            FileUtils.writeLines(getirem(),arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.ujianpraktek;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ujianpraktek.helper.DbHelper;

import com.example.ujianpraktek.helper.DbHelper;

public class AddEdit extends AppCompatActivity {
    EditText txt_id ,txt_name,txt_address;
    Button btn_submit,btn_cancel;
    DbHelper SQLite = new DbHelper(this);
    String id,name,address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = findViewById(R.id.txt_id);
        txt_name = findViewById(R.id.txt_name);
        txt_address = findViewById(R.id.txt_address);
        btn_submit = findViewById(R.id.btn_submit);
        btn_cancel = findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADRRESS);
        Log.d("debug", "data : " + id + " " + name + " " + address);
        if(id == null || id.equals("")){
            setTitle("Add data");

        }else {
            setTitle("Edit Data");
            txt_id.setText(id);
            txt_name.setText(name);
            txt_address.setText(address);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        if (txt_id.getText().toString().equals("")) {
                            save();
                        }else {
                            edit();
                        }
                }
                catch (Exception e){
                    Log.e("btn submit ", "instance initializer: "  + e.toString() );
                }
            }

        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                blank();
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void blank() {
        txt_name.requestFocus();
        txt_id.setText(null);
        txt_name.setText(null);
        txt_address.setText(null);
    }
    private void save(){
        if(String.valueOf(txt_name.getText()).equals(null)|| String.valueOf(txt_name.getText()).equals("")
        || String.valueOf(txt_address.getText()).equals(null)||String.valueOf(txt_address.getText()).equals("")){
            Toast.makeText(getApplicationContext(),"Data Yang Dimasukan Tidak Boleh Kosong!",Toast.LENGTH_SHORT).show();
        }else {
            SQLite.insert(txt_name.getText().toString().trim(),txt_address.getText().toString().trim());
            blank();
            finish();
        }
    }
    private void edit(){
        if(String.valueOf(txt_name.getText()).equals(null)|| String.valueOf(txt_name.getText()).equals("")
                || String.valueOf(txt_address.getText()).equals(null)||String.valueOf(txt_address.getText()).equals("")){
            Toast.makeText(getApplicationContext(),"Data Yang Dimasukan Tidak Boleh Kosong!",Toast.LENGTH_SHORT).show();
        }else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString().trim()) ,txt_name.getText().toString().trim(),txt_address.getText().toString().trim());
            blank();
            finish();
        }
    }
}

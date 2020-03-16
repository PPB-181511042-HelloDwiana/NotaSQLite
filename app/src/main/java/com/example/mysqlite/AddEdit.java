package com.example.mysqlite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddEdit extends AppCompatActivity {
    EditText txt_id, txt_name, txt_addres;;
    Button btn_submit, btn_cancel;
    DBHelper SQLite= new DBHelper (this);
    String id, name, address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_edit);
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

        txt_id = (EditText) findViewById (R.id.txt_id);
        txt_name = (EditText) findViewById (R.id.txt_name);
        txt_addres = (EditText) findViewById (R.id.txt_address);
        btn_submit = (Button) findViewById (R.id.btn_submit);
        btn_cancel = (Button) findViewById (R.id.btn_cancel);

        id = getIntent ().getStringExtra (MainActivity.TAG_ID);
        name = getIntent ().getStringExtra (MainActivity.TAG_NAME);
        address = getIntent ().getStringExtra (MainActivity.TAG_ADDRESS);

        if (id == null || id == "") {
            setTitle ("Add Data");
        } else {
            txt_id.setText (id);
            txt_name.setText (name);
            txt_addres.setText (address);
        }

        btn_submit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                try {
                    if (txt_id.getText ().toString ().equals ("")) {
                        save ();
                    } else {
                        edit ();
                    }
                } catch (Exception e) {
                    Log.e ("Submit", e.toString ());
                }

            }
        });

        btn_cancel.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                blank ();
                finish ();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish ();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home:
                blank ();
                this.finish ();
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }

    // Kosongkan semua Edit Text
    private void blank() {
        txt_name.requestFocus ();
        txt_id.setText (null);
        txt_name.setText (null);
        txt_addres.setText (null);
    }

    //Menyimpan data ke databse SQLite
    private void save() {
        if (String.valueOf (txt_name.getText ()).equals (null) || String.valueOf (txt_name.getText ()).equals ("") ||
                String.valueOf (txt_addres.getText ()).equals (null) || String.valueOf (txt_addres.getText ()).equals ("")) {
            Toast.makeText (getApplicationContext (), "Please input name....", Toast.LENGTH_SHORT).show ();
        } else {
            SQLite.insert (txt_name.getText ().toString ().trim (), txt_addres.getText ().toString ().trim ());
            blank ();
            finish ();
        }
    }

    //Update data kedalam Database SQLIte
    private void edit() {
        if (String.valueOf (txt_name.getText ()).equals (null) || String.valueOf (txt_name.getText ()).equals ("") ||
                String.valueOf (txt_addres.getText ()).equals (null) || String.valueOf (txt_addres.getText ()).equals ("")) {
            Toast.makeText (getApplicationContext (), "Please input task or deadline...", Toast.LENGTH_SHORT).show ();
        } else {
            SQLite.update (Integer.parseInt (txt_id.getText ().toString ().trim ()),txt_name.getText ().toString ().trim (), txt_addres.getText ().toString ().trim ());
            blank ();
            finish ();
        }
    }

}

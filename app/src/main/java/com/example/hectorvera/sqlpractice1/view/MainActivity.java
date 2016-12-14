package com.example.hectorvera.sqlpractice1.view;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hectorvera.sqlpractice1.R;
import com.example.hectorvera.sqlpractice1.database.ContactDH;
import com.example.hectorvera.sqlpractice1.objects.Contact;

public class MainActivity extends AppCompatActivity {
    EditText eName;
    EditText ePhone;
    TextView tId;
    ContactDH contactDH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactDH = new ContactDH(this);

        tId = ((TextView)findViewById(R.id.tId));
        eName = ((EditText) findViewById(R.id.eName));
        ePhone = ((EditText) findViewById(R.id.ePhone));
    }

    public void onRegister(View view) {
        String tempName = eName.getText().toString();
        String tempPhone = ePhone.getText().toString();

        contactDH.addContact(new Contact(tempName, tempPhone));

        Contact c = contactDH.getContact(tempName, tempPhone);

        tId.setText("Id: "+c.getId());
    }

    public void onDeleteAllInfo(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactDH.close();
    }
}

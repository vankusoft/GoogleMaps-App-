package com.ivan.jgntic.taximaptest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements View.OnClickListener{

    EditText registerUsername,registerPassword;
    Button registerButton;

    Database database;

    String username,password;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        registerUsername=(EditText)findViewById(R.id.registerUsername);
        registerPassword=(EditText)findViewById(R.id.registerPassword);

        registerButton=(Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        database=new Database(this);

    }

    @Override
    public void onClick(View v) {
        username=registerUsername.getText().toString();
        password=registerPassword.getText().toString();

        try
        {
            database.open();

            if(database.checkIfDatabaseIsEmpty())
            {
                c=database.getCurrentUser(username,password);
                if(c.moveToFirst())
                {
                    Toast.makeText(this,"USERNAME IS ALREADY TAKEN",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!username.equals("") && !password.equals(""))
                    {
                        database.insert(username,password);
                        Toast.makeText(this,"REGISTRATION SUCCESSFUL",Toast.LENGTH_SHORT).show();
                    }


                }

            }else
            {
                if(!username.equals("") && !password.equals(""))
                {
                    database.insert(username,password);
                }

            }

        }catch (SQLiteException e)
        {
            e.printStackTrace();
        }finally
        {
         database.close();

            Intent i=new Intent(this,Login.class);
            startActivity(i);
        }



    }
}

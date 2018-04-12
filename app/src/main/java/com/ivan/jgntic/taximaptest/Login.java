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

/**
 * Created by IVAN on 30.11.2016 г..
 */

public class Login extends Activity implements View.OnClickListener{

    EditText loginUsername,loginPassword;
    Button loginButton,logRegButton;

    String username,password;
    Database database;

    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        loginUsername=(EditText)findViewById(R.id.loginUsername);
        loginPassword=(EditText)findViewById(R.id.loginPassword);

        loginButton=(Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        logRegButton=(Button)findViewById(R.id.logRegButton);
        logRegButton.setOnClickListener(this);

        database=new Database(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.loginButton:

                try
                {
                    database.open();

                    username=loginUsername.getText().toString();
                    password=loginPassword.getText().toString();

                    c=database.getCurrentUser(username,password);

                    if(c.moveToFirst())
                    {
                        Toast.makeText(this,"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();

                        Intent goToMap=new Intent(this,MapsActivity.class);
                        startActivity(goToMap);

                    }
                    else
                    {
                        Toast.makeText(this,"USERNAME OR PASSWORD INCORRECT!",Toast.LENGTH_SHORT).show();
                    }


                }
                catch (SQLiteException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    database.close();
                }

                break;

            case R.id.logRegButton:

                Intent i = new Intent(this,Register.class);
                startActivity(i);

                break;
        }

    }
}

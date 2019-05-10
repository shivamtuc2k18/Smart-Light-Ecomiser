package com.stlight.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.stlight.R;



public class MenuScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.Numberprofiler:
                startActivity(new Intent(this, DeviceConfigActivity.class));
            break;
            case R.id.controllerprofiler:
                startActivity(new Intent(this, AddPhoneNumberActivity.class));
                break;

        }

    }

}

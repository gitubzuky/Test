
package com.example.administrator.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.test.widget.MyTestSwitch;

public class VideoActivity extends AppCompatActivity implements MyTestSwitch.OnStateChangeListener {
    MyTestSwitch myTestSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        myTestSwitch = (MyTestSwitch) findViewById(R.id.mytestswitch);
        /** 默认蓝色，这里修改为红色 */
        myTestSwitch.setOpenBackgroundColor(Color.parseColor("#f66359"));
        myTestSwitch.setState(true);
        myTestSwitch.setOnStateChangeListener(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTestSwitch.changeState(false);
            }
        });
    }

    @Override
    public void onStateChange(MyTestSwitch sv, boolean isOpen) {
        if (isOpen) {
            Toast.makeText(this, "打开", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "关闭", Toast.LENGTH_SHORT).show();
        }
    }
}

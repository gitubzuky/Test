
package com.example.administrator.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.test.model.WvBean;
import com.example.administrator.test.widget.MyTestSwitch;

import java.util.ArrayList;

public class WidgetActivity extends AppCompatActivity
        implements MyTestSwitch.OnStateChangeListener {
    MyTestSwitch myTestSwitch;
    // WheelView wv;
    ArrayList<WvBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        myTestSwitch = (MyTestSwitch) findViewById(R.id.mytestswitch);
        // wv = (WheelView) findViewById(R.id.wv);

        /** 默认蓝色，这里修改为红色 */
        myTestSwitch.setOpenBackgroundColor(Color.parseColor("#f66359"));
        myTestSwitch.setState(true);
        myTestSwitch.setOnStateChangeListener(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTestSwitch.changeState(false);
                startActivity(new Intent(WidgetActivity.this, CalActivity.class));
            }
        });

        showWv();
    }

    private void showWv() {
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            WvBean bean = new WvBean();
            bean.setTag(0);
            bean.setDay(i + 1);
            list.add(bean);
        }
        // wv.setAdapter(new ArrayWheelAdapter(list));
        // wv.setItemsVisible(3);
        // wv.setCyclic(false);
        // wv.setLineSpacingMultiplier(2.5f);
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

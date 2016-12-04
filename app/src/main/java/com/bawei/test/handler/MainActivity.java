package com.bawei.test.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*post方法解决UI更新问题handler创建方式*/
    private Handler handler_post = new Handler();

    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "Callback boolean", Toast.LENGTH_SHORT).show();
            if (msg.arg1==1) {
                tv1.setText("Callback boolean");//更新控件
            }
            return false;
        }
    }) {
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "Callback void", Toast.LENGTH_SHORT).show();
            if (msg.arg2==2) {
                tv2.setText("Callback void");//更新控件
            }
        };
    };
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        //初始化控件
        initCtrl();

        //更新ui,View中的Post
        Viewpost();

        //更新ui,handler中的sendMessage
        Messages();//Callback

        //更新ui,RunOnUiThread
        runonui();

        //更新ui,handler中的post
        hPost();
    }

    private void hPost() {
        new Thread(new Runnable() {

            @Override
            public void run() {
             final String   new_str = "使用handler.post更新UI";
                /*post方法解决UI更新，直接在runnable里面完成更新操作，这个任务会被添加到handler所在线程的消息队列中，即主线程的消息队列中*/
                handler_post.post(new Runnable() {

                    @Override
                    public void run() {
                        tv5.setText(new_str);
                    }
                });
            }
        }).start();
    }

    private void initCtrl() {
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
    }

    private void runonui() {
    new Thread(new Runnable() {
        @Override
        public void run() {
           activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv4.setText("使用runOnUiThread");
                }
            });
        }
    }).start();
    }

    private void Viewpost() {

        tv3.post(new Runnable() {
            @Override
            public void run() {
                tv3.setText("TextView,View中的Post");
            }
        });
    }

    private void Messages() {
        Message message = new Message();//创建消息对象
        //传递
        message.arg1=1;//用来存放整型数据
        message.arg2=2;
        message.what=3;//用于指定用户自定义的消息代码，这样接收者可以了解这个消息的信息
        message.obj=4;//用来存放发送给接收器的Object类型的任意对象
        mHandler.sendMessage(message);//handler发送

    }
}

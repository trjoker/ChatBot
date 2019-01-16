package com.example.chatbot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WelcomeActivity extends Activity {
    protected static final String TAG = "WelcomeActivity";
    private Context mContext;
    private RelativeLayout relativeLayout;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = this;
        findView();
        init();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                socket();
//            }
//        }).start();

    }

    private void socket() {
        String _pattern = "yyyy-MM-dd HH:mm:ss SSS";
        SimpleDateFormat format = new SimpleDateFormat(_pattern);
        Socket socket = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            socket = new Socket("222.190.139.10", 12222);
            socket.setSoTimeout(10 * 1000);
            Log.i("taoran", "connection establishment \n");

            //读取服务器端数据
            DataInputStream input = new DataInputStream(socket.getInputStream());
            //向服务器端发送数据
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Log.i("taoran", "" + format.format(new Date()));
            Log.i("taoran", "writing \n");
            out.writeUTF("你放假去哪玩");

            Log.i("taoran", "" + format.format(new Date()));
            Log.i("taoran", "send \n");
            String ret = input.readUTF();


            Log.i("taoran", format.format(new Date()) + "\nServer:" + ret);
        } catch (SocketTimeoutException e) {
            Log.i("taoran", format.format(new Date()) + "\n" + 10 + "s  time out\n\n\n\n\n");
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findView() {
        relativeLayout = findViewById(R.id.rl);
    }

    private void init() {
        relativeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);


    }
}

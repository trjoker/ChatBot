package com.example.chatbot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatbot.Data.DataManager;

/**
 * Created by Ryan on 2018/6/13.
 */
public class LoginActivity extends Activity {
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText = findViewById(R.id.account);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editText.getText().toString();
                if (!userName.isEmpty()) {
                    //存储用户名
                    DataManager.currentUserName = userName;


                    //跳转到聊天页面
                    startActivity(new Intent(LoginActivity.this, ChatActivity.class));

                } else {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

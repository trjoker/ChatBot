package com.example.chatbot.bean;

import com.example.chatbot.Data.DataManager;

import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 聊天实体
 * Created by Ryan on 2018/6/13.
 */
public class ChatMessage extends LitePalSupport {

    //发生时间
    public String time;

    //与ai对话的用户
    public String username;

    //对话内容
    public String content;

    //是发送出去的吗
    public boolean send;

    public ChatMessage(String content, boolean send) {
        this.content = content;
        this.send = send;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
        String sendTime = sdf.format(date);
        this.time = sendTime;
        this.username = DataManager.currentUserName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}

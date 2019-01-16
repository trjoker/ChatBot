package view.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatbot.R;
import com.example.chatbot.bean.ChatEntity;
import com.example.chatbot.bean.ChatMessage;

import java.util.List;


public class ChatMessageAdapter extends BaseAdapter {
    private List<ChatMessage> messageList;
    private LayoutInflater mInflater;
    private Context mContext0;

    public ChatMessageAdapter(Context context, List<ChatMessage> vector) {
        this.messageList = vector;
        mInflater = LayoutInflater.from(context);
        mContext0 = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMessageView;
        TextView rightMessageView;
        TextView timeView;
        ImageView leftPhotoView;
        ImageView rightPhotoView;
        view = mInflater.inflate(R.layout.chat_message_item_, null);
        ChatMessage message = messageList.get(position);
        leftLayout = (LinearLayout) view
                .findViewById(R.id.chat_friend_left_layout);
        rightLayout = (LinearLayout) view
                .findViewById(R.id.chat_user_right_layout);
        timeView = (TextView) view.findViewById(R.id.message_time);
        leftPhotoView = (ImageView) view
                .findViewById(R.id.message_friend_userphoto);
        rightPhotoView = (ImageView) view
                .findViewById(R.id.message_user_userphoto);
        leftMessageView = (TextView) view.findViewById(R.id.friend_message);
        rightMessageView = (TextView) view.findViewById(R.id.user_message);

        timeView.setText(message.time);
        if (message.send) {
            rightLayout.setVisibility(View.VISIBLE);
            leftLayout.setVisibility(View.GONE);
            rightMessageView.setText(message.content);
        } else {// 本身作为接收方
            leftLayout.setVisibility(View.VISIBLE);
            rightLayout.setVisibility(View.GONE);
            leftMessageView.setText(message.content);
        }
        return view;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

}

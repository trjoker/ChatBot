package com.example.chatbot;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chatbot.Data.DataManager;
import com.example.chatbot.bean.ChatMessage;
import com.example.chatbot.bean.WordVec;
import com.example.chatbot.inference.MyInference;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import network.Urls;
import util.Interfence;
import util.WordVecUtil;
import view.Adapter.ChatMessageAdapter;
import view.TitleBarView;

/**
 * Created by Ryan on 2018/6/13.
 */
public class ChatActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    private TitleBarView mTitleBarView;
    private int friendId;
    private String friendName;
    private ListView chatMeessageListView;
    private ChatMessageAdapter chatMessageAdapter;
    private Button sendButton;
    private ImageButton emotionButton;
    private EditText inputEdit;

    private String content;
    private List<ChatMessage> messageList;


    private Boolean isModelLoaded = false;

    private MyInference myInference;
    private Interfence interfence;

    private static final String HANDLE_THREAD_NAME = "ReplyBackGround";
    private Handler backgroundHandler;
    private HandlerThread backgroundThread;
    private Handler handler;
    private Executor executor = Executors.newSingleThreadExecutor();
    private final Object lock = new Object();
    private boolean runClassifier = false;
    private RadioButton localChatRB;
    private RadioButton netChatRB;
    private RadioButton netFudanRB;
    private RadioGroup radioGroup;


    private String requestURL = Urls.CHAT_URL;

    //是否使用服务端模型
    private boolean isNet = true;
    private Runnable periodicClassify =
            new Runnable() {
                @Override
                public void run() {
                    synchronized (lock) {
                        if (runClassifier) {

                            getReply();
                        }
                    }
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        initViews();
        initValues();
        initEvents();
        prepareBackgroundThread();
    }


    private void setModelLoadedFlag() {
        isModelLoaded = true;
    }

    private void initValues() {
        //读数据
        messageList = LitePal.where("username = ? ", DataManager.currentUserName).find(ChatMessage
                .class);

        // 初始化线程池
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //判断要不要将文件存入数据库
                    List<WordVec> wordVecs = LitePal.findAll(WordVec.class);
                    if (wordVecs == null || wordVecs.size() == 0) {
                        WordVecUtil.createTabelFromJson(getAssets());
                    }
                    //初始化模型
                    myInference = MyInference.create(
                            getAssets()
                    );
                    setModelLoadedFlag();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    protected void initViews() {
        // TODO Auto-generated method stub
        chatMeessageListView = (ListView) findViewById(R.id.chat_Listview);
        sendButton = (Button) findViewById(R.id.chat_btn_send);
        inputEdit = (EditText) findViewById(R.id.chat_edit_input);
        radioGroup = findViewById(R.id.rg);
    }

    protected void initEvents() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        chatMessageAdapter.notifyDataSetChanged();
                        chatMeessageListView.setSelection(messageList.size());
                        break;
                    default:
                        break;
                }
            }
        };


        if (messageList == null || messageList.size() == 0) {
            messageList = new ArrayList<>();
            creatChatMessage(DataManager.currentUserName + "你好！", false);
        }
        chatMessageAdapter = new ChatMessageAdapter(ChatActivity.this, messageList);
        chatMeessageListView.setAdapter(chatMessageAdapter);
        chatMeessageListView.setSelection(messageList.size());
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isModelLoaded) {
                    Toast.makeText(ChatActivity.this, "模型还在加载中", Toast.LENGTH_SHORT).show();
                    return;
                }


                content = inputEdit.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                inputEdit.setText("");
                creatChatMessage(content, true);
                if (isNet) {
                    //请求
                    requestBackMessage(content);
                } else {
                    //本地
                    backgroundHandler.post(periodicClassify);
                }


            }
        });
        radioGroup.setOnCheckedChangeListener(this);

    }


    private void getReply() {
        String response = "";
        if (myInference != null) {
            response = myInference.getRely(content);
        }
        creatChatMessage(response, false);

    }


    private void creatChatMessage(String content, boolean isSend) {
        ChatMessage chatMessage = new ChatMessage(content, isSend);
        messageList.add(chatMessage);
        saveChatMessage(chatMessage);
        handler.sendEmptyMessage(1);
    }


    //请求返回对话
    private void requestBackMessage(String message) {

        OkGo.<String>post(requestURL).tag(this).isMultipart(true).params("content", message)
                .execute(new StringCallback() {


                    @Override
                    public void onSuccess(Response<String> response) {
                        creatChatMessage(response.body(), false);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        if (response != null && response.message() != null) {
                        }
                        super.onError(response);
                    }
                });

    }

    private boolean saveChatMessage(ChatMessage message) {

        //TODO 存入消息 key 是用户名
        message.save();
        return true;
    }

    private void stopBackgroundThread() {
        backgroundThread.quitSafely();
        try {
            backgroundThread.join();
            backgroundThread = null;
            backgroundHandler = null;
            synchronized (lock) {
                runClassifier = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void prepareBackgroundThread() {
        backgroundThread = new HandlerThread(HANDLE_THREAD_NAME);
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
        synchronized (lock) {
            runClassifier = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopBackgroundThread();
        myInference.close();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_local_chat:
                Toast.makeText(this, "使用本地对话模型", Toast.LENGTH_SHORT).show();
                isNet = false;
                break;

            case R.id.rb_net_chat:
                Toast.makeText(this, "使用网络对话模型", Toast.LENGTH_SHORT).show();
                isNet = true;
                requestURL = Urls.CHAT_URL;
                break;
            case R.id.rb_net_fudan:
                Toast.makeText(this, "使用网络语义分析", Toast.LENGTH_SHORT).show();
                isNet = true;
                requestURL = Urls.FUDAN_NLP_URL;
                break;
            default:
                break;
        }

    }
}

package util;

import android.content.Context;

import com.example.chatbot.bean.WordVec;
import com.example.chatbot.inference.MyInference;

import org.litepal.LitePal;

import java.util.List;

/**
 * Created by Ryan on 2018/7/5.
 */
public class LocalInterfence extends Interfence {
    private MyInference myInference;

    @Override
    String getInterfence(String content) {
        String response = myInference.getRely(content);
        return response;
    }

    @Override
    void prepare(Context context) {
        //判断要不要将文件存入数据库
        List<WordVec> wordVecs = LitePal.findAll(WordVec.class);
        if (wordVecs == null || wordVecs.size() == 0) {
            WordVecUtil.createTabelFromJson(context.getAssets());
        }
        //初始化模型
        myInference = MyInference.create(
                context.getAssets()
        );
    }
}

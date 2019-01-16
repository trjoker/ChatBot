package util;

import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.example.chatbot.bean.WordVec;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Ryan on 2018/6/27.
 */
public class WordVecUtil extends LitePalSupport {


    public static String vec2word(int[] vec) {
        String words = new String();

        for (int i = 0; i < vec.length; i++) {
            if (vec[i] == 0) {
                break;
            }
            //查表
            List<WordVec> wordVecs = LitePal.where("key = ? ", vec[i] + "").find(WordVec
                    .class);
            if (wordVecs != null) {
                WordVec wordVec = wordVecs.get(0);
                words = words + wordVec.getValue() + " ";
            }
        }
        return words;
    }

    public static int[] word2Vec(String sentence) {

        String[] split = sentence.split("\\s+");
        int[] vec = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            //查表
            List<WordVec> wordVecs = LitePal.where("value = ? ", split[i]).find(WordVec
                    .class);
            if (wordVecs.size() != 0) {
                WordVec wordVec = wordVecs.get(0);
                vec[split.length - i - 1] = wordVec.getKey();
            } else {
                vec[split.length - i - 1] = 3;
            }
        }
        return vec;
    }

    public static void createTabelFromJson(AssetManager assetManager) {
        InputStream is = null;
        try {
            is = assetManager.open("map.json");
            String text = readTextFromSDcard(is);
            List<WordVec> wordVecs = JSON.parseArray(text, WordVec.class);
            LitePal.saveAll(wordVecs);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}

package com.example.chatbot.inference;

import android.content.res.AssetManager;
import android.os.Trace;
import android.util.Log;


import org.tensorflow.Operation;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.IntBuffer;
import java.util.Vector;

import util.WordVecUtil;

/**
 * Created by Ryan on 2018/6/25.
 */
public class MyInference {
    private static final String TAG = "MyInference";
    private static final String MODEL_FILE =
            "file:///android_asset/frozen_model_7_11_2.pb";
    private TensorFlowInferenceInterface inferenceInterface;
    private boolean logStats = false;
    private Vector<String> labels = new Vector<String>();
    private int[] intValues;
    private float[] floatValues;
    private int[] outputs;

    //输入名称
    private String[] INPUTNAMES = new String[]{"encoder_inputs",
            "encoder_inputs_length", "keep_prob_placeholder",
            "batch_size"};
    private String[] outputNames = new String[]{"decoder/decoder_predict_decode"};

    public static MyInference create(AssetManager assetManager) {
        MyInference c = new MyInference();

        // Read the label names into memory.
        // TODO(andrewharp): make this handle non-assets.
//        String actualFilename = LABEL_FILE.split("file:///android_asset/")[1];
//        Log.i(TAG, "Reading labels from: " + actualFilename);
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new InputStreamReader(assetManager.open(actualFilename)));
//            String line;
//            while ((line = br.readLine()) != null) {
//                c.labels.add(line);
//            }
//            br.close();
//        } catch (IOException e) {
//            throw new RuntimeException("Problem reading label file!", e);
//        }

        c.inferenceInterface = new TensorFlowInferenceInterface(assetManager, MODEL_FILE);

        // The shape of the output is [N, NUM_CLASSES], where N is the batch size.
//        final Operation operation = c.inferenceInterface.graphOperation(OUTPUT_NAME);
//        final int numClasses = (int) operation.output(0).shape().size(1);


        // Pre-allocate buffers.
        return c;
    }

    public void close() {
        if (inferenceInterface != null) {
            inferenceInterface.close();
        }
    }

    public String getRely(final String content) {
        Long start = System.currentTimeMillis();
        // Log this method so that it can be analyzed with systrace.
        Trace.beginSection("getRely");
        Trace.beginSection("preprocessContent");

        //TODO 预处理
        int[] x1 = WordVecUtil.word2Vec(content);
//        int[] x1 = new int[]{9, 35, 13, 43};
        int[] x2 = new int[]{x1.length};
        float[] x3 = new float[]{1.0f};
        int[] x4 = new int[]{1};
        Trace.endSection();
        Trace.beginSection("feed");
        inferenceInterface.feed(INPUTNAMES[0], x1, 1, x1.length);
        inferenceInterface.feed(INPUTNAMES[1], x2, 1);
        inferenceInterface.feed(INPUTNAMES[2], x3, 1);
        inferenceInterface.feed(INPUTNAMES[3], x4);
        Trace.endSection();

        // Run the inference call.
        Trace.beginSection("run");
        inferenceInterface.run(outputNames, logStats);
        Trace.endSection();

        // Copy the output Tensor back into the output array.
        Trace.beginSection("fetch");
        outputs = new int[]{20};
        IntBuffer intBuffer = IntBuffer.allocate(20);
        inferenceInterface.fetch(outputNames[0], intBuffer);
        outputs = intBuffer.array();
        Trace.endSection();
        Trace.endSection();
        Log.i(TAG, "" + outputs);
        String reply = WordVecUtil.vec2word(outputs);
        Long end = System.currentTimeMillis();
        Log.i("taoran", "耗时" + (end - start));
        return reply;

    }
}

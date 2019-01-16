package util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Ryan on 2018/7/5.
 */
public class SocketInterfence extends Interfence {
    Socket socket = null;
    String response = "服务端异常";
    String error = "服务端异常";

    @Override
    String getInterfence(String content) {
        if (socket == null) {
            return error;
        }
        try {
            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write(content);
            writer.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = br.readLine();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return response;
        }
    }

    @Override
    void prepare(Context context) {
        try {
            socket = new Socket("222.190.128.214", 12222);
            socket.setSoTimeout(10 * 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

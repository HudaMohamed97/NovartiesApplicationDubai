package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketInstance {
    private static Socket iSocket;
    private static final String URL = "http://cat-events.cat-sw.com";
    private SharedPreferences loginPreferences;


    public SocketInstance(Context context) {
        try {
            IO.Options opts = new IO.Options();
            loginPreferences = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
            String authToken = loginPreferences.getString("accessToken", "");
            opts.query = "Authorization" + authToken;
            iSocket = IO.socket("https://socket-io-chat.now.sh/");

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public static Socket getSocketInstance() {
        return iSocket;
    }
}

package com.wasltec.backpack.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasltec.backpack.R;
import com.wasltec.backpack.Session;
import com.wasltec.backpack.models.ChatMessage_model;
import com.wasltec.backpack.utils.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    ArrayList<ChatMessage_model> chatMessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ChatView chatView = (ChatView) findViewById(R.id.chat_view);


        AndroidNetworking.get(URLManager.getInstance().getCust_Chat(""+getIntent().getIntExtra("ticketId",1)))
                .addHeaders("Authorization", "bearer "+ Session.getInstance(ChatActivity.this).getToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Type listType = new TypeToken<List<ChatMessage>>() {
                        }.getType();
                        Gson gson= new Gson();


                        try {
                            chatMessages=gson.fromJson(
                                    response.getJSONArray("messages").toString(),
                                    listType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = chatMessages.size()-1; i >=0; i--) {

                            if (Session.getInstance(ChatActivity.this).getUser().getId()== chatMessages.get(i).getUserId())
                            {
                                chatView.addMessage(
                                        new ChatMessage(
                                                chatMessages.get(i).getMessage(),
                                                chatMessages.get(i).getcurrTime() ,
                                                ChatMessage.Type.SENT,
                                                chatMessages.get(i).getUser_name()
                                        )
                                );
                            }
                            else{
                                chatView.addMessage(
                                        new ChatMessage(
                                                chatMessages.get(i).getMessage(),
                                                chatMessages.get(i).getcurrTime() ,
                                                ChatMessage.Type.RECEIVED,
                                                chatMessages.get(i).getUser_name()
                                        )
                                );
                            }
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });







        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {


                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("message",chatMessage.getMessage());
                    jsonObject.put("typeId",2);
                    jsonObject.put("regardingId",getIntent().getIntExtra("ticketId",1));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.post(URLManager.getInstance().getSend_message(getIntent().getIntExtra("ChatId",1) ,0)
                )
                        .addHeaders("Authorization", "bearer "+ Session.getInstance(ChatActivity.this).getToken())
                        .addJSONObjectBody(jsonObject)
                        .build()
                        .getAsOkHttpResponse(new OkHttpResponseListener() {
                            @Override
                            public void onResponse(Response response) {
                                Toast.makeText(ChatActivity.this,"sent ",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(ChatActivity.this,"  "+anError.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                return true;
            }
        });

        chatView.setTypingListener(new ChatView.TypingListener() {
            @Override
            public void userStartedTyping() {

            }

            @Override
            public void userStoppedTyping() {

            }
        });

    }
}

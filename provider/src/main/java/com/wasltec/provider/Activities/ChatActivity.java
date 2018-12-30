package com.wasltec.provider.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.anychart.core.annotations.PlotController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.victor.loading.rotate.RotateLoading;
import com.wasltec.provider.R;
import com.wasltec.provider.Utils.SharedPreferencesManager;
import com.wasltec.provider.Utils.URLManger;
import com.wasltec.provider.model.Session;

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
    RotateLoading loader;

    ArrayList<com.wasltec.provider.model.ChatMessage> chatMessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ChatView chatView = (ChatView) findViewById(R.id.chat_view);
        getSupportActionBar().setTitle(getIntent().getStringExtra("chatname"));

            if (getIntent().getIntExtra("ChatId",1)>=0){
                loader = (RotateLoading) findViewById(R.id.rotateloading);
                loader.start();
                AndroidNetworking.get(URLManger.getInstance().getUser_Chat(getIntent().getIntExtra("ChatId", 1)))
                        .addHeaders("Authorization", "bearer " + SharedPreferencesManager.getInstance(ChatActivity.this).getToken())
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {


                                Type listType = new TypeToken<List<com.wasltec.provider.model.ChatMessage>>() {
                                }.getType();
                                Gson gson = new Gson();
                                chatMessages = gson.fromJson(response.toString(), listType);
                                for (int i = chatMessages.size() - 1; i >= 0; i--) {

                                    if (Session.getInstance().getId() == chatMessages.get(i).getUserId()) {
                                        chatView.addMessage(
                                                new ChatMessage(
                                                        chatMessages.get(i).getMessage(),
                                                        chatMessages.get(i).getcurrTime(),
                                                        ChatMessage.Type.SENT,
                                                        chatMessages.get(i).getUser_name()
                                                )
                                        );
                                    } else {
                                        chatView.addMessage(
                                                new ChatMessage(
                                                        chatMessages.get(i).getMessage(),
                                                        chatMessages.get(i).getcurrTime(),
                                                        ChatMessage.Type.RECEIVED,
                                                        chatMessages.get(i).getUser_name()
                                                )
                                        );
                                    }
                                }

                                if (loader.isStart())
                                    loader.stop();
                            }

                            @Override
                            public void onError(ANError anError) {
                                if (loader.isStart())
                                    loader.stop();
                            }
                        });
            }else {


            }




        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {



                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message",chatMessage.getMessage());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.post(URLManger.getInstance().getSend_message(getIntent()
                        .getIntExtra("ChatId",1),getIntent().getExtras().getInt("fromoverview",0))
                )
                        .addHeaders("Authorization", "bearer "+ SharedPreferencesManager.getInstance(ChatActivity.this).getToken())
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

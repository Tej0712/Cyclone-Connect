package com.example.myapplication.CycloneAI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class CycloneAI extends AppCompatActivity {

    // creating variables for our
    // widgets in xml file.
    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    // creating a variable for
    // our volley request queue.
    private RequestQueue mRequestQueue;

    // creating a variable for array list and adapter class.
    private ArrayList<MessageModal> messageModalArrayList;
    private MessageRVAdapter messageRVAdapter;

    // Brainshop.ai API credentials
    private static final String BRAIN_ID = "181740";
    private static final String API_KEY = "oTQkSxF1lGPadfSj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cyclone_ai);

        // on below line we are initializing all our views.
        chatsRV = findViewById(R.id.idRVChats);
        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);

        // below line is to initialize our request queue.
        mRequestQueue = Volley.newRequestQueue(CycloneAI.this);
        mRequestQueue.getCache().clear();

        // creating a new array list
        messageModalArrayList = new ArrayList<>();

        ImageButton backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // This will close the current activity
            }
        });


        // adding on click listener for send message button.
        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if the message entered
                // by user is empty or not.
                if (userMsgEdt.getText().toString().isEmpty()) {
                    // if the edit text is empty display a toast message.
                    Toast.makeText(CycloneAI.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // calling a method to send message
                // to our bot to get response.
                sendMessage(userMsgEdt.getText().toString());

                // below line we are setting text in our edit text as empty
                userMsgEdt.setText("");
            }
        });

        // on below line we are initializing our adapter class and passing our array list to it.
        messageRVAdapter = new MessageRVAdapter(messageModalArrayList, this);

        // below line we are creating a variable for our linear layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CycloneAI.this, RecyclerView.VERTICAL, false);

        // below line is to set layout
        // manager to our recycler view.
        chatsRV.setLayoutManager(linearLayoutManager);

        // below line we are setting
        // adapter to our recycler view.
        chatsRV.setAdapter(messageRVAdapter);
        sendMessage("Welcome");
    }

    @SuppressLint("NotifyDataSetChanged")
    private void sendMessage(String userMsg) {
        // below line is to pass message to our
        // array list which is entered by the user.
        messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();
        scrollToLatestMessage();

        try {
            // encode the user message
            String encodedUserMsg = URLEncoder.encode(userMsg, "UTF-8");

            // url for our brain
            // make sure to add mshape for uid.
            // make sure to add your url.
            String url = "http://api.brainshop.ai/get?bid=" + BRAIN_ID + "&key=" + API_KEY + "&uid=" + USER_KEY + "&msg=" + encodedUserMsg;

            // creating a variable for our request queue.
            RequestQueue queue = Volley.newRequestQueue(CycloneAI.this);

            // on below line we are making a json object request for a get request and passing our url .
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        // in on response method we are extracting data
                        // from json response and adding this response to our array list.
                        String botResponse = response.getString("cnt");
                        messageModalArrayList.add(new MessageModal(botResponse, BOT_KEY));

                        // notifying our adapter as data changed.
                        messageRVAdapter.notifyDataSetChanged();
                        scrollToLatestMessage();
                    } catch (JSONException e) {
                        e.printStackTrace();

                        // handling error response from bot.
                        messageModalArrayList.add(new MessageModal("No response", BOT_KEY));
                        messageRVAdapter.notifyDataSetChanged();
                        scrollToLatestMessage();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error handling.
                    messageModalArrayList.add(new MessageModal("Sorry, no response found", BOT_KEY));
                    messageRVAdapter.notifyDataSetChanged();
                    scrollToLatestMessage();

                    if (error instanceof NetworkError) {
                        Toast.makeText(CycloneAI.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CycloneAI.this, "No response from the bot..", Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                }
            });

            // at last adding json object
            // request to our queue.
            queue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scrollToLatestMessage() {
        chatsRV.post(new Runnable() {
            @Override
            public void run() {
                chatsRV.smoothScrollToPosition(messageModalArrayList.size() - 1);
            }
        });
    }
}
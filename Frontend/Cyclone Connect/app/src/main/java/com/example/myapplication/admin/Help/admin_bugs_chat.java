package com.example.myapplication.admin.Help;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.helper.WebSocketListener;
import com.example.myapplication.helper.WebSocketManager1;

import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;
import java.util.List;

public class admin_bugs_chat extends AppCompatActivity implements WebSocketListener {

    private EditText messageEditText;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_live_chat);

        messageEditText = findViewById(R.id.edit_gchat_message);
        recyclerView = findViewById(R.id.recycler_gchat);
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        findViewById(R.id.button_gchat_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditText.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    sendMessage(messageText);
                    messageEditText.setText("");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("AdminBugsChatActivity", "onResume called");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Long userId = preferences.getLong("userId", -1);

        Log.d("AdminBugsChatActivity", "Retrieved userId: " + userId);

        if (userId != -1) {
            String serverUrl = "ws://coms-309-046.class.las.iastate.edu:8080/chat/1/" + userId;
            Log.d("AdminBugsChatActivity", "Connecting to WebSocket: " + serverUrl);
            WebSocketManager1.getInstance().connectWebSocket(serverUrl);
            WebSocketManager1.getInstance().setWebSocketListener(this);
        } else {
            Log.d("AdminBugsChatActivity", "User ID not found");
            Toast.makeText(this, "User ID not found. Please login again.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("AdminBugsChatActivity", "onPause called");

        WebSocketManager1.getInstance().disconnectWebSocket();
    }

    private void sendMessage(String messageText) {
        WebSocketManager1.getInstance().sendMessage(messageText);
        Message message = new Message(messageText, true);
        messageList.add(message);
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        recyclerView.scrollToPosition(messageList.size() - 1);
    }

    @Override
    public void onWebSocketMessage(String message) {
        Log.d("AdminBugsChatActivity", "Received message: " + message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Message receivedMessage = new Message(message, false);
                messageList.add(receivedMessage);
                messageAdapter.notifyItemInserted(messageList.size() - 1);
                recyclerView.scrollToPosition(messageList.size() - 1);
            }
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        Log.d("AdminBugsChatActivity", "WebSocket connection closed. Code: " + code + ", Reason: " + reason);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(admin_bugs_chat.this, "Disconnected from the chat: " + reason, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        Log.d("AdminBugsChatActivity", "WebSocket connection opened");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(admin_bugs_chat.this, "Connected to the chat", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onWebSocketError(Exception ex) {
        Log.d("AdminBugsChatActivity", "WebSocket error: " + ex.getMessage());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(admin_bugs_chat.this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class Message {
        private final String text;
        private final boolean isSent;

        public Message(String text, boolean isSent) {
            this.text = text;
            this.isSent = isSent;
        }

        public String getText() {
            return text;
        }

        public boolean isSent() {
            return isSent;
        }
    }

    private static class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int VIEW_TYPE_SENT = 0;
        private static final int VIEW_TYPE_RECEIVED = 1;

        private final List<Message> messageList;

        public MessageAdapter(List<Message> messageList) {
            this.messageList = messageList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == VIEW_TYPE_SENT) {
                View view = inflater.inflate(R.layout.message_view, parent, false);
                return new SentMessageViewHolder(view);
            } else {
                View view = inflater.inflate(R.layout.message_receive_view, parent, false);
                return new ReceivedMessageViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Message message = messageList.get(position);
            if (holder instanceof SentMessageViewHolder) {
                ((SentMessageViewHolder) holder).bind(message);
            } else if (holder instanceof ReceivedMessageViewHolder) {
                ((ReceivedMessageViewHolder) holder).bind(message);
            }
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }

        @Override
        public int getItemViewType(int position) {
            Message message = messageList.get(position);
            if (message.isSent()) {
                return VIEW_TYPE_SENT;
            } else {
                return VIEW_TYPE_RECEIVED;
            }
        }

        private static class SentMessageViewHolder extends RecyclerView.ViewHolder {
            private final TextView messageTextView;

            public SentMessageViewHolder(View itemView) {
                super(itemView);
                messageTextView = itemView.findViewById(R.id.text_gchat_message_me);
            }

            public void bind(Message message) {
                messageTextView.setText(message.getText());
            }
        }

        private static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
            private final TextView messageTextView;

            public ReceivedMessageViewHolder(View itemView) {
                super(itemView);
                ImageView profileImageView = itemView.findViewById(R.id.image_gchat_profile_other);
                messageTextView = itemView.findViewById(R.id.text_gchat_message_other);
            }

            public void bind(Message message) {
                messageTextView.setText(message.getText());
            }
        }
    }
}
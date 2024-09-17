package com.example.myapplication.advisor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.helper.WebSocketListener;
import com.example.myapplication.helper.WebSocketManager2;

import org.java_websocket.handshake.ServerHandshake;

public class Advisor_Student_Chat extends AppCompatActivity implements WebSocketListener {

    private EditText messageInput;
    private TextView chatDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_page);

        // Initialize UI components
        Button sendButton = findViewById(R.id.send_button);
        messageInput = findViewById(R.id.message_input);
        chatDisplay = findViewById(R.id.chat_recycler_view); // Consider using a RecyclerView for chat messages

        // Set this class as the WebSocket listener
        WebSocketManager2.getInstance().setWebSocketListener(this);

        // Button click listener to send a message
        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();
            if (!message.isEmpty()) {
                // Send the message through the WebSocket
                WebSocketManager2.getInstance().sendMessage(message);
                messageInput.setText(""); // Clear input field after sending
            }
        });

        // Connect to the WebSocket upon activity start
        connectToWebSocketUsingUserId();
    }

    private void connectToWebSocketUsingUserId() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = preferences.getLong("userId", -1); // Assuming you've stored the userId somewhere after logging in

        if (userId != -1) {
            // Correct WebSocket URL to match your server's endpoint
            String serverUrl = "ws://coms-309-046.class.las.iastate.edu:8080/chat/2/" + userId;
            WebSocketManager2.getInstance().connectWebSocket(serverUrl);
        } else {
            Log.e("Advisor_Student_Chat", "User ID not found. Please login again.");
            // Handle user not found scenario, possibly redirect to login
        }
    }

    // WebSocket events handled by this class
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        Log.d("Advisor_Student_Chat", "WebSocket Opened");
        runOnUiThread(() -> chatDisplay.append("\nConnected\n"));
    }

    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> chatDisplay.append("\n" + message));
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        Log.d("Advisor_Student_Chat", "WebSocket Closed: " + reason);
        runOnUiThread(() -> chatDisplay.append("\nDisconnected: " + reason + "\n"));
    }

    @Override
    public void onWebSocketError(Exception ex) {
        Log.e("Advisor_Student_Chat", "WebSocket Error: " + ex.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebSocketManager2.getInstance().disconnectWebSocket();
        WebSocketManager2.getInstance().removeWebSocketListener();
    }
}

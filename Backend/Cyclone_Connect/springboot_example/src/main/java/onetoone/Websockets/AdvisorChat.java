package onetoone.Websockets;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@ServerEndpoint("/chat/2/{userId}")
@Component
public class AdvisorChat {

    private static Map<Session, Long> sessionUserIdMap = new Hashtable<>();
    private static Map<Long, Session> userIdSessionMap = new Hashtable<>();
    private final Logger logger = LoggerFactory.getLogger(AdvisorChat.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) throws IOException {
        logger.info("[onOpen] UserId: " + userId);

        if (userIdSessionMap.containsKey(userId)) {
            session.getBasicRemote().sendText("This user ID is already connected.");
            session.close();
        } else {
            sessionUserIdMap.put(session, userId);
            userIdSessionMap.put(userId, session);

            sendMessageToParticularUser(userId, "Welcome to the advisor chat server!");
            broadcast("User ID: " + userId + " has joined the chat.");
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        Long userId = sessionUserIdMap.get(session);
        logger.info("[onMessage] UserId: " + userId + ": " + message);

        if (message.startsWith("@")) {
            // Direct messaging logic here, modified to work with userId
        } else {
            broadcast("User ID: " + userId + ": " + message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        Long userId = sessionUserIdMap.remove(session);
        userIdSessionMap.remove(userId);
        logger.info("[onClose] UserId: " + userId);
        broadcast("User ID: " + userId + " disconnected.");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        Long userId = sessionUserIdMap.get(session);
        logger.error("[onError] UserId: " + userId + ": " + throwable.getMessage());
    }

    private void sendMessageToParticularUser(Long userId, String message) {
        try {
            Session session = userIdSessionMap.get(userId);
            if (session != null) {
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            logger.error("[DM Exception] " + e.getMessage());
        }
    }

    private void broadcast(String message) {
        sessionUserIdMap.forEach((session, userId) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("[Broadcast Exception] " + e.getMessage());
            }
        });
    }
}
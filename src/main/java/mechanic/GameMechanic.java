package mechanic;

import websocket.GameWebSocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qwerty on 24.05.16.
 */
public class GameMechanic {

    private final Map<String, Lobby> userToLobby = new ConcurrentHashMap<>();
    private final Map<String, GameWebSocket> userToSocket = new ConcurrentHashMap<>();
    Lobby vacantLobby;

    public void registerUser(String userName, GameWebSocket gameWebSocket) {
        userToSocket.put(userName, gameWebSocket);
        if (vacantLobby == null) {
            vacantLobby = new Lobby(new GameUser(userName));
        } else {
            vacantLobby.setSecondUser(new GameUser(userName));
            userToLobby.put(vacantLobby.getFirstUser().getUsername(), vacantLobby);
            userToLobby.put(vacantLobby.getSecondUser().getUsername(), vacantLobby);
            vacantLobby = null;
        }
    }

    public void onMessage(String username, String message) {
    }

}

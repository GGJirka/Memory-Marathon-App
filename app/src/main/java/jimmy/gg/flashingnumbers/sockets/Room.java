package jimmy.gg.flashingnumbers.sockets;

import java.util.ArrayList;

/**
 *
 * @author ggjimmy
 */
public class Room {
    private ArrayList<String> users;
    private String roomName;

    public Room(String roomName){
        this.roomName = roomName;
        users = new ArrayList<>();
    }

    public ArrayList<String> getUsers(){
        return this.users;
    }
    public void addUser(String user){
        this.users.add(user);
    }
    public String getRoomName(){
        return this.roomName;
    }
}

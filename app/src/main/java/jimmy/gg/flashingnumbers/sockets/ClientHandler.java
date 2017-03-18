package jimmy.gg.flashingnumbers.sockets;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


import jimmy.gg.flashingnumbers.sockets.Server;

/**
 *
 * @author root
 */

public class ClientHandler extends Thread {
    private BufferedReader br;
    private BufferedWriter bw;
    private Server server;
    private String receivedMessage = "";

    public ClientHandler(Server server, BufferedReader br, BufferedWriter bw) {
        this.server = server;
        this.br = br;
        this.bw = bw;
    }

    @Override
    public void run() {
        while (true) {
            try {
                receivedMessage = br.readLine();

                System.out.println("received:  " + receivedMessage);
                String manageMessage = "";
                try{
                    if(receivedMessage.split(" ").length>0){
                        manageMessage = receivedMessage.split(" ")[0];
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(manageMessage.equals("ROOMNAME")){
                    Room room = new Room(receivedMessage.split(" ")[2]);
                    room.addUser(receivedMessage.split(" ")[1]);
                    server.getRooms().add(room);

                }else if(manageMessage.equals("ROOMCONNECT")){
                    if(server.roomExist(receivedMessage.split(" ")[2])){
                        System.out.println(receivedMessage.split(" ")[2]+" exists");
                        server.findRoomByName(receivedMessage.split(" ")[2]).addUser(
                                receivedMessage.split(" ")[1]);
                        System.out.println(receivedMessage.split(" ")[1]+" added");
                        /*server.sendToAllClients(receivedMessage.split(" ")[1]+" "+
                                receivedMessage.split(" ")[2]);*/
                        server.sendToAllClients(receivedMessage+"\r\n");
                    }else{
                        server.sendToAllClients("NOEXISTROOM "+receivedMessage.split(" ")[1]+" "+receivedMessage.split(" ")[2]);
                    }
                }else if(manageMessage.equals("ROOMDISCONNECT")){
                    server.findRoomByName(receivedMessage.split(" ")[2])
                            .removeUser(receivedMessage.split(" ")[1]);
                    if(server.findRoomByName(receivedMessage.split(" ")[2])
                            .getUsers().isEmpty()){
                        server.getRooms().remove(server.findRoomByName(receivedMessage.split(" ")[2]));
                    }
                    server.sendToAllClients(receivedMessage);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void send() throws IOException{
        bw.write(receivedMessage);
        bw.flush();
    }

    public String getMessage() {
        return receivedMessage;
    }
    public void disconnect(){
        server.getClients().remove(this);
    }
}

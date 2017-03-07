package jimmy.gg.flashingnumbers.sockets;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by ggjimmy on 3/7/17.
 */

public class Client extends AsyncTask<Void, Void, Void>{
    private Socket clientSocket;
    private BufferedWriter bw;
    private BufferedReader br;
    private String message;

    public Client(){
        try {
            clientSocket = new Socket("192.168.1.100",4758);
            bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bw.write("TEST"+"\r\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... params){
        try {
            message = br.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getMessage(){
        return this.message;
    }
}

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

public class Client extends AsyncTask<Object, Object, String> {
    private Socket clientSocket;
    private BufferedWriter bw;
    private BufferedReader br;
    private String message = "";
    private TextView view;

    public Client(TextView view){
        this.view = view;
    }

    @Override
    protected String doInBackground(Object... params){
                clientSocket = null;
                try {
                    clientSocket = new Socket("192.168.1.101", 4758);
                    message = "";
                    bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    bw.write("TEST"+"\r\n");
                    bw.flush();
                } catch (IOException e) {
                    message = e.getMessage();
                }


        return message;
    }

    @Override
    protected void onPostExecute(String s) {
       // readingThread();
        super.onPostExecute(s);
    }

    public void readingThread(){
        if(br != null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            view.setText("waiting");
                            String message = br.readLine();
                            view.setText(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }else{
            readingThread();
        }
    }

    public String getMessage(){
        return this.message;
    }
}

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;


public class Client {

    private String ip; // create a ip
    private int port;  // create port
    ObjectOutputStream out;
    ClientThread theClient = new ClientThread();
    private Consumer<Serializable> callback1;

    Client(String ip, int port, Consumer<Serializable> callback1) {
        this.callback1 = callback1;
        this.ip = ip; // get the ip from the GUI
        this.port = port; // get the port from the GUI
    }

    public void getConnect(){
        theClient.start();
    }

    public String getIp(){
        return this.ip;
    }

    public int getPort(){
        return this.port;
    }

    public class ClientThread extends Thread {
        private Socket socketClient;

        private ObjectOutputStream out;
        private ObjectInputStream in;

        public void run() {

            try {
                socketClient = new Socket(getIp(), getPort());

                out = new ObjectOutputStream(socketClient.getOutputStream());
                in = new ObjectInputStream(socketClient.getInputStream());

                this.socketClient = socketClient;
                this.out = out;
                socketClient.setTcpNoDelay(true);

            } catch (Exception e) {
                callback1.accept("Connection Shutdown");
            }

            while (true) {
                try {
                    String message = in.readObject().toString(); //Message recieved from server
                        callback1.accept(message);
                } catch (Exception e) {
                }
            } // end of the while loop

        }// end of run
    } // end of Client thread

    public void send(Serializable data) {

        try {
            theClient.out.writeObject(data); //send the output data
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}


import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.io.Serializable;

public class Server {
    private GameInfo game;

    int playerCount = 1; // client or player count
    ArrayList<ClientThread> clientCount = new ArrayList<>(); // An array list to store the clients
    ServerThread mainServer = new ServerThread(); // create a server thread object
    private int port; // create a private port
    ObjectOutputStream out;

    private Consumer<Serializable> callback;

    Server(int port,Consumer<Serializable> call){
        this.port = port; // this is the port number from the GUI
        this.game = new GameInfo();
        this.callback = call; // this might be on the game info
    }

    public void getServerOn (){
         mainServer.start();
    }

    public void turnOffServer() throws Exception{
        for(ClientThread player: clientCount){ // goes through array list of clients and close them
            player.socketConnect.close();
        }
    }
    public int getPlayer(){
        return playerCount;
    }

    public int getPort(){
        return port;
    }

    public Boolean have2Players(){ // this function will help start the game
        int size = clientCount.size();
        if (size == 2) {
            return true;
        }
        else {
        return false;
        }
    }

    // create a Server Thread and a Client Thread
    public class ServerThread extends Thread{
        public void run(){
            try {

                ServerSocket mySocket = new ServerSocket(getPort());
                System.out.println("Server is waiting for a client! the Port is " + mySocket.getLocalPort());
                callback.accept("Server is waiting for a client! the Port is " + mySocket.getLocalPort());

                while (true){ // the purpose of this loop is to keep listening to the client
                    // create a client thread which accepts/listening the designated socket and client/player count
                    ClientThread myClient = new ClientThread(mySocket.accept(), playerCount);
                    //
                    callback.accept("Player " + playerCount + " has connected to the server");
                    clientCount.add(myClient); // add it on the array list
                    playerCount++; // increment for the next one to join
                    myClient.start(); // start the client thread

                }
            }
            catch (Exception e){
                callback.accept("Server socket failed");
                System.out.println("Error wrong socket");
            }
        }
    }


    public class ClientThread extends Thread{
        Socket socketConnect;
        int count;
        ObjectInputStream inPut;
        ObjectOutputStream outPut;
        // constructor
        public ClientThread(Socket accept, int playerCount) {
            this.socketConnect = accept;
            this.count = playerCount;
        }

        // This is used to send string to ALL clients from server
        public void updateClientServer(String string){
            // Loop/iterate through the array list
            for (int i = 0; i < clientCount.size(); i++){
                ClientThread c = clientCount.get(i);
                try {
                    c.outPut.writeObject(string);
                }
                catch (Exception e) {}
            }
        }

        // This is used to send string to N client from server
        public void updateNthClientServer(int n, String string){
            if (n < 0 || n > clientCount.size()) // Make sure n client is within bounds
                {System.out.println("Incorrect amount of players");return;}
                ClientThread c = clientCount.get(n);
                try {
                    c.outPut.writeObject(string);
                }
                catch (Exception e) {}
            }

        // need like a start game thing
        public void run(){
            try {
                inPut = new ObjectInputStream(socketConnect.getInputStream());
                outPut = new ObjectOutputStream(socketConnect.getOutputStream());
                socketConnect.setTcpNoDelay(true);
            }
            catch (Exception e) {
                System.out.println("No open streams");
            }
            updateClientServer("New Player joined the server: Player " + count);

            while(true) {
                try { // this will listen to what is the client thread is sending
                    String data = inPut.readObject().toString();

                    callback.accept("Player " + count + " picked : " + data);

                    if(count == 1)
                        Server.this.game.p1Plays = data; //Set Player One's Pick
                    else if(count == 2)
                        Server.this.game.p2Plays = data; //Set Player Two's Pick
                    if(Server.this.game.bothPlayed()) {
                        String result_WhoWon = Server.this.game.whoWon(Server.this.game.p1Plays, Server.this.game.p2Plays); // (Makes things more readable)

                        Server.this.game.evaluateRound(result_WhoWon); //Update points and round.
                        callback.accept(result_WhoWon); //Tell Sever who won.
                        callback.accept(Server.this.game.pointString());
                        updateClientServer(   "Round " + Server.this.game.roundNumber + ": Player 1 Played: " + Server.this.game.p1Plays + " -- Player2 Played: " + Server.this.game.p2Plays);
                        updateClientServer(result_WhoWon); //Clients who won.
                        updateClientServer("Score#" + Server.this.game.roundNumber + "#"
                                + Server.this.game.p1Points + "#" + Server.this.game.p2Points); //Score/Round/p1Points/p2Points (Format)
                        //Figure out how a way to send clientGUI points, so it can update them.
                        if(Server.this.game.gameOver()){
                            callback.accept("GAME OVER!");
                            updateClientServer("GAME OVER!");
                            updateClientServer(result_WhoWon + " Great Job!");
                            updateClientServer("#GAMEOVER"); //UPDATE GUI's Using A Token
                            Server.this.game = new GameInfo(); //Reset Point System
                        }

                        Server.this.game.p1Plays = ""; Server.this.game.p2Plays = ""; //Finished Eval! Reset Player Pick's
                    }
                }
                catch(Exception e) { // when the client closes the program
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                    updateClientServer("Player #"+count+" has left the server!");
                    clientCount.remove(this);
                    break;
                }
            }
        } // end of run function
    } // end of Client Thread

}

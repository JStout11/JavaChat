import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ServerClientReader implements Runnable{

    private final BufferedReader in;
    private final ServerMain server;
    private final Socket clientSocket;

    ServerClientReader(ServerMain server, Socket clientSocket, BufferedReader in) {
        this.in = in;
        this.server = server;
        this.clientSocket = clientSocket;
    }


    public void run() {
        try {
            reader();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

        public void reader() throws IOException, SocketException{
            String inputLine;
                try {
                    while (true) {
                        if ((inputLine = in.readLine()) != null) {
                            System.out.println(inputLine);
                            server.addToMessageList(inputLine);
                        }
                    }
                } catch (SocketException e) {
                    System.out.println("Client disconnected " + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (!clientSocket.isClosed())
                    clientSocket.close();
                }
            }




}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientHandler implements Runnable {

    private final Socket client;
    private final ServerMain parent;

    ServerClientHandler(ServerMain parent, Socket client) {
        this.parent = parent;
        this.client = client;
    }

    public void run() {

        try {
            PrintWriter out =
               new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));

            Thread outThread = new Thread(new ServerClientSender(parent, client, out));
            Thread inThread = new Thread(new ServerClientReader(parent,client, in));

            outThread.start();
            inThread.start();


            inThread.join();
            outThread.interrupt();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }



    }

}

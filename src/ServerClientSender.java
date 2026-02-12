import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientSender implements Runnable{

    private int lastMessageIndex = 0;
    private final ServerMain server;
    private final PrintWriter out;
    private final Socket clientSocket;

    ServerClientSender(ServerMain server, Socket clientSocket, PrintWriter out) {

       this.server = server;
       this.out = out;
       this.clientSocket = clientSocket;

    }

    public void run() {
        int tempIndex;
        while (true) {
                tempIndex = lastMessageIndex;
                lastMessageIndex = server.getCurrentMessageIndex();
                server.sendMessage(tempIndex, out);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }


}

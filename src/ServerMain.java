import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {

    static int port = 5555;
    static int currentMessageIndex = 0;
    static ArrayList<String> messageList = new ArrayList<>();
    boolean messageListLock = false;
    boolean messageIndexLock = false;


    public static void main(String[] args) throws IOException {
        ServerMain server = new ServerMain();

        server.start(server);
    }

    public void start(ServerMain server) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket newClient = serverSocket.accept();
            new Thread(new ServerClientHandler(server, newClient)).start();
        }
    }


    public synchronized void lockCurrentMessageIndex() {
        messageIndexLock = true;
    }

    public synchronized void unlockCurrentMessageIndex() {
        messageIndexLock = false;
    }

    public int getCurrentMessageIndex() {
        return currentMessageIndex;
    }

    public synchronized void addToMessageList(String message) {
        //temp spin
        lockMessageList();
        lockCurrentMessageIndex();
        messageList.add(currentMessageIndex, message);
        currentMessageIndex+= 1;
        unlockMessageList();
        unlockCurrentMessageIndex();
    }

    public synchronized void lockMessageList() {
        messageListLock = true;
    }

    public synchronized void unlockMessageList() {
        messageListLock = false;
    }

    public void sendMessage(int lastIndex, PrintWriter out) {
        //lockMessageList();
        //lockCurrentMessageIndex();
        for (int i = lastIndex; i < currentMessageIndex; i++) {
            out.println(messageList.get(i));
        }
        //unlockMessageList();
        //unlockCurrentMessageIndex();
    }


}
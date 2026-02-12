import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Main implements Runnable{

    //uses socket

    static String userName;
    static int port;
    static String hostname;
    static String DeleteLastFromTerminal = "\033[1A\033[2K";

    static PrintWriter out;
    static BufferedReader in;
    static BufferedReader stdin;
    static int runType = 0;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your username");
        System.out.print(">");
        userName = scanner.nextLine();
        //System.out.print(DeleteLastFromTerminal);
        System.out.println("Welcome " + userName);
        System.out.println("Please enter the hostname");
        System.out.print(">");
        hostname = scanner.nextLine();
        System.out.println("Please enter the port to connect to");
        //Unsafe
        port = scanner.nextInt();
        System.out.println("Attempting connection....");
        Socket chatSocket = new Socket(hostname, port);
        out = new PrintWriter(chatSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
        stdin = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Connected!");
        Thread input = new Thread(new Main());
        input.start();
        while (runType != 1) {}
        Thread output = new Thread(new Main());
        output.start();


    }

    public void run() {
        if (runType == 0) {
            try {
                inputRun();
                return;
            } catch (IOException e) {
                System.out.println("Connection Terminated, printing stack trace (This is okay!)");
                e.printStackTrace();
                System.exit(0);
            }
        }
        if (runType == 1) {
            try {
                outputRun();
            } catch (IOException e) {
                System.out.println("Connection Terminated, printing stack trace (This is okay!)");
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public String requestInput() throws IOException {
        String input = stdin.readLine();
        System.out.println(DeleteLastFromTerminal);
        return input;
    }

    public void inputRun() throws IOException{
        runType = 1;
        String userInput;
        while ((userInput = requestInput()) != null) {
            out.println(userName + ": " + userInput);
        }
    }

    public void outputRun() throws IOException {
        String nextLine;
        while (true) {
            if ((nextLine = in.readLine()) != null) {
                System.out.println(nextLine);
            }
        }
    }


}

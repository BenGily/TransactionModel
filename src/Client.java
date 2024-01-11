import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Timer timer;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg);
        //String resp = in.readLine();
        //System.out.println("Server response: " + resp);
    }

    public void startPeriodicUpdates(GUI GUI) {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    //String journalEntries = transactionModel.getVirtualJournalEntries();
                    String journalEntries = GUI.getLatest();
                    sendMessage(journalEntries);
                } catch (IOException e) {
                    System.out.println("Error sending periodic update: " + e.getMessage());
                    this.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 2000);
    }

    public void run(GUI GUI) throws IOException {
        //while (true) {
            String journalEntries = GUI.getLatest();
            sendMessage(journalEntries);
        //}
    }


    /**
    public void stopConnection() throws IOException {
        if (timer != null) {
            timer.cancel();
        }
        in.close();
        out.close();
        clientSocket.close();
    }*/

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        GUI GUI = new GUI();

        //Me
        client.startConnection("192.168.168.7", 1234);

        //Michael
        //client.startConnection("192.168.168.24", 5000);


        //client.startPeriodicUpdates(transactionModel);
        client.run(GUI);
    }
}




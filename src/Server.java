import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port: " + port);
    }

    public void start() {
        try {
            System.out.println("Waiting for clients...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            //outputWriter.println("Hello from the server!");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port or listening for a connection");
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(1234); // Port number 1234

        server.start();
    }
}
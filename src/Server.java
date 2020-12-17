import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;


public class Server {
    public static final int PORT = 4444;

    public static void main(String[] args) throws IOException {
        new Server().runServer();
    }

    public void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server up & ready for connections...");
        while (true) {
            Socket socket = serverSocket.accept();
            new ServerThread(socket).start();
        }
    }
}

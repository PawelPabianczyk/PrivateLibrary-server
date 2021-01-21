import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerThreadTest {

    private ServerThread serverThread;

    @BeforeEach
    void setUp() throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);
        Socket socket = new Socket("127.0.0.1", 4444);;
        serverThread = new ServerThread(socket);
    }

    @Test
    void getUser_WhenUserExistsInDB_ReturnUser() {
        String username = "pawpab";

        User user = serverThread.getUser(username);

        Assertions.assertEquals(username,user.getUsername());
    }

    @Test
    void getUser_WhenUserNotExistsInDB_ReturnNull() {
        String username = "user5654";

        User user = serverThread.getUser(username);

        Assertions.assertNull(user);
    }

    @Test
    void getFavouriteGenre_WhenUsernameHasFavGenre_ReturnGenre() {
        String username = "pawpab";
        String genreTest = "Horror";

        String genreDB = serverThread.getFavouriteGenre(username);

        Assertions.assertEquals(genreTest,genreDB);
    }

    @Test
    void getFavouriteGenre_WhenUsernameHasNoFavGenre_ReturnNoGenre() {
        String username = "pudzian";
        String genreTest = "no genre";

        String genreDB = serverThread.getFavouriteGenre(username);

        Assertions.assertEquals(genreTest,genreDB);
    }
}
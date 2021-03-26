package server;

import server.database.Database;
import server.database.DatabaseFile;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private final Database<String> database = new DatabaseFile();
    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public Server(int port, String address, int poolSize) throws IOException {
        serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address));
        pool = Executors.newFixedThreadPool(poolSize);
    }

    public void run() {
        do {
            try {
                pool.execute(new RequestHandler(serverSocket.accept(), database, serverSocket));
            } catch (IOException | RuntimeException ex) {
                pool.shutdown();
                break;
            }
        } while (!serverSocket.isClosed());
    }
}

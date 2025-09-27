package org.oosd.teamorange_finalsubmission;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

// External AI client
public class ExternalClient {
    // Connection config
    private static final String HOST = "localhost";
    private static final int PORT = 3000;

    // JSON + executor
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ExecutorService EXEC = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r, "ExternalClient");
        t.setDaemon(true);
        return t;
    });

    // Blocking request
    private OpMove requestMoveBlocking(PureGame game) throws IOException {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String json = MAPPER.writeValueAsString(game);
            out.println(json);
            String response = in.readLine();
            if (response == null || response.isEmpty()) return null;
            return MAPPER.readValue(response, OpMove.class);
        }
    }

    // Async request
    public CompletableFuture<OpMove> requestMoveAsync(PureGame game) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return requestMoveBlocking(game);
            } catch (IOException e) {
                return null;
            }
        }, EXEC);
    }
}

package org.oosd.teamorange_finalsubmission;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

public class ExternalClient {
    private static final String HOST = "localhost";
    private static final int PORT = 3000;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    // small daemon pool so calls don’t block the FX thread
    private static final ExecutorService EXEC = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r, "ExternalClient");
        t.setDaemon(true);
        return t;
    });

    /**
     * Blocking call (used from async wrapper)
     */
    private OpMove requestMoveBlocking(PureGame game) throws IOException {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String json = MAPPER.writeValueAsString(game);
            out.println(json); // server expects a line and then replies

            String response = in.readLine();
            if (response == null || response.isEmpty()) return null;

            return MAPPER.readValue(response, OpMove.class);
        }
    }

    /**
     * Non-blocking request; completes with null on any error
     */
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

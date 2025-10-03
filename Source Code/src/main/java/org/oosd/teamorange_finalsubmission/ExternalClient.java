/// ExternalClient.java
// Lightweight TCP client used when a player type is EXTERNAL.
// - Serializes a PureGame snapshot to JSON (Jackson).
// - Sends it as a single newline-terminated line to localhost:3000.
// - Reads one JSON line back and deserializes it to OpMove.
// - Exposes the result via CompletableFuture for the game loop to consume.
// - The server closes the connection after each reply;
// - This client opens a new socket per query and includes simple error/timeout handling.


package org.oosd.teamorange_finalsubmission;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public final class ExternalClient {
    private static final String HOST = "127.0.0.1"; // force IPv4
    private static final int PORT = 3000;
    private static final int CONNECT_TIMEOUT_MS = 1500;
    private static final int READ_TIMEOUT_MS = 3000;

    private final ObjectMapper mapper = new ObjectMapper();

    public CompletableFuture<OpMove> requestMoveAsync(PureGame game) {
        return CompletableFuture.supplyAsync(() -> {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(HOST, PORT), CONNECT_TIMEOUT_MS);
                socket.setSoTimeout(READ_TIMEOUT_MS);

                try (BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {

                    String json = mapper.writeValueAsString(game);
                    out.write(json);
                    out.write("\n");             // IMPORTANT: newline terminator
                    out.flush();                  // IMPORTANT: flush

                    String line = in.readLine();  // server replies one line then closes
                    if (line == null || line.isBlank()) {
                        throw new IOException("Empty reply from server");
                    }
                    return mapper.readValue(line, OpMove.class);
                }
            } catch (Exception ex) {
                System.err.println("[ExternalClient] " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                return null; // TetrisGame shows the red "External server unavailable" badge
            }
        });
    }
}

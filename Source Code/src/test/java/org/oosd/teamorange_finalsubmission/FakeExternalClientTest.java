package org.oosd.teamorange_finalsubmission;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstrates a Fake test double for an external move provider.
 * It behaves like ExternalClient.requestMoveAsync but without any I/O.
 */
class FakeExternalClientTest {

    // Test-only fake
    static class FakeExternalClient {
        private final OpMove move;

        FakeExternalClient(int x, int r) {
            this.move = new OpMove(x, r);
        }

        CompletableFuture<OpMove> requestMoveAsync(PureGame g) {
            return CompletableFuture.completedFuture(move);
        }
    }

    @Test
    void returnsPredeterminedMove() {
        FakeExternalClient fake = new FakeExternalClient(3, 1);
        OpMove m = fake.requestMoveAsync(new PureGame()).join();
        assertEquals(3, m.opX());
        assertEquals(1, m.opRotate());
    }
}

package org.oosd.teamorange_finalsubmission;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class TetrisSoundsMockitoTest {

    @Test
    void choosesCorrectSounds() {
        MediaManager media = Mockito.mock(MediaManager.class);

        TetrisSounds.playForLines(media, 0);
        TetrisSounds.playForLines(media, 2);
        TetrisSounds.playForLines(media, 4);

        // order not important here; verify expected calls occurred
        verify(media, atLeastOnce()).playSound("drop");
        verify(media, atLeastOnce()).playSound("line_clear");
        verify(media, atLeastOnce()).playSound("tetris_clear");
    }
}

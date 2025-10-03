package org.oosd.teamorange_finalsubmission;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RulesRotateIndexTest {

    @ParameterizedTest
    @CsvSource({
            // px,py,r,expectedIndex
            "0,1,0,4", "0,1,1,13", "0,1,2,11", "0,1,3,2",
            "3,2,0,11", "3,2,1,2", "3,2,2,4", "3,2,3,13"
    })
    void maps4x4IndicesCorrectly(int px, int py, int r, int expected) {
        assertEquals(expected, Rules.rotateIndex(px, py, r));
    }
}

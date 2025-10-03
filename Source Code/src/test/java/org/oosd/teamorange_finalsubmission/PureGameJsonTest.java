package org.oosd.teamorange_finalsubmission;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PureGameJsonTest {

    @Test
    void serializesExpectedShape() throws Exception {
        PureGame g = new PureGame();
        g.setWidth(10);
        g.setHeight(20);
        g.setCells(new int[][]{{0, 1}, {1, 0}});
        g.setCurrentShape(new int[][]{{1, 1}, {0, 1}});
        g.setNextShape(new int[][]{{1, 1, 1, 1}});

        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(om.writeValueAsString(g));

        assertEquals(10, root.get("width").asInt());
        assertEquals(20, root.get("height").asInt());
        assertTrue(root.has("cells"));
        assertTrue(root.has("currentShape"));
        assertTrue(root.has("nextShape"));
    }
}

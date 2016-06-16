package vanilla.java.util;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Peter on 16/06/2016.
 */
public class ArrayMapTest {
    final Map<String, String> empty = new ArrayMap<>(Collections.emptyMap(), new String[0]);
    final Map<String, Integer> oneKey = new LinkedHashMap<>();
    final Map<String, String> one = new ArrayMap<>(oneKey, new String[]{"world"});

    {
        oneKey.put("hello", 0);
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(0, empty.size());
        assertEquals(1, one.size());
    }

    @Test
    public void testContainsValue() throws Exception {
        assertFalse(empty.containsValue("world"));
        assertTrue(one.containsValue("world"));
    }

    @Test
    public void testContainsKey() throws Exception {
        assertFalse(empty.containsKey("hello"));
        assertTrue(one.containsKey("hello"));
    }

    @Test
    public void testGet() throws Exception {
        assertNull(empty.get("hello"));
        assertEquals("world", one.get("hello"));
    }

    @Test
    public void testPut() throws Exception {
        assertEquals("world", one.put("hello", "there"));
        assertEquals("there", one.get("hello"));
    }

    @Test
    public void testKeySet() throws Exception {
        assertEquals("[]", empty.keySet().toString());
        assertEquals("[hello]", one.keySet().toString());
    }

    @Test
    public void testValues() throws Exception {
        assertEquals("[]", empty.values().toString());
        assertEquals("[world]", one.values().toString());
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(Collections.emptyMap(), empty);
        Map<String, String> map = new HashMap<>();
        map.put("hello", "world");
        assertEquals(map, one);
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(0, empty.hashCode());
        assertEquals("world".hashCode(), one.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("{}", empty.toString());
        assertEquals("{hello=world}", one.toString());
    }
}
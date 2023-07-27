package persistence;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

// Represents test cases for the JsonFileHandler class
class JsonFileHandlerTest {

    private static final String TEST_FILENAME = "myFile.json";
    private JSONArray jsonArray;

    @BeforeEach
    void setup() {
        jsonArray = new JSONArray();
        jsonArray.put("test1");
        jsonArray.put("test2");
    }

    // ensures clean test each time by deleting existing file
    @AfterEach
    void teardown() throws IOException {
        Files.deleteIfExists(Path.of("./data/" + TEST_FILENAME));
    }

    @Test
    void testReadInvalidFile() {
        assertThrows(IOException.class, () -> JsonFileHandler.readJsonFile("invalidFile.json"));
    }

    @Test
    void testWriteAndReadValidFile() throws IOException {
        // Write to file
        JsonFileHandler.writeJsonFile(TEST_FILENAME, jsonArray);

        // Read from file
        JSONArray readArray = JsonFileHandler.readJsonFile(TEST_FILENAME);

        // Check that the read array matches the written one
        assertEquals(jsonArray.length(), readArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            assertEquals(jsonArray.get(i), readArray.get(i));
        }
    }

    @Test
    void testWriteInvalidFile() {
        assertThrows(IOException.class, () -> JsonFileHandler.writeJsonFile("/invalid/path/myFile.json", jsonArray));
    }
}

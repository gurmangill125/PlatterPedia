package persistence;

import org.json.JSONArray;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// A utility class that provides methods to handle reading from and writing to JSON files.
// Code inspired by JsonSerializationDemo, specifically JsonReader and JsonWriter classes
public class JsonFileHandler {


    // REQUIRES: filename - a valid filename of a JSON file located in the "./data/" directory
    // EFFECTS: reads the content from the given file and converts it into a JSONArray
    public static JSONArray readJsonFile(String filename) throws IOException {
        String content = Files.readString(Path.of("./data/" + filename));
        return new JSONArray(content);
    }

    // REQUIRES: filename - a valid filename where the JSON content will be written
    //           jsonArray - a valid JSONArray containing data to be written to the file
    // MODIFIES: the file corresponding to the provided filename
    // EFFECTS: writes the jsonArray into a file located in the "./data/" directory
    public static void writeJsonFile(String filename, JSONArray jsonArray) throws IOException {
        Files.writeString(Path.of("./data/" + filename), jsonArray.toString());
    }
}

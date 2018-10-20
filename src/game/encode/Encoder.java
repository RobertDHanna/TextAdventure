package game.encode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class handles all encoding(and will have methods for decoding) to and from JSON
 */
public class Encoder {
    public void encodeToFile(Object obj, String filename) throws IOException {
        try (Writer writer = new FileWriter(filename)){
            writer.write(encode(obj));
        } catch (IOException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * This encodes java objects into JSON
     *
     * @param obj      The object to encode
     */
    public String encode(Object obj) {
        System.out.println(obj.getClass());
        Gson gson = new GsonBuilder().create();
        return gson.toJson(obj);
    }

    /**
     * This method decodes a JSON file into a Java object
     *
     * @param myClass The class we are returning
     * @param filename  The file we are reading from
     */
    public <T> T decodeFromFile(Class<T> myClass,String filename) throws IOException {
        try {
            String json = new String(Files.readAllBytes(Paths.get(filename)));
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(json, myClass);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public <T> T decode(Class<T> myClass, String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, myClass);
    }
}


package game.encode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * This class handles all encoding(and will have methods for decoding) to and from JSON
 */
public class Encoder {

    public Encoder() {
    }

    /**
     * This encodes java objects into JSON
     *
     * @param obj      The object to encode
     * @param filename  The file we are writing to
     */
    public void encode(Object obj, String filename) throws Exception {
        System.out.println(obj.getClass());
        try {
            Writer writer = new FileWriter(filename);
            Gson gson = new GsonBuilder().create();
            gson.toJson(obj, writer);
            writer.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * This method decodes a JSON file into a Java object
     *
     * @param myClass The class we are returning
     * @param filename  The file we are reading from
     */
    public Object decode(Class myClass,String filename) throws Exception {
        Object obj = null;
        try {
            Reader reader = new FileReader(filename);
            Gson gson = new GsonBuilder().create();
            obj = gson.fromJson(reader, myClass);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return obj;
    }
}


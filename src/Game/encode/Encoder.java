package Game.encode;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

/**
 * This class handles all encoding(and will have methods for decoding) to and from JSON
 */
public class Encoder {

    private Gson gson = new Gson();

    public Encoder() {
    }

    /**
     * This encodes java objects into JSON
     *
     * @param obj      The object to encode
     * @param respBody The output stream
     */
    public void encode(Object obj, OutputStream respBody) throws IOException {
        System.out.println(obj.getClass());
        OutputStreamWriter writer = new OutputStreamWriter(respBody);
        writer.write(gson.toJson(obj));
        writer.flush();


    }

    /*public Object decode(InputStream stream) {
        try {
            Reader reader = new InputStreamReader(stream);
            return gson.fromJson(reader, Object.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return
    }*/
}


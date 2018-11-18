package front.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Random;

/**
 * Created by jdwinters on 7/16/17.
 */
public class Helper_RandomString {

    private static final char[] symbols;

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }

    private final Random random = new Random();

    private final char[] buf;

    public Helper_RandomString(int length) {

        if (length < 1)
            throw new IllegalArgumentException("oh no!! length < 1: " + length);

        buf = new char[length];
    }

    public String nextString() {

        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];

        return new String(buf);
    }

    public static int     randomTwoDigits() {

        long n2 = 0;

        for (int i=0 ; i < 99999999; i++) {

            n2 = Math.round(Math.random() * (99 - 10) + 10);
        }

        return (int) n2;
    }


    public static String prettyFormatter(String jsonString) {

        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}

package vanilla.java.async;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by peter_2 on 27/11/2014.
 */
public class ThreadLocalMain {

    /*
        static final ThreadLocal<SimpleDateFormat> SDF = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss.SSSZ");
            }
        };
    */
    static final ThreadLocal<SimpleDateFormat> SDF = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss.SSSZ"));

    public static Date parseDate(String text) {
        try {
            return SDF.get().parse(text);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

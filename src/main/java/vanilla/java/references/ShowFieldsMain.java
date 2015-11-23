package vanilla.java.references;

import java.lang.reflect.Field;

/**
 * Created by peter.lawrey on 22/11/2015.
 */
public class ShowFieldsMain {
    public static void main(String[] args) {
        for (Class c = MyClass.class; c != null; c = c.getSuperclass())
            for (Field field : c.getDeclaredFields())
                System.out.println(field);
    }
}

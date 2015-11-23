package vanilla.java.references;

import java.lang.reflect.Field;

/**
 * Created by peter.lawrey on 22/11/2015.
 */
public class ShowFieldsMain {
    public static void main(String[] args) {
        for(Field field: MyClass.class.getDeclaredFields()) {
            System.out.println(field);
        }
    }
}

package infrastructure.ecs;

import infrastructure.collections.TypeArray;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class EntitySystem {

    public EntitySystem() {
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            // Look for any type array fields
            if (field.getType().isAssignableFrom(TypeArray.class)) {
                if (field.getGenericType() instanceof ParameterizedType genericType) {
                    Type type = genericType.getActualTypeArguments()[0];
                    try {
                        //set type array field to the specific type
                        field.set(this, field.getType().getDeclaredConstructors()[0].newInstance(type));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
    }
}

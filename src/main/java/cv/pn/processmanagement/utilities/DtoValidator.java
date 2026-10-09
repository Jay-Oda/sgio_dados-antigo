package cv.pn.processmanagement.utilities;

import java.lang.reflect.Method;

public final class DtoValidator {

    private DtoValidator() {
    }

    public static boolean isEmpty(Object dto) {
        if (dto == null) {
            return true;
        }
        for (Method method : dto.getClass().getMethods()) {
            if (!method.getName().startsWith("get") || method.getParameterCount() != 0
                    || method.getReturnType() == void.class || method.getReturnType().isPrimitive()
                    || method.getDeclaringClass() == Object.class) {
                continue;
            }
            try {
                Object value = method.invoke(dto);
                if (value != null) {
                    return false;
                }
            } catch (ReflectiveOperationException ignored) {
            }
        }
        return true;
    }
}

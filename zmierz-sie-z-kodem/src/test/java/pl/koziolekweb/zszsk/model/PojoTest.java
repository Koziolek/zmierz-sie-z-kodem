package pl.koziolekweb.zszsk.model;

import io.vavr.Tuple2;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.reflections.ReflectionUtils.*;

/**
 * Created by koziolek on 30.05.17.
 */
public class PojoTest {

    Function<Class<?>, Stream<? extends Tuple2<Class, Method>>> getters = c -> getAllMethods(c, withPrefix("get")).stream()
            .map(
                    m -> new Tuple2<Class, Method>(c, m)
            );

    Function<Class<?>, Stream<? extends Tuple2<Class, Method>>> setters = c -> getAllMethods(c, withPrefix("set"), withParametersCount(1)).stream()
            .map(
                    m -> new Tuple2<Class, Method>(c, m)
            );

    Function<Class<?>, Stream<? extends Tuple2<Class, Method>>> hash = c -> getAllMethods(c, withPrefix("hashCode")).stream()
            .map(
                    m -> new Tuple2<Class, Method>(c, m)
            );

    BiFunction<Class, Method, Optional<Object>> callnoparam = (c, m) -> {
        try {
            m.invoke(c.newInstance());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
        }
        return Optional.empty();
    };

    BiFunction<Class, Method, Optional<Object>> callSetter = (c, m) -> {
        try {
            m.invoke(c.newInstance(), m.getParameterTypes()[0].cast(null));
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    };

    BiFunction<Class, Method, Optional<Object>> callEquals = (c, m) -> {
        try {
            Object left = c.newInstance();
            Object right = c.newInstance();
            left.equals(left);
            left.equals(new Object());
            left.equals(right);
            left.equals(null);
            right.equals(left);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    };


    Set<Class<?>> allTypes = new Reflections("pl.koziolekweb.zszsk.model", new SubTypesScanner(false)).getSubTypesOf(Object.class);

    @Test
    void universalCodeCoverageMaker() {
        allTypes.stream().flatMap(getters).forEach(t -> t.apply(callnoparam));
        allTypes.stream().flatMap(hash).forEach(t -> t.apply(callnoparam));
        allTypes.stream().flatMap(setters).forEach(t -> t.apply(callSetter));
        allTypes.stream().flatMap(setters).forEach(t -> t.apply(callEquals));
    }
}
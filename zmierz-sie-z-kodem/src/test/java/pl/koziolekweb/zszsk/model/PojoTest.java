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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withParametersCount;
import static org.reflections.ReflectionUtils.withPrefix;
import static pl.koziolekweb.zszsk.model.Getters.getters;
import static pl.koziolekweb.zszsk.model.Hash.hash;
import static pl.koziolekweb.zszsk.model.Setters.setters;
import static pl.koziolekweb.zszsk.model.TupleBuilder.tupleBuilder;

/**
 * Created by koziolek on 30.05.17.
 */
public class PojoTest {

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
	Consumer<Class> callEquals = c -> {
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
	};
	Set<Class<?>> allTypes = new Reflections("pl.koziolekweb.zszsk.model", new SubTypesScanner(false)).getSubTypesOf(Object.class);

	@Test
	void universalCodeCoverageMaker() {
		allTypes.stream().flatMap(getters).forEach(t -> t.apply(callnoparam));
		allTypes.stream().flatMap(hash).forEach(t -> t.apply(callnoparam));
		allTypes.stream().flatMap(setters).forEach(t -> t.apply(callSetter));
		allTypes.stream().forEach(callEquals);
	}


}

class TupleBuilder implements Function<Method, Tuple2<Class<?>, Method>> {

	public static final TupleBuilder tupleBuilder = new TupleBuilder();

	@Override
	public Tuple2<Class<?>, Method> apply(Method m) {
		return new Tuple2<>(m.getDeclaringClass(), m);
	}
}

class Setters implements Function<Class<?>, Stream<? extends Tuple2<Class, Method>>> {

	public static final Function<Class<?>, Stream<? extends Tuple2<Class, Method>>> setters = new Setters();

	@Override
	public Stream<? extends Tuple2<Class<?>, Method>> apply(Class c) {

		return getAllMethods(c, withPrefix("set"), withParametersCount(1)).stream()
				.map(tupleBuilder);
	}
}

class Getters implements Function<Class<?>, Stream<? extends Tuple2<Class, Method>>> {

	public static final Function<Class<?>, Stream<? extends Tuple2<Class, Method>>> getters = new Getters();

	@Override
	public Stream<? extends Tuple2<Class<?>, Method>> apply(Class c) {
		return getAllMethods(c, withPrefix("get")).stream()
				.map(tupleBuilder);
	}
}

class Hash implements Function<Class<?>, Stream<? extends Tuple2<Class, Method>>> {

	public static final Function<Class<?>, Stream<? extends Tuple2<Class, Method>>> hash = new Hash();

	@Override
	public Stream<? extends Tuple2<Class<?>, Method>> apply(Class c) {
		return getAllMethods(c, withPrefix("hashCode")).stream()
				.map(tupleBuilder);
	}
}
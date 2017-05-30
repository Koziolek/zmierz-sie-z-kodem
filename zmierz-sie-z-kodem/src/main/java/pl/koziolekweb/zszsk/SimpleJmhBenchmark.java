package pl.koziolekweb.zszsk;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static pl.koziolekweb.zszsk.Params.LOOP;
import static pl.koziolekweb.zszsk.Params.TIME;

/**
 * Created by koziolek on 29.05.17.
 */
public class SimpleJmhBenchmark {

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(SimpleJmhBenchmark.class.getSimpleName())
				.build();

		new Runner(options).run();
	}

	@Benchmark
//	@Warmup(iterations = LOOP, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
//	@Measurement(iterations = LOOP, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public int benchmark() {
		return businessMethod();
	}

	private int businessMethod() {
		int[] arr = new int[100_000];
		fill(arr);
		arr = shakeIt(arr);
		Arrays.sort(arr);
		return arr[0];
	}

	private int[] shakeIt(int[] arr) {
		List<Integer> l = Arrays.stream(arr).boxed().collect(Collectors.toList());
		Collections.shuffle(l);
		return l.stream().mapToInt(Integer::intValue).toArray();
	}

	private void fill(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
	}
}

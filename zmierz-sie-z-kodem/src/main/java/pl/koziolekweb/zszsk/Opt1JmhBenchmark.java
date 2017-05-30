package pl.koziolekweb.zszsk;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static pl.koziolekweb.zszsk.Params.LOOP;
import static pl.koziolekweb.zszsk.Params.TIME;

/**
 * Created by koziolek on 29.05.17.
 */
public class Opt1JmhBenchmark {

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(Opt1JmhBenchmark.class.getSimpleName())
				.build();

		new Runner(options).run();
	}

	@Benchmark
	@Warmup(iterations = LOOP, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = LOOP, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
//	@BenchmarkMode(Mode.AverageTime)
	public void empty() {

	}

	@Benchmark
	@Warmup(iterations = LOOP, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = LOOP, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
//	@BenchmarkMode(Mode.AverageTime)
	public void benchmark() {
		businessMethod();
	}

	private void businessMethod() {
		int sum = 0;
		for (int i = 0; i <10000; i++)
			sum +=i;
	}
}

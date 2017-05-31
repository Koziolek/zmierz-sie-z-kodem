package pl.koziolekweb.zszsk.beanchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by koziolek on 29.05.17.
 */
@State(Scope.Benchmark)
public class Opt2JmhBenchmark {

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(Opt2JmhBenchmark.class.getSimpleName())
				.build();

		new Runner(options).run();
	}

	@Benchmark
	@Warmup(iterations = Params.LOOP, time = Params.TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = Params.LOOP, time = Params.TIME, timeUnit = TimeUnit.MILLISECONDS)
	public void empty() {

	}

	@Benchmark
	@Warmup(iterations = Params.LOOP, time = Params.TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = Params.LOOP, time = Params.TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Fork(Params.LOOP)
	public void benchmark1(Blackhole bh) {
		bh.consume(businessMethod1());

	}
	@Benchmark
	@Warmup(iterations = Params.LOOP, time = Params.TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = Params.LOOP, time = Params.TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Fork(Params.LOOP)
	public void benchmark2(Blackhole bh) {
		bh.consume(businessMethod2());
	}

	private Val v = new Val();

	private double businessMethod1() {
		return Math.log(42);
	}

	private double businessMethod2() {
		return Math.log(v.val);
	}
}

class Val {
	int val = 42;
}
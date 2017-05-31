package pl.koziolekweb.zszsk.beanchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by koziolek on 29.05.17.
 */
public class FailSort {

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(FailSort.class.getSimpleName())
				.build();

		new Runner(options).run();
	}

	@Benchmark
	@Warmup(iterations = Params.LOOP, time = Params.TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = Params.LOOP, time = Params.TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Fork(Params.LOOP)
	public int qs() {
		int[] ints = prepareData();
		return new QuickSort().sort(ints);
	}

	@Benchmark
	@Warmup(iterations = Params.LOOP, time = Params.TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = Params.LOOP, time = Params.TIME, timeUnit = TimeUnit.MILLISECONDS)
	@Fork(Params.LOOP)
	public int bs() {
		int[] ints = prepareData();
		return new BubbleSort().sort(ints);
	}

	private int[] prepareData() {
		int[] arr = new int[100_000];
		fill(arr);
		return arr;
	}

	private void fill(int[] arr) {
		for (int j = 0; j < arr.length; j++) {
			arr[j] = arr.length - j;
		}
	}
}

class QuickSort implements Sort {

	@Override
	public int sort(int[] i) {
		return partition(i, 0, i.length - 1);
	}

	private int partition(int arr[], int left, int right) {
		int i = left, j = right;
		int tmp;
		int pivot = arr[left];

		while (i <= j) {
			while (arr[i] < pivot)
				i++;
			while (arr[j] > pivot)
				j--;
			if (i <= j) {
				tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				i++;
				j--;
			}
		}
		return i;
	}
}

class BubbleSort implements Sort {
	@Override
	public int sort(int[] arr) {
		int n = arr.length;
		int temp = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {
				if (arr[j - 1] > arr[j]) {
					//swap elements
					temp = arr[j - 1];
					arr[j - 1] = arr[j];
					arr[j] = temp;
				}

			}
		}
		return temp;
	}

}

interface Sort {
	int sort(int[] i);
}
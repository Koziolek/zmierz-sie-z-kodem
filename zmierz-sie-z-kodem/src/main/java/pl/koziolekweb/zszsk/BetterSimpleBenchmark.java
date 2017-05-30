package pl.koziolekweb.zszsk;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by BKuczynski on 2017-05-29.
 */
public class BetterSimpleBenchmark {

	public static void main(String[] args) {
		BetterSimpleBenchmark bsb = new BetterSimpleBenchmark();

		long benchmark = (bsb.benchmark() / 1000000);
		System.out.printf("Całkowity czas pomiaru %sms%nŚredni czas jednego wywołania %sms%n", benchmark, benchmark / 1000.);
	}

	public long benchmark() {
		long start = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			businessMethod();
		}
		long end = System.nanoTime();
		return (long) ((end - start));
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

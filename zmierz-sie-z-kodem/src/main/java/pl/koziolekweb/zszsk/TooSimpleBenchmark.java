package pl.koziolekweb.zszsk;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by BKuczynski on 2017-05-29.
 */
public class TooSimpleBenchmark {

	public static void main(String[] args) {
		TooSimpleBenchmark tsb = new TooSimpleBenchmark();

		long benchmark = tsb.benchmark();
		System.out.printf("Całkowity czas pomiaru %sms%nŚredni czas jednego wywołania %sms%n", benchmark, benchmark);
	}

	public long benchmark() {
		long start = System.currentTimeMillis();
		businessMethod();
		long end = System.currentTimeMillis();
		return end - start;
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

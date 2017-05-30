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
		BetterSimpleBenchmark tsb = new BetterSimpleBenchmark();

		System.out.println(tsb.benchmark());
	}

	public long benchmark() {
		long start = System.currentTimeMillis();
		for (int i =0 ; i < 100000; i ++) {
			businessMethod();
		}
		long end = System.currentTimeMillis();
		return (end - start)/100000;
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

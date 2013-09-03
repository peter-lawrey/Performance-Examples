package vanilla.java.perfeg.lambdas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: peter
 * Date: 03/09/13
 * Time: 09:00
 */
public class PriceSearchMain {

	static List<Product> products = new ArrayList<>();
	static volatile List<Product> filteredProducts;
	static double minPrice, maxPrice;

	public static void main(String[] args) {
		for (int sampleSize : new int[]{100_000, 30_000, 10_000, 3_000, 1_000, 300, 100, 30, 10, 3, 1}) {
			generateProducts(sampleSize);

			long time1 = 0, time2 = 0, time3 = 0;
			int count = 0;
			long start = System.currentTimeMillis();
			do {
				for (double window : new double[]{0.1, 0.5, 0.9}) {
					minPrice = 0.5 - window / 2;
					maxPrice = 0.5 + window / 2;
					time1 += testLoopingFilter();
					time2 += testFlitering();
					time3 += testParallelFlitering();
					count++;
				}
			} while (System.currentTimeMillis() - start < 2e3);
			System.out.printf("%,d: Average time per search was %,d as loop, %,d as stream(), %,d as parallelStream() in ns %n",
					sampleSize, time1 / count, time2 / count, time3 / count);
		}
	}

	private static void generateProducts(int sampleSize) {
		products.clear();
		for (int i = 0; i < sampleSize; i++) {
			products.add(new Product(Math.random()));
		}
	}

	public static long testLoopingFilter() {
		long start = System.nanoTime();
		List<Product> found = new ArrayList<>();
		for (Product product : products)
			if (product.price > minPrice && product.price < maxPrice)
				found.add(product);
		filteredProducts = found;
		return System.nanoTime() - start;
	}

	public static long testFlitering() {
		long start = System.nanoTime();
		filteredProducts = products.stream().filter(p -> p.price > minPrice).filter(p -> p.price < maxPrice).collect(Collectors.toList());
		return System.nanoTime() - start;
	}

	public static long testParallelFlitering() {
		long start = System.nanoTime();
		filteredProducts = products.parallelStream().filter(p -> p.price > minPrice).filter(p -> p.price < maxPrice).collect(Collectors.toList());
		return System.nanoTime() - start;
	}

	static class Product {
		final double price;

		Product(double price) {
			this.price = price;
		}
	}
}

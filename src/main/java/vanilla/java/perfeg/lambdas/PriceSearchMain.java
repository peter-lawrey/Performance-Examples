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

	static List<Product> products = new ArrayList<Product>();
	static volatile List<Product> filteredProducts;
	static double minPrice, maxPrice;

	public static void main(String[] args) {
		int sampleSize = 10000;
		generateProducts(sampleSize);

		for (int i = 0; i < 30; i++) {
			long time1 = 0;
			long time2 = 0;
			for (double window : new double[]{0.1, 0.5, 0.9}) {
				minPrice = 0.5 - window / 2;
				maxPrice = 0.5 + window / 2;
				time1 += testLoopingFilter();
				time2 += testFlitering();
			}
			System.out.printf("Time per entry was %,d and %,d ns %n",
					time1 / sampleSize / 3, time2 / sampleSize / 3);
		}
	}

	public static long testLoopingFilter() {
		long start = System.nanoTime();
		List<Product> found = new ArrayList<Product>();
		for (Product product : products)
			if (product.price > minPrice && product.price < maxPrice)
				found.add(product);
		filteredProducts = found;
		return System.nanoTime() - start;
	}

	public static long testFlitering() {
		long start = System.nanoTime();
		filteredProducts = products.parallelStream().filter(p -> p.price > minPrice).filter(p -> p.price < maxPrice).collect(Collectors.toList());
		return System.nanoTime() - start;
	}

	private static void generateProducts(int sampleSize) {
		for (int i = 0; i < sampleSize; i++) {
			products.add(new Product(Math.random()));
		}
	}

	static class Product {
		final double price;

		Product(double price) {
			this.price = price;
		}
	}
}

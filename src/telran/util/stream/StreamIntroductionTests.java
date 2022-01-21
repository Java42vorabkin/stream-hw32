package telran.util.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StreamIntroductionTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void streamArraySourceTest() {
		int ar[] = {10, -8, 17, 13, 10};
		int expected[] = {-8, 10};
		int actual[] = Arrays.stream(ar).filter(n -> n % 2 == 0).distinct().sorted()
				.toArray();
		assertArrayEquals(expected, actual);
		
	}
	@Test
	void streamRandomSourceTest() {
		Random gen = new Random();
		assertEquals(10,gen.ints().limit(10).toArray().length);
		gen.ints(10, 10, 25).forEach(n -> assertTrue(n >= 10 && n < 25));
	}
	@Test
	void streamCollectionSourceTest() {
		List<Integer> list = Arrays.asList(10, -8, 30);
		
		Integer [] actual = list.stream().filter(n -> n < 30).sorted().toArray(Integer[]::new);
		Integer [] expected = {-8, 10};
		assertArrayEquals(expected, actual);
	}
	@Test
	void streamStringSourceTest() {
		String str = "Hello";
		str.chars().forEach(n -> System.out.printf("%c;", n));
		
		
	}
	@Test
	void conversionFromIntToInteger( ) {
		List<Integer> expected = Arrays.asList(1, 2, 3);
		int ar[] = {1, 2, 3};
		List<Integer> actual = Arrays.stream(ar).boxed().toList();
		assertIterableEquals(expected, actual);
	}
	@Test
	void conversionFromIntegerToInt() {
		List<Integer> list = Arrays.asList(1, 2, 3);
		assertEquals(6, list.stream().mapToInt(n -> n).sum());
		assertArrayEquals(new int[] {1, 2,3}, list.stream().mapToInt(n -> n).toArray());
	}
	private Integer [] getLotoNumbers(int nNumbers, int min, int max) {
		//TODO - Done
		//using one stream to get array of unique random numbers in the given range
		/*
		Integer arr[] = new Random().ints(min, max).distinct().limit(nNumbers).boxed()
				.toList().toArray(Integer[]::new);
		for(Integer num : arr) {
			System.out.print(num+"\n");
		}
		return arr;
		*/	
		return new Random().ints(min, max).distinct().limit(nNumbers).boxed()
				.toList().toArray(Integer[]::new);	
	}
	@Test
	void lotoTest () {
		Integer [] lotoNumbers = getLotoNumbers(7, 1, 49);
		assertEquals(7, lotoNumbers.length);
		
		assertEquals(7, new HashSet<Integer>(Arrays.asList(lotoNumbers)).size());
		Arrays.stream(lotoNumbers).forEach(n -> assertTrue(n >= 1 && n <= 49));
	}
	/**
	 * 
	 * @param ar
	 * @return true if ar contains two numbers, the sum of which equals half of all array's numbers
	 * complexity O[N] 
	 */
	/*   Using two loops
	 *   O(n²)
	 */
	private boolean isHalfSum_1(int [] originalArray) {
		//TODO Done
		int desiredSum = Arrays.stream(originalArray).sum() / 2;
		for(int i=0; i<originalArray.length-1; i++) {
			for(int j=i+1; j<originalArray.length; j++) { 
				if((originalArray[i]+originalArray[j]) == desiredSum) {
					return true;
				}
			}
		}
		return false;
		
	}
	/*  Sorting and two Pointer approach
	   Time Complexity = Time complexity of sorting + Time complexity of two pointer approach
	   (while loop) = O (n log n) + O(n) = O (n log n)
	 */
	private boolean isHalfSum_2(int [] originalArray) {
		//TODO Done
		Arrays.sort(originalArray);
		int desiredSum = Arrays.stream(originalArray).sum() / 2;
		int leftIndex = 0, rightIndex = originalArray.length-1;
		while(leftIndex < rightIndex) {
			int sum = originalArray[leftIndex] + originalArray[rightIndex];
			if(sum == desiredSum) return true;
			if(sum > desiredSum) rightIndex--;
			if(sum < desiredSum) leftIndex++;
		}
		return false;
	}
/* Using a Hash Table
 				Complexity Analysis
	In worst case scenario, we scan the whole array and didn't find any such pair.
 	Time Complexity = Time complexity of inserting n elements in 
 	hash table + Time complexity of searching (K-A[i]) n times in hash table = n.
  	O(1) + n . O(1) = O(n)

 */
	private boolean isHalfSum_3(int [] originalArray) {
		//TODO 
		int desiredSum = Arrays.stream(originalArray).sum() / 2;
		HashSet<Integer> hash = new HashSet<>();
		for(int n : originalArray) {
//			System.out.println(n);
			int x = desiredSum - n;
			if(hash.contains(x)) {
				return true;
			}
			hash.add(n);
		}
		return false;
	}
	@Test
	void isHalfSumTest() {
		int ar1[] = {1,2, 10, -7};
		assertTrue(isHalfSum_1(ar1));
		assertTrue(isHalfSum_2(ar1));
		assertTrue(isHalfSum_3(ar1));
		int ar2[] = {1, 2, 10, 7};
		assertFalse(isHalfSum_1(ar2));
		assertFalse(isHalfSum_2(ar2));
		assertFalse(isHalfSum_3(ar2));
		int[] ar3 = { 4, 5, 6, 17, 18, 20 };
		assertTrue(isHalfSum_1(ar3));
		assertTrue(isHalfSum_2(ar3));
		assertTrue(isHalfSum_3(ar3));
		int[] ar4 = {11,11,11,11};
		assertTrue(isHalfSum_1(ar4));
		assertTrue(isHalfSum_2(ar4));
		assertTrue(isHalfSum_3(ar4));
		int[] ar5 = { 17, 18, 4, 5, 6, 20 };
		assertTrue(isHalfSum_1(ar5));
		assertTrue(isHalfSum_2(ar5));
		assertTrue(isHalfSum_3(ar5));
	}

}

package edu.wit.cs.comp2350.tests;

import java.security.Permission;
import java.util.Arrays;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import edu.wit.cs.comp2350.LAB1;

import static org.junit.Assert.*;

public class LAB1TestCase{
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(15);
	
	@SuppressWarnings("serial")
	private static class ExitException extends SecurityException {}
	
	private static class NoExitSecurityManager extends SecurityManager 
    {
        @Override
        public void checkPermission(Permission perm) {}
        
        @Override
        public void checkPermission(Permission perm, Object context) {}
        
        @Override
        public void checkExit(int status) { super.checkExit(status); throw new ExitException(); }
    }
	
	@Before
    public void setUp() throws Exception 
    {
        System.setSecurityManager(new NoExitSecurityManager());
    }
	
	@After
    public void tearDown() throws Exception 
    {
        System.setSecurityManager(null);
    }
	
	private void _test(int[] values, int[] expected, char algo) {
		
		int[] actual = new int[0];
		
		try {
			if (algo == 'i')
				actual = LAB1.insertionSort(values);
			else
				actual = LAB1.mergeSort(values);
		} catch (ExitException e) {}
		
		assertEquals("Output has an incorrect number of items.", expected.length, actual.length);
		for (int i = 0; i < actual.length; i++)
			assertEquals("Mismatch in position " + i + ".", expected[i], actual[i]);
		
	}

	private int[] generateRandArray(int size) {
		int[] ret = new int[size];
		
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			ret[i] = r.nextInt(LAB1.MAX_INPUT+1);
		}
		return ret;
	}
	
	private void testRand(char c, int size) {
		int[] randArray = generateRandArray(size);
		int[] sortedArray = Arrays.copyOf(randArray, size);
		Arrays.sort(sortedArray);
		
		_test(randArray, sortedArray, c);
	}
	
	@Test
	public void testEmptyInsertion() {
		_test(new int[0], new int[0], 'i');
	}

	@Test
	public void testSingleInsertion() {
		_test(new int[] {1}, new int[] {1}, 'i');
		_test(new int[] {10000}, new int[] {10000}, 'i');
	}

	@Test
	public void testSmallInsertion() {
		_test(new int[] {1, 2, 3}, new int[] {1, 2, 3}, 'i');
		_test(new int[] {3, 2, 1}, new int[] {1, 2, 3}, 'i');
		_test(new int[] {1, 2, 3, 4}, new int[] {1, 2, 3, 4}, 'i');
		_test(new int[] {3, 2, 1, 4}, new int[] {1, 2, 3, 4}, 'i');
		_test(new int[] {2, 1}, new int[] {1, 2}, 'i');
		_test(new int[] {9999, 10000}, new int[] {9999, 10000}, 'i');
		_test(new int[] {10000, 9999}, new int[] {9999, 10000}, 'i');
	}

	@Test
	public void testRandInsertion() {
		testRand('i', 1000);
	}

	@Test
	public void testSizesInsertion() {
		_test(new int[] {1, 10, 100, 1000, 10000, 100000}, new int[] {1, 10, 100, 1000, 10000, 100000}, 'i');
		_test(new int[] {1, 10, 100, 1000, 10000, 100000}, new int[] {1, 10, 100, 1000, 10000, 100000}, 'i');
		_test(new int[] {100000, 10000, 1000, 100, 10, 1}, new int[] {1, 10, 100, 1000, 10000, 100000}, 'i');
		_test(new int[] {10000, 10, 1, 1000, 100, 100000}, new int[] {1, 10, 100, 1000, 10000, 100000}, 'i');
	}

	@Test
	public void testEmptyMerge() {
		_test(new int[0], new int[0], 'm');
	}

	@Test
	public void testSingleMerge() {
		_test(new int[] {1}, new int[] {1}, 'm');
		_test(new int[] {10000}, new int[] {10000}, 'm');
	}

	@Test
	public void testSmallMerge() {
		_test(new int[] {1, 2, 3}, new int[] {1, 2, 3}, 'm');
		_test(new int[] {3, 2, 1}, new int[] {1, 2, 3}, 'm');
		_test(new int[] {1, 2, 3, 4}, new int[] {1, 2, 3, 4}, 'm');
		_test(new int[] {3, 2, 1, 4}, new int[] {1, 2, 3, 4}, 'm');
		_test(new int[] {2, 1}, new int[] {1, 2}, 'm');
		_test(new int[] {9999, 10000}, new int[] {9999, 10000}, 'm');
		_test(new int[] {10000, 9999}, new int[] {9999, 10000}, 'm');
	}

	@Test
	public void testRandMerge() {
		testRand('m', 1000);
	}

	@Test
	public void testSizesMerge() {
		_test(new int[] {1, 10, 100, 1000, 10000, 100000}, new int[] {1, 10, 100, 1000, 10000, 100000}, 'm');
		_test(new int[] {1, 10, 100, 1000, 10000, 100000}, new int[] {1, 10, 100, 1000, 10000, 100000}, 'm');
		_test(new int[] {100000, 10000, 1000, 100, 10, 1}, new int[] {1, 10, 100, 1000, 10000, 100000}, 'm');
		_test(new int[] {10000, 10, 1, 1000, 100, 100000}, new int[] {1, 10, 100, 1000, 10000, 100000}, 'm');
	}

}
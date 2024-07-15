package assign07;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphUtilityTest<T> {
	private List<Integer> sources;
	private List<Integer> destinations;

	@BeforeEach
	void setUp() {
		// Initialize test data
		sources = Arrays.asList(1, 2, 3);
		destinations = Arrays.asList(3, 4, 5);
	}

// test throw correctly
	@Test
	void testAreConnectedThrows() {

	}

	@Test
	void testShortestPathThrows() {
	}

	@Test
	void testSortThrows() {
	}

	
	
	@Test
	void testAreConnected() {
		List<Integer> sources = new ArrayList<>();
        sources.add(1);
        sources.add(2);
        sources.add(3);

        List<Integer> destinations = new ArrayList<>();
        destinations.add(4);
        destinations.add(5);
        destinations.add(6);

        // Specify source and destination data
        Integer srcData = 1;
        Integer dstData = 4;

        // Test case: Valid scenario
        assertTrue(GraphUtility.areConnected(sources, destinations, srcData, dstData));
	}
	
	
	// test normal case shortestPath
	@Test
	void testShortestPath() {
		List<Integer> shortestPath = GraphUtility.shortestPath(sources, destinations, 1, 4);
		assertEquals(Arrays.asList(1, 4), shortestPath);
	}
	@Test
	void testGraphUtilAreConnected() {
		
	}
	 //test normal case sort
//	@Test
//	void testSort() {
//		List<Integer> sortedList = GraphUtility.sort(sources, destinations);
//		assertEquals(Arrays.asList(1, 2, 3, 4, 5), sortedList);
//	}

	// test edge cases
	

	@Test
	void testShortestPathEmpty() {
	}

	@Test
	void testSortEmpty() {
	}
	
	
}

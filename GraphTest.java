package assign07;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	// test normal case areconnected
	@Test
	void testDFS() {
		Graph<Integer> graph = new Graph<>();

		// Initialize vertices
		Integer vertex1 = 1;
		Integer vertex2 = 2;
		Integer vertex3 = 3;
		Integer vertex4 = 4;
		Integer vertex5 = 5;

		// Add edges
		graph.addEdge(vertex1, vertex2);
		graph.addEdge(vertex1, vertex3);
		graph.addEdge(vertex2, vertex4);
		graph.addEdge(vertex3, vertex5);

		assertTrue(graph.dfs(1, 5)); // There's a path from 1 to 5
		assertTrue(graph.dfs(2, 4)); // There's a path from 1 to 5

		assertFalse(graph.dfs(3, 4)); // There's no path from 3 to 4
		assertFalse(graph.dfs(5, 1)); // There's no path from 5 to 1
		assertFalse(graph.dfs(4, 1)); // There's no path from 4 to 1
	}

	@Test
	void testDOTExample1() {
		Graph<Integer> graph = new Graph<>();

		Integer v1 = 1;
		Integer v2 = 2;
		Integer vertex3 = 3;
		Integer vertex4 = 4;

		graph.addEdge(v1, v2);
		graph.addEdge(v2, vertex3);
		graph.addEdge(v1, vertex3);
		graph.addEdge(vertex3, vertex4);
		graph.addEdge(vertex4, v2);
		graph.addEdge(v1, vertex4);

		assertTrue(graph.dfs(1, 4));
		assertTrue(graph.dfs(2, 3));
		assertTrue(graph.dfs(1, 3));
		assertFalse(graph.dfs(2, 1));
		assertFalse(graph.dfs(4, 1));

	}

	@Test
	void testDOTExample8() {
		Graph<String> graph = new Graph<>();

		String slc = "Salt Lake City";
		String ds = "Dallas";
		String atl = "Alanta";
		String wst = "Washington";
		String sdg = "Sandiego";

		graph.addEdge(slc, ds);
		graph.addEdge(slc, atl);
		graph.addEdge(ds, atl);
		graph.addEdge(ds, wst);
		graph.addEdge(atl, wst);
		graph.addEdge(atl, sdg);
		graph.addEdge(wst, sdg);
		graph.addEdge(sdg, slc);
		assertTrue(graph.dfs(slc, sdg)); 
		assertTrue(graph.dfs(atl, slc)); 
		assertTrue(graph.dfs(atl, wst));
		assertTrue(graph.dfs(sdg, ds));
		assertTrue(graph.dfs(wst, slc)); 

	}

	@Test
	void testDFSEmpty() {
		Graph<Integer> graph = new Graph<>();

		assertFalse(graph.dfs(null, null));
	}
	
	@Test
	void testBFSExample1() {
		Graph<Integer> graph = new Graph<>();

		Integer vertex1 = 1;
		Integer vertex2 = 2;
		Integer vertex3 = 3;
		Integer vertex4 = 4;

		graph.addEdge(vertex1, vertex2);
		graph.addEdge(vertex2, vertex3);
		graph.addEdge(vertex1, vertex3);
		graph.addEdge(vertex3, vertex4);
		graph.addEdge(vertex4, vertex2);
		graph.addEdge(vertex1, vertex4);

//		assertEquals(graph.bfs(vertex1, vertex2));
//		assertEquals(graph.bfs(vertex1, vertex2));
//		assertEquals(graph.bfs(vertex1, vertex2));
//		assertEquals(graph.bfs(vertex1, vertex2));
//		assertEquals(graph.bfs(vertex1, vertex2));
	}
	

}

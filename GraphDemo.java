package assign07;

/**
 * Demonstrates how to use the Graph class.
 * 
 * @author Erin Parker
 * @version March 3, 2022
 */
public class GraphDemo {

	public static void main(String[] args) {
		
		// build a sample directed graph
		Graph sample = new Graph();
		sample.addEdge("a", "b");
		sample.addEdge("b", "c");
		sample.addEdge("c", "d");
		sample.addEdge("b", "d");
		sample.addEdge("d", "a");

		// print textual representation of sample graph
		System.out.println(sample);
		
		// print DOT representation of sample graph (paste into http://www.webgraphviz.com)
	}
}
package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains several methods for solving problems on generic, directed,
 * unweighted, sparse graphs. This utility class provides methods for
 * determining connectivity between vertices, finding shortest paths between
 * vertices, and performing topological sorting on the graph.
 * 
 * @param <T> the type of data stored in the vertices of the graph
 * 
 * @author Phuc Do & Khang Nguyen & CS 2420 instructors
 * @version Mar 14, 2024
 */
public class GraphUtility<T> {
	/**
	 * Checks whether there is a path between two given vertices in the graph.
	 * 
	 * @param sources      a list of source vertices
	 * @param destinations a list of destination vertices
	 * @param srcData      the data of the source vertex
	 * @param dstData      the data of the destination vertex
	 * @return true if there exists a path from srcData to dstData, false otherwise
	 * @throws IllegalArgumentException if the sizes of sources and destinations do
	 *                                  not match, if either sources or destinations
	 *                                  is empty, or if either sources or
	 *                                  destinations contain null values
	 */
	public static <T> boolean areConnected(List<T> sources, List<T> destinations, T srcData, T dstData)
			throws IllegalArgumentException {
		if (sources.size() != destinations.size() || sources.isEmpty() || destinations.isEmpty())
			throw new IllegalArgumentException("The sources and the destination size does not match");

		if (sources.contains(null) || destinations.contains(null))
			throw new IllegalArgumentException("Cannot contain null values");

		Graph<T> graph = buildGraph(sources, destinations);
		return graph.dfs(srcData, dstData);
	}

	/**
	 * Finds the shortest path between two given vertices in the graph.
	 * 
	 * @param sources      a list of source vertices
	 * @param destinations a list of destination vertices
	 * @param srcData      the data of the source vertex
	 * @param dstData      the data of the destination vertex
	 * @return a list representing the shortest path from srcData to dstData
	 * @throws IllegalArgumentException if the sizes of sources and destinations do
	 *                                  not match, if either sources or destinations
	 *                                  is empty, if srcData or dstData is not found
	 *                                  in their respective lists, or if either
	 *                                  sources or destinations contain null values
	 */
	public static <T> List<T> shortestPath(List<T> sources, List<T> destinations, T srcData, T dstData)
			throws IllegalArgumentException {
		if (sources.size() != destinations.size() || sources.isEmpty() || destinations.isEmpty())
			throw new IllegalArgumentException("The sources and the destination size does not match");

		if (!sources.contains(srcData) || !destinations.contains(dstData))
			throw new IllegalArgumentException("The srcData or dstData was not found");

		if (sources.contains(null) || destinations.contains(null))
			throw new IllegalArgumentException("Cannot contain null values");

		Graph<T> graph = buildGraph(sources, destinations);
		return graph.bfs(srcData, dstData);
	}

	/**
	 * Performs topological sorting on the graph represented by sources and
	 * destinations lists.
	 * 
	 * @param sources      a list of source vertices
	 * @param destinations a list of destination vertices
	 * @return a list containing the topologically sorted vertices of the graph
	 * @throws IllegalArgumentException if the sizes of sources and destinations do
	 *                                  not match, if either sources or destinations
	 *                                  is empty, or if either sources or
	 *                                  destinations contain null values
	 */
	public static <T> List<T> sort(List<T> sources, List<T> destinations) throws IllegalArgumentException {
		if (sources.size() != destinations.size() || sources.isEmpty() || destinations.isEmpty())
			throw new IllegalArgumentException("The sources and the destination size does not match");
		if (sources.contains(null) || destinations.contains(null))
			throw new IllegalArgumentException("Cannot contain null values");
		Graph<T> graph = buildGraph(sources, destinations);

		return graph.topologicalSort();
	}

	/**
	 * Builds "sources" and "destinations" lists according to the edges specified in
	 * the given DOT file (e.g., "a -> b"). Assumes that the vertex data type is
	 * String.
	 * 
	 * Accepts many valid "digraph" DOT files (see examples posted on Canvas).
	 * --accepts \\-style comments --accepts one edge per line or edges terminated
	 * with ; --does not accept attributes in [] (e.g., [label = "a label"])
	 * 
	 * @param filename     - name of the DOT file
	 * @param sources      - empty ArrayList, when method returns it is a valid
	 *                     "sources" list that can be passed to the public methods
	 *                     in this class
	 * @param destinations - empty ArrayList, when method returns it is a valid
	 *                     "destinations" list that can be passed to the public
	 *                     methods in this class
	 */
	public static void buildListsFromDot(String filename, ArrayList<String> sources, ArrayList<String> destinations) {

		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		scan.useDelimiter(";|\n");

		// Determine if graph is directed (i.e., look for "digraph id {").
		String line = "", edgeOp = "";
		while (scan.hasNext()) {
			line = scan.next();

			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");

			if (line.indexOf("digraph") >= 0) {
				edgeOp = "->";
				line = line.replaceFirst(".*\\{", "");
				break;
			}
		}
		if (edgeOp.equals("")) {
			System.out.println("DOT graph must be directed (i.e., digraph).");
			scan.close();
			System.exit(0);

		}

		// Look for edge operator -> and determine the source and destination
		// vertices for each edge.
		while (scan.hasNext()) {
			String[] substring = line.split(edgeOp);

			for (int i = 0; i < substring.length - 1; i += 2) {
				// remove " and trim whitespace from node string on the left
				String vertex1 = substring[0].replace("\"", "").trim();
				// if string is empty, try again
				if (vertex1.equals("")) {
					continue;
				}

				// do the same for the node string on the right
				String vertex2 = substring[1].replace("\"", "").trim();
				if (vertex2.equals("")) {
					continue;
				}

				// indicate edge between vertex1 and vertex2
				sources.add(vertex1);
				destinations.add(vertex2);
			}

			// do until the "}" has been read
			if (substring[substring.length - 1].indexOf("}") >= 0) {
				break;
			}

			line = scan.next();

			line = line.replaceFirst("//.*", "");
		}

		scan.close();
	}

	/**
	 * Builds a graph representation from the provided lists of sources and
	 * destinations.
	 * 
	 * @param sources     a list of source vertices
	 * @param destination a list of destination vertices
	 * @return a Graph instance representing the graph defined by the provided lists
	 */
	private static <T> Graph<T> buildGraph(List<T> sources, List<T> destination) {
		Graph<T> graph = new Graph<>();
		for (int i = 0; i < sources.size(); i++) {
			T src = sources.get(i);
			T des = destination.get(i);
			graph.addEdge(src, des);
		}
		return graph;
	}
}
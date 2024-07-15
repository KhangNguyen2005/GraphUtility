package assign07;

/**
 * Represents an edge in a directed graph, pointing from a source vertex to a
 * destination vertex.
 * 
 * @param <T> the type of data stored in the vertices of the graph
 * @author Phuc Do & Khang Nguyen
 * @version Mar 14, 2024
 */
public class Edge<T> {
	private Vertex<T> destination;

	/**
	 * Constructs an edge pointing to the specified destination vertex.
	 * 
	 * @param destination the destination vertex of the edge
	 */
	public Edge(Vertex<T> destination) {
		this.destination = destination;
	}

	/**
	 * Gets the destination vertex of the edge.
	 * 
	 * @return the destination vertex of the edge
	 */
	public Vertex<T> getDestination() {
		return this.destination;
	}

	/**
	 * Generates a textual representation of the edge.
	 * 
	 * @return a string representing the destination vertex of the edge
	 */
	public String toString() {
		return this.destination.toString();
	}
}
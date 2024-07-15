package assign07;

import java.util.*;

/**
 * Represents a generic directed graph.
 * 
 * @param <T> the type of data stored in the vertices of the graph
 * @author Phuc Do & Khang Nguyen
 * @version Mar 14, 2024
 */
public class Graph<T> {
	private HashMap<T, Vertex<T>> vertices;

	/**
	 * Constructs an empty graph.
	 */
	public Graph() {
		vertices = new HashMap<>();
	}

	/**
	 * Adds an edge between two vertices in the graph.
	 * 
	 * @param name1 the data of the source vertex
	 * @param name2 the data of the destination vertex
	 */
	public void addEdge(T name1, T name2) {
		Vertex<T> vertex1;
		// if vertex already exists in graph, get its object
		if (vertices.containsKey(name1))
			vertex1 = vertices.get(name1);
		// else, create a new object and add to graph
		else {
			vertex1 = new Vertex<>(name1);
			vertices.put(name1, vertex1);
		}

		Vertex<T> vertex2;
		if (vertices.containsKey(name2))
			vertex2 = vertices.get(name2);
		else {
			vertex2 = new Vertex<>(name2);
			vertices.put(name2, vertex2);
		}

		// add new directed edge from vertex1 to vertex2
		vertex1.addEdge(vertex2);
	}

	/**
	 * Finds a vertex in the graph based on the given data.
	 * 
	 * @param data the data to search for
	 * @return the vertex containing the data, or null if not found
	 */
	public Vertex<T> findVertex(T data) {
		for (T vertex : vertices.keySet()) {
			if (vertex.equals(data))
				return vertices.get(data);

		}
		return null;
	}

	/**
	 * Performs a depth-first search to determine if there is a path between two
	 * vertices.
	 * 
	 * @param srcData the data of the source vertex
	 * @param dstData the data of the destination vertex
	 * @return true if there is a path from srcData to dstData, false otherwise
	 */
	public boolean dfs(T srcData, T dstData) {
		if (!vertices.containsKey(srcData) || !vertices.containsKey(dstData))
			throw new IllegalArgumentException("The srcData or dstData was not found");

		Vertex<T> srcVertex = vertices.get(srcData);
		Vertex<T> dstVertex = vertices.get(dstData);

		if (srcVertex == null || dstVertex == null)
			return false;

		HashSet<Vertex<T>> visited = new HashSet<>();
		return dfsRecursive(srcVertex, dstVertex, visited);
	}

	/**
	 * Helper method for performing recursive depth-first search.
	 * 
	 * @param current   the current vertex being explored
	 * @param dstVertex the destination vertex being searched for
	 * @param visited   a set of visited vertices
	 * @return true if a path from current vertex to destination vertex is found,
	 *         false otherwise
	 */
	private boolean dfsRecursive(Vertex<T> current, Vertex<T> dstVertex, HashSet<Vertex<T>> visited) {
		if (current.equals(dstVertex))
			return true;

		for (Edge<T> edge : current.getNeighbors()) {
			Vertex<T> neighbor = edge.getDestination();
			if (!visited.contains(neighbor)) {
				visited.add(neighbor);
				if (dfsRecursive(neighbor, dstVertex, visited))
					return true;
			}
		}

		return false;
	}

	/**
	 * Performs a breadth-first search to find the shortest path between two
	 * vertices.
	 * 
	 * @param srcData the data of the source vertex
	 * @param dstData the data of the destination vertex
	 * @return a list representing the shortest path from srcData to dstData
	 * @throws IllegalArgumentException if no path is found between the vertices
	 */
	public List<T> bfs(T srcData, T dstData) {
		Vertex<T> srcVertex = findVertex(srcData);
		Vertex<T> dstVertex = findVertex(dstData);

		Queue<Vertex<T>> queue = new LinkedList<>();
		Set<Vertex<T>> visited = new HashSet<>();
		// Initialize a map to keep track of the parent vertices during traversal

		Map<Vertex<T>, Vertex<T>> parentMap = new HashMap<>();
		// Initialize a list to store the path from the source vertex to the current
		// vertex
		List<T> path = new ArrayList<>();
		// Add the source vertex to the queue to start the BFS traversal
		queue.offer(srcVertex);
		// Mark the source vertex as visited
		visited.add(srcVertex);

		while (!queue.isEmpty()) {
			// Dequeue the current vertex from the queue
			Vertex<T> current = queue.poll();
			// Iterate through the edges of the current vertex
			var Itr = current.edges();
			while (Itr.hasNext()) {
				// Get the next edge and its destination vertex
				Edge<T> curr = Itr.next();
				Vertex<T> neighbor = curr.getDestination();
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					parentMap.put(neighbor, current);
					queue.offer(neighbor);
				}
			}

			// Reconstruct the path
			if (current.equals(dstVertex)) {
				Vertex<T> node = current;
				while (node != null) {
					path.add(0, node.getName());
					node = parentMap.get(node);
				}
				return path;
			}
		}
		throw new IllegalArgumentException("No path found");
	}

	/**
	 * Performs a topological sort on the graph.
	 * 
	 * @return a list containing the topologically sorted vertices of the graph
	 * @throws IllegalArgumentException if the graph contains a cycle
	 */
	public List<T> topologicalSort() {
		Queue<Vertex<T>> queue = new LinkedList<>();
		List<T> result = new ArrayList<>();

		// Enqueue vertices with indegree 0
		for (Vertex<T> vertex : vertices.values()) {
			if (vertex.indegree == 0) {
				queue.offer(vertex);
			}
		}

		// Process vertices in the queue
		while (!queue.isEmpty()) {
			Vertex<T> current = queue.poll();
			result.add(current.getName());

// Update indegrees of neighbors and enqueue them if indegree becomes 0
			var neighbors = current.edges();
			while (neighbors.hasNext()) {
				Edge<T> edge = neighbors.next();
				Vertex<T> neighbor = edge.getDestination();
				neighbor.indegree--;

				if (neighbor.indegree == 0) {
					queue.offer(neighbor);
				}
			}
		}

		// Check if a cycle exists (if all vertices are not visited)
		if (result.size() != vertices.size()) {
			throw new IllegalArgumentException("Graph contains a cycle");
		}
		return result;
	}
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	
	private static final String FULL_OF_ENERGY = "Full of energy", PAYS = "Pays";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int[] cd = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		
		Edge[] edges = new Edge[cd[1]];
		
		for (int i = 0; i < cd[1]; i++) {
			String[] decision = in.readLine().split(" ");
			int cost = Integer.parseInt(decision[2]);
			if (decision[1].equals(PAYS)) cost *= -1;
			edges[i] = new Edge(Integer.parseInt(decision[0]), Integer.parseInt(decision[3]), cost);
		}
		
		int[] swe = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		
		int maxEnergy = solve(cd[0], edges, swe);
		
		if (maxEnergy > swe[2]) {
			System.out.println(FULL_OF_ENERGY);
		}
		else {
			System.out.println(maxEnergy);
		}
	}
	
	private static int solve(int numNodes, Edge[] edges, int[] swe) {
		int maxEnergy = 0;
		int[] distance = new int[edges.length+1];
		
		for (int i = 0; i < numNodes; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		
		distance[swe[0]] = swe[2];
		boolean changes = false;
		
		for (int i = 1; i < numNodes; i++){
			if (!(changes = updateDistances(edges, distance))) {
				break;
			}
		}
		
		int energyAtWizard = distance[swe[1]];
		
		if (changes && updateDistances(edges, distance)) {
			if (distance[swe[1]] > energyAtWizard) {
				return Integer.MAX_VALUE;
			}
			if (distance[swe[1]] < energyAtWizard) {
				return 0;
			}
		}
		
		return energyAtWizard;
	}
	
	private static boolean updateDistances(Edge[] edges, int[] distance) {
		boolean changes = false;
		
		for (Edge edge : edges) {
			if (distance[edge.source] < Integer.MAX_VALUE) {
				int cost = distance[edge.destination] + edge.weight;
				
				if (cost < distance[edge.destination]) {
					distance[edge.destination] = cost;
					changes = true;
				}
			}
		}
		
		return changes;
	}
	
	private static class Edge {
		
		public final int source, destination, weight;
		
		public Edge(int source, int destination, int weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}
		
	}
	
}

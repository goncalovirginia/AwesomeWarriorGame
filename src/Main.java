import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
		int[] energy = new int[numNodes];
		boolean[] hasPathToWizard = new boolean[numNodes];
		
		for (int i = 0; i < numNodes; i++) {
			energy[i] = Integer.MIN_VALUE;
		}
		
		energy[swe[0]] = swe[2];
		hasPathToWizard[swe[1]] = true;
		boolean changes = false;
		
		for (int i = 1; i < numNodes; i++){
			if (!(changes = !updateEnergy(edges, energy, hasPathToWizard).isEmpty())) {
				break;
			}
		}
		
		if (changes) {
			for (int node : updateEnergy(edges, energy, hasPathToWizard)) {
				if (hasPathToWizard[node]) {
					return Integer.MAX_VALUE;
				}
			}
		}
		
		return Math.max(0, energy[swe[1]]);
	}
	
	private static List<Integer> updateEnergy(Edge[] edges, int[] energy, boolean[] hasPathToWizard) {
		List<Integer> updated = new LinkedList<>();
		
		for (Edge edge : edges) {
			if (hasPathToWizard[edge.destination]) {
				hasPathToWizard[edge.source] = true;
			}
			
			if (energy[edge.source] > Integer.MIN_VALUE) {
				int newEnergy = energy[edge.source] + edge.weight;
				
				if (newEnergy > energy[edge.destination]) {
					energy[edge.destination] = newEnergy;
					updated.add(edge.destination);
				}
			}
		}
		
		return updated;
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

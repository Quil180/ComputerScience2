/* Yousef Alaa Awad
 * Dr. Steinberg
 * COP3503 Spring 2025
 * Programming Assignment 5
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Railroad
{
  // each railroad track has a cost, source, and destination
  private int cost;
  private String source;
  private String destination;

  // array of all rails
  private static Railroad[] allRails;

  // first and only public constructor
  public Railroad(int tracks, String inputFile)
  {
    // clear previous rails
    allRails = new Railroad[tracks];

    // read input file
    try (Scanner scanner = new Scanner(new File(inputFile)))
    {
      // read specified number of tracks
      for (int i = 0; i < tracks; i++)
      {
        String src = scanner.next();
        String dest = scanner.next();
        int cost = scanner.nextInt();

        // add rail to array
        allRails[i] = new Railroad(src, dest, cost);
      }
    }
    catch (FileNotFoundException e)
    {
      System.err.println("File was not found: " + e.getMessage());
    }
  }

  // private constructor for rail entries
  private Railroad(String source, String destination, int cost)
  {
    this.source = source;
    this.destination = destination;
    this.cost = cost;
  }

  // kruskal wrapper for finding optimal tracks
  public String buildRailroad()
  {
    // sort rails by ascending cost
    sortRailways();

    // apply kruskal's algorithm to find minimum spanning tree
    Railroad[] mstEdges = kruskal();

    // build output string
    StringBuilder result = new StringBuilder();
    int totalCost = 0;

    // add each rail in MST to output
    for (Railroad rail : mstEdges)
    {
      if (rail != null)
      {
        result.append(printOutput(rail.source, rail.destination, rail.cost));
        totalCost += rail.cost;
      }
    }

    // add total cost to output
    result.append("The cost of the railroad is $").append(totalCost).append(".");

    return result.toString();
  }

  private void sortRailways()
  {
    // sort allRails by cost ascending
    Arrays.sort(allRails, (a, b) -> Integer.compare(a.cost, b.cost));
  }

  private String printOutput(String track1, String track2, int cost)
  {
    // generate ordered rail output
    String a = track1;
    String b = track2;

    // swap if out of order
    if (a.compareTo(b) > 0)
    {
      a = track2;
      b = track1;
    }

    return a + "---" + b + "\t$" + cost + "\n";
  }

  // apply kruskal's algorithm to compute MST
  private Railroad[] kruskal()
  {
    // map vertex names to indices
    Map<String, Integer> vertices = new HashMap<>();
    int vertexCount = 0;

    // register vertices
    for (Railroad rail : allRails)
    {
      if (!vertices.containsKey(rail.source))
      {
        // add source if new
        vertices.put(rail.source, vertexCount++);
      }
      if (!vertices.containsKey(rail.destination))
      {
        // add destination if new
        vertices.put(rail.destination, vertexCount++);
      }
    }

    DisjointSet ds = new DisjointSet(vertexCount);
    Railroad[] mstEdges = new Railroad[vertexCount - 1];
    int mstEdgeCount = 0;

    // select edges without creating cycles
    for (Railroad rail : allRails)
    {
      int sourceRoot = ds.find(vertices.get(rail.source));
      int destRoot = ds.find(vertices.get(rail.destination));

      // add edge if no cycle
      if (sourceRoot != destRoot)
      {
        mstEdges[mstEdgeCount++] = rail;
        ds.union(sourceRoot, destRoot);

        // stop when MST complete
        if (mstEdgeCount == vertexCount - 1)
        {
          break;
        }
      }
    }

    return mstEdges;
  }

  // disjoint-set for union-find operations
  private static class DisjointSet
  {
    private int[] parent;
    private int[] rank;

    public DisjointSet(int size)
    {
      parent = new int[size];
      rank = new int[size];

      for (int i = 0; i < size; i++)
      {
        parent[i] = i;
        rank[i] = 0;
      }
    }

    public int find(int x)
    {
      if (parent[x] != x)
      {
        parent[x] = find(parent[x]);
      }
      return parent[x];
    }

    public void union(int x, int y)
    {
      int rootX = find(x);
      int rootY = find(y);

      if (rootX == rootY)
      {
        return;
      }

      if (rank[rootX] < rank[rootY])
      {
        parent[rootX] = rootY;
      }
      else if (rank[rootX] > rank[rootY])
      {
        parent[rootY] = rootX;
      }
      else
      {
        parent[rootY] = rootX;
        rank[rootX]++;
      }
    }
  }
}

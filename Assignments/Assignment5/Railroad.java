/* Yousef Alaa Awad
 * Dr. Steinberg
 * +COP3503 Spring 2025
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
  private int cost;
  private String source;
  private String destination;

  private static Railroad[] allRails; 

  public Railroad(int tracks, String inputFile)
  {
    // ok so this clears all the allRails
    allRails = new Railroad[tracks];

    // file reading
    try (Scanner scanner = new Scanner(new File(inputFile)))
    {
      // only getting the correct amount of tracks in the file
      for (int i = 0; i < tracks; i++)
      {
        String src = scanner.next();
        String dest = scanner.next();
        int cost = scanner.nextInt();

        // putting them into the array
        allRails[i] = new Railroad(src, dest, cost);
      }
    }
    catch (FileNotFoundException e)
    {
      System.err.println("File was not found: " + e.getMessage());
    }
  }

  // second constructor for populating array of all tracks
  public Railroad(String source, String destination, int cost) 
  {
    this.source = source;
    this.destination = destination;
    this.cost = cost;
  }

  public String buildRailroad() 
  {
    // sorts the railways by cost
    sortRailways();
    
    // this runs kruskal's to find minimum spanning tree
    Railroad[] mstEdges = kruskal();
    
    // constructs the output string
    StringBuilder result = new StringBuilder();
    int totalCost = 0;
    
    for (Railroad rail : mstEdges) {
      if (rail != null) {
        result.append(printOutput(rail.source, rail.destination, rail.cost));
        totalCost += rail.cost;
      }
    }
    
    result.append("The cost of the railroad is $").append(totalCost).append(".");
    return result.toString();
  }
  
  private void sortRailways() 
  {
    // sorts the allRails array by cost in ascending order
    Arrays.sort(allRails, (a, b) -> Integer.compare(a.cost, b.cost));
  }

  private String printOutput(String track1, String track2, int cost)
  {
    return track1 + "---" + track2 + "\t$" + cost + "\n";
  }

  private Railroad[] kruskal()
  {
    // this will help find all unique vertices
    Map<String, Integer> vertices = new HashMap<>();
    int vertexCount = 0;
    
    for (Railroad rail : allRails) 
    {
      if (!vertices.containsKey(rail.source))
      {
        vertices.put(rail.source, vertexCount++);
      }
      if (!vertices.containsKey(rail.destination)) 
      {
        vertices.put(rail.destination, vertexCount++);
      }
    }
    
    // intializes disjoint set for all vertices
    DisjointSet ds = new DisjointSet(vertexCount);
    
    // Array to store minimum spanning tree edges
    Railroad[] mstEdges = new Railroad[vertexCount - 1];
    int mstEdgeCount = 0;
    
    // applying kruskals
    for (Railroad rail : allRails) 
    {
      int sourceRoot = ds.find(vertices.get(rail.source));
      int destRoot = ds.find(vertices.get(rail.destination));
      
      // if including this edge doesn't form a cycle, add it to MST
      if (sourceRoot != destRoot)   
      {
        mstEdges[mstEdgeCount++] = rail;
        ds.union(sourceRoot, destRoot);
        
        // if we have n-1 edges, MST is complete
        if (mstEdgeCount == vertexCount - 1) 
        {
          break;
        }
      }
    }
    
    return mstEdges;
  }
  
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
    
    // union by rank
    public void union(int x, int y) 
    {
      int rootX = find(x);
      int rootY = find(y);
      
      if (rootX == rootY) 
      {
        return;
      }
      
      // helps keep the tree balanced vro
      if (rank[rootX] < rank[rootY]) 
      {
        parent[rootX] = rootY;
      } else if (rank[rootX] > rank[rootY]) 
      {
        parent[rootY] = rootX;
      } else 
      {
        parent[rootY] = rootX;
        rank[rootX]++;
      }
    }
  }
}
  

/* Yousef Alaa Awad
 * Dr. Andrew Steinberg
 * COP3503 Spring 2025
 * Programming Assignment 4
 */

import java.util.Arrays;

public class HopStepGame
{
  public int minCost(int[] squareCost, int length)
  {
    // Start from position -1 (before the first square)
    return minCostRec(squareCost, -1, length);
  }

  // Recursive helper: pos is the index of the current square
  private int minCostRec(int[] squareCost, int position, int length)
  {
    // Base Case. we have reached the end of the array
    if (position == length - 1 || position == length - 2)
    {
      return squareCost[length];
    }

    // Setting cost values for both options
    int costStep = Integer.MAX_VALUE;
    int costHop = Integer.MAX_VALUE;

    // Option 1: Step to the next square (if it exists)
    if (position + 1 < length)
    {
      costStep = squareCost[position + 1] + minCostRec(squareCost, position + 1, length);
    }

    // Option 2: Hop over one square (if it exists)
    if (position + 2 < length)
    {
      costHop = squareCost[position + 2] + minCostRec(squareCost, position + 2, length);
    }

    return Math.min(costStep, costHop);
  }

  public int minCostMemoization(int[] squareCost, int numSquares, int[] previousResult)
  {
    // Initialize the previousResult array with -1 to denote uncomputed positions
    Arrays.fill(previousResult, -1);
    // Start recursion from position -1 (before the first square)
    return minCostMemoizationHelper(squareCost, -1, numSquares, previousResult);
  }

  // Recursive helper function using memoization.
  private int minCostMemoizationHelper(int[] squareCost, int position, int length, int[] previousResult)
  {
    // Use an offset for memoization since positions start at -1
    int memoIndex = position + 1;

    // If already computed, return the stored result
    if (previousResult[memoIndex] != -1)
    {
      return previousResult[memoIndex];
    }

    // Base Case, we have gotten to the end
    if (position == length - 1 || position == length - 2)
    {
      return squareCost[length];
    }

    int costStep = Integer.MAX_VALUE;
    int costHop = Integer.MAX_VALUE;

    // Option 1: Step to the next square (if it exists)
    if (position + 1 < length)
    {
      costStep = squareCost[position + 1]
          + minCostMemoizationHelper(squareCost, position + 1, length, previousResult);
    }

    // Option 2: Hop over one square (if it exists)
    if (position + 2 < length)
    {
      costHop = squareCost[position + 2]
          + minCostMemoizationHelper(squareCost, position + 2, length, previousResult);
    }

    int result = Math.min(costStep, costHop);
    // Store the computed result in the memoization array.
    previousResult[memoIndex] = result;
    return result;
  }

  public int minCostTabulation(int[] squareCost)
  {
    int length = squareCost.length;

    if (length == 0)
    {
      return 0;
    }
    if (length == 1)
    {
      return squareCost[0];
    }

    int[] squareTable = new int[length];

    squareTable[0] = squareCost[0];
    squareTable[1] = squareCost[1];

    for (int i = 2; i < length; i++)
    {
      squareTable[i] = squareCost[i] + Math.min(squareTable[i - 1], squareTable[i - 2]);
    }

    return Math.min(squareTable[length - 1], squareTable[length - 2]);
  }
}

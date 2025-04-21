/* Yousef Awad
 * Dr. Steinberg
 * COP3503 Spring 2025
 * Programming Assignment 2
 */

import java.util.ArrayList;

public class TreasureCoordinates
{
  /**
   * Returns all valid (x,y) coordinate pairs
   * from the string (digits).
   * Uses recursive backtracking.
   *
   * @param input - A string like "(123)" with digits.
   * @return A list of valid "(x, y)" pairs.
   */
  public ArrayList<String> determineCoordinates(String input)
  {
    // Removing parentheses from input
    String digits = input.substring(1, input.length() - 1);

    // output/where we put valid pairs
    ArrayList<String> result = new ArrayList<>();

    /*
     * Recursively splits for indices in [1 to length-1].
     * At each index, left = digits[0 to i-1],
     * right = digits[i to end].
     */
    backtrack(digits, 1, result);

    return result;
  }

  /*
   * Recursively attempts every split at a set splitIndex.
   * Combines valid forms of left and right
   * into (x, y).
   */
  private void backtrack(String input, int splitIndex, ArrayList<String> result)
  {
    // Stop if splitIndex goes beyond
    if (splitIndex >= input.length())
    {
      return;
    }

    // Split into left/right
    String leftDigits = input.substring(0, splitIndex);
    String rightDigits = input.substring(splitIndex);

    // Generate valid decimal forms
    ArrayList<String> leftForms = generateAllDecimals(leftDigits);
    ArrayList<String> rightForms = generateAllDecimals(rightDigits);

    // Combining each valid pair
    for (String left : leftForms)
    {
      for (String right : rightForms)
      {
        result.add("(" + left + ", " + right + ")");
      }
    }

    // Recurse to next split
    backtrack(input, splitIndex + 1, result);
  }

  /*
   * Returns all valid decimal forms of s:
   * 1) No decimal point
   * 2) One decimal point in each position
   */
  private ArrayList<String> generateAllDecimals(String input)
  {
    ArrayList<String> candidates = new ArrayList<>();
    candidates.add(input); // no decimal placed

    // Try putting a decimal in each gap
    for (int i = 1; i < input.length(); i++)
    {
      String left = input.substring(0, i);
      String right = input.substring(i);
      candidates.add(left + "." + right);
    }

    // Keep only valid forms
    ArrayList<String> validResults = new ArrayList<>();
    for (String string : candidates)
    {
      // for every string in the candidates
      // check if it's valid
      if (isValidDecimal(string))
      {
        // if valid add to validResults
        validResults.add(string);
      }
    }

    // all results added are valid so return them.
    return validResults;
  }

  /*
   * A valid decimal must satisfy the following:
   * - If it has no '.', then either it's "0" or has no leading '0'
   * - If it has a '.', go straight to after
   *     the decimal and get rid of trailing '0's
   */
  private boolean isValidDecimal(String candidate)
  {
    // finding where the decimal is
    int dotPos = candidate.indexOf('.');
    if (dotPos < 0)
    {
      // pure integer as the decimal does not exist.
      return isValidInteger(candidate);
    }
    else
    {
      // split around '.'
      String wholeNumberPart = candidate.substring(0, dotPos); // digits left of '.'
      String decimalPart = candidate.substring(dotPos + 1); // digits right of '.'

      // the decimal must exist
      if (decimalPart.isEmpty())
      {
        return false;
      }

      // the whole number must be valid
      if (!isValidInteger(wholeNumberPart))
      {
        return false;
      }

      // the decimal part must not end with '0'
      if (decimalPart.charAt(decimalPart.length() - 1) == '0')
      {
        return false;
      }

      // we have a valid decimal number!
      return true;
    }
  }

  /*
   * A valid integer is either:
   * - "0"
   * - or has no leading '0's
   */
  private boolean isValidInteger(String integer)
  {
    if (integer.isEmpty())
    {
      // there is no integer
      return false;
    }
    if (integer.equals("0"))
    {
      // the integer is a 0
      return true;
    }
    // the integer has no trailing '0's
    return (integer.charAt(0) != '0');
  }
}

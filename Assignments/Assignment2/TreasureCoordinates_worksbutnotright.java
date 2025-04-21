/* Yousef Awad
 * Dr. Steinberg
 * COP3503 Spring 2025
 * Programming Assignment 2
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TreasureCoordinates {
  // ------------------------------------------------------------
  // Required public method as described in the PDF.
  // Takes in a string of the form "(123)" and returns
  // an ArrayList of valid coordinates of the form "(x, y)".
  // ------------------------------------------------------------
  public ArrayList<String> determineCoordinates(String input) {
    // Strip away the outer parentheses
    // e.g. "(123)" -> "123"
    String digits = input.substring(1, input.length() - 1);

    ArrayList<String> results = new ArrayList<>();

    // Split digits into two parts: one for x, one for y
    // i goes from 1 to digits.length()-1 (so each part is non-empty)
    for (int i = 1; i < digits.length(); i++) {
      String left = digits.substring(0, i);
      String right = digits.substring(i);

      // Recursively generate all valid strings (with 0 or 1 decimal point) for left
      // and right
      ArrayList<String> leftParts = generateAllPossibleStrings(left);
      ArrayList<String> rightParts = generateAllPossibleStrings(right);

      // Combine each valid x with each valid y
      for (String lx : leftParts) {
        for (String ry : rightParts) {
          results.add("(" + lx + ", " + ry + ")");
        }
      }
    }

    System.out.println(results);
    return results;
  }

  // ------------------------------------------------------------
  // Helper method to generate all possible valid strings (with
  // zero or one decimal point) for a given digits string.
  // We use recursive backtracking to place or not place a
  // decimal point at each step. We'll collect all raw
  // candidates, then we'll filter them with isValid().
  // ------------------------------------------------------------
  private ArrayList<String> generateAllPossibleStrings(String digits) {
    Set<String> set = new HashSet<>(); // to avoid duplicates
    backtrack(digits, 0, false, new StringBuilder(), set);

    // Convert the set to an ArrayList
    return new ArrayList<>(set);
  }

  // ------------------------------------------------------------
  // Recursive function to build possible strings by deciding:
  // 1. Add digits[i] and continue
  // 2. Optionally insert one decimal point (if not used yet)
  //
  // At the end, we check validity to see if the formed string
  // is acceptable. We'll store it only if isValid() is true.
  // ------------------------------------------------------------
  private void backtrack(String digits, int index, boolean usedDecimal,
      StringBuilder current, Set<String> resultSet) {
    // Base case: we've processed all digits
    if (index == digits.length()) {
      // Check if the current built string is valid
      String candidate = current.toString();
      if (isValid(candidate)) {
        resultSet.add(candidate);
      }
      return;
    }

    // Choice 1: Add the current digit (without decimal)
    current.append(digits.charAt(index));
    backtrack(digits, index + 1, usedDecimal, current, resultSet);
    // remove the added digit for backtracking
    current.deleteCharAt(current.length() - 1);

    // Choice 2: If we haven't used a decimal yet, we can place one
    // BUT we only place a '.' if we are not at the very end, to ensure
    // that there's at least one digit after the decimal.
    if (!usedDecimal && index < digits.length() - 1) {
      current.append(digits.charAt(index));
      current.append('.'); // place the decimal
      backtrack(digits, index + 1, true, current, resultSet);
      // remove the added digit and decimal for backtracking
      current.deleteCharAt(current.length() - 1); // remove '.'
      current.deleteCharAt(current.length() - 1); // remove the digit
    }
  }

  // ------------------------------------------------------------
  // Checks if a string s is a valid representation of a number
  // under the assignment’s rules:
  // - If there's no decimal point, it's an integer that cannot
  // have leading zeros unless it is exactly "0".
  // - If there is a decimal point, then:
  // * The integer part cannot have leading zeros (unless it is "0").
  // * The fraction part cannot be empty.
  // * The fraction part cannot end with '0' (no trailing zeros).
  // ------------------------------------------------------------
  private boolean isValid(String s) {
    // Must not be empty at all (defensive check)
    if (s.length() == 0) {
      return false;
    }

    if (!s.contains(".")) {
      // It's an integer
      // Must not have leading zeros unless it is exactly "0"
      if (s.length() > 1 && s.charAt(0) == '0')
        return false;
      return true;
    } else {
      // Contains a decimal
      int dotIndex = s.indexOf('.');
      String intPart = s.substring(0, dotIndex);
      String fracPart = s.substring(dotIndex + 1);

      // Fraction part must not be empty
      if (fracPart.length() == 0) {
        return false;
      }

      // Fraction part cannot end with '0'
      if (fracPart.charAt(fracPart.length() - 1) == '0') {
        return false;
      }

      // Integer part must not have leading zeros unless exactly "0"
      if (intPart.length() > 1 && intPart.charAt(0) == '0') {
        return false;
      }

      return true;
    }
  }
}

/* Yousef Alaa Awad
   Dr. Steinberg
   COP3503 Spring 2025
   Programming Assignment 3
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class GreedyChildren
{
    int[] greedLevel; // for every child
    int[] sweetness; // for the candy
    int happyChildren; // number of happy children
    int angryChildren; // number of angry children

    public GreedyChildren(int candyAmount, int childrenAmount, String greedyFile, String sweetnessFile)
    {
        /*
          candyAmount is the amount of candy you will hand out 
          instead of eating it yourself.

          childrenAmount is the amount of children that will sadly disturb 
          you.

          greedyFile is just the file that stores the greedy values 
          of every child, rip.

          sweetnessFile is the file name of the sweetness value 
          of every candy you can hand out.
        */

        this.greedLevel = new int[childrenAmount];
        this.sweetness = new int[candyAmount];
        this.happyChildren = 0;
        this.angryChildren = 0;

        // reading the dataaaa
        this.read(greedyFile, sweetnessFile);
    }

    public GreedyChildren()
    {
        this.greedLevel = null;
        this.sweetness = null;
        this.happyChildren = -1;
        this.angryChildren = -1;
        System.out.println("Invalid Constructor");
    }

    public int[] read(String gfileName, String sfileName)
    {
        try
        {
            // reading greedy first
            Scanner gfileScanner = new Scanner(new File(gfileName));

            // getting greedLevels of children
            int i = 0;
            while (gfileScanner.hasNextInt())
            {
                this.greedLevel[i++] = gfileScanner.nextInt();
            }
            gfileScanner.close();

            Scanner sfileScanner = new Scanner(new File(sfileName));

            // getting greedLevels of children
            i = 0;
            while (sfileScanner.hasNextInt())
            {
                this.sweetness[i++] = sfileScanner.nextInt();
            }
            sfileScanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void greedyCandy()
    {
        // sort the greedLevel and sweetness arrays
        // greedy algorithm
        // if greedLevel[i] <= sweetness[j] then happyChildren++
        // else angryChildren++

        // sort the data first, lmao, this has a maximum 
        // runtime of xlogx, with x being array size.
        Arrays.sort(this.sweetness);
        Arrays.sort(this.greedLevel);

        // now we do the greedy Algo....
        int childrenNumber = 0;
        int candyNumber = 0;

        // while we have chidlren still bothering us...
        // this sorting is just O(m or n), with m always being larger meaning it is
        // O(m).
        while (childrenNumber < this.greedLevel.length && candyNumber < this.sweetness.length)
        {
            // check if the candy we can give them is good enuf
            if (this.sweetness[candyNumber] >= this.greedLevel[childrenNumber])
            {
                // we give out the candy.
                this.happyChildren += 1;
                childrenNumber += 1;
            }
            // and no matter what we lost a candy to give...
            candyNumber += 1;
        }
        this.angryChildren = greedLevel.length - this.happyChildren;
    }

    public void display()
    {
        // displaying the results of the greedy algorithm
        System.out.println("There are " + this.happyChildren + " happy children.");
        System.out.println("There are " + this.angryChildren + " angry children.");
    }
}

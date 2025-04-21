/* Yousef Awad
   Dr. Steinberg
   COP3503 Spring 2025
   Programming Assignment 1
*/

import java.util.Random; 

public class Game
{
  // declaring inherent attributes
  int[][] board = new int[8][8];
  char[] moves = {'d', 'r', 'b'};
  Random random;

  // constructor for the Game class
  public Game(Random randomGiven)
  {
    // giving the random attribute the random inputted into it.
    this.random = randomGiven;
  }

  // Methods are below:

  // selecting the user's move randomly.
  public char selectPlayerTwoMove()
  {
    int randomNumber = this.random.nextInt(3);
    return this.moves[randomNumber];
  }

  private int[] playerOneMove(int[] position, char playerTwoMove)
  {
    // check if we are at the right of the board.
    if (position[0] == 7)
    {
      // we are at the right-most side of the board,
      // therefore we can only move down.
      this.board[position[0]][position[1]] = 0;
      position[1] += 1; // updating y-position down one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }

    // check if we are at the bottom of the board.
    if (position[1] == 7)
    {
      // we are at the bottom of the board, therefore we 
      // can only move right.
      this.board[position[0]][position[1]] = 0;
      position[0] += 1; // updating x-position right one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }
    // if we are in a position to win, win.
    if (position[0] == 6 && position[1] == 6)
    {
      // winning the game!
      this.board[position[0]][position[1]] = 0;
      position[0] += 1; // updating x-position right one.
      position[1] += 1; // updating y-position down one.
      this.board[position[0]][position[1]] = 1;
      
      return position;
    }
    if (position[0] >= 5 && position[1] < 5)
    {
      // the optimal move is to move down so that player
      // 2 fixes it.
      this.board[position[0]][position[1]] = 0;
      position[1] += 1; // updating x-position right one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }

    if (position[0] < 5 && position[1] >= 5)
    {
      // the optimal move is to move down so that player
      // 2 fixes it.
      this.board[position[0]][position[1]] = 0;
      position[0] += 1; // updating x-position right one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }

    // now we check if we are at any position of 
    // 5 so that we can stop player 2 from winning.
    if (position[0] >= 5 || position[1] >= 5)
    {
      // checknig which position is closer to the goal.
      if (position[0] > position[1])
      {
        this.board[position[0]][position[1]] = 0;
        position[0] += 1; // updating x-position right one.
        this.board[position[0]][position[1]] = 1;

        return position;
      }

      this.board[position[0]][position[1]] = 0;
      position[1] += 1; // updating x-position right one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }


    // check what the last move of player 2 was, and
    // act accordingly.
    if (playerTwoMove == 'd') 
    {
      // player two moved down diagonally, therefore
      // player one will copy.
      this.board[position[0]][position[1]] = 0;
      position[0] += 1; // updating x-position right one.
      position[1] += 1; // updating y-position down one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }
    if (playerTwoMove == 'r') 
    {
      // player two moved right one, therefore
      // player one will move down.
      this.board[position[0]][position[1]] = 0;
      position[1] += 1; // updating y-position down one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }
    if (playerTwoMove == 'b') 
    {
      // player two moved down one, therefore
      // player one will move right one.
      this.board[position[0]][position[1]] = 0;
      position[0] += 1; // updating y-position right one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }

    return position;
  }

  private int[] playerTwoMove(int[] position, char playerTwoMove)
  {
    // check if we are at the right of the board.
    if (position[0] == 7)
    {
      // we are at the right-most side of the board,
      // therefore we can only move down.
      this.board[position[0]][position[1]] = 0;
      position[1] += 1; // updating y-position down one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }

    // check if we are at the bottom of the board.
    if (position[1] == 7)
    {
      // we are at the bottom of the board, therefore we 
      // can only move right.
      this.board[position[0]][position[1]] = 0;
      position[0] += 1; // updating x-position right one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }
    
    // check what the last move of player 2 was, and
    // act accordingly.
    if (playerTwoMove == 'd') 
    {
      // player two will move down diagonally.
      this.board[position[0]][position[1]] = 0;
      position[0] += 1; // updating x-position right one.
      position[1] += 1; // updating y-position down one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }
    if (playerTwoMove == 'r') 
    {
      // player two will move right one.
      this.board[position[0]][position[1]] = 0;
      position[0] += 1; // updating x-position down one.
      this.board[position[0]][position[1]] = 1;

      return position;
    }
    if (playerTwoMove == 'b') 
    {
      // player two will move down one.
      this.board[position[0]][position[1]] = 0;
      position[1] += 1; // updating y-position down one.
      this.board[position[0]][position[1]] = 1;
      
      return position;
    }

    return position;
  }

  // playing the game to see who wins.
  public int play() 
  {
    // storing the position of the knight of {x, y}
    int[] position = {0, 0};
    // starting the board properly with knight in top left of
    // board.
    this.board[position[0]][position[1]] = 1;

    char playerTwoMove = 'd';
    // 1 is player 1's turn, 2 is player 2's.
    int turn = 1;

    // starting the board of the game having the knight.
    while (true)
    {
      // doing player one's move
      position = playerOneMove(position, playerTwoMove);
      // selecting player two's move
      if (position[0] == 7 && position[1] == 7)
      {
        return 1;
      }
      playerTwoMove = this.selectPlayerTwoMove();
      // doing player two's move
      position = this.playerTwoMove(position, playerTwoMove);
      if (position[0] == 7 && position[1] == 7)
      {
        return 2;
      }
    }
  }
}

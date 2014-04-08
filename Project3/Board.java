import java.util.*;

public class Board
{ 
  public int dimension;
  public int hamming;
  public int manhattanDistance;
  public int [][] board;
  public Board(int [] [] blocks)
  {
    //construct a board from an N-by-N array of blocks
    //where blocks[i][j] = block in row i, column j

    //we know that the board is NxN so the board.length will yield N
    dimension = blocks.length;
    board = new int[dimension][dimension];
    
    //make board have same elements as blocks
    for(int i=0; i<dimension; i++)
    {
      System.arraycopy(blocks[i], 0, board[i], 0, blocks[i].length);
    }
  }
  
  public int dimension()
  {    
    //baord dimension N
    return dimension;
  }
  
  public int hamming()
  {
    //number of blocks out of place
    hamming = 0;
    int compare = 0;
    for (int i = 0; i < dimension; i++)
    {
      for (int j = 0; j < dimension; j++)
      {
        compare++;
        int value = board[i][j]; 
        if(i != 0)
          if(value != compare)
            hamming++;
      }
    }
    return hamming;
  }
  
  public int manhattan()
  {
    //sum of manhattan distance between blocks and goal
    manhattanDistance = 0;
    for (int x = 0; x < dimension; x++)
    {
      for (int y = 0; y < dimension; y++) 
      { 
        int value = board[x][y]; 
        if (value != 0) 
        { 
          int expectX = (value - 1) / dimension;
          int expectY = (value - 1) % dimension;
          int dx = x - expectX;
          int dy = y - expectY; 
          manhattanDistance += Math.abs(dx) + Math.abs(dy); 
        } 
      }
    }
    return manhattanDistance;
  }
  
  public boolean isGoal()
  {
    //is this board the goal board?
    boolean goal = true;
    
    //check if the element in the board is in the right place and if the last element
    //equals 0
    for(int i=0; i< dimension; i++)
    {
      for(int j =0; j<dimension; j++)
      {
        //for 3x3, i*j = 2*2. 4x4, 3*3, etc...
        if(i*j == Math.pow(dimension-1, 2))
        {
          if(!(board[i][j] == 0))
            goal = false;
        }
        else if(board[i][j] != (i*dimension) + (j+1))
        {
          goal = false;
          break;
        }
      }
    }
    
    return goal;
  }
  
  public boolean isSolvable()
  {
    //is the board solvable
    boolean canSolve = true;
    
    //take our 2d array and turn it into a list of numbers
    int[] elements = new int[(dimension * dimension) - 1];
    int elementNum = 0;
    int zeroRow = 0;
    
    for (int i = 0; i < dimension; i++) 
    {
      for (int j = 0; j < dimension; j++) 
      {
        if (board[i][j] != 0) 
        {
          elements[elementNum] = board[i][j];
          elementNum++;
        }
        else
          zeroRow = i;
      }
    }
    
    //odd board size: the parity of the number of inversions of the N^2 - 1 blocks
    //even board size: the parity of the number of inversions of the N^2 - 1 blocks plus the row in which the blank square plus one.
    int length = elements.length;
    int inversions = 0;
    //traverse the single array checking for inversions  
    //An inversion is when a tile precedes another tile with a lower number on it
    for (int x = 0; x < length; x++) 
    {
      for (int y = x; y < length; y++)
      {
        if (elements[x] > elements[y])
          inversions++;
      }
    }
    //if length is odd
    if(length % 2 != 0)
    {
      if(inversions % 2 == 0)
      {
        //nothing canSolve is already true
      }
      else
        canSolve = false;
    }
    //if length is even
    else
    {
      //AND the 0 appears on an even row counting from the bottom
      if(zeroRow % 2 == 0)
      {
        //then the number of inversions in a solvable situation is odd
        if(inversions % 2 != 0)
        {
          //can solve
        }
        else
          canSolve = false;
      }
      //AND the 0 appears on an odd row counting from the bottom
      else
      {
        //then the number of inversions in a solvable situation is even
        if(inversions % 2 == 0)
        {
          //can solve
        }
        else
          canSolve = false;
      }  
    }
    return canSolve;
  }
  
  public boolean equals(Object y)
  {
    //does this board = y?
    if (this == y)  
      return true;
    if (y == null) 
      return false;
    if (y.getClass() != this.getClass()) 
      return false;
    Board that = (Board)y;
    if (this.board.length != that.board.length) 
      return false;
    for (int i = 0; i < dimension; ++i) {
      if (this.board[i].length != that.board[i].length) 
        return false;
      for (int j = 0; j < dimension; ++j) 
      {
        if (this.board[i][j] != that.board[i][j])
          return false; 
      }
    }
    return true;
  }

  public Iterable<Board> neighbors()
  {
    //place all neighboring boards into your iterable Queue
    Queue<Board> neighBoards = new Queue<Board>();
      
    int row=-1, column=-1;
        
    //search our board until we find 0
    for(int i = 0; i<dimension; i++)
    {
      for(int j=0; j<dimension; j++)
      {
        if(board[i][j] ==0)
        {
          row = i;
          column = j;
          //we need to break out of both loops
        }
      }
    }
    
    //swap with adjacent columns
    for(int j=-1; j<2; j+=2)
    {
    int ooby = column + j;
      //this will catch outofbounds exception
      if(ooby >= dimension || ooby < 0)
        continue;
      
      int [][] tempBoard = new int [dimension][dimension];
      for(int k=0; k<dimension; k++)
      {
        System.arraycopy(board[k], 0, tempBoard[k], 0, board[k].length);
      }

      //swap 0 with adjacent column
      tempBoard[row][column] = tempBoard[row][column + j];
      tempBoard[row][column+j] = 0;
      Board newBoard = new Board(tempBoard);
      neighBoards.enqueue(newBoard);
    }
    
    //swap with adjacent rows
    for(int i=-1; i<2; i+=2)
    {
      int oobx = row + i;
      
      if(oobx >= dimension || oobx < 0)
        continue;
      
      int [][]tempBoard = new int[dimension][dimension];
      for(int l = 0; l<dimension; l++)
      {
        System.arraycopy(board[l], 0, tempBoard[l], 0, board[l].length);
      }
      
      tempBoard[row][column] = tempBoard[row+i][column];
      tempBoard[row+i][column]=0;
      Board newBoard = new Board(tempBoard);
      neighBoards.enqueue(newBoard);
    }
           
    return neighBoards;
  }
  
  public String toString()
  {
    /*string representation of the board
     * N
     * 1 2 3 
     * 4 5 6
     * 7 8 0
     */
    StringBuilder string = new StringBuilder();
    string.append(dimension + "\n");
    for(int i=0; i< dimension; i++)
    {
      for(int j=0; j<dimension; j++)
      {
        string.append(String.format("%2d ", board[i][j]));
      }
      string.append("\n");
    }
    return string.toString();
  }
}
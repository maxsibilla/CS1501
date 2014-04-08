import java.util.*;

public class Solver
{
  /*First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into an PQ.
   * Then, delete from the PQ the search node with the minimum priority, and insert into the PQ of all neighboring search nodes (those that can be reached in one move from the removed search node)
   * Repeat this procedure until the search node that has been removed corresponds to a goal board
   */
  public boolean isSolve = false;
  public int minMoves = 0;
  public int totMoves = 0;
  public Stack<Board> finalSequence;
  public MinPQ<SearchNode> minPQ;
  public Solver(Board initial)
  {
    //find a solution to the initial board 
    finalSequence = new Stack<Board>();
    minPQ = new MinPQ<SearchNode>();
    SearchNode tempNode = new SearchNode(initial, totMoves, null);
    minPQ.insert(tempNode);
    Queue<Board> neighBoard = new Queue<Board>();
    Board prevBoard = initial;
    SearchNode searchNode;
    int breaker = 0;
    
    while(!minPQ.isEmpty())
    {
      if(breaker == 1)
      {
        break;
      }
      //remove head of minPQ, which is the least element with respect to the specified ordering
      searchNode = minPQ.delMin();
      neighBoard = (Queue<Board>)searchNode.board.neighbors();
      
      //if our searchNode has a previous node, then update our prevBoard to that node's previous
      if(searchNode.previous != null)
      {
        prevBoard = searchNode.previous.board;
      }
      //To reduce unnecessary exploration of useless search nodes, when considering the neighbors of a search node, 
      //don't add a neighbor if its board is the same as the board of the previous search node.
      for(Board neighbors : neighBoard) 
      {
        if (!prevBoard.equals((Board)neighbors)) 
        {
          totMoves = searchNode.moves + 1;
          SearchNode newNode = new SearchNode(neighbors, totMoves, searchNode);
          minPQ.insert(newNode);
        }
      }
      
      if(searchNode.board.isGoal())
      {
        //isSolve is set to true
        isSolve = true;

        while (searchNode.previous != null) 
        {
          finalSequence.push(searchNode.board);
          searchNode = searchNode.previous;
          minMoves++;
        }
        finalSequence.push(initial);

        breaker = 1;
        break;
      }
    }
  }
  
  public boolean isSolvable()
  {
    //is the initial board solvable?
    return isSolve;
  }
  
  public int moves()
  {
    //min number of moves to solve initial board
    return minMoves;
  }
  
  public Iterable<Board> solution()
  {
    //sequence of board moves in a shortest sequence
    if(minMoves != -1)
      return finalSequence;
    else
      return null;
  }
  
  public static void main(String [] args)
  {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    
    for (int i = 0; i < N; i++)
    {
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    }
    Board initial = new Board(blocks);      // solve the puzzle
    Solver solver = new Solver(initial);    // print solution to standard output
    
    if (!initial.isSolvable())
      System.out.println("No solution possible");
    else 
    {
      System.out.println("Minimum number of moves = " + solver.moves());
      
      for (Board board : solver.solution())
         System.out.println(board);
    }
  }  
}
public class Node
{
  //search node: a board, the number of moves made to reach the board, and the previous search node
  public Board board;
  public int moves;
  public Node previous;
  
  public Node(Board board, int moves, Node previous)
  {
    this.board = board;
    this.moves = moves;
    this.previous = previous;
  }
  
  
}
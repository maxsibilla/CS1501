public class SearchNode implements Comparable <SearchNode>
{
  //search node: a board, the number of moves made to reach the board, and the previous search node
  public Board board;
  public int moves;
  public SearchNode previous;
  
  public SearchNode(Board board, int moves, SearchNode previous)
  {
    this.board = board;
    this.moves = moves;
    this.previous = previous;
  }
  public int compareTo(SearchNode comp)
  {
    if (board.manhattan() + moves  > comp.board.manhattan() + comp.moves) 
      return 1;
    else if (board.manhattan() + moves < comp.board.manhattan() + comp.moves) 
      return -1;
    else  
      return 0;
  }
  
  //use this when testing hamming, default will be manhattan
  /*
  public int compareTo(SearchNode comp)
  {
    if (board.hamming() + moves  > comp.board.hamming() + comp.moves) 
      return 1;
    else if (board.hamming() + moves < comp.board.hamming() + comp.moves) 
      return -1;
    else  
      return 0;
  }*/
}
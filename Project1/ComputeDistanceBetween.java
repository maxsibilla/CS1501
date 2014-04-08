//Project One
import java.io.File;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class ComputeDistanceBetween
{
  public static void main(String[]args) throws IOException
  {
    Stopwatch timer = new Stopwatch();
    int invalid = 0;
    //check to see how many arguments were passed
    if(args.length == 0)
    {
      System.out.println("Usage: java ComputeDistanceBetween filename_1 filename_2");
      System.exit(0);
    }
    if(args.length == 1)
    {
      System.out.println("Usage: java ComputeDistanceBetween filename_1 filename_2");
      System.exit(0);
    }
    
    Map<String, Double> fileWords1 = new TreeMap<String, Double>();
    Map<String, Double> fileWords2 = new TreeMap<String, Double>();
    
    List<String> freqList1 = new ArrayList<String>();
    List<String> freqList2 = new ArrayList<String>();
         
    //create new Set - collect whihc contains no duplicate elements - for 3)
    Set<String> distinctWord1 = new HashSet<String>();
    Set<String> distinctWord2 = new HashSet<String>();
    
    //check if file 1 exists
    File file1 = new File(args[0]);
    if(file1.exists())
    {
      //1) count number of lines in file
      int file1Lines = 0;
      
      //2-3) count number of words - distinct words
      //create a second bufferedreader
      BufferedReader reader1 = new BufferedReader(new FileReader(file1));
      int file1Words = 0;
      String lines = reader1.readLine();     //return the read line as a string
      
      //as long as the line isn't empty, split with spaces, apostrophes, hyphens, etc... into an array
      while(lines != null)
      {
        file1Lines++; //for 1
        
        String [] words = lines.split("\\P{Alnum}+");
        //System.out.println(Arrays.toString(words));
        
        //from that array, increment file1Words for each element 
        for(String stdword : words)
        {
          //case is not significant
          String word = stdword.toLowerCase();
          if(word.equals(""))
          {
            //nothing
          }
          else
          {
            Double freq1 = fileWords1.get(word);
            fileWords1.put(word, (freq1 == null) ? 1 : freq1 + 1);
          
            file1Words++; //for 2
            distinctWord1.add(word);  //for 3
            freqList1.add(word);  //for 4
          }
        }
        //advance line
        lines = reader1.readLine();
      }
      System.out.println("File " + args[0] + ": " + file1Lines + " lines, " + file1Words + " words, " + distinctWord1.size() + " distinct words");
    }
    else
    {
      invalid = 1;
      System.out.println(args[0] + " is not found!");
      System.out.println("File " + args[0] + ": 0 lines, 0 words, 0 distinct words");
    }
    
    //check if file 2 exists
    File file2 = new File(args[1]);
    if(file2.exists())
    {
      //1) count number of lines in file
      int file2Lines = 0;
      
      //2-3) count number of words - distinct words
      //create a second bufferedreader
      BufferedReader reader2 = new BufferedReader(new FileReader(file2));
      int file2Words = 0;
      String lines = reader2.readLine();     //return the read line as a string
      
      //as long as the line isn't empty, split with spaces, apostrophes, hyphens, etc... into an array
      while(lines != null)
      {
        file2Lines++; //for 1
        String [] words = lines.split("\\P{Alnum}+");
        //from that array, increment file1Words for each element 
        for(String stdword : words)
        {
          //case is not significant
          String word = stdword.toLowerCase();
          if(word.equals(""))
          {
            //nothing
          }
          else
          {
            Double freq2 = fileWords2.get(word);
            fileWords2.put(word, (freq2 == null) ? 1 : freq2 + 1);
          
            file2Words++; //for 2
            distinctWord2.add(word);  //for 3
            freqList2.add(word);  //for 4
          }
        }
        //advance line
        lines = reader2.readLine();
      }
      reader2.close();
      System.out.println("File " + args[1] + ": " + file2Lines + " lines, " + file2Words + " words, " + distinctWord2.size() + " distinct words");
    }
    else
    {
      invalid = 1;
      System.out.println(args[1] + " is not found!");
      System.out.println("File " + args[1] + ": 0 lines, 0 words, 0 distinct words");
    }
    
    //sort our lists
    Collections.sort(freqList1);
    Collections.sort(freqList2);
    
    /*get frequency of each word: norm(x) and norm(y) = the freq of each word of the file squared
     * d(x,y) = arccos(inner_product(x,y) / (norm(x)*norm(y)))
     */
    double normx = 0;
    double normy = 0;
    
    for(String vector1 : distinctWord1)
    {
      normx += Math.pow(fileWords1.get(vector1), 2);
    }
    for(String vector2 : distinctWord2)
    {
      normy += Math.pow(fileWords2.get(vector2), 2);
    }
    normx = Math.sqrt(normx);
    normy = Math.sqrt(normy);
    
    //inner_product(x,y)
    double innerproduct = 0;
    //use the smaller set to compare
    if(distinctWord1.size() > distinctWord2.size())
    {
      for(String compareWord : distinctWord2)
      {
        if(fileWords1.containsKey(compareWord))
          innerproduct += (fileWords1.get(compareWord) * fileWords2.get(compareWord));
      }
    }
    else
    {
      for(String compareWord : distinctWord1)
      {
        if(fileWords2.containsKey(compareWord))
          innerproduct += (fileWords1.get(compareWord) * fileWords2.get(compareWord));
      }
    }
    double result = (innerproduct/(normx*normy));
    result = (double)Math.round(result*1000000)/1000000;
    if(invalid == 0)
      System.out.format("The distance between the two documents is: %.6f radians %n", Math.acos(result));
    else
      System.out.println("The distance between the two documents is: NaN radians");      
    StdOut.println("Time elapsed = " + timer.elapsedTime() + " seconds ");
  }
}
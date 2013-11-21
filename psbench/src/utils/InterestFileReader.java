package utils;
/**
 * Author: Chen
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

/**
 * @author Chen
 * 
 */
public class InterestFileReader
{
  private BufferedReader m_br = null;
  private Vector<String> m_subscriptions;
  private HashMap<Integer, Integer> m_topicIdMap; // Key is the topicId, Value is the unique index
  private boolean[][] intMatrix;

  public InterestFileReader(String fileName)
  {
    this.m_topicIdMap = new HashMap<Integer, Integer>();
    init(fileName);
    this.intMatrix = null;
  }

  private void openFile(String sFile)
  {
    try
    {
      FileReader fr = new FileReader(sFile);
      m_br = new BufferedReader(fr);
    }
    catch (IOException e)
    {
      // catch possible io errors from readLine()
      System.out.println("IOException error");
      e.printStackTrace();
    }
  }

  public Object getTopics(int nodeIndex)
  {
    Vector<Integer> topicMap = new Vector<Integer>();
    String subscription = m_subscriptions.get(nodeIndex);
    // First token is the subscriber id so we ignore it
    String[] tokens = subscription.split(",");
    for(int i = 0 ; i < tokens.length ; ++i)
    {
      topicMap.add(Integer.parseInt(tokens[i]));
    }
    return topicMap;
  }

  public HashMap<Integer, Integer> getUnsortedPopularityList() {
	  	boolean[][] intMatrix = getIntMatrix();
			
		HashMap<Integer, Integer> popularityList = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < intMatrix.length; i++){
			for(int j = 0; j < intMatrix[i].length; j++){
	
				if(!popularityList.containsKey(j))
					popularityList.put(j, 0);
				
				if(intMatrix[i][j]){
					int value = popularityList.get(j) + 1;
					popularityList.put(j, value);
				}
			}
		}
	
		return popularityList;
  }
  
  public TreeMap<Integer, Integer> getPopularityList() {
	  HashMap<Integer, Integer> popularityList = this.getUnsortedPopularityList();
	  ValueComparator comp = new ValueComparator(popularityList);
	  TreeMap<Integer, Integer> sortedList = new TreeMap<Integer, Integer>(comp);
	  
	  sortedList.putAll(popularityList);
	  
	  return sortedList;
  }
  
  public boolean[][] getIntMatrix() {
	  int numNodes = m_subscriptions.size();  
	  return getIntMatrix(numNodes);
  }

  
  public boolean[][] getIntMatrix( int numNodes ) {
	  if(this.intMatrix != null)
		  return this.intMatrix;
	  
		// int numNodes = subscriptions.size();  
	    int uniqueid = 0;
	    
	    for(int v = 0; v < numNodes; v++)
	    {
	      String subscription = m_subscriptions.get(v);
	      String[] tokens = subscription.split(",");
	      for(int j = 0 ; j < tokens.length ; ++j)
	      {
	        int topicName = Integer.parseInt(tokens[j]);
	        if(m_topicIdMap.containsKey(topicName) == false) {
	          m_topicIdMap.put(topicName, uniqueid++);
	        }
//	        int topicIndex = m_topicIdMap.get(topicName);
	      }
	    }
	    
	    int numTopics = uniqueid;
	       
	    boolean[][] intMatrix = new boolean[numNodes][numTopics];

	    for(int v = 0 ; v < numNodes ; ++v)
	    {
	      String subscription = m_subscriptions.get(v);
	      String[] tokens = subscription.split(",");
	      for(int j = 0 ; j < tokens.length ; ++j)
	      {
	        int topicName = Integer.parseInt(tokens[j]);
	        int topicIndex = m_topicIdMap.get(topicName);
	        intMatrix[v][topicIndex] = true;
	      }
	    }
	    
	    //System.out.println("numNodes: " + numNodes + "\t" + "numTopics: " + numTopics);
	    this.intMatrix = intMatrix;
	    
	    return intMatrix;
  } 
  
  public boolean [][] getJoinIntMatrix ( int numNodes, int numTopics ) {
		int numFileEntries = m_subscriptions.size();  
	    int uniqueid = 0;
	    
	    for( int v = numNodes ; v < numFileEntries ; v++ )
	    {
	      String subscription = m_subscriptions.get(v);
	      String[] tokens = subscription.split(",");
	      for(int j = 0 ; j < tokens.length ; ++j)
	      {
	        int topicName = Integer.parseInt(tokens[j]);
	        if(m_topicIdMap.containsKey(topicName) == false)
	        {
	          m_topicIdMap.put(topicName, uniqueid++);
	        }
	      }
	    }

	    int numJoinNodes = numFileEntries - numNodes;
	    
	    boolean[][] intMatrix = new boolean[numJoinNodes][numTopics];

	    for(int i = 0 ; i < numJoinNodes; i++)
	    {
	    	int v = numNodes + i;
	    	String subscription = m_subscriptions.get(v);
	    	String[] tokens = subscription.split(",");
	    	for(int j = 0 ; j < tokens.length ; ++j){
	    		int topicName = Integer.parseInt(tokens[j]);
	    		int topicIndex = m_topicIdMap.get(topicName);
	    		intMatrix[i][topicIndex] = true;
	    	}
	    }
	    
	    System.out.println("numJoinNodes: " + numJoinNodes + "\t" + "numTopics: " + numTopics);
	    
//	    for ( int v = 0; v < numJoinNodes; v++ ) {
//	    	for ( int t = 0; t < numTopics; t++ ) {
//	    		if ( intMatrix[v][t] )
//	    			System.out.print( "1, ");
//	    		else 
//	    			System.out.print( "0, ");
//	    	}
//	    	System.out.println();
//	    }
	    
	    return intMatrix;
  }
  
  /*
   * 
   * numTopics doesn't make sense here
   */
  private void init(String fileName)
  {
    if (m_br==null)
      openFile(fileName);
    // Do it only once just in case we already initialized it then just return
    if (m_subscriptions!=null)
      return;
    
    String line;
    m_subscriptions = new Vector<String>();
    try
    {
      while ((line = m_br.readLine())!=null)
      {
        m_subscriptions.add(line);
      }
      m_br.close();
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}

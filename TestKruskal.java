import org.jgrapht.graph.*;      // jgrapht package v1.1.0  
import org.jgrapht.traverse.*;   // CLASSPATH jgrapht-1.1.0\lib\jgrapht-core-1.1.0.jar

import java.util.*;

public class TestKruskal{
   public static void main(String[] args){
      SimpleWeightedGraph<String, DefaultWeightedEdge> myGraph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);  // create graph
      // Example from Sample Exam 1
      myGraph.addVertex("A");    // add vertices
      myGraph.addVertex("B");
      myGraph.addVertex("C");
      myGraph.addVertex("D");
      myGraph.addVertex("E");
      myGraph.addVertex("F");
      myGraph.addVertex("G");
      DefaultWeightedEdge AB = myGraph.addEdge("A", "B");   // create edge e1 on vertices "A" and "B"
      myGraph.setEdgeWeight(AB, 15);      // set weight
      DefaultWeightedEdge AC = myGraph.addEdge("A", "C");   
      myGraph.setEdgeWeight(AC, 19);  
      DefaultWeightedEdge AG = myGraph.addEdge("A", "G");   
      myGraph.setEdgeWeight(AG, 11);   
      DefaultWeightedEdge BC = myGraph.addEdge("B", "C");   
      myGraph.setEdgeWeight(BC, 25);
      DefaultWeightedEdge BD = myGraph.addEdge("B", "D");   
      myGraph.setEdgeWeight(BD, 18);
      DefaultWeightedEdge BE = myGraph.addEdge("B", "E");   
      myGraph.setEdgeWeight(BE, 3);
      DefaultWeightedEdge BF = myGraph.addEdge("B", "F");   
      myGraph.setEdgeWeight(BF, 15);
      DefaultWeightedEdge BG = myGraph.addEdge("B", "G");   
      myGraph.setEdgeWeight(BG, 7);
      DefaultWeightedEdge CD = myGraph.addEdge("C", "D");   
      myGraph.setEdgeWeight(CD, 27);
      DefaultWeightedEdge DE = myGraph.addEdge("D", "E");   
      myGraph.setEdgeWeight(DE, 10);
      DefaultWeightedEdge DF = myGraph.addEdge("D", "F");   
      myGraph.setEdgeWeight(DF, 9);
      DefaultWeightedEdge DG = myGraph.addEdge("D", "G");   
      myGraph.setEdgeWeight(DG, 10);
      DefaultWeightedEdge EF = myGraph.addEdge("E", "F");   
      myGraph.setEdgeWeight(EF, 8);
      DefaultWeightedEdge EG = myGraph.addEdge("E", "G");   
      myGraph.setEdgeWeight(EG, 5);
      DefaultWeightedEdge FG = myGraph.addEdge("F", "G");   
      myGraph.setEdgeWeight(FG, 20);
      
      Set<DefaultWeightedEdge> myEdgeSet = myGraph.edgeSet();  // create a Set of edges; just for edge size   
      DefaultWeightedEdge[] myEdgeSetAsArray = myEdgeSet.toArray(new DefaultWeightedEdge[myEdgeSet.size() ] ); // store Set myEdgeSet as an array with type DefaultWeightedEdge
      System.out.println("Original Edge List: ");
      for(int i = 0; i < myEdgeSetAsArray.length; i++){   // verify array of edge set
         System.out.print(myEdgeSetAsArray[i] + " ");
      }
      System.out.println();
      DefaultWeightedEdge[] sortedEdgeSetArray = SortArray(myEdgeSetAsArray, myGraph);    // store sorted edge set array
      System.out.println("Sorted Edge List: ");
      for(int i = 0; i < sortedEdgeSetArray.length; i++){   // verify array of sorted edge set
         System.out.print(sortedEdgeSetArray[i] + " ");
      }
      System.out.println();
      Set<String> vertexSet = myGraph.vertexSet();      // create vertex set for size only
      int size = vertexSet.size();     // size of vertex set (param)
      SimpleWeightedGraph<String, DefaultWeightedEdge> mst = Kruskal(sortedEdgeSetArray, myGraph, size);    // mst = minimal spanning tree graph using Krusal's algorithm
      Set<DefaultWeightedEdge> mstEdgeSet = mst.edgeSet();        // create set from mst graph
      DefaultWeightedEdge[] mstAsArray = mstEdgeSet.toArray(new DefaultWeightedEdge[mstEdgeSet.size() ] );     // create array from set
      System.out.println("Kruskal Minimum Spanning Tree: ");
      for(int i = 0; i < mstAsArray.length; i++)   // display edge set of Kruskal mst
         System.out.print(mstAsArray[i] + " ");
   }
   public static DefaultWeightedEdge[] SortArray(DefaultWeightedEdge[] a, SimpleWeightedGraph<String, DefaultWeightedEdge> g){  // sorts the edge set array (simple selection sort)
      for(int i = 0; i < a.length - 1; i++){    // selection sort algorithm
         int position = i;
         DefaultWeightedEdge temp = a[i];
         for(int j = i + 1; j < a.length; j++){
            if(g.getEdgeWeight(a[j] ) < g.getEdgeWeight(a[i] ) ){     // getWeight from graph, DefaultEdgeWeight methods are protected (method param must take graph)
               position = j;
               temp = a[i];
               a[i] = a[position];
               a[position] = temp;
            }
         }
      }
      return a;
   }
   //Kruskal method: return graph with original Vertices, only mst Edges      @param: a = sorted edge array, g = myGraph, size = size of vertices set
   public static SimpleWeightedGraph<String, DefaultWeightedEdge> Kruskal(DefaultWeightedEdge[] a, SimpleWeightedGraph<String, DefaultWeightedEdge> g, int size){
      for(int i = 0; i < a.length; i++){     // clear all edges from graph
         g.removeEdge(a[i] );
      }
      String[] visited = new String[size];      // array of visited vertices
      for(int i = 0; i < visited.length; i++)   // fill visited array with dummy Strings, null causes errors in comparison
         visited[i] = "0u0";
      int vindex = 0;   // visited index counter
      for(int i = 0; i < a.length; i++){
         int scount = 0;   // source counter
         int tcount = 0;   // target counter 
         for(int j = 0; j < visited.length; j++){       
            if(visited[j].equals(g.getEdgeSource(a[i] ) ) )    // IF edge source vertex = any visited vertex, ++
               scount++;
            if(visited[j].equals(g.getEdgeTarget(a[i] ) ) )    // IF edge target vertex = any visited vertex, ++
               tcount++;
         }
         if(scount == 1 && tcount == 1){}    // if there is a cycle, do NOT add edge (vertex's source AND target are in visited[] )
         else{       // ELSE add edge to graph; add vertex to visited[] ... (source and/or target vertex)
            g.addEdge(g.getEdgeSource(a[i] ), g.getEdgeTarget(a[i] ), a[i] );       // add edge
            int s2count = 0;
            int t2count = 0;
            for(int k = 0; k < visited.length; k++){
               if(visited[k].equals(g.getEdgeSource(a[i] ) ) )    // ++ if vertex already in visited[]
                  s2count++;
               if(visited[k].equals(g.getEdgeTarget(a[i] ) ) )    // ++ if vertex already in visited[]
                  t2count++;
            }
            if(s2count == 0){
               visited[vindex] = g.getEdgeSource(a[i] );    // if vertex not in visited[], add it
               vindex++;
            }
            if(t2count == 0){
               visited[vindex] = g.getEdgeTarget(a[i] );    // if vertex not in visited[], add it
               vindex++;
            }
         }
      }
      return g;      // return a graph with only MST edges using Kruskal
   }
}
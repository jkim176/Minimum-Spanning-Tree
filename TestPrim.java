import org.jgrapht.graph.*;      // jgrapht package v1.1.0  
import org.jgrapht.traverse.*;   // CLASSPATH jgrapht-1.1.0\lib\jgrapht-core-1.1.0.jar

import java.util.*;

public class TestPrim{
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
      DefaultWeightedEdge AB = myGraph.addEdge("A", "B");   // create edge AB on vertices "A" and "B"
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
      Set<String> vertexSet = myGraph.vertexSet();      // create vertex set
      SimpleWeightedGraph<String, DefaultWeightedEdge> mst = Prim(myGraph, vertexSet, "A");
      Set<DefaultWeightedEdge> eSet = mst.edgeSet();
      DefaultWeightedEdge[] edgeArray = eSet.toArray(new DefaultWeightedEdge[eSet.size() ] );
      for(int i = 0; i < edgeArray.length; i++){
         System.out.print(edgeArray[i] + " ");
      }
   }
   //Method: Prim          @param: graph, vertex set, root vertex
   public static SimpleWeightedGraph<String, DefaultWeightedEdge> Prim(SimpleWeightedGraph<String, DefaultWeightedEdge> g, Set<String> vSet, String root){
      // create RESULT graph with vertices, no edges ... add mst edges ... return
      SimpleWeightedGraph<String, DefaultWeightedEdge> resultGraph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
      for(String v : vSet){
         resultGraph.addVertex(v);     // add each vertex to RESULT graph ... edges will be added via Prim's algorithm
      }
      ArrayList<String> visited = new ArrayList<String>();
      ArrayList<DefaultWeightedEdge> groupEdges = new ArrayList<DefaultWeightedEdge>();
      LinkedList<String> q = new LinkedList<String>();      // Create Queue, q
      q.add(root);            // add root to queue
      visited.add(root);      // add root to visited
      while(visited.size() < vSet.size() ){        // WHILE all vertices have not been visited
         while(!q.isEmpty() ){                           // WHILE queue is not empty
            String current = q.poll();       //dequeue, head
            for(DefaultWeightedEdge edge : g.edgesOf(current) ){     // set - edges of dequeue'd vertex ... to add to groupEdges
               if(!groupEdges.contains(edge) )   // IF edge is not already in groupEdges, add edge
                  groupEdges.add(edge);
            }////////////
         }     // END WHILE
         DefaultWeightedEdge[] sorted = sortArray(groupEdges.toArray(new DefaultWeightedEdge[groupEdges.size() ] ), g);  // call sortArray; return sorted array on group edges
         int savedIndex = 0;     // save index
         for(int i = 0; i < sorted.length; i++){      // add MIN edge of groupEdges to RESULT, IF NOT a cycle
            if(visited.contains(g.getEdgeSource(sorted[i]) ) && visited.contains(g.getEdgeTarget(sorted[i]) ) ){}     // IF edge creates a cycle, do not add edge; BOTH target + source are in visited
            else{
               if(!resultGraph.containsEdge(sorted[i] ) ){     // IF MIN edge is NOT in RESULT
                  resultGraph.addEdge(g.getEdgeSource(sorted[i] ), g.getEdgeTarget(sorted[i] ), sorted[i] );    // ELSE add the min edge (that does not create cycle) to RESULT graph
                  savedIndex = i;      // save index
                  break;      // if edge is added, exit loop
               }
            }                              
         }      
         // add MIN edge's source OR target vertex to visited; add to queue
         if(!visited.contains(g.getEdgeSource(sorted[savedIndex] ) ) ){      // if vertex-source is not in visited, add + add to queue
            visited.add(g.getEdgeSource(sorted[savedIndex] ) );
            q.add(g.getEdgeSource(sorted[savedIndex] ) );
         }
         if(!visited.contains(g.getEdgeTarget(sorted[savedIndex] ) ) ){      // if vertex-target is not in visited, add + add to queue
            visited.add(g.getEdgeTarget(sorted[savedIndex] ) );
            q.add(g.getEdgeTarget(sorted[savedIndex] ) );
         }
      }     // END WHILE
      return resultGraph;
   }
   // Method: sortArray
   public static DefaultWeightedEdge[] sortArray(DefaultWeightedEdge[] a, SimpleWeightedGraph<String, DefaultWeightedEdge> g){  // return sorted array; sorts the edge set array (simple selection sort)
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
      return a;      // return sorted edge set array; min to max
   }
}
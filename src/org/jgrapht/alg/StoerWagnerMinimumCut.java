/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/* ----------------
 * StoerWagnerMinimumCut.java
 * ----------------
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * Original Author:  Robby McKilliam
 * Contributor(s):   -
 *
 * $Id: StoerWagnerMinimumCut.java $
 *
 * Changes
 * -------
 *
 */
package org.jgrapht.alg;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;
import org.jgrapht.util.MapVertices;

/**
 * Implements the Stoer and Wagner minimum cut algorithm.  Runs in O(|V||E|  + |V|log|V|) time.
 * 
 * M. Stoer and F. Wagner, "A Simple Min-Cut Algorithm", Journal of the ACM, 
 * volume 44, number 4. pp 585-591, 1997.
 * 
 * @author Robby McKilliam
 */
public class StoerWagnerMinimumCut<V, E> {
    
    final WeightedGraph<Set<V>, E> G;
    
    /**
     * Will compute the minimum cut in graph.
     * Second argument is the edge class, to get around java generic behaviour.
     */
    public StoerWagnerMinimumCut(WeightedGraph<V, E> graph, Class<E> edgeclass){
        
        G = new SimpleWeightedGraph<Set<V>, E>(edgeclass);
        MapVertices<V,E,Set<V>> vamp = new MapVertices<V,E,Set<V>>(graph, G) {
            @Override
            public Set<V> function(V v) { 
                Set<V> list = new HashSet<V>();
                list.add(v);
                return list;
            }
        };
        
        //arbitrary vertex used to seed the algorithm.
        Set<V> a = G.vertexSet().iterator().next();
        
        //System.out.println(G);
        
    }
    
    
    protected void minimumCutPhase(Set<V> a){
        //construct Fibonacci heap with vertices connected to a
        FibonacciHeap<Set<V>> heap = new FibonacciHeap<Set<V>>();
        for(Set<V> v : G.vertexSet()) {
            if( v != a ) 
                heap.insert(new FibonacciHeapNode<Set<V>>(v), G.getEdgeWeight(G.getEdge(v, a)));
        }
        //traverse and update the heap until only two vertices remain
    }
    
      
    /** Merges vertex t into vertex s, summing the weights as required. */
    protected void mergeVertices(Set<V> s, Set<V> t){
        
        //construct the new combinedvertex
        Set<V> set = new HashSet<V>();
        for( V  v : s) set.add(v);
        for( V  v : t) set.add(v);
        G.addVertex(set);
        
        //add edges and weights to the combined vertex
        for( Set<V> v : G.vertexSet() ){
            if(s != v  && t != v){
                E etv = G.getEdge(t, v);
                E esv = G.getEdge(s, v);
                double wtv = 0.0, wsv = 0.0; 
                if(etv != null)  wtv = G.getEdgeWeight(etv);
                if(esv != null)  wsv = G.getEdgeWeight(esv);
                double neww = wtv + wsv;
                if(neww != 0.0) 
                    G.setEdgeWeight(G.addEdge(set, v), neww);
            }
        }
        
        //remove original vertices
        G.removeVertex(t);
        G.removeVertex(s);
        
    }
    
    /** For testing */
    protected WeightedGraph<Set<V>, E> getWorkingGraph(){ return G; }
    
    
    
    
}

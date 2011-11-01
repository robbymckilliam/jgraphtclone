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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
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
    
    final Class<E> edgeclass;
    
    final WeightedGraph<Set<V>, E> G;
    
    double bestcutweight = Double.POSITIVE_INFINITY;
    Set<V> bestCut;
    
    /**
     * Will compute the minimum cut in graph.
     * Second argument is the edge class, to get around java generic behaviour.
     */
    public StoerWagnerMinimumCut(WeightedGraph<V, E> graph, Class<E> edgeclass){
        this.edgeclass = edgeclass;
        
        //get a version of this graph where each vertex is wrapped with a list
        G = new SimpleWeightedGraph<Set<V>, E>(edgeclass);
        MapVertices<V,E,Set<V>> vamp = new MapVertices<V,E,Set<V>>(graph, G) {
            public Set<V> function(V v) { 
                Set<V> list = new HashSet<V>();
                list.add(v);
                return list;
            }
        };
        
        //arbitrary vertex used to seed the algorithm.
        Set<V> a = G.vertexSet().iterator().next();
        while(G.vertexSet().size() > 2) minimumCutPhase(a);
        
    }
    
    boolean firstrun = true;
    /** Implements the MinimumCutPhase function of Stoer and Wagner */
    protected void minimumCutPhase(Set<V> a){
        
        //construct sorted queue with vertices connected to vertex a
        PriorityQueue<VertexAndWeight> queue = new PriorityQueue<VertexAndWeight>();
        Map<Set<V>, VertexAndWeight> dmap = new HashMap<Set<V>, VertexAndWeight>();
        for(Set<V> v : G.vertexSet()) {
            if( v != a ){
                Double w = -G.getEdgeWeight(G.getEdge(v, a));
                VertexAndWeight vandw = new VertexAndWeight(v,w);
                queue.add(vandw);
                dmap.put(v, vandw);
            }
        }
        
        //now iteratatively update the queue to get the required vertex ordering
        List<Set<V>> list = new ArrayList<Set<V>> (G.vertexSet().size());
        list.add(a);
        while(!queue.isEmpty()){
            //System.out.println(queue);
            Set<V> v = queue.poll().vertex; 
            dmap.remove(v);         
            list.add(v);
            for( E e : G.edgesOf(v) ){
                Set<V> vc;
                if( v != G.getEdgeSource(e) ) vc = G.getEdgeSource(e); 
                else vc = G.getEdgeTarget(e);
                if(dmap.get(vc) != null){
                    Double neww = - G.getEdgeWeight(G.getEdge(v, vc)) + dmap.get(vc).weight;                 
                    queue.remove(dmap.get(vc)); //this is O(logn) but could be O(1)?
                    dmap.get(vc).weight = neww;
                    queue.add(dmap.get(vc)); //this is O(logn) but could be O(1)?
                }
            }
        }
        
        //if this is the first run we compute the weight of last vertex in the list
        if(firstrun){
            Set<V> v = list.get(list.size()-1);
            double w = vertexWeight(v);
            //System.out.println(w);
            //System.out.println(v);
            if(w < bestcutweight){
                bestcutweight = w;
                bestCut = v;
            }
            firstrun = false;
        }
        
        //the last two elements in list are the vertices we want to merge. 
        Set<V> s = list.get(list.size()-2);
        Set<V> t = list.get(list.size()-1);
        //merge these vertices and get the weight.  
        VertexAndWeight vw = mergeVertices(s, t); 
        //If this is the best cut so far store it.
        if(vw.weight < bestcutweight){
            bestcutweight = vw.weight;
            bestCut = vw.vertex;
        }
        //System.out.println(vw);
        
    }
    
    /** Return the weight of the minimum cut */
    public double minCutWeight() { return bestcutweight; }
    
    /** Return a set of vertices on one side of the cut */
    public Set<V> minCut() { return bestCut; }
    
    /** 
     * Merges vertex t into vertex s, summing the weights as required.
     * Returns the merged vertex and the sum of its weights
     */
    protected VertexAndWeight mergeVertices(Set<V> s, Set<V> t){
        
        //construct the new combinedvertex
        Set<V> set = new HashSet<V>();
        for( V  v : s) set.add(v);
        for( V  v : t) set.add(v);
        G.addVertex(set);
        
        //add edges and weights to the combined vertex
        double wsum = 0.0;
        for( Set<V> v : G.vertexSet() ){
            if(s != v  && t != v){
                E etv = G.getEdge(t, v);
                E esv = G.getEdge(s, v);
                double wtv = 0.0, wsv = 0.0; 
                if(etv != null)  wtv = G.getEdgeWeight(etv);
                if(esv != null)  wsv = G.getEdgeWeight(esv);
                double neww = wtv + wsv;
                wsum += neww;
                if(neww != 0.0) 
                    G.setEdgeWeight(G.addEdge(set, v), neww);
            }
        }
        
        //remove original vertices
        G.removeVertex(t);
        G.removeVertex(s);
        
        return new VertexAndWeight(set, wsum);
        
    }
    
    /** Compute the sum of the weights entering a vertex */
    public double vertexWeight(Set<V> v){
        double wsum = 0.0;
        for( E e : G.edgesOf(v) )
            wsum += G.getEdgeWeight(e);
        return wsum;
    }
    
    //for testing
    protected WeightedGraph<Set<V>, E> getWorkingGraph() { return G; }
    
    /** Class for weighted vertices */
    protected class VertexAndWeight implements Comparable<VertexAndWeight> {
        public Set<V> vertex;
        public Double weight;
        
        public VertexAndWeight(Set<V> v, double w){
            this.vertex = v;
            this.weight = w;
        }
        
        @Override
        public int compareTo(VertexAndWeight that) {
            return Double.compare(weight, that.weight);
        } 

        @Override
        public String toString() {
            return "(" + vertex + ", " + weight + ")";
        }
    
    }
}

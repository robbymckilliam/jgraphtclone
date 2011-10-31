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
 * $Id: StoerWagnerMinimumCut.java 681 2009-05-25 06:17:31Z perfecthash $
 *
 * Changes
 * -------
 *
 */
package org.jgrapht.alg;

import java.util.Iterator;
import java.util.List;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.Subgraph;

/**
 * Implements the Stoer and Wanger minimum cut algorithm.  Runs in O(|V||E|  + |V|log|V|) time.
 * 
 * M. Stoer and F. Wagner, "A Simple Min-Cut Algorithm", Journal of the ACM, 
 * volume 44, number 4. pp 585-591, 1997.
 * 
 * @author Robby McKilliam
 */
public class StoerWagnerMinimumCut<V, E> {
    
    final V a;
    
    final WeightedGraph<V, E> G;
    
    public StoerWagnerMinimumCut(WeightedGraph<V, E> graph){
        
        //arbitrary vertex use to seed the algorithm.
        a = graph.vertexSet().iterator().next();
        
        G = graph;
        
        //System.out.println(G);
        
    }
    
    /** Merges two vertices together, summing the weights as required. */
    protected void mergeVertices(V s, V t){
        for( V v : G.vertexSet() ){
            if(s != v  && t != v){
                E etv = G.getEdge(t, v);
                E esv = G.getEdge(s, v);
                double wtv = 0.0, wsv = 0.0; 
                if(etv != null)  wtv = G.getEdgeWeight(etv);
                if(esv != null)  wsv = G.getEdgeWeight(esv);
                double neww = wtv + wsv;
                if(neww != 0.0){
                    if(esv == null) G.addEdge(s, v);
                    G.setEdgeWeight(esv, neww);
                }
            }
        }
        G.removeVertex(t);
    }
    
    /** Returns the closest vertex in G to the set of vertices A */
    protected V closestVertex(List<V> A, List<V> Ad){
        return null;
    }
    
    
    
    
}

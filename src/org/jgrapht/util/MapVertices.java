/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jgrapht.util;

import java.util.HashMap;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

/**
 * Takes a graph, and constructs an isomorphic graph with a function applied to each vertex.
 * 
 * @author Robby McKilliam
 */
public abstract class MapVertices<V,E,Y> {
    
    public MapVertices(Graph<V,E> Gin, Graph<Y,E> Gout){
        Map<V,Y> map = new HashMap<V,Y>(Gin.vertexSet().size());
        
        if(!Gout.vertexSet().isEmpty()) throw new RuntimeException("Gout graph must be empty");
        
        //fill the Gout graph with vertices
        for( V v: Gin.vertexSet() ){
            Y y = function(v);
            map.put(v, y);
            Gout.addVertex(y);
        }
        
        //now iterate over all pairs of vertices and add respective edges
        for( V v1: Gin.vertexSet() ){
            for( V v2: Gin.vertexSet() ){
                Y y1 = map.get(v1);
                Y y2 = map.get(v2);
                if(Gin.containsEdge(v1, v2)) Gout.addEdge(y1, y2);
            }
        }
    }
    
    public MapVertices(WeightedGraph<V,E> Gin, WeightedGraph<Y,E> Gout){
        Map<V,Y> map = new HashMap<V,Y>(Gin.vertexSet().size());
        
        if(!Gout.vertexSet().isEmpty()) throw new RuntimeException("Gout graph must be empty");
        
        //fill the Gout graph with vertices
        for( V v: Gin.vertexSet() ){
            Y y = function(v);
            map.put(v, y);
            Gout.addVertex(y);
        }
        
        //now iterate over all pairs of vertices and add respective edges
        for( V v1: Gin.vertexSet() ){
            for( V v2: Gin.vertexSet() ){
                if(v1 == v2) break;
                Y y1 = map.get(v1);
                Y y2 = map.get(v2);
                if(Gin.containsEdge(v1, v2)){
                    double w = Gin.getEdgeWeight(Gin.getEdge(v1, v2));
                    E e = Gout.addEdge(y1, y2);
                    Gout.setEdgeWeight(e, w);
                }
            }
        }
        
        
    }
    
    public abstract Y function(V v);
    
}

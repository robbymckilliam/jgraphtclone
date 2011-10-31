/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jgrapht.util;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class MapVerticesTest {
    
    Integer v1 = 1;
    Integer v2 = 2;
    Integer v3 = 3;
    Integer v4 = 4;
    
    
    public MapVerticesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of function method, of class MapVertices.
     */
    @Test
    public void testMap() {
        System.out.println("test map vertices");
        
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> Gin = 
                new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        
        Gin.addVertex(v1);
        Gin.addVertex(v2);
        Gin.addVertex(v3);
        Gin.addVertex(v4);
        
        Gin.setEdgeWeight(Gin.addEdge(v1, v2), 3.0);         
        Gin.setEdgeWeight(Gin.addEdge(v1, v3), 2.0);
        Gin.setEdgeWeight(Gin.addEdge(v1, v4), 4.0);
        Gin.setEdgeWeight(Gin.addEdge(v2, v3), 1.0);
        Gin.setEdgeWeight(Gin.addEdge(v3, v4), 1.0);
        
        System.out.println(Gin);
        
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> Gout = 
                new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
                
        MapVertices<Integer, DefaultWeightedEdge, Integer> vmap = 
                new MapVertices<Integer, DefaultWeightedEdge, Integer>(Gin, Gout) {
            @Override
            public Integer function(Integer v) { return 10*v; }
        };
        
        System.out.println(Gout);
        
        
    }

}

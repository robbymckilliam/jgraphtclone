/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jgrapht.alg;

import java.util.List;
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
public class StoerWagnerMinimumCutTest {
    
    private String v1 = "v1";
    private String v2 = "v2";
    private String v3 = "v3";
    private String v4 = "v4";
    
    public StoerWagnerMinimumCutTest() {
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
     * Test of mergeVertices method, of class StoerWagnerMinimumCut.
     */
    @Test
    public void testMergeVertices() {
        
        SimpleWeightedGraph<String, DefaultWeightedEdge> g = new
                SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        
        DefaultWeightedEdge e;
        e = g.addEdge(v1, v2); g.setEdgeWeight(e, 3.0);         
        e = g.addEdge(v1, v3); g.setEdgeWeight(e, 2.0);
        e = g.addEdge(v1, v4); g.setEdgeWeight(e, 4.0);
        e = g.addEdge(v2, v3); g.setEdgeWeight(e, 1.0);
        e = g.addEdge(v3, v4); g.setEdgeWeight(e, 1.0);
        
        System.out.println(g);
        
        StoerWagnerMinimumCut<String, DefaultWeightedEdge> mincut = 
                new StoerWagnerMinimumCut<String, DefaultWeightedEdge>(g);
        
        mincut.mergeVertices(v1, v2);
        System.out.println(g);
        
    }

    /**
     * Test of closestVertex method, of class StoerWagnerMinimumCut.
     */
    @Test
    public void testClosestVertex() {
        System.out.println("closestVertex");

        fail("The test case is a prototype.");
    }
}

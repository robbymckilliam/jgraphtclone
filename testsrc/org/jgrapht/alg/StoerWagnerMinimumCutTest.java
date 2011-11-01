/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jgrapht.alg;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    public void testMinCut() {
        System.out.println("testMinCut");
        
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
        
        StoerWagnerMinimumCut<String, DefaultWeightedEdge> mincut = 
                new StoerWagnerMinimumCut<String, DefaultWeightedEdge>(g, DefaultWeightedEdge.class);
      
        System.out.println(mincut.bestCut);
        System.out.println(mincut.bestcutweight);
        
        assertEquals(4.0, mincut.bestcutweight, 0.000001);
        
    }
    

//    /**
//     * Test of mergeVertices method, of class StoerWagnerMinimumCut.
//     */
//    @Test
//    public void testMergeVertices() {
//        System.out.println("testMergeVertices");
//        
//        SimpleWeightedGraph<String, DefaultWeightedEdge> g = new
//                SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
//        g.addVertex(v1);
//        g.addVertex(v2);
//        g.addVertex(v3);
//        g.addVertex(v4);
//        
//        DefaultWeightedEdge e;
//        e = g.addEdge(v1, v2); g.setEdgeWeight(e, 3.0);         
//        e = g.addEdge(v1, v3); g.setEdgeWeight(e, 2.0);
//        e = g.addEdge(v1, v4); g.setEdgeWeight(e, 4.0);
//        e = g.addEdge(v2, v3); g.setEdgeWeight(e, 1.0);
//        e = g.addEdge(v3, v4); g.setEdgeWeight(e, 1.0);
//        
//        System.out.println(g);
//        
//        StoerWagnerMinimumCut<String, DefaultWeightedEdge> mincut = 
//                new StoerWagnerMinimumCut<String, DefaultWeightedEdge>(g, DefaultWeightedEdge.class);
//        
//        System.out.println(mincut.getWorkingGraph());
//
//        Iterator<Set<String>> vitr = mincut.getWorkingGraph().vertexSet().iterator();
//        
//        Set<String> v1set = vitr.next();
//        Set<String> v2set = vitr.next();
//        
//        mincut.mergeVertices(v1set, v2set);
//        
//        //System.out.println(mincut.getWorkingGraph());
//        
////        assertTrue(g.containsEdge(v1, v3));
////        assertTrue(g.containsEdge(v3, v1));
////        e = g.getEdge(v1, v3); assertEquals(g.getEdgeWeight(e), 3.0, 0.000001);
////        assertTrue(g.containsEdge(v1, v4));
////        assertTrue(g.containsEdge(v4, v1));
////        e = g.getEdge(v1, v4); assertEquals(g.getEdgeWeight(e), 4.0, 0.000001);
//        
//    }
//    
//    /**
//     * Test of mergeVertices method, of class StoerWagnerMinimumCut.
//     */
//    @Test
//    public void testMinCutPhase() {
//        System.out.println("testMinCutPhase");
//        
//        SimpleWeightedGraph<String, DefaultWeightedEdge> g = new
//                SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
//        g.addVertex(v1);
//        g.addVertex(v2);
//        g.addVertex(v3);
//        g.addVertex(v4);
//        
//        DefaultWeightedEdge e;
//        e = g.addEdge(v1, v2); g.setEdgeWeight(e, 3.0);         
//        e = g.addEdge(v1, v3); g.setEdgeWeight(e, 1.0);
//        e = g.addEdge(v1, v4); g.setEdgeWeight(e, 4.0);
//        e = g.addEdge(v2, v3); g.setEdgeWeight(e, 1.0);
//        e = g.addEdge(v3, v4); g.setEdgeWeight(e, 2.0);
//        
//        StoerWagnerMinimumCut<String, DefaultWeightedEdge> mincut = 
//                new StoerWagnerMinimumCut<String, DefaultWeightedEdge>(g, DefaultWeightedEdge.class);
//        
//        mincut.minimumCutPhase(mincut.getWorkingGraph().vertexSet().iterator().next());
//        
//        //assertEquals(6.0,mincut.bestcutweight, 0.00000001);
//        
//        //System.out.println(mincut.getWorkingGraph());
//        
//        
//    }

}

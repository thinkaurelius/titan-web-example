package com.thinkaurelius.titan.webexample;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class JavaGraphOp {
    // Autowired via setter. I leave this as a blueprints.Graph unless I have to do Titan specific stuff.
    private Graph g;

    @Autowired
    // I like to autowire via setters because it makes it easy to write spring-free unit tests.
    public void setGraph(TitanGraphFactory gf) {
        this.g = gf.getGraph();
    }

    public String listVertices() {
        List<String> gods = new ArrayList<String>();
        Iterator<Vertex> itty = g.vertices();
        Vertex v;
        while (itty.hasNext()) {
            v = itty.next();
            gods.add((String) v.property("name").value());
        }
        return gods.toString();
    }
}

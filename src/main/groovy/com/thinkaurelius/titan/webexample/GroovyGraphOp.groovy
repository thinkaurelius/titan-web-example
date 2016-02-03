package com.thinkaurelius.titan.webexample

import org.apache.tinkerpop.gremlin.groovy.loaders.GremlinLoader
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Now this is cool... Here we have a Groovy class that will be injected into our java classesthatrunthings. Since it's a Groovy file we
 * can initialize Gremlin and write nice succinct gremlin.
 *
 * Docs: https://github.com/tinkerpop/gremlin/wiki/Using-Gremlin-through-Groovy#groovy-classes-with-gremlin
 */
@Component
class GroovyGraphOp {
    // This enables the Gremlin magic.
    static {
        GremlinLoader.load()
    }

    // Autowired via setter. I leave this as a blueprints.Graph unless I have to do Titan specific stuff.
    private Graph graph
    private GraphTraversalSource g

    @Autowired
    // I like to autowire via setters because it makes it easy to write spring-free unit tests.
    public void setGraph(TitanGraphFactory gf) {
        this.graph = gf.getGraph()
        this.g = this.graph.traversal()
    }

    public String getPlutosBrothers() {
        def plutosBrothers
        // sweet gremlin all up in my java project. aw yeah...
        plutosBrothers = g.V().has("name", "pluto").both("brother").dedup().values("name").toList()
        return plutosBrothers.toString()
    }
}

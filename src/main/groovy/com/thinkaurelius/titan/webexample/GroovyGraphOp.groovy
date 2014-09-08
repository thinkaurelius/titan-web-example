package com.thinkaurelius.titan.webexample

import com.tinkerpop.blueprints.Graph
import com.tinkerpop.gremlin.groovy.Gremlin
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
        Gremlin.load()
    }

    // Autowired via setter. I leave this as a blueprints.Graph unless I have to do Titan specific stuff.
    private Graph g

    @Autowired
    // I like to autowire via setters because it makes it easy to write spring-free unit tests.
    public void setGraph(TitanGraphFactory gf) {
        this.g = gf.getGraph()
    }

    public String getPlutosBrothers() {
        def plutosBrothers = []
        // sweet gremlin all up in my java project. aw yeah...
        g.V("name", "pluto").both("brother").dedup().name.store(plutosBrothers).iterate()
        return plutosBrothers.toString()
    }
}

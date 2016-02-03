package com.thinkaurelius.titan.webexample;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.thinkaurelius.titan.core.TitanGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class intercepts all incoming requests and outgoing responses. Essentially, it wraps all Servlet traffic. A
 * rollback is executed at the beginning and end of each Request.
 * <p/>
 * This strategy relies on 2 things: 1) Titan will create a thread-local transaction automatically when the first
 * operation is performed 2) Each request must be handled synchronously by a single thread. The later may vary from
 * container to container. Check the specification for your container.
 * <p/>
 * In practice, you will need to choose a transaction scheme suitable for your use cases. Performing a rollback before
 * and after each request is only suitable for a read-only application. If your application performs writes then they
 * will need to be explicitly committed within your application code.
 */
@Component
public class RequestWrapper implements ContainerResponseFilter, ContainerRequestFilter {

    // Autowired via setter.
    private TitanGraph g;

    @Autowired
    public void setGraph(TitanGraphFactory gf) {
        this.g = gf.getGraph();
    }

    /**
     * Executed before any servlets get the request
     */
    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) {
        g.tx().rollback(); // ensure there is not a stale transaction
        return containerRequest;
    }

    /**
     * Executed after every servlet returns, even if there is an exception.
     */
    @Override
    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
        g.tx().rollback(); // perform a rollback to clean up any dangling transactions
        return containerResponse;
    }
}

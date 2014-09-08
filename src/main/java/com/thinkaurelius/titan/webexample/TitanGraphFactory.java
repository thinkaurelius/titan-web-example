package com.thinkaurelius.titan.webexample;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This Component exists to provide other beans with access to the TitanGraph instance.
 */
@Component
public class TitanGraphFactory {
    private static final Logger logger = LoggerFactory.getLogger(TitanGraphFactory.class);

    public static final String PROPS_PATH = "titan-web-example/config/titan-cassandra-es.properties";

    // One graph to rule them all...
    private TitanGraph g;

    @PostConstruct
    public void init() {
        try {
            logger.info("Titan Properties Path: {}", PROPS_PATH);
            Configuration conf = new PropertiesConfiguration(PROPS_PATH);
            g = TitanFactory.open(conf);
            logger.info("Titan graph loaded successfully.");
        } catch (ConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    public TitanGraph getGraph() {
        return g;
    }
}

titan-web-example
=================
This is an example project that shows one way to build a RESTful Java web app around Titan, Cassandra, and Elasticsearch.

The application is built using Spring (with annotations based config), JAX-RS, and Jersey. It is assumed that you are already familiar with these technologies. 

A Servlet Filter is used to show how TitanGraph transactions can be managed with a pre/post request Filter.

This project also shows how Groovy classes can be injected into a Java web app to enable Gremlin Groovy sweetness. (See [GroovyGraphOp.groovy](src/main/groovy/com/thinkaurelius/titan/webexample/GroovyGraphOp.groovy).)

The "graph of the gods" example data set is used.

This application utilizes Cassandra in [Remote Server Mode](http://s3.thinkaurelius.com/docs/titan/0.5.0/cassandra.html#_remote_server_mode).

A Vagrant box is provided that hosts Cassandra and Elasticsearch.

Getting Started
===============

If you prefer to install Cassandra and Elasticsearch manually then skip the first 3 steps but make sure you adjust the [titan-cassandra-es.properties](src/main/resources/titan-web-example/config/titan-cassandra-es.properties) file's host values accordingly.

- Install [Virtual Box](https://www.virtualbox.org/wiki/Downloads) (because you need it for Vagrant).

- Install [Vagrant](https://docs.vagrantup.com/v2/installation/).

- Vagrant up! This will start a VM w/ IP 10.10.10.10 that has compatible versions of Cassandra and Elasticsearch running. There is also a Titan distribution with a pre-configured titan-cassandra-es.properties file that you can use for local gremlin shell access. (The host's Titan distro isn't used by the web app at all.)
```bash
cd titan-web-example/vagrant; vagrant up
```
- Load the project in your favorite IDE (or compile and run via mvn).

- Run the classesthatrunthings.PopulateDB class. This will load the "graph of the gods" example data set.

- Run the classesthatrunthings.RunApp class. This will launch the web app in an embedded Jetty container.

- Visit http://localhost:9091

If the links work then you're ready to dig in to the code and see what's goin on! The [TitanWebService class](src/main/java/com/thinkaurelius/titan/webexample/TitanWebService.java) is a great place to start.

Resources
=========

[Titan Docs](http://s3.thinkaurelius.com/docs/titan/0.5.0-SNAPSHOT/)

http://gremlindocs.com

http://thinkaurelius.com/blog/

Q&A happens in the [Aurelius Google Group](https://groups.google.com/forum/#!forum/aureliusgraphs) or on http://stackoverflow.com. If you have questions then those are the best places to search first.

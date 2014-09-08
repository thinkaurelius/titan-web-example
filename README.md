titan-web-example
=================
This is an example project that shows one way to build a RESTful Java web app around Titan, Cassandra, and Elasticsearch.

The application is built using Spring (annotations based config), JAX-RS, and Jersey. It is assumed that you are already familiar with these technologies. A Servlet Filter is wired up to show how TitanGraph transactions can be managed with a pre/post request Filter.

This example also shows how Groovy classes can be injected into a Java web app to enable Gremlin Groovy sweetness. (See src/main/groovy/.)

The "graph of the gods" example data set is used.

Getting Started
===============

If you prefer to install Cassandra and ES manually then skip the first 3 steps but make sure you adjust the titan-cassandra-es.properties file's host values accordingly.

- Install [Virtual Box](https://www.virtualbox.org/wiki/Downloads) (because you need it for Vagrant).

- Install [Vagrant](https://docs.vagrantup.com/v2/installation/).

- Vagrant up! This will start a VM w/ IP 10.10.10.10 that has compatible versions of Cassandra and Elasticsearch running. There is also a Titan distribution with a pre-configured titan-cassandra-es.properties file that you can use for local gremlin shell access.
```bash
cd titan-web-example/vagrant; vagrant up
```
- Load the project in your favorite IDE (or compile and run via mvn).

- Run  classesthatrunthings.PopulateDB.java. This will load the "graph of the gods" example data set.

- Run classesthatrunthings.RunApp.java. This will launch the web app in an embedded Jetty container.

- Visit http://localhost:9091

If the links works then it's time to dig in to the code and see what's goin on! Start with the TitanWebService class.

Resources
=========

[Titan Docs](http://s3.thinkaurelius.com/docs/titan/0.5.0-SNAPSHOT/)

http://gremlindocs.com

http://thinkaurelius.com/blog/

Q&A happens in the [Aurelius Google Group](https://groups.google.com/forum/#!forum/aureliusgraphs) or on http://stackoverflow.com. If you have questions then those are the best places to search first.

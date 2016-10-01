# Introduction

This repository contains illustrative material for a talk I gave on 15th October
2016 at Hong Kong Code Conf, entitled *In Defence of Boilerplate Code*.

The main argument of the talk is that while boilerplate code is certainly
undesirable all else being equal, sometimes the cure is worse than the disease:
techniques used to eliminate boilerplate code can cause worse problems than the
boilerplate itself.

The repository consists of several Java modules, each of which illustrates this
theme in a different way.

# Application objects

The ```00-app``` module contains a set of "business" interfaces and objects used
by several other modules. I have made no attempt to give them realistic names
and purposes: I simply called the interfaces ```A```, ```B```, and ```C``` and
the implementations ```AImpl```, ```BImpl```, and ```CImpl```.

```AImpl``` depends on ```BImpl``` and ```CImpl```; ```BImpl``` depends on
```CImpl```; so we have a simple dependency graph of three classes.

# Dependency injection

The ```01-depinject``` series of modules illustrates several different
techniques for wiring the application objects together, and overriding those
wirings for testing.

The idea is we want to use a single instance of each class: for example
```AImpl``` and ```BImpl``` should share the same instance of ```CImpl```.

There are four modules in total: three use the common dependency injection
frameworks [Spring](http://projects.spring.io/spring-framework/),
[Guice](https://github.com/google/guice) and
[Dagger](https://google.github.io/dagger/), while the fourth,
```01-depinject-diy```, uses a simple framework-free technique.

My preferred method is ```01-depinject-diy```. At the cost of a small amount of
boilerplate code (the fields in the Config class and their initializers), it is
simple, requires no dependencies or special compiler plugins, and discourages
XML configuration, Aspect Oriented Programming, and other techniques which
complicate the application and make it harder to reason about, debug, test,
maintain etc.

If I were forced to use a dependency-injection framework, I would prefer Guice
or Dagger to Spring, as they are more focussed. Guice uses runtime reflection,
which makes it harder to reason about the application's behaviour at compile
time; Dagger uses compile-time code generation, which addresses this problem at
the expense of complicating the build (for example, not every JVM language will
necessarily have the necessary annotation-processing plugins).

## Configuration

In ```01-depinject-spring``` and ```01-depinject-diy```, I have illustrated two
approaches to managing configuration, in the spirit of each module.

In the former, I have used Spring's implicit configuration parsing through the
```@Value``` annotation with ```${}``` placeholder expressions. This is supposed
to give flexible configuration management through [the Spring
environment](http://docs.spring.io/spring/docs/4.3.3.RELEASE/spring-framework-reference/html/beans.html#beans-property-source-abstraction),
with the ability to define a hierarchy of ```PropertySource```s, for example
allowing properties from the configuration file to be overridden on the command
line.

In the latter, I have written a small class ```ConfigProperties``` which wraps
```java.util.Properties```, providing a method which throws an exception when a
property is missing, with a message which includes the source from which the
property was loaded.

By now, you will not be surprised to learn that I strongly favour the latter
approach. It greatly simplifies configuration management, making it far less
likely for mistakes to occur (as each property has one unambiguous source), as
well as far easier to find and fix errors when they do happen. In addition, it
requires no new dependencies on the classpath. These benefits are easily worth
the price of the small amount of boilerplate code required to parse and use a
properties file.

## Error messages

Using simpler, more direct abstractions tends to produce better error messages.

To demonstrate this, I have included an ```errors``` package in both
```01-depinject-spring``` and ```01-depinject-diy```.

In ```MissingBeanDemo```, I show what happens when you forget to declare a required
bean in Spring: you get a long and complex exception message. The longer the
chain from the desired bean to the missing one, the longer the message, but even
in this very simple case, it is meaty!

Users of Spring can expect to spend large amounts of time reading and debugging
error messages similar to this one; notice how the stack traces contain no
frames whatsoever from the ```Config``` class, so in more complex cases (perhaps
involving missing annotations or circular dependencies), the only options are to
debug the Spring Framework code itself (not an enjoyable exercise) or trawl the
Web in the hope that someone else has had a similar problem and posted the
solution to it.

There is no equivalent of ```ErrorDemo``` in the ```diy``` module, because the
plain vanilla Java compiler would make it impossible to make such a mistake (as
you would have to call a method which does not exist). So here we see another
advantage of using a little boilerplate: the compiler can help us check that it
does what we want it to do!

In ```SpringMissingPropertyDemo``` and ```DIYMissingPropertyDemo```, I show what
happens in the two approaches when you refer to a property which does not exist
in the configuration file. In the DIY version, you get a simple straightforward
exception message telling you which property name was missing from which
configuration file. In the Spring version, you get a message many times longer,
full of class names such as ```AutowiredAnnotationBeanPostProcessor``` and
```DefaultSingletonBeanRegistry```, but missing the most important piece of
information, namely the name of the file in which the program looked for the
missing property!

Ask yourself which of these two you would prefer to maintain and debug! I know
my answer.

# Transactions

The ```02-transactions``` module illustrates two different ways of using
[Spring's transaction management
features](http://docs.spring.io/spring-framework/docs/4.3.3.RELEASE/spring-framework-reference/html/transaction.html).
This is not intended as an endorsement of Spring's facilities in this area:
rather, it is intended to demonstrate the consequences of using Aspect Oriented
Programming (AOP) and how those consequences can often be avoided at the expense
of just a little boilerplate code.

The module contains two different implementations of the same ```DAO```
interface: ```AnnotatedDAO``` uses Spring's ```@Transactional``` annotation,
which is the style typically recommended in Spring's documentation and
tutorials, whereas ```BoilerPlateDAO``` avoids the use of AOP by injecting the
Spring ```PlatformTransactionManager``` into the class and using
```TransactionTemplate``` (this is what the Spring documentation calls
[programmatic transaction
management](http://docs.spring.io/spring-framework/docs/4.3.3.RELEASE/spring-framework-reference/html/transaction.html#transaction-programmatic)).

Both implementations do nothing more than throw an exception. Running
```TransactionalApp``` runs both: in both cases you will see "rollback" being
invoked on the transaction manager, followed by a stack trace. With the
"boilerplate DAO", you will see just *one* Spring stack frame in the trace,
whereas with the "annotated DAO", there are *eight*, due to the AOP proxy,
transaction interceptor etc. This shows just how much complexity and additional
hidden dependencies are introduced by the use of AOP.

If I were forced to use Spring transaction management, I would strongly prefer
the ```BoilerPlateDAO``` style. In addition to the above advantages, this style
avoids the notorious differences between self-invocation and external invocation
caused by the use of AOP proxies (search for "self-invocation" in the Spring
docs). In my view, these advantages are well worth the small amount of
boilerplate code.
 
# Build

The project uses [Apache Maven](https://maven.apache.org/) to build. The
```maven-dependency-plugin``` is used to generate a set of dependency reports
for each module under ```/target/dependency-reports```: this shows the
consequences of each technique for the dependencies which need to be pulled in.
Anyone who has maintained a sizeable Java application for any length of time
knows how important a consideration this is.

As a bonus, in the root POM I have turned on [dependency cleanliness
enforcement](http://maven.apache.org/plugins/maven-dependency-plugin/examples/failing-the-build-on-dependency-analysis-warnings.html)
and [duplicate class
bans](http://www.mojohaus.org/extra-enforcer-rules/banDuplicateClasses.html)
which are techniques I would highly recommend to anyone maintaining Java
projects.

# License

In the unlikely event that you want to use any material in this repository for
your own purposes, you may do so under the Apache License 2.0, which is
available as noted below or in LICENSE.txt.

   Copyright 2016 Andrew Medworth

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

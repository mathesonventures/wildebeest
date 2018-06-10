# Developer Guide

## Introduction

This document will get you started on building and hacking on Wildebeest.

## Toolchain

The mandatory tools for working on Wildebeest are:

- JDK 1.8 or later
- Any text editor
- A vanilla installation of [Apache Ant](https://ant.apache.org/) 1.8.2 or later

If you want to use an IDE, we recommend using the latest version of IntelliJ.  IntelliJ project files are included.

Regardless of your choice of editor please ensure you configure it to follow our [code standard](Code Standard.md).

We support development on Linux, Windows and macOS.

## Command-Line Quick Start

### Introduction

The most lightweight way to get started is using your command-line tools to build and test Wildebeest.

### Pre-Flight Check

Make sure your tools are setup correctly:

```
$ javac -version
javac 1.8.0_144

$ java -version
java version "1.8.0_144"
Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)

$ ant -version
Apache Ant(TM) version 1.8.2 compiled on December 20 2010
```

### Build the API Component

The API component defines a number of interfaces and types that are used by the Core of Wildebeest, and must be built first.  This can be done using Ant as follows:

```
$ cd MV.Wildebeest.Api
$ ant core.jar:pub-project
```

The Ant-based build will retrieve dependencies, compile the code and build a JAR, and publish it to a project-level repository called /repo/ in the root of the project.

Now you can move to the Core component and build it:

```
$ cd ../MV.Wildebeest.Core
$ ant core.jar:pub-project
```

This does the same thing for Core as we just did for API, building and publishing a JAR artifact into the project-level repository at /repo/

You can also run the test suite for Core while you're here:

```
$ ant test.app:report
```

The test report is placed in /target/test/report.

### Cleaning Up

You can clean up the build for each component separately by running the "clean" Ant target:

```
$ ant clean
```

From either component you can also purge the project-level artifact repository using the cleanrepo target:

```
$ ant cleanrepo
```


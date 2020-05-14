## About
Project is intended to provide comparison of selected libraries with persistent collections.

## Used collections libraries
Project contains benchmarks for following collections libraries (and data structures):
* Java Built-In Collections
    * [LinkedList](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html)
    * [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)
    * [HashSet](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html)
    * [HashMap](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html)
* Paguro 3.1.2
    * [PersistentVector](https://glenkpeterson.github.io/Paguro/apidocs/org/organicdesign/fp/collections/PersistentVector.html)
    * [PersistentHashSet](https://glenkpeterson.github.io/Paguro/apidocs/org/organicdesign/fp/collections/PersistentHashSet.html)
    * [PersistentHashMap](https://glenkpeterson.github.io/Paguro/apidocs/org/organicdesign/fp/collections/PersistentHashMap.html)
* Vavr 0.10.2
    * [List](https://javadoc.io/static/io.vavr/vavr/0.10.2/io/vavr/collection/List.html)
    * [Vector](https://javadoc.io/static/io.vavr/vavr/0.10.2/io/vavr/collection/Vector.html)
    * [HashSet](https://javadoc.io/static/io.vavr/vavr/0.10.2/io/vavr/collection/HashSet.html)
    * [HashMap](https://javadoc.io/static/io.vavr/vavr/0.10.2/io/vavr/collection/HashMap.html)
* PCollections 3.1.2
    * [ConsPStack](https://javadoc.io/static/org.pcollections/pcollections/3.1.2/org/pcollections/ConsPStack.html)
    * [HashTreePSet](https://javadoc.io/doc/org.pcollections/pcollections/3.2.1/index.html)
    * [HashPMap](https://www.javadoc.io/doc/org.pcollections/pcollections/3.2.1/org/pcollections/HashPMap.html)
* Bifurcan 0.1.0
    * [List](https://lacuna.io/docs/bifurcan/io/lacuna/bifurcan/List.html)
    * [Set](https://lacuna.io/docs/bifurcan/io/lacuna/bifurcan/Set.html)
    * [Map](https://lacuna.io/docs/bifurcan/io/lacuna/bifurcan/Map.html)
* Cyclops 10.4.0
    * [Seq](https://github.com/aol/cyclops/wiki/X-Seq)
    * [HashSet](https://github.com/aol/cyclops/blob/master/cyclops/src/main/java/cyclops/data/HashSet.java)
    * [HashMap](https://github.com/aol/cyclops/blob/master/cyclops/src/main/java/cyclops/data/HashMap.java)
    
## Test scenarios
Three test scenarios are implemented for each of the basic type of data structure (List, Map and Set):
* Insert N integers into data structure.
* Iterate over data structure containing N elements.
* Read N random elements from data structure containing N elements.
Following values are being used for N: 100, 1000, 10000.
You could easily configure tests by modifying [BaseExecutionPlan](src/main/java/pl/dreamlab/persistentcollectionsbenchmark/common/BaseExecutionPlan.java) class.

Below, you can find references to the data structure specific tests.

### List scenarios
* [InsertIntoListCopyBenchmark](src/main/java/pl/dreamlab/persistentcollectionsbenchmark/list/InsertIntoListCopyBenchmark.java)
* [IterationOverListBenchmark](src/main/java/pl/dreamlab/persistentcollectionsbenchmark/list/IterationOverListBenchmark.java)
* [RandomAccessListBenchmark](src/main/java/pl/dreamlab/persistentcollectionsbenchmark/list/RandomAccessListBenchmark.java)

### Set scenarios
* [InsertIntoSetCopyBenchmark](src/main/java/pl/dreamlab/persistentcollectionsbenchmark/set/InsertIntoSetCopyBenchmark.java)
* [IterateOverSetBenchmark](src/main/java/pl/dreamlab/persistentcollectionsbenchmark/set/IterateOverSetBenchmark.java)
* [RandomAccessSetBenchmark](src/main/java/pl/dreamlab/persistentcollectionsbenchmark/set/RandomAccessSetBenchmark.java)

### Map scenarios
* [InsertIntoMapCopyBenchmark](src/main/java/pl/dreamlab/persistentcollectionsbenchmark/map/InsertIntoMapCopyBenchmark.java)
* [IterateOverMapBenchmark](src/main/java/pl/dreamlab/persistentcollectionsbenchmark/map/IterateOverMapBenchmark.java)
* [RandomAccessMapBenchmark](src/main/java/pl/dreamlab/persistentcollectionsbenchmark/map/RandomAccessMapBenchmark.java)

### How to run
1. Build project with maven
`mvn clean install`
2. Run specific benchmark using following command:
`java -jar ./target/persistent-collections-benchmark.jar <BenchmarkClassName> -rf json`
If no `BenchmarkClassName` parameter is passed then all benchmarks will be executed.
Results in JSON format will be saved in `jmh-result.json` file.

You may also override default configuration by passing command line arguments.
Please check [JHM documentation](https://github.com/guozheng/jmh-tutorial/blob/master/README.md#jmh-command-line-options) 
for details.

### How to easily visualize results
You can use [following site](https://jmh.morethan.io/) to easily visualize the results.
Just upload jhm-result.json file. 
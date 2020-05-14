package pl.dreamlab.persistentcollectionsbenchmark.common;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.Random;

import static java.lang.System.currentTimeMillis;

@State(Scope.Benchmark)
public class BaseExecutionPlan {
    @Param({"JAVA", "PAGURO", "VAVR", "PCOLLECTIONS", "BIFURCAN", "CYCLOPS"})
    public CollectionsLibrary collectionsLibrary;

    @Param({"100", "1000", "10000"})
    public int numberOfOperations;

    public Random random = new Random(currentTimeMillis());
}

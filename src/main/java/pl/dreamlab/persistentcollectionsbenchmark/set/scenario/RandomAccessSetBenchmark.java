package pl.dreamlab.persistentcollectionsbenchmark.set.scenario;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.organicdesign.fp.collections.PersistentHashSet;
import org.pcollections.MapPSet;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseCollectionBenchmark;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseExecutionPlan;
import pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary;
import pl.dreamlab.persistentcollectionsbenchmark.common.RandomAccessTestCasesGenerator;
import pl.dreamlab.persistentcollectionsbenchmark.set.plan.SetsInitializingExecutionPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiConsumer;

import static pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary.*;

public class RandomAccessSetBenchmark extends BaseCollectionBenchmark {
    private static Map<CollectionsLibrary, BiConsumer<SetRandomAccessExecutionPlan, Blackhole>> libraryToMethod;

    static {
        libraryToMethod = new HashMap<>();
        libraryToMethod.put(JAVA, RandomAccessSetBenchmark::randomAccessSetJava);
        libraryToMethod.put(VAVR, RandomAccessSetBenchmark::randomAccessSetVavr);
        libraryToMethod.put(PAGURO, RandomAccessSetBenchmark::randomAccessSetPaguro);
        libraryToMethod.put(PCOLLECTIONS, RandomAccessSetBenchmark::randomAccessSetPCollections);
        libraryToMethod.put(BIFURCAN, RandomAccessSetBenchmark::randomAccessSetBifurcan);
        libraryToMethod.put(CYCLOPS, RandomAccessSetBenchmark::randomAccessSetCyclops);
    }

    @State(Scope.Benchmark)
    public static class SetRandomAccessExecutionPlan extends BaseExecutionPlan {
        public ArrayList<Integer> toRead;
        SetsInitializingExecutionPlan.BaseSetsExecutionPlan basePlan;

        @Setup(Level.Invocation)
        public void setUpInsertData(SetsInitializingExecutionPlan plan) {
            toRead = RandomAccessTestCasesGenerator.generate(plan.basePlan.numberOfOperations, random, plan.toInsert);
            basePlan = plan.basePlan;
        }
    }

    @Benchmark
    public void randomAccessSetBenchmark(SetRandomAccessExecutionPlan plan, Blackhole blackhole) {
        CollectionsLibrary collectionsLibrary = plan.collectionsLibrary;
        BiConsumer<SetRandomAccessExecutionPlan, Blackhole> methodToBeExecuted = libraryToMethod.get(collectionsLibrary);

        assert methodToBeExecuted != null;

        methodToBeExecuted.accept(plan, blackhole);
    }

    private static void randomAccessSetJava(SetRandomAccessExecutionPlan plan, Blackhole blackhole) {
        HashSet<Integer> subject = plan.basePlan.javaSet;

        for (Integer i : plan.toRead) {
            blackhole.consume(subject.contains(i));
        }
    }

    private static void randomAccessSetVavr(SetRandomAccessExecutionPlan plan, Blackhole blackhole) {
        io.vavr.collection.HashSet<Integer> subject = plan.basePlan.vavrSet;

        for (Integer i : plan.toRead) {
            blackhole.consume(subject.contains(i));
        }
    }

    private static void randomAccessSetPaguro(SetRandomAccessExecutionPlan plan, Blackhole blackhole) {
        PersistentHashSet<Integer> subject = plan.basePlan.paguroSet;

        for (Integer i : plan.toRead) {
            blackhole.consume(subject.contains(i));
        }
    }

    private static void randomAccessSetBifurcan(SetRandomAccessExecutionPlan plan, Blackhole blackhole) {
        io.lacuna.bifurcan.Set<Integer> subject = plan.basePlan.bifucanSet;

        for (Integer i : plan.toRead) {
            blackhole.consume(subject.contains(i));
        }
    }

    private static void randomAccessSetPCollections(SetRandomAccessExecutionPlan plan, Blackhole blackhole) {
        MapPSet<Integer> subject = plan.basePlan.pcollectionsSet;

        for (Integer i : plan.toRead) {
            blackhole.consume(subject.contains(i));
        }
    }

    private static void randomAccessSetCyclops(SetRandomAccessExecutionPlan plan, Blackhole blackhole) {
        cyclops.data.HashSet<Integer> subject = plan.basePlan.cyclopsSet;

        for (Integer i : plan.toRead) {
            blackhole.consume(subject.contains(i));
        }
    }
}

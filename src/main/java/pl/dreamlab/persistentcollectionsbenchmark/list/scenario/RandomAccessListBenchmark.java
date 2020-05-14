package pl.dreamlab.persistentcollectionsbenchmark.list.scenario;

import cyclops.data.Seq;
import io.vavr.collection.Vector;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.organicdesign.fp.collections.PersistentVector;
import org.pcollections.ConsPStack;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseCollectionBenchmark;
import pl.dreamlab.persistentcollectionsbenchmark.list.plan.ListsCollections;
import pl.dreamlab.persistentcollectionsbenchmark.list.plan.ListsInitializingExecutionPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiConsumer;

import static pl.dreamlab.persistentcollectionsbenchmark.list.plan.ListsCollections.*;

public class RandomAccessListBenchmark extends BaseCollectionBenchmark {
    private static Map<ListsCollections, BiConsumer<ListsInitializingExecutionPlan, Blackhole>> listCollectionToMethod;

    static {
        listCollectionToMethod = new HashMap<>();
        listCollectionToMethod.put(JAVA, RandomAccessListBenchmark::randomAccessListBuiltIn);
        listCollectionToMethod.put(JAVA_ARRAY_LIST, RandomAccessListBenchmark::randomAccessArrayListBuiltIn);
        listCollectionToMethod.put(VAVR, RandomAccessListBenchmark::randomAccessListVavr);
        listCollectionToMethod.put(VAVR_VECTOR, RandomAccessListBenchmark::randomAccessVectorVavr);
        listCollectionToMethod.put(PAGURO, RandomAccessListBenchmark::randomAccessListPaguro);
        listCollectionToMethod.put(PCOLLECTIONS, RandomAccessListBenchmark::randomAccessListPCollections);
        listCollectionToMethod.put(BIFURCAN, RandomAccessListBenchmark::randomAccessListBifurcan);
        listCollectionToMethod.put(CYCLOPS, RandomAccessListBenchmark::randomAccessListCyclops);
    }

    @Benchmark
    public void randomAccessListBenchmark(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        ListsCollections collectionsLibrary = plan.basePlan.listCollection;
        BiConsumer<ListsInitializingExecutionPlan, Blackhole> methodToBeExecuted = listCollectionToMethod.get(collectionsLibrary);

        assert methodToBeExecuted != null;

        methodToBeExecuted.accept(plan, blackhole);
    }

    private static void randomAccessArrayListBuiltIn(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        ArrayList<Integer> subject = plan.basePlan.javaArrayList;

        for (int i = 0; i < plan.basePlan.numberOfOperations; i++) {
            blackhole.consume(subject.get(plan.basePlan.random.nextInt(subject.size())));
        }
    }

    private static void randomAccessListBuiltIn(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        LinkedList<Integer> subject = plan.basePlan.javaList;

        for (int i = 0; i < plan.basePlan.numberOfOperations; i++) {
            blackhole.consume(subject.get(plan.basePlan.random.nextInt(subject.size())));
        }
    }

    private static void randomAccessListVavr(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        io.vavr.collection.List<Integer> subject = plan.basePlan.vavrList;

        for (int i = 0; i < plan.basePlan.numberOfOperations; i++) {
            blackhole.consume(subject.get(plan.basePlan.random.nextInt(subject.size())));
        }
    }

    private static void randomAccessVectorVavr(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        Vector<Integer> subject = plan.basePlan.vavrVector;

        for (int i = 0; i < plan.basePlan.numberOfOperations; i++) {
            blackhole.consume(subject.get(plan.basePlan.random.nextInt(subject.size())));
        }
    }

    private static void randomAccessListPaguro(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        PersistentVector<Integer> subject = plan.basePlan.paguroList;

        for (int i = 0; i < plan.basePlan.numberOfOperations; i++) {
            blackhole.consume(subject.get(plan.basePlan.random.nextInt(subject.size())));
        }
    }

    private static void randomAccessListPCollections(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        ConsPStack<Integer> subject = plan.basePlan.pcollectionsList;

        for (int i = 0; i < plan.basePlan.numberOfOperations; i++) {
            blackhole.consume(subject.get(plan.basePlan.random.nextInt(subject.size())));
        }
    }

    private static void randomAccessListBifurcan(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        io.lacuna.bifurcan.List<Integer> subject = plan.basePlan.bifucanList;

        for (int i = 0; i < plan.basePlan.numberOfOperations; i++) {
            blackhole.consume(subject.nth(plan.basePlan.random.nextInt((int) subject.size())));
        }
    }

    private static void randomAccessListCyclops(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        Seq<Integer> subject = plan.basePlan.cyclopsList;

        for (int i = 0; i < plan.basePlan.numberOfOperations; i++) {
            blackhole.consume(subject.get(plan.basePlan.random.nextInt(subject.size())));
        }
    }

}
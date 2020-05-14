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

public class IterationOverListBenchmark extends BaseCollectionBenchmark {
    private static Map<ListsCollections, BiConsumer<ListsInitializingExecutionPlan, Blackhole>> listCollectionToMethod;

    static {
        listCollectionToMethod = new HashMap<>();
        listCollectionToMethod.put(JAVA, IterationOverListBenchmark::iterateOverListBuiltIn);
        listCollectionToMethod.put(JAVA_ARRAY_LIST, IterationOverListBenchmark::iterateOverArrayListBuiltIn);
        listCollectionToMethod.put(VAVR, IterationOverListBenchmark::iterateOverListVavr);
        listCollectionToMethod.put(VAVR_VECTOR, IterationOverListBenchmark::iterateOverVectortVavr);
        listCollectionToMethod.put(PAGURO, IterationOverListBenchmark::iterateOverListPaguro);
        listCollectionToMethod.put(PCOLLECTIONS, IterationOverListBenchmark::iterateOverListPCollections);
        listCollectionToMethod.put(CYCLOPS, IterationOverListBenchmark::iterateOverListCyclops);
    }

    @Benchmark
    public void iterateOverListListBenchmark(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        ListsCollections collectionsLibrary = plan.basePlan.listCollection;
        BiConsumer<ListsInitializingExecutionPlan, Blackhole> methodToBeExecuted = listCollectionToMethod.get(collectionsLibrary);
        methodToBeExecuted.accept(plan, blackhole);
    }

    private static void iterateOverListBuiltIn(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        LinkedList<Integer> subject = plan.basePlan.javaList;

        for (Integer i : subject) {
            blackhole.consume(i);
        }
    }

    private static void iterateOverArrayListBuiltIn(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        ArrayList<Integer> subject = plan.basePlan.javaArrayList;

        for (Integer i : subject) {
            blackhole.consume(i);
        }
    }

    private static void iterateOverListVavr(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        io.vavr.collection.List<Integer> subject = plan.basePlan.vavrList;

        for (Integer i : subject) {
            blackhole.consume(i);
        }
    }

    private static void iterateOverVectortVavr(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        Vector<Integer> subject = plan.basePlan.vavrVector;

        for (Integer i : subject) {
            blackhole.consume(i);
        }
    }

    private static void iterateOverListPaguro(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        PersistentVector<Integer> subject = plan.basePlan.paguroList;

        for (Integer i : subject) {
            blackhole.consume(i);
        }
    }

    private static void iterateOverListPCollections(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        ConsPStack<Integer> subject = plan.basePlan.pcollectionsList;

        for (Integer i : subject) {
            blackhole.consume(i);
        }
    }

    public static void iterateOverListCyclops(ListsInitializingExecutionPlan plan, Blackhole blackhole) {
        Seq<Integer> subject = plan.basePlan.cyclopsList;

        for (Integer i : subject) {
            blackhole.consume(i);
        }
    }

}

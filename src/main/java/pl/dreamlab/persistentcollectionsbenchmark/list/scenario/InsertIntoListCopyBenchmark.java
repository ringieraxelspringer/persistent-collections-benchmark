package pl.dreamlab.persistentcollectionsbenchmark.list.scenario;


import cyclops.data.Seq;
import io.vavr.collection.Vector;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.organicdesign.fp.collections.PersistentVector;
import org.pcollections.ConsPStack;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseCollectionBenchmark;
import pl.dreamlab.persistentcollectionsbenchmark.list.plan.ListsCollections;
import pl.dreamlab.persistentcollectionsbenchmark.list.plan.BaseListExecutionPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiConsumer;

import static pl.dreamlab.persistentcollectionsbenchmark.list.plan.ListsCollections.*;

public class InsertIntoListCopyBenchmark extends BaseCollectionBenchmark {
    private static Map<ListsCollections, BiConsumer<BaseListExecutionPlan, Blackhole>> listCollectionToMethod;

    static {
        listCollectionToMethod = new HashMap<>();
        listCollectionToMethod.put(JAVA, InsertIntoListCopyBenchmark::insertIntoListCopyBuiltIn);
        listCollectionToMethod.put(JAVA_ARRAY_LIST, InsertIntoListCopyBenchmark::insertIntoArrayListCopyBuiltIn);
        listCollectionToMethod.put(VAVR, InsertIntoListCopyBenchmark::insertIntoListVavr);
        listCollectionToMethod.put(VAVR_VECTOR, InsertIntoListCopyBenchmark::insertIntoVectorVavr);
        listCollectionToMethod.put(PAGURO, InsertIntoListCopyBenchmark::insertIntoListPaguro);
        listCollectionToMethod.put(PCOLLECTIONS, InsertIntoListCopyBenchmark::insertIntoListPCollections);
        listCollectionToMethod.put(BIFURCAN, InsertIntoListCopyBenchmark::insertIntoListBifurcan);
        listCollectionToMethod.put(CYCLOPS, InsertIntoListCopyBenchmark::insertIntoListCyclops);
    }

    @Benchmark
    public void insertIntoListCopyBenchmark(BaseListExecutionPlan plan, Blackhole blackhole) {
        ListsCollections collectionsLibrary = plan.listCollection;
        BiConsumer<BaseListExecutionPlan, Blackhole> methodToBeExecuted = listCollectionToMethod.get(collectionsLibrary);

        assert methodToBeExecuted != null;

        methodToBeExecuted.accept(plan, blackhole);
    }

    private static void insertIntoListCopyBuiltIn(BaseListExecutionPlan plan, Blackhole blackhole) {
        LinkedList<Integer> subject = new LinkedList<>();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = new LinkedList<>(subject);
            subject.add(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }


    private static void insertIntoArrayListCopyBuiltIn(BaseListExecutionPlan plan, Blackhole blackhole) {
        ArrayList<Integer> subject = new ArrayList<>();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = new ArrayList<>(subject);
            subject.add(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoListVavr(BaseListExecutionPlan plan, Blackhole blackhole) {
        io.vavr.collection.List<Integer> subject = io.vavr.collection.List.of();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.append(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoVectorVavr(BaseListExecutionPlan plan, Blackhole blackhole) {
        Vector<Integer> subject = Vector.empty();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.append(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoListPaguro(BaseListExecutionPlan plan, Blackhole blackhole) {
        PersistentVector<Integer> subject = PersistentVector.empty();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.append(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoListPCollections(BaseListExecutionPlan plan, Blackhole blackhole) {
        ConsPStack<Integer> subject = ConsPStack.from(new LinkedList<>());

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.plus(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoListBifurcan(BaseListExecutionPlan plan, Blackhole blackhole) {
        io.lacuna.bifurcan.List<Integer> subject = io.lacuna.bifurcan.List.of();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.addLast(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoListCyclops(BaseListExecutionPlan plan, Blackhole blackhole) {
        Seq<Integer> subject = Seq.empty();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.plus(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

}

package pl.dreamlab.persistentcollectionsbenchmark.map.scenario;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.organicdesign.fp.collections.PersistentHashMap;
import org.pcollections.HashPMap;
import org.pcollections.IntTreePMap;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseCollectionBenchmark;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseExecutionPlan;
import pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.function.BiConsumer;

import static pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary.*;

public class InsertIntoMapCopyBenchmark extends BaseCollectionBenchmark {
    private static EnumMap<CollectionsLibrary, BiConsumer<BaseExecutionPlan, Blackhole>> libraryToMethod;

    static {
        libraryToMethod = new EnumMap<>(CollectionsLibrary.class);
        libraryToMethod.put(JAVA, InsertIntoMapCopyBenchmark::insertIntoMapCopyJava);
        libraryToMethod.put(VAVR, InsertIntoMapCopyBenchmark::insertIntoMapVavr);
        libraryToMethod.put(PAGURO, InsertIntoMapCopyBenchmark::insertIntoMapPaguro);
        libraryToMethod.put(PCOLLECTIONS, InsertIntoMapCopyBenchmark::insertIntoMapPCollections);
        libraryToMethod.put(BIFURCAN, InsertIntoMapCopyBenchmark::insertIntoMapBifurcan);
        libraryToMethod.put(CYCLOPS, InsertIntoMapCopyBenchmark::insertIntoMapCyclops);
    }

    @Benchmark
    public void insertIntoMapCopyBenchmark(BaseExecutionPlan plan, Blackhole blackhole) {
        CollectionsLibrary collectionsLibrary = plan.collectionsLibrary;
        BiConsumer<BaseExecutionPlan, Blackhole> methodToBeExecuted = libraryToMethod.get(collectionsLibrary);

        assert methodToBeExecuted != null;

        methodToBeExecuted.accept(plan, blackhole);
    }

    private static void insertIntoMapCopyJava(BaseExecutionPlan plan, Blackhole blackhole) {
        HashMap<Integer, Integer> subject = new HashMap<>();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = new HashMap<>(subject);
            subject.put(plan.random.nextInt(), plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoMapVavr(BaseExecutionPlan plan, Blackhole blackhole) {
        io.vavr.collection.HashMap<Integer, Integer> subject = io.vavr.collection.HashMap.empty();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.put(plan.random.nextInt(), plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoMapPaguro(BaseExecutionPlan plan, Blackhole blackhole) {
        PersistentHashMap<Integer, Integer> subject = PersistentHashMap.empty();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.assoc(plan.random.nextInt(), plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoMapBifurcan(BaseExecutionPlan plan, Blackhole blackhole) {
        io.lacuna.bifurcan.Map<Integer, Integer> subject = io.lacuna.bifurcan.Map.from(Collections.emptyList());

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.put(plan.random.nextInt(), plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoMapPCollections(BaseExecutionPlan plan, Blackhole blackhole) {
        HashPMap<Integer, Integer> subject = HashPMap.empty(IntTreePMap.empty());

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.plus(plan.random.nextInt(), plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoMapCyclops(BaseExecutionPlan plan, Blackhole blackhole) {
        cyclops.data.HashMap<Integer, Integer> subject = cyclops.data.HashMap.empty();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.put(plan.random.nextInt(), plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

}

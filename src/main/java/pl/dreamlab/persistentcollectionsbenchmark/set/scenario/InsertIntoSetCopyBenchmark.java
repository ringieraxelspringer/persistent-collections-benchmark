package pl.dreamlab.persistentcollectionsbenchmark.set.scenario;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.organicdesign.fp.collections.PersistentHashSet;
import org.pcollections.HashTreePSet;
import org.pcollections.MapPSet;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseCollectionBenchmark;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseExecutionPlan;
import pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiConsumer;

import static pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary.*;

public class InsertIntoSetCopyBenchmark extends BaseCollectionBenchmark {
    private static Map<CollectionsLibrary, BiConsumer<BaseExecutionPlan, Blackhole>> libraryToMethod;

    static {
        libraryToMethod = new HashMap<>();
        libraryToMethod.put(JAVA, InsertIntoSetCopyBenchmark::insertIntoSetJava);
        libraryToMethod.put(VAVR, InsertIntoSetCopyBenchmark::insertIntoSetVavr);
        libraryToMethod.put(PAGURO, InsertIntoSetCopyBenchmark::insertIntoSetPaguro);
        libraryToMethod.put(PCOLLECTIONS, InsertIntoSetCopyBenchmark::insertIntoSetPCollections);
        libraryToMethod.put(BIFURCAN, InsertIntoSetCopyBenchmark::insertIntoSetBifurcan);
        libraryToMethod.put(CYCLOPS, InsertIntoSetCopyBenchmark::insertIntoSetCyclops);
    }

    @Benchmark
    public void insertIntoSetCopyBenchmark(BaseExecutionPlan plan, Blackhole blackhole) {
        CollectionsLibrary collectionsLibrary = plan.collectionsLibrary;
        BiConsumer<BaseExecutionPlan, Blackhole> methodToBeExecuted = libraryToMethod.get(collectionsLibrary);

        assert methodToBeExecuted != null;

        methodToBeExecuted.accept(plan, blackhole);
    }

    private static void insertIntoSetJava(BaseExecutionPlan plan, Blackhole blackhole) {
        HashSet<Integer> subject = new HashSet<>();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = new HashSet<>(subject);
            subject.add(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoSetVavr(BaseExecutionPlan plan, Blackhole blackhole) {
        io.vavr.collection.HashSet<Integer> subject = io.vavr.collection.HashSet.of();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.add(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoSetPaguro(BaseExecutionPlan plan, Blackhole blackhole) {
        PersistentHashSet<Integer> subject = PersistentHashSet.empty();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.put(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoSetBifurcan(BaseExecutionPlan plan, Blackhole blackhole) {
        io.lacuna.bifurcan.Set<Integer> subject = io.lacuna.bifurcan.Set.of();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.add(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoSetPCollections(BaseExecutionPlan plan, Blackhole blackhole) {
        MapPSet<Integer> subject = HashTreePSet.empty();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.plus(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }

    private static void insertIntoSetCyclops(BaseExecutionPlan plan, Blackhole blackhole) {
        cyclops.data.HashSet<Integer> subject = cyclops.data.HashSet.empty();

        for (int i = 0; i < plan.numberOfOperations; i++) {
            subject = subject.plus(plan.random.nextInt());
        }

        blackhole.consume(subject);
    }
}

package pl.dreamlab.persistentcollectionsbenchmark.set.scenario;

import io.lacuna.bifurcan.Set;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.organicdesign.fp.collections.PersistentHashSet;
import org.pcollections.MapPSet;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseCollectionBenchmark;
import pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary;
import pl.dreamlab.persistentcollectionsbenchmark.set.plan.SetsInitializingExecutionPlan;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiConsumer;

import static pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary.*;


public class IterateOverSetBenchmark extends BaseCollectionBenchmark {
    private static Map<CollectionsLibrary, BiConsumer<SetsInitializingExecutionPlan, Blackhole>> libraryToMethod;

    static {
        libraryToMethod = new HashMap<>();
        libraryToMethod.put(JAVA, IterateOverSetBenchmark::iterateOverSetJava);
        libraryToMethod.put(VAVR, IterateOverSetBenchmark::iterateOverSetVavr);
        libraryToMethod.put(PAGURO, IterateOverSetBenchmark::iterateOverSetPaguro);
        libraryToMethod.put(PCOLLECTIONS, IterateOverSetBenchmark::iterateOverSetPCollections);
        libraryToMethod.put(BIFURCAN, IterateOverSetBenchmark::iterateOverSetBifurcan);
        libraryToMethod.put(CYCLOPS, IterateOverSetBenchmark::iterateOverSetCyclops);
    }

    @Benchmark
    public void iterateOverSetBenchmark(SetsInitializingExecutionPlan plan, Blackhole blackhole) {
        CollectionsLibrary collectionsLibrary = plan.basePlan.collectionsLibrary;
        BiConsumer<SetsInitializingExecutionPlan, Blackhole> methodToBeExecuted = libraryToMethod.get(collectionsLibrary);

        assert methodToBeExecuted != null;

        methodToBeExecuted.accept(plan, blackhole);
    }

    private static void iterateOverSetJava(SetsInitializingExecutionPlan plan, Blackhole blackhole) {
        HashSet<Integer> subject = plan.basePlan.javaSet;

        for (Integer entry : subject) {
            blackhole.consume(entry);
        }
    }

    private static void iterateOverSetVavr(SetsInitializingExecutionPlan plan, Blackhole blackhole) {
        io.vavr.collection.HashSet<Integer> subject = plan.basePlan.vavrSet;

        for (Integer integer : subject) {
            blackhole.consume(integer);
        }
    }

    private static void iterateOverSetPaguro(SetsInitializingExecutionPlan plan, Blackhole blackhole) {
        PersistentHashSet<Integer> subject = plan.basePlan.paguroSet;

        for (Integer entry : subject) {
            blackhole.consume(entry);
        }
    }

    private static void iterateOverSetPCollections(SetsInitializingExecutionPlan plan, Blackhole blackhole) {
        MapPSet<Integer> subject = plan.basePlan.pcollectionsSet;

        for (Integer entry : subject) {
            blackhole.consume(entry);
        }
    }

    private static void iterateOverSetBifurcan(SetsInitializingExecutionPlan plan, Blackhole blackhole) {
        Set<Integer> subject = plan.basePlan.bifucanSet;

        for (Integer entry : subject) {
            blackhole.consume(entry);
        }
    }

    private static void iterateOverSetCyclops(SetsInitializingExecutionPlan plan, Blackhole blackhole) {
        cyclops.data.HashSet<Integer> cyclopsSet = plan.basePlan.cyclopsSet;

        for (Integer entry : cyclopsSet) {
            blackhole.consume(entry);
        }
    }

}

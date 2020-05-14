package pl.dreamlab.persistentcollectionsbenchmark.map.scenario;

import io.lacuna.bifurcan.IEntry;
import io.vavr.Tuple2;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.organicdesign.fp.collections.PersistentHashMap;
import org.organicdesign.fp.collections.UnmodMap;
import org.pcollections.HashPMap;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseCollectionBenchmark;
import pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary;
import pl.dreamlab.persistentcollectionsbenchmark.map.plan.MapsInitializingExecutionPlan;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary.*;

public class IterateOverMapBenchmark extends BaseCollectionBenchmark {
    private static Map<CollectionsLibrary, BiConsumer<MapsInitializingExecutionPlan, Blackhole>> libraryToMethod;

    static {
        libraryToMethod = new HashMap<>();
        libraryToMethod.put(JAVA, IterateOverMapBenchmark::iterateOverMapJava);
        libraryToMethod.put(VAVR, IterateOverMapBenchmark::iterateOverMapVavr);
        libraryToMethod.put(PAGURO, IterateOverMapBenchmark::iterateOverMapPaguro);
        libraryToMethod.put(PCOLLECTIONS, IterateOverMapBenchmark::iterateOverMapPCollections);
        libraryToMethod.put(BIFURCAN, IterateOverMapBenchmark::iterateOverMapBifurcan);
        libraryToMethod.put(CYCLOPS, IterateOverMapBenchmark::iterateOverMapCyclops);
    }

    @Benchmark
    public void iterateOverMapBenchmark(MapsInitializingExecutionPlan plan, Blackhole blackhole) {
        CollectionsLibrary collectionsLibrary = plan.basePlan.collectionsLibrary;
        BiConsumer<MapsInitializingExecutionPlan, Blackhole> methodToBeExecuted = libraryToMethod.get(collectionsLibrary);

        assert methodToBeExecuted != null;

        methodToBeExecuted.accept(plan, blackhole);
    }

    private static void iterateOverMapJava(MapsInitializingExecutionPlan plan, Blackhole blackhole) {
        HashMap<Integer, Integer> subject = plan.basePlan.javaMap;

        for (Map.Entry<Integer, Integer> entry : subject.entrySet()) {
            blackhole.consume(entry);
        }
    }

    private static void iterateOverMapVavr(MapsInitializingExecutionPlan plan, Blackhole blackhole) {
        io.vavr.collection.HashMap<Integer, Integer> subject = plan.basePlan.vavrMap;

        for (Tuple2<Integer, Integer> entry : subject) {
            blackhole.consume(entry);
        }
    }

    private static void iterateOverMapPaguro(MapsInitializingExecutionPlan plan, Blackhole blackhole) {
        PersistentHashMap<Integer, Integer> subject = plan.basePlan.paguroMap;

        for (UnmodMap.UnEntry<Integer, Integer> entry : subject) {
            blackhole.consume(entry);
        }
    }

    private static void iterateOverMapBifurcan(MapsInitializingExecutionPlan plan, Blackhole blackhole) {
        io.lacuna.bifurcan.Map<Integer, Integer> subject = plan.basePlan.bifucanMap;

        for (IEntry<Integer, Integer> entry : subject) {
            blackhole.consume(entry);
        }
    }

    private static void iterateOverMapPCollections(MapsInitializingExecutionPlan plan, Blackhole blackhole) {
        HashPMap<Integer, Integer> subject = plan.basePlan.pcollectionsMap;

        for (Map.Entry<Integer, Integer> entry : subject.entrySet()) {
            blackhole.consume(entry);
        }
    }

    private static void iterateOverMapCyclops(MapsInitializingExecutionPlan plan, Blackhole blackhole) {
        cyclops.data.HashMap<Integer, Integer> cyclopMap = plan.basePlan.cyclopsMap;

        for (cyclops.data.tuple.Tuple2<Integer, Integer> entry : cyclopMap) {
            blackhole.consume(entry);
        }
    }

}
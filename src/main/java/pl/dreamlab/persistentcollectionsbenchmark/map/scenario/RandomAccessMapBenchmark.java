package pl.dreamlab.persistentcollectionsbenchmark.map.scenario;

import cyclops.data.tuple.Tuple2;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.organicdesign.fp.collections.PersistentHashMap;
import org.pcollections.HashPMap;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseCollectionBenchmark;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseExecutionPlan;
import pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary;
import pl.dreamlab.persistentcollectionsbenchmark.common.RandomAccessTestCasesGenerator;
import pl.dreamlab.persistentcollectionsbenchmark.map.plan.MapsInitializingExecutionPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static java.util.stream.Collectors.toCollection;
import static pl.dreamlab.persistentcollectionsbenchmark.common.CollectionsLibrary.*;

public class RandomAccessMapBenchmark extends BaseCollectionBenchmark {
    private static Map<CollectionsLibrary, BiConsumer<MapRandomAccessExecutionPlan, Blackhole>> libraryToMethod;

    static {
        libraryToMethod = new HashMap<>();
        libraryToMethod.put(JAVA, RandomAccessMapBenchmark::randomAccessMapJava);
        libraryToMethod.put(VAVR, RandomAccessMapBenchmark::randomAccessMapVavr);
        libraryToMethod.put(PAGURO, RandomAccessMapBenchmark::randomAccessMapPaguro);
        libraryToMethod.put(PCOLLECTIONS, RandomAccessMapBenchmark::randomAccessMapPCollections);
        libraryToMethod.put(BIFURCAN, RandomAccessMapBenchmark::randomAccessMapBifurcan);
        libraryToMethod.put(CYCLOPS, RandomAccessMapBenchmark::randomAccessMapCyclops);
    }

    @State(Scope.Benchmark)
    public static class MapRandomAccessExecutionPlan extends BaseExecutionPlan {
        public ArrayList<Integer> toRead;
        public MapsInitializingExecutionPlan basePlan;

        @Setup(Level.Invocation)
        public void setUpInsertData(MapsInitializingExecutionPlan initializingPlan) {
            basePlan = initializingPlan;
            toRead = RandomAccessTestCasesGenerator.generate(initializingPlan.basePlan.numberOfOperations, random,
                    basePlan.toInsert.stream().map(insert -> insert._1).collect(toCollection(ArrayList::new))
            );
        }
    }

    @Benchmark
    public void randomAccessMapBenchmark(MapRandomAccessExecutionPlan plan, Blackhole blackhole) {
        CollectionsLibrary collectionsLibrary = plan.collectionsLibrary;
        BiConsumer<MapRandomAccessExecutionPlan, Blackhole> methodToBeExecuted = libraryToMethod.get(collectionsLibrary);

        assert methodToBeExecuted != null;

        methodToBeExecuted.accept(plan, blackhole);
    }

    private static void randomAccessMapJava(MapRandomAccessExecutionPlan plan, Blackhole blackhole) {
        HashMap<Integer, Integer> subject = plan.basePlan.basePlan.javaMap;

        for (Integer toRead : plan.toRead) {
            blackhole.consume(subject.get(toRead));
        }
    }

    private static void randomAccessMapVavr(MapRandomAccessExecutionPlan plan, Blackhole blackhole) {
        io.vavr.collection.HashMap<Integer, Integer> subject = plan.basePlan.basePlan.vavrMap;

        for (Integer toRead : plan.toRead) {
            blackhole.consume(subject.get(toRead));
        }
    }

    private static void randomAccessMapPaguro(MapRandomAccessExecutionPlan plan, Blackhole blackhole) {
        PersistentHashMap<Integer, Integer> subject = plan.basePlan.basePlan.paguroMap;

        for (Integer toRead : plan.toRead) {
            blackhole.consume(subject.get(toRead));
        }
    }

    private static void randomAccessMapBifurcan(MapRandomAccessExecutionPlan plan, Blackhole blackhole) {
        io.lacuna.bifurcan.Map<Integer, Integer> subject = plan.basePlan.basePlan.bifucanMap;

        for (Integer toRead : plan.toRead) {
            blackhole.consume(subject.get(toRead));
        }
    }

    private static void randomAccessMapPCollections(MapRandomAccessExecutionPlan plan, Blackhole blackhole) {
        HashPMap<Integer, Integer> subject = plan.basePlan.basePlan.pcollectionsMap;

        for (Integer toRead : plan.toRead) {
            blackhole.consume(subject.get(toRead));
        }
    }

    private static void randomAccessMapCyclops(MapRandomAccessExecutionPlan plan, Blackhole blackhole) {
        cyclops.data.HashMap<Integer, Integer> cyclopsMap = plan.basePlan.basePlan.cyclopsMap;

        for (Tuple2<Integer, Integer> entry : cyclopsMap) {
            blackhole.consume(entry);
        }
    }
}

package pl.dreamlab.persistentcollectionsbenchmark.map.plan;

import io.vavr.Tuple2;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.organicdesign.fp.collections.PersistentHashMap;
import org.pcollections.HashPMap;
import org.pcollections.IntTreePMap;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseExecutionPlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static java.util.stream.IntStream.range;


@State(Scope.Benchmark)
public class MapsInitializingExecutionPlan {
    public BaseMapsExecutionPlan basePlan;
    public ArrayList<Tuple2<Integer, Integer>> toInsert;

    @State(Scope.Benchmark)
    public static class BaseMapsExecutionPlan extends BaseExecutionPlan {
        public HashMap<Integer, Integer> javaMap;
        public io.vavr.collection.HashMap<Integer, Integer> vavrMap;
        public PersistentHashMap<Integer, Integer> paguroMap;
        public HashPMap<Integer, Integer> pcollectionsMap;
        public io.lacuna.bifurcan.Map<Integer, Integer> bifucanMap;
        public cyclops.data.HashMap<Integer, Integer> cyclopsMap;
    }

    @Setup(Level.Invocation)
    public void setUp(BaseMapsExecutionPlan plan) {
        basePlan = plan;
        toInsert = new ArrayList<>();

        for (int i = 0; i < basePlan.numberOfOperations; i++) {
            Tuple2<Integer, Integer> tuple = new Tuple2<>(basePlan.random.nextInt(), basePlan.random.nextInt());
            toInsert.add(tuple);
        }

        switch (plan.collectionsLibrary) {
            case JAVA:
                initJava(plan, toInsert);
                break;
            case VAVR:
                initVavr(plan, toInsert);
                break;
            case PCOLLECTIONS:
                initPcollections(plan, toInsert);
                break;
            case PAGURO:
                initPaguro(plan, toInsert);
                break;
            case BIFURCAN:
                initBifurcan(plan, toInsert);
                break;
            case CYCLOPS:
                initCyclops(plan, toInsert);
                break;
        }
    }

    private void initJava(BaseMapsExecutionPlan plan, ArrayList<Tuple2<Integer, Integer>> toInsert) {
        plan.javaMap = new HashMap<>();

        range(0, plan.numberOfOperations).forEach(i -> plan.javaMap.put(toInsert.get(i)._1(), toInsert.get(i)._2()));
    }

    private void initVavr(BaseMapsExecutionPlan plan, ArrayList<Tuple2<Integer, Integer>> toInsert) {
        plan.vavrMap = io.vavr.collection.HashMap.empty();

        range(0, plan.numberOfOperations)
                .forEach(i -> plan.vavrMap = plan.vavrMap.put(toInsert.get(i)._1(), toInsert.get(i)._2()));
    }

    private void initPaguro(BaseMapsExecutionPlan plan, ArrayList<Tuple2<Integer, Integer>> toInsert) {
        plan.paguroMap = PersistentHashMap.empty();

        range(0, plan.numberOfOperations)
                .forEach(i -> plan.paguroMap = plan.paguroMap.assoc(toInsert.get(i)._1(), toInsert.get(i)._2()));
    }

    private void initPcollections(BaseMapsExecutionPlan plan, ArrayList<Tuple2<Integer, Integer>> toInsert) {
        plan.pcollectionsMap = HashPMap.empty(IntTreePMap.empty());

        range(0, plan.numberOfOperations)
                .forEach(i -> plan.pcollectionsMap = plan.pcollectionsMap.plus(toInsert.get(i)._1(), toInsert.get(i)._2()));
    }

    private void initBifurcan(BaseMapsExecutionPlan plan, ArrayList<Tuple2<Integer, Integer>> toInsert) {
        plan.bifucanMap = io.lacuna.bifurcan.Map.from(Collections.emptyList());

        range(0, plan.numberOfOperations)
                .forEach(i -> plan.bifucanMap = plan.bifucanMap.put(toInsert.get(i)._1(), toInsert.get(i)._2()));
    }

    private void initCyclops(BaseMapsExecutionPlan plan, ArrayList<Tuple2<Integer, Integer>> toInsert) {
        plan.cyclopsMap = cyclops.data.HashMap.empty();

        range(0, plan.numberOfOperations)
                .forEach(i -> plan.cyclopsMap = plan.cyclopsMap.put(toInsert.get(i)._1(), toInsert.get(i)._2()));
    }
}

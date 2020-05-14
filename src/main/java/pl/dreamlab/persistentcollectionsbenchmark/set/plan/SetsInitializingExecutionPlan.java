package pl.dreamlab.persistentcollectionsbenchmark.set.plan;

import io.lacuna.bifurcan.Set;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.organicdesign.fp.collections.PersistentHashSet;
import org.pcollections.HashTreePSet;
import org.pcollections.MapPSet;
import pl.dreamlab.persistentcollectionsbenchmark.common.BaseExecutionPlan;

import java.util.ArrayList;
import java.util.HashSet;

import static java.util.stream.IntStream.range;


@State(Scope.Benchmark)
public class SetsInitializingExecutionPlan {
    public BaseSetsExecutionPlan basePlan;
    public ArrayList<Integer> toInsert;

    @State(Scope.Benchmark)
    public static class BaseSetsExecutionPlan extends BaseExecutionPlan {
        public HashSet<Integer> javaSet;
        public io.vavr.collection.HashSet<Integer> vavrSet;
        public PersistentHashSet<Integer> paguroSet;
        public MapPSet<Integer> pcollectionsSet;
        public Set<Integer> bifucanSet;
        public cyclops.data.HashSet<Integer> cyclopsSet;
    }

    @Setup(Level.Invocation)
    public void setUp(BaseSetsExecutionPlan plan) {
        basePlan = plan;
        toInsert = new ArrayList<>();

        for (int i = 0; i < basePlan.numberOfOperations; i++) {
            toInsert.add(basePlan.random.nextInt());
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

    private void initCyclops(BaseSetsExecutionPlan plan, ArrayList<Integer> toInsert) {
        plan.cyclopsSet = cyclops.data.HashSet.empty();

        range(0, plan.numberOfOperations).forEach(i -> plan.cyclopsSet = plan.cyclopsSet.add(toInsert.get(i)));
    }

    private void initJava(BaseSetsExecutionPlan plan, ArrayList<Integer> toInsert) {
        plan.javaSet = new HashSet<>();

        range(0, plan.numberOfOperations).forEach(i -> plan.javaSet.add(toInsert.get(i)));
    }

    private void initVavr(BaseSetsExecutionPlan plan, ArrayList<Integer> toInsert) {
        plan.vavrSet = io.vavr.collection.HashSet.of();

        range(0, plan.numberOfOperations).forEach(i -> plan.vavrSet = plan.vavrSet.add(toInsert.get(i)));
    }

    private void initPaguro(BaseSetsExecutionPlan plan, ArrayList<Integer> toInsert) {
        plan.paguroSet = PersistentHashSet.empty();

        range(0, plan.numberOfOperations).forEach(i -> plan.paguroSet = plan.paguroSet.put(toInsert.get(i)));
    }

    private void initPcollections(BaseSetsExecutionPlan plan, ArrayList<Integer> toInsert) {
        plan.pcollectionsSet = HashTreePSet.empty();

        range(0, plan.numberOfOperations).forEach(i -> plan.pcollectionsSet = plan.pcollectionsSet.plus(toInsert.get(i)));
    }

    private void initBifurcan(BaseSetsExecutionPlan plan, ArrayList<Integer> toInsert) {
        plan.bifucanSet = io.lacuna.bifurcan.Set.of();

        range(0, plan.numberOfOperations).forEach(i -> plan.bifucanSet = plan.bifucanSet.add(toInsert.get(i)));
    }
}

package pl.dreamlab.persistentcollectionsbenchmark.list.plan;

import cyclops.data.Seq;
import io.vavr.collection.Vector;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.organicdesign.fp.collections.PersistentVector;
import org.pcollections.ConsPStack;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.util.stream.IntStream.range;


@State(Scope.Benchmark)
public class ListsInitializingExecutionPlan extends BaseListExecutionPlan {
    public BaseListExecutionPlan basePlan;

    @Setup(Level.Invocation)
    public void setUp(BaseListExecutionPlan plan) {
        this.basePlan = plan;

        switch (plan.listCollection) {
            case JAVA:
                initJavaLinkedList(plan);
                break;
            case JAVA_ARRAY_LIST:
                initJavaArrayList(plan);
                break;
            case VAVR:
                initVavrList(plan);
                break;
            case VAVR_VECTOR:
                initVavrVector(plan);
                break;
            case PCOLLECTIONS:
                initPcollections(plan);
                break;
            case PAGURO:
                initPaguro(plan);
                break;
            case BIFURCAN:
                initBifurcan(plan);
                break;
            case CYCLOPS:
                initCyclops(plan);
                break;
        }
    }

    private void initJavaArrayList(BaseListExecutionPlan plan) {
        plan.javaArrayList = new ArrayList<>();
        range(0, plan.numberOfOperations).forEach(i -> plan.javaArrayList.add(plan.random.nextInt()));
    }

    private void initVavrVector(BaseListExecutionPlan plan) {
        plan.vavrVector = Vector.empty();
        range(0, plan.numberOfOperations).forEach(i -> plan.vavrVector = plan.vavrVector.append(plan.random.nextInt()));
    }

    private void initJavaLinkedList(BaseListExecutionPlan plan) {
        plan.javaList = new LinkedList<>();
        range(0, plan.numberOfOperations).forEach(i -> plan.javaList.add(plan.random.nextInt()));
    }

    private void initVavrList(BaseListExecutionPlan plan) {
        plan.vavrList = io.vavr.collection.List.of();
        range(0, plan.numberOfOperations).forEach(i -> plan.vavrList = plan.vavrList.append(plan.random.nextInt()));
    }

    private void initPaguro(BaseListExecutionPlan plan) {
        plan.paguroList = PersistentVector.empty();
        range(0, plan.numberOfOperations).forEach(i -> plan.paguroList = plan.paguroList.append(plan.random.nextInt()));
    }

    private void initPcollections(BaseListExecutionPlan plan) {
        plan.pcollectionsList = ConsPStack.empty();
        range(0, plan.numberOfOperations).forEach(i -> plan.pcollectionsList = plan.pcollectionsList.plus(plan.random.nextInt()));
    }

    private void initBifurcan(BaseListExecutionPlan plan) {
        plan.bifucanList = io.lacuna.bifurcan.List.of();
        range(0, plan.numberOfOperations).forEach(i -> plan.bifucanList = plan.bifucanList.addLast(plan.random.nextInt()));
    }

    private void initCyclops(BaseListExecutionPlan plan) {
        plan.cyclopsList = Seq.empty();
        range(0, plan.numberOfOperations).forEach(i -> plan.cyclopsList = plan.cyclopsList.plus(plan.random.nextInt()));
    }

}

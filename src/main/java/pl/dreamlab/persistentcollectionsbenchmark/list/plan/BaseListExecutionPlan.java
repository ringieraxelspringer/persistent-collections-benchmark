package pl.dreamlab.persistentcollectionsbenchmark.list.plan;

import cyclops.data.Seq;
import io.vavr.collection.Vector;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.organicdesign.fp.collections.PersistentVector;
import org.pcollections.ConsPStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

@State(Scope.Benchmark)
public class BaseListExecutionPlan {
    public LinkedList<Integer> javaList;
    public ArrayList<Integer> javaArrayList;
    public io.vavr.collection.List<Integer> vavrList;
    public Vector<Integer> vavrVector;
    public PersistentVector<Integer> paguroList;
    public ConsPStack<Integer> pcollectionsList;
    public io.lacuna.bifurcan.List<Integer> bifucanList;
    public Seq<Integer> cyclopsList;

    @Param({"JAVA", "JAVA_ARRAY_LIST", "PAGURO", "VAVR", "VAVR_VECTOR", "PCOLLECTIONS", "BIFURCAN", "CYCLOPS"})
    public ListsCollections listCollection;

    @Param({"100", "1000", "10000"})
    public int numberOfOperations;

    public Random random = new Random(currentTimeMillis());
}
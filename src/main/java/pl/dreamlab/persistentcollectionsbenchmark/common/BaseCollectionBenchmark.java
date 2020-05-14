package pl.dreamlab.persistentcollectionsbenchmark.common;

import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static pl.dreamlab.persistentcollectionsbenchmark.common.BenchmarkConfig.*;


@Threads(THREADS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = FORK_VALUE)
@Warmup(iterations = WARMUP_ITERATIONS)
@Measurement(iterations = ITERATIONS, time = ITERATION_TIME_SECONDS)
@Timeout(time = ITERATION_TIMEOUT_SECONDS)
@OutputTimeUnit(NANOSECONDS)
public class BaseCollectionBenchmark {
}

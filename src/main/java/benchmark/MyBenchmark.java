package benchmark;
import bloomfilter.BloomFilterArray;
import bloomfilter.BloomFilterArrayList;
import bloomfilter.BloomFilterLinkedList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 1, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(3)
@State(Scope.Benchmark)
public class MyBenchmark {
    @Param({"1000", "10000", "1000000"})
    public static int arraySize;

    @Param({"20"})
    public static int nbHashFunctions;

    BloomFilterLinkedList bloomFilterLinkedList;
    BloomFilterArray bloomFilterArray;
    BloomFilterArrayList bloomFilterArrayList;

    @Setup
    public void setup(){
        bloomFilterArray = new BloomFilterArray(arraySize, nbHashFunctions);
        bloomFilterLinkedList = new BloomFilterLinkedList(arraySize, nbHashFunctions);
        bloomFilterArrayList = new BloomFilterArrayList(arraySize, nbHashFunctions);
    }

    @Benchmark
    public void time_add_linkedlist(Blackhole bh){
        bloomFilterLinkedList.add(938407180.92364);
        bh.consume(bloomFilterLinkedList);
    }

    @Benchmark
    public void time_add_arraylist(Blackhole bh){
        bloomFilterArrayList.add(938407180.92364);
        bh.consume(bloomFilterArrayList);
    }

    @Benchmark
    public void time_add_array(Blackhole bh){
        bloomFilterArray.add(938407180.92364);
        bh.consume(bloomFilterArray);
    }

    @Benchmark
    public boolean time_isPresent_linkedlist(){
        return bloomFilterLinkedList.isPresent("En Saga, Op.9 - Jean Sibelius");
    }

    @Benchmark
    public boolean time_isPresent_arraylist(){
        return bloomFilterArrayList.isPresent("En Saga, Op.9 - Jean Sibelius");
    }

    @Benchmark
    public boolean time_isPresent_array(){
        return bloomFilterArray.isPresent("En Saga, Op.9 - Jean Sibelius");
    }

    public static void main() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

}

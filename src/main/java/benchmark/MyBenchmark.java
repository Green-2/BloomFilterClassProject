package benchmark;
import bloomfilter.BloomFilterArray;
import bloomfilter.BloomFilterArrayList;
import bloomfilter.BloomFilterLinkedList;
import bloomfilter.CSVWriter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 1, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(3)
@State(Scope.Benchmark)
public class MyBenchmark {
    static private final Random r = new Random();
    static private BloomFilterLinkedList bloomFilterLinkedList;
    static private BloomFilterArray bloomFilterArray;
    static private BloomFilterArrayList bloomFilterArrayList;

    @Param({"100", "1000", "10000", "100000", "1000000"})
    public static int arraySize;

    @Param({"5"})
    public static int nbHashFunctions;



    @Setup
    public void setup(){
        bloomFilterArray = new BloomFilterArray(arraySize, nbHashFunctions);
        bloomFilterLinkedList = new BloomFilterLinkedList(arraySize, nbHashFunctions);
        bloomFilterArrayList = new BloomFilterArrayList(arraySize, nbHashFunctions);
    }
/*
    @Benchmark
    public void time_add_linkedlist(Blackhole bh){
        bloomFilterLinkedList.add(938407180.92364);
        bh.consume(bloomFilterLinkedList);
    }

*/
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


    /**
     * Puts random ints into all three filters.
     * @param amount The amount of ints to add.
     */
    private static void putRandomElementsInFilters(int amount){
        int element;
        for(int i=0; i<amount; i++) {
            element = r.nextInt(Integer.MAX_VALUE);
            bloomFilterArray.add(element);
            bloomFilterArrayList.add(element);
            bloomFilterLinkedList.add(element);
        }
    }

    /**
     * Tests the error rate for all bloom filters and returns a list of results.
     * This function recreates all static filters and puts random elements into them, then tests them according
     * to the parameters passed.
     * <br><br>
     * It tests the false positive rate for 1000, 10000 and 100000 elements added to the filters, in the form of random
     * integers.
     * <br><br>
     * The filters get a length of 1000*relativeLength, 10000*relativeLength and 100000*relativeLength (There
     * are three iterations.)
     *
     * @param relativeLength Expressed in percentage (at least 100 to make sense) this parameter gives the filters a
     *                       length according to the amount of elements added to the filters for each iteration.
     * @param k              Number of hash functions the bloom filters should use.
     */
    private static ArrayList<String> testErrorRate(int relativeLength, int k){
        int filterLength = 10000 *(relativeLength/100);
        bloomFilterArray = new BloomFilterArray(filterLength, k);
        bloomFilterArrayList = new BloomFilterArrayList(filterLength, k);
        bloomFilterLinkedList = new BloomFilterLinkedList(filterLength, k);
        putRandomElementsInFilters(10000);
        int falseAmountLinkedList = 0;
        int falseAmountArrayList = 0;
        int falseAmountArray = 0;
        int amountOfTests = 10000;
        for(int y = 0; y < amountOfTests; y++){
            if(bloomFilterArray.isPresent(r.nextFloat())){
                falseAmountArray++;
            }
            if(bloomFilterArrayList.isPresent(r.nextFloat())){
                falseAmountArrayList++;
            }
            if(bloomFilterLinkedList.isPresent(r.nextFloat())){
                falseAmountLinkedList++;
            }
        }

        ArrayList<String> results = new ArrayList<>();
        int arrayResults = ((falseAmountArray*100)/amountOfTests);
        int arrayListResults = ((falseAmountArrayList*100)/amountOfTests);
        int linkedListResults = ((falseAmountLinkedList*100)/amountOfTests);
        results.add("Array"+ "," + (""+relativeLength) + "," + (""+arrayResults));
        results.add("ArrayList" + "," + (""+relativeLength) + "," + (""+arrayListResults));
        results.add("LinkedList"+ "," + (""+relativeLength) + "," + (""+linkedListResults));

        System.out.println("False positive rate for an array bloom filter with a length of " + filterLength +
                " and " + 10000 + " elements added to it  " + arrayResults + "%");
        System.out.println("False positive rate for an ArrayList bloom filter with a length of " + filterLength +
                " and " + 10000 + " elements added to it : " + arrayListResults + "%");
        System.out.println("False positive rate for a LinkedList bloom filter with a length of " + filterLength +
                " and " + 10000 + " elements added to it : " + linkedListResults + "%");

        return results;
    }

    public static void testPerformance() throws RunnerException, IOException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .resultFormat(ResultFormatType.CSV)
                .build();

        new Runner(opt).run();
        ArrayList<String> tocsv = new ArrayList<>();
        tocsv.add("Data Structure,Relative filter length (% of elements added to filter), False positive rate (%)");
        tocsv.addAll(testErrorRate(300, 3));
        tocsv.addAll(testErrorRate(500, 3));
        tocsv.addAll(testErrorRate(800, 3));
        tocsv.addAll(testErrorRate(1000, 3));
        CSVWriter.makeCSV(tocsv, "error_rates_results.csv");
    }
}

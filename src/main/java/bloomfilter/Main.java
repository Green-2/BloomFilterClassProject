
package bloomfilter;

import benchmark.MyBenchmark;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws RunnerException, IOException {
        MyBenchmark.testPerformance();
    }
}

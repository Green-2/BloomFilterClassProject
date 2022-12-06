package bloomfilterTest;

import bloomfilter.BloomFilterArray;
import bloomfilter.BloomFilterArrayList;
import bloomfilter.BloomFilterInterface;
import bloomfilter.BloomFilterLinkedList;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class BloomFilterTest {

    public BloomFilterInterface filter;

    /**
     * Fills a given filter with some data, which will be tested.
     * @param filterToTest Filter to fill.
     */
    public void setTestValues(BloomFilterInterface filterToTest){
        filter = filterToTest;
        filter.add("Lay Down - Caravan Palace");
        filter.add("The little man who wasn't there - Odd Chap");
        filter.add("My Game - Deluxe");
        filter.add(796763.5893);
    }

    /**
     * Tests whether the filter gives correct answers when asked.
     */
    private void testFilter(){
        assertTrue(filter.isPresent("Lay Down - Caravan Palace"));
        assertTrue(filter.isPresent("The little man who wasn't there - Odd Chap"));
        assertTrue(filter.isPresent("My Game - Deluxe"));
        assertTrue(filter.isPresent(796763.5893));
        assertFalse(filter.isPresent("The Swan of Tuonela, Op 22, No.2 : Andante molto sostenuto"));
        assertFalse(filter.isPresent(796763.589));
    }

    /**
     * Calls the testFilter() function for each kind of Filter.
     */
    @Test
    public void testAllFilterIntegrity(){
        setTestValues(new BloomFilterArray(1024));
        testFilter();
        setTestValues(new BloomFilterArrayList(1024));
        testFilter();
        setTestValues(new BloomFilterLinkedList(1024));
        testFilter();
    }
}
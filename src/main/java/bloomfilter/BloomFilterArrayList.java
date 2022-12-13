package bloomfilter;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;

/**
 * Implementation of a Bloom filter using an arraylist as a
 */
public class BloomFilterArrayList implements BloomFilterInterface{
    final private ArrayList<Integer> bitArray;
    final private int arrayLength;
    final private int k;

    @Override
    public void add(Object object) {
        if (object == null) {
            System.err.println("There was an error whilst adding an element to the filter; object can't be null.");
            return;
        }
        for (int hashID=1; hashID<=k; hashID++) {
            this.bitArray.set(abs(MyHash.hash(object, hashID)) % this.arrayLength, 1);
        }
    }
    /**
     * Constructor for a bloom filter using an arrayList for its bit array.
     * @param arrayLength length of the array
     * @param k amount of hash functions
     */
    public BloomFilterArrayList(int arrayLength, int k) {
        this.arrayLength = arrayLength;
        this.bitArray = new ArrayList<>(Collections.nCopies(arrayLength, 0));
        this.k = k;
    }

    @Override
    public boolean isPresent(Object object) {
        for(int hashID=1; hashID<=k; hashID++) {
            if(this.bitArray.get(abs(MyHash.hash(object, hashID)) % this.arrayLength) == 0)
                return false;
        }
        return true;
    }
}

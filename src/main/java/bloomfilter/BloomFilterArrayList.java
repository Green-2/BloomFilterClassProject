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

    @Override
    public void add(Object object) {
        if (object == null){
            System.err.println("There was an error whilst adding an element to the filter; object can't be null.");
            return;
        }
        this.bitArray.set(abs(Utils.hashFunc1(object) % this.arrayLength), 1);
        this.bitArray.set(abs(Utils.hashFunc2(object) % this.arrayLength), 1);
        this.bitArray.set(abs(Utils.hashFunc3(object) % this.arrayLength), 1);
    }

    /**
     * Constructor for a bloom filter using an arrayList for its bit array.
     * @param arrayLength length of the array
     */
    public BloomFilterArrayList(int arrayLength) {
        this.arrayLength = arrayLength;
        this.bitArray = new ArrayList<>(Collections.nCopies(arrayLength, 0));
    }

    @Override
    public boolean isPresent(Object object) {
        return this.bitArray.get(abs(Utils.hashFunc1(object) % this.arrayLength)) == 1
                && this.bitArray.get(abs(Utils.hashFunc2(object) % this.arrayLength)) == 1
                && this.bitArray.get(abs(Utils.hashFunc3(object) % this.arrayLength)) == 1
                ;
    }
}

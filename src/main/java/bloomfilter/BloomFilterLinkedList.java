package bloomfilter;

import java.util.Collections;
import java.util.LinkedList;

import static java.lang.Math.abs;

public class BloomFilterLinkedList implements BloomFilterInterface {

    final private LinkedList<Integer> bitList;
    final private int listLength;
    final private int k;


    /**
     * Constructor for a Bloom filter using a LinkedList for its bit array.
     * @param arrayLength The length of the filter.
     * @param k The number of hash functions the filter should use.
     */
    public BloomFilterLinkedList(int arrayLength, int k) {
        this.bitList = new LinkedList<>(Collections.nCopies(arrayLength, 0));
        this.listLength = arrayLength;
        this.k = k;
    }

    @Override
    public void add(Object object) {
        if (object == null){
            System.err.println("There was an error whilst adding an element to the filter; object can't be null.");
            return;
        }
        for(int hashID=0; hashID<k; hashID++){
            this.bitList.set(abs(MyHash.hash(object, hashID)) % this.listLength, 1);
        }
    }


    @Override
    public boolean isPresent(Object object) {
        for (int hashID = 0; hashID < k; hashID++) {
            if (this.bitList.get(abs(MyHash.hash(object, hashID) % this.listLength)) == 0)
                return false;
        }
        return true;
    }
}

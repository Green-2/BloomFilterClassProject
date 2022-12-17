package bloomfilter;

import static java.lang.Math.abs;

public class BloomFilterArray implements BloomFilterInterface{

    final private int[] bitArray;
    final private int arrayLength;
    final private int k;

    /**
     * Constructor for a bloom filter using an ArrayList.
     * @param arrayLength length of the filter.
     * @param k amount of hash functions.
     */
    public BloomFilterArray(int arrayLength, int k){
        this.arrayLength = arrayLength;
        this.bitArray = new int[arrayLength];
        this.k = k;
    }

    @Override
    public void add(Object object) {
        if (object == null){
            System.err.println("There was an error whilst adding an element to the filter; object can't be null.");
            return;
        }
        for(int hashID=1; hashID<=k; hashID++){
            this.bitArray[abs(MyHash.hash(object, hashID) % this.arrayLength)]= 1;
        }
    }

    @Override
    public boolean isPresent(Object object) {
        for(int hashID=1; hashID<=k; hashID++){
            if(this.bitArray[abs(MyHash.hash(object, hashID)) % this.arrayLength] == 0)
                return false;
        }
        return true;
    }
}

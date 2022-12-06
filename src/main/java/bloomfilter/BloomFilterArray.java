package bloomfilter;

import static java.lang.Math.abs;

public class BloomFilterArray implements BloomFilterInterface{

    final private int[] bitArray;
    final private int arrayLength;

    public BloomFilterArray(int arrayLength){
        this.arrayLength = arrayLength;
        this.bitArray = new int[arrayLength];
    }

    /**
     *
     * @param object
     */
    @Override
    public void add(Object object) {
        if (object == null){
            System.err.println("There was an error whilst adding an element to the filter; object can't be null.");
            return;
        }
        this.bitArray[abs(Utils.hashFunc1(object) % this.arrayLength)]= 1;
        this.bitArray[abs(Utils.hashFunc2(object) % this.arrayLength)]= 1;
        this.bitArray[abs(Utils.hashFunc3(object) % this.arrayLength)]= 1;
    }

    @Override
    public boolean isPresent(Object object) {
        return this.bitArray[abs(Utils.hashFunc1(object) % this.arrayLength)] == 1
                && this.bitArray[abs(Utils.hashFunc2(object) % this.arrayLength)] == 1
                && this.bitArray[abs(Utils.hashFunc3(object) % this.arrayLength)] == 1
                ;
    }
}

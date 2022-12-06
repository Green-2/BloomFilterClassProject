package bloomfilter;

import java.util.Collections;
import java.util.LinkedList;

import static java.lang.Math.abs;

public class BloomFilterLinkedList implements BloomFilterInterface {

    final private LinkedList<Integer> bitList;
    final private int listLength;

    public BloomFilterLinkedList(int arrayLength) {
        this.bitList = new LinkedList<>(Collections.nCopies(arrayLength, 0));
        this.listLength = arrayLength;
    }

    @Override
    public void add(Object object) {
        if (object == null){
            System.err.println("There was an error whilst adding an element to the filter; object can't be null.");
            return;
        }
        this.bitList.set(abs(Utils.hashFunc1(object) % this.listLength), 1);
        this.bitList.set(abs(Utils.hashFunc2(object) % this.listLength), 1);
        this.bitList.set(abs(Utils.hashFunc3(object) % this.listLength), 1);
    }


    @Override
    public boolean isPresent(Object object) {
        return this.bitList.get(abs(Utils.hashFunc1(object) % this.listLength)) == 1
                && this.bitList.get(abs(Utils.hashFunc2(object) % this.listLength)) == 1
                && this.bitList.get(abs(Utils.hashFunc3(object) % this.listLength)) == 1
                ;
    }
}

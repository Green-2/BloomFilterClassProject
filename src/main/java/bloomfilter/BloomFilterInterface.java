/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bloomfilter;

/**
 * Interface for a bloom filter.
 * Should be used to create a bloom filter object with an array type of your choice.
 * @author olivierpergande
 */
public interface BloomFilterInterface {

    /**
     * Adds an object's hash to the filter.
     * Does nothing with an error output if the object is null.
     * @param object The object to add.
     */
    public void add(Object object);


    /**
     * Checks whether an object's hash is present in the bit array.
     * @param object Object to check.
     * @return true if the hash is present, false if not.
     */
    public boolean isPresent(Object object);
}

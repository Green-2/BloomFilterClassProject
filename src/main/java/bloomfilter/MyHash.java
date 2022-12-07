package bloomfilter;

/**
 * Single use class. Implements a parametrized hash function.
 */
public class MyHash {
    /**
     * Should not be called.
     */
    private MyHash(){}

    /**
     * Parametrized hash function for any Object.
     * @param o The object.
     * @param n A parameter used to generate different hashes.
     * @return A hash generated with Java's hashCode() and multiplications applied to it.
     */
    static public int hash(Object o, int n){
        if(o instanceof Integer && (int)o < 0){
            return o.hashCode() * 379 * n;
        }
        return o.hashCode() * n;
    }
}

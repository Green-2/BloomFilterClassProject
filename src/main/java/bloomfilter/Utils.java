package bloomfilter;

public class Utils {

    static public int hashFunc1(Object o){
        return o.hashCode();
    }

    static public int hashFunc2(Object o){
        return o.hashCode() * 31 ;
    }

    static public int hashFunc3(Object o){
        return o.hashCode() * 89 ;
    }

}

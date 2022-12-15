# My introduction to bloom filters — and JMH.
>A project introducing myself to bloom filters, conducted during the first 
>semester of my second year as a CS student. As I had to benchmark my code, 
>I decided to learn how to use JMH. 


## Specifications & Run instructions
This project is fully written with Java and maven as project manager. However, I don't recommend launching the project with maven, as 
I have not thought it to work that way and it very probably won't work.

Use your favorite development interface and start the main function in the Main class.
This will launch the Benchmarks. Be aware that this will take some time, expect it to take 6 minutes or more with the 
default configuration.

## What is a Bloom filter ? And how is it implemented here ?

### Short explanation
A Bloom filter is a data structure having a list of bits able to do two things. 
- Check that an object *is not* in the filter with a **100% probability**
- Check that an object *is* in the filter with **a certain probability < 100%**

"But how is this certain probability defined ?", you might ask. That is the fun part. We will talk about that a little bit later.
First, let me explain how we add an object to a bloom filter. It's really simple !

At the beginning of the process, we must decide the <ins>**k**</ins> of the bloom filter we're working with.
The k of a Bloom filter is the **amount of hash functions** it uses to store objects.

When an object is getting added to the filter, the filter generates *k* hashes of the objects and passes all corresponding
bits to 1. Of course, the hashes are being applied a modulo on the length of the array. 

For example, if a bloom filter starts with a bit list of [0,0,0,0,0,0,0,0,0,0], the filter has k=2, and we want to add an object.
Let's say the object's hashes are 61 and 34. In this case, the filter will be updated to [0,1,0,1,0,0,0,0,0,0].
This really is just a very short introduction. To get a better understanding of this probabilistic data structure,
I recommend getting a look at the <a href="https://en.wikipedia.org/wiki/Bloom_filter">Wikipedia article</a> on the matter.

### Implementation 

Three Bloom filters exist in this project : 
- [BloomFilterArray](src/main/java/bloomfilter/BloomFilterArray.java), using a java array to store its objects
- [BloomFilterArrayList](src/main/java/bloomfilter/BloomFilterArrayList.java), using an ArrayList to store its objects 
- [BloomFilterLinkedList](src/main/java/bloomfilter/BloomFilterLinkedList.java), using a LinkedList to sotre its objects
>*It's easy, isn't it?*

You can look at the code for yourself, but I want explain it to you as best as I can. 
However, you can look at it directly and read the documentation, it should help your understanding. 

All three bloom filters implement the same functions. That's why 
[an interface](src/main/java/bloomfilter/BloomFilterInterface.java) with these functions 
makes up the base of all filters. 
```java 
public interface BloomFilterInterface {

    public void add(Object object);

    public boolean isPresent(Object object);
}
```

The only difference between all the filters is the data structure used to represent the bit set of the filters. 

That's why you will find 
```java 
final private Integer[] bitArray;
```
For the Bloom filter using a java array; 

```java 
final private ArrayList<Integer> bitArray;
```
For the bloom filter using an ArrayList; 

```java 
final private LinkedList<Integer> bitList;
```
For the Bloom filter using a LinkedList.



The **k** and the **length** of all Bloom filters are passed as an argument in the constructor. Once set, they can't be 
changed. So be aware that if you want to change them, you have to create new filters. 

The implementation of the ```add(Object)``` functionality is basically the same for each filter type : 

```java 
    @Override
    public void add(Object object) {
        if (object == null) {
            System.err.println("There was an error whilst adding an element to the filter; object can't be null.");
            return;
        }
        for (int hashID=1; hashID<=k; hashID++) {
            //ADD THE HASH OF THE OBJECT TO THE FILTER'S ARRAY
        }
    }
```

And the ```isPresent(Object)``` method doesn't look very different : 
```java 
    @Override
    public boolean isPresent(Object object) {
        for (int hashID=1; hashID<=k; hashID++) {
            if ((/* THE ELEMENT IN THE FILTER'S ARRAY AT THE HASHE'S INDEX */) == 0)
                return false;
        }
        return true;
    }
```

When running an ``isPresent(Object)`` method, it returns false as soon as it encounters a 0, which means that the object 
hasn't been added to the filter.
However, if all filter's bits at hashes of the object are set to true by other objects, it can return true even though 
the tested object hasn't been added.

Notice how the amount of hashes checked (and added) depends on the **k** of the filter.

#### Hashes 

Talking about hashes, the way I chose to implement them is very simple as well. 

There is a [MyHash](src/main/java/bloomfilter/MyHash.java) class implementing a single function : ```hash(object o, int n)``` where `o` is the object to hash
and n the *iteration in which the hash is used*. For instance, the implementation of the add functionality for the 
``BloomFilterArray`` is the following : 

```java 
    @Override
    public void add(Object object) {
        if (object == null){
            System.err.println("There was an error whilst adding an element to the filter; object can't be null.");
            return;
        }
        for(int hashID=1; hashID<=k; hashID++){
-->         this.bitArray[abs(MyHash.hash(object, hashID) % this.arrayLength)]= 1;
        }
    }
```

The hash function does nothing really special. It takes java's ``.hash()`` and multiplies it by ``n``, except for negative
integers, where it first multiplies it by a prime number, so that opposite numbers don't have the same hash.

## Benchmarking the filters 

> First, what does it mean ? 
> For this project, we want to measure how long operations take to execute. 
> 
> That's to say, we want to know which of them is the most efficient (takes the the least time)
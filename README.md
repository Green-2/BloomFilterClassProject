# My introduction to bloom filters — and JMH.
>A project introducing myself to bloom filters, conducted during the first 
>semester of my second year as a CS student. As I had to benchmark my code, 
>I decided to learn how to use JMH. 


## Specifications & Run instructions
This project is fully written with Java and maven as project manager. However, I don't recommend launching the project with maven, as 
I have not thought it to work that way.

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
- BloomFilterArray, using a java array to store its objects
- BloomFilterArrayList, using an ArrayList to store its objects 
- BloomFilterLinkedList, using a LinkedList to sotre its objects


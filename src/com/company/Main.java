package com.company;

public class Main {

    public static void main(String[] args) {

	// BloomFilter implementation
        int filterSize = 1000;
        int numHashFunctions = 3;

        BloomFilter<String> bloomFilter = new BloomFilter<>(filterSize, numHashFunctions);

        // Insert elements into the Bloom filter
        bloomFilter.insert("apple");
        bloomFilter.insert("banana");
        bloomFilter.insert("cherry");

        // Check if elements are present in the Bloom filter
        System.out.println(bloomFilter.contains("apple")); // true
        System.out.println(bloomFilter.contains("banana")); // true
        System.out.println(bloomFilter.contains("cherry")); // true
        System.out.println(bloomFilter.contains("orange")); // false


        //CuckooFilter implementation

        int numBuckets = 8;
        int entriesPerBucket = 4;
        int maxAttempts = 3;

        CuckooFilter<String> cuckooFilter = new CuckooFilter<>(numBuckets, entriesPerBucket, maxAttempts);

        // Insert elements into the Cuckoo filter
        cuckooFilter.insert("apple");
        cuckooFilter.insert("banana");
        cuckooFilter.insert("cherry");

        // Check if elements are present in the Cuckoo filter
        System.out.println(cuckooFilter.contains("apple")); // true
        System.out.println(cuckooFilter.contains("banana")); // true
        System.out.println(cuckooFilter.contains("cherry")); // true
        System.out.println(cuckooFilter.contains("orange")); // false



        //QuotientFilter implementation
        int fSize = 10;

        QuotientFilter<String> quotientFilter = new QuotientFilter<>(fSize);

        // Insert elements into the Quotient Filter
        quotientFilter.insert("apple");
        quotientFilter.insert("banana");
        quotientFilter.insert("cherry");

        // Check if elements are present in the Quotient Filter
        System.out.println("Contains apple: " + quotientFilter.contains("apple")); // true
        System.out.println("Contains banana: " + quotientFilter.contains("banana")); // true
        System.out.println("Contains cherry: " + quotientFilter.contains("cherry")); // true
        System.out.println("Contains orange: " + quotientFilter.contains("orange")); // false

    }
}

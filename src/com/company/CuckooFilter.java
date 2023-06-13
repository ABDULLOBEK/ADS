package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CuckooFilter<T> {
    private int numBuckets;
    private int entriesPerBucket;
    private int maxAttempts;
    private List<T>[] table;
    private Function<T, Integer>[] hashFunctions;

    public CuckooFilter(int numBuckets, int entriesPerBucket, int maxAttempts) {
        this.numBuckets = numBuckets;
        this.entriesPerBucket = entriesPerBucket;
        this.maxAttempts = maxAttempts;
        table = new List[numBuckets];
        hashFunctions = new Function[2];
        for (int i = 0; i < numBuckets; i++) {
            table[i] = new ArrayList<>();
        }
        hashFunctions[0] = element -> Math.abs(element.hashCode() % numBuckets);
        hashFunctions[1] = element -> Math.abs((element.hashCode() / numBuckets) % numBuckets);
    }

    public void insert(T element) {
        if (contains(element)) {
            return;
        }

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            T displacedElement = element;
            int index = hashFunctions[0].apply(element);
            for (int i = 0; i < entriesPerBucket; i++) {
                if (table[index].size() < entriesPerBucket) {
                    table[index].add(element);
                    return;
                }
                displacedElement = table[index].set(i, displacedElement);
                index = hashFunctions[index % 2].apply(displacedElement);
            }

            if (table[index].size() < entriesPerBucket) {
                table[index].add(displacedElement);
                return;
            }
            element = table[index].set(0, displacedElement);
        }

        rehash();
        insert(element);
    }

    public boolean contains(T element) {
        int index1 = hashFunctions[0].apply(element);
        int index2 = hashFunctions[1].apply(element);
        return table[index1].contains(element) || table[index2].contains(element);
    }

    private void rehash() {
        int newNumBuckets = numBuckets * 2;
        List<T>[] newTable = new List[newNumBuckets];
        for (int i = 0; i < newNumBuckets; i++) {
            newTable[i] = new ArrayList<>();
        }
        for (List<T> bucket : table) {
            for (T element : bucket) {
                int index1 = hashFunctions[0].apply(element);
                int index2 = hashFunctions[1].apply(element);
                if (newTable[index1].size() < entriesPerBucket) {
                    newTable[index1].add(element);
                } else if (newTable[index2].size() < entriesPerBucket) {
                    newTable[index2].add(element);
                } else {
                    throw new IllegalStateException("Unable to rehash element: " + element);
                }
            }
        }
        numBuckets = newNumBuckets;
        table = newTable;
    }
}
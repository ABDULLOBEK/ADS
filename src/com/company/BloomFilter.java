package com.company;
import java.util.BitSet;
import java.util.function.Function;

public class BloomFilter<T> {
    private BitSet bitSet;
    private int size;
    private int numHashFunctions;
    private Function<T, Integer>[] hashFunctions;

    public BloomFilter(int size, int numHashFunctions) {
        this.size = size;
        this.numHashFunctions = numHashFunctions;
        bitSet = new BitSet(size);
        hashFunctions = new Function[numHashFunctions];
        for (int i = 0; i < numHashFunctions; i++) {
            int finalI = i;
            hashFunctions[i] = element -> Math.abs((element.hashCode() + finalI) % size);
        }
    }

    public void insert(T element) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int index = hashFunction.apply(element);
            bitSet.set(index, true);
        }
    }

    public boolean contains(T element) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int index = hashFunction.apply(element);
            if (!bitSet.get(index)) {
                return false;
            }
        }
        return true;
    }
}
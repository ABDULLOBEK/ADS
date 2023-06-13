package com.company;
import java.util.BitSet;
import java.util.function.Function;

public class QuotientFilter<T> {
    private int size;
    private int[] filter;
    private int[] quotient;
    private int[] remainder;

    public QuotientFilter(int size) {
        this.size = size;
        filter = new int[size];
        quotient = new int[size];
        remainder = new int[size];
    }

    public void insert(T element) {
        int hashCode = element.hashCode();
        int index = Math.abs(hashCode % size);

        if (filter[index] == 0) {
            filter[index] = hashCode;
            quotient[index] = hashCode / size;
            remainder[index] = hashCode % size;
        } else {
            int newIndex = index;
            int tempHashCode = hashCode;
            int tempQuotient = tempHashCode / size;
            int tempRemainder = tempHashCode % size;

            while (filter[newIndex] != 0) {
                int temp = filter[newIndex];
                filter[newIndex] = tempHashCode;
                tempHashCode = temp;

                temp = quotient[newIndex];
                quotient[newIndex] = tempQuotient;
                tempQuotient = temp;

                temp = remainder[newIndex];
                remainder[newIndex] = tempRemainder;
                tempRemainder = temp;

                newIndex = Math.abs((newIndex + tempRemainder) % size);
            }

            filter[newIndex] = tempHashCode;
            quotient[newIndex] = tempQuotient;
            remainder[newIndex] = tempRemainder;
        }
    }

    public boolean contains(T element) {
        int hashCode = element.hashCode();
        int index = Math.abs(hashCode % size);

        int tempHashCode = hashCode;
        int tempQuotient = tempHashCode / size;
        int tempRemainder = tempHashCode % size;

        while (filter[index] != 0) {
            if (filter[index] == tempHashCode && quotient[index] == tempQuotient && remainder[index] == tempRemainder) {
                return true;
            }

            index = Math.abs((index + tempRemainder) % size);
        }

        return false;
    }
}
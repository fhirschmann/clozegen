/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.fhirschmann.clozegen.lib.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * An {@link Iterator} which is able to peek the next {@code n} elements and
 * knows the last {@code n} elements.
 *
 * @param <T> the type of the iterator's elements
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class AdjacencyIterator<T> implements Iterator<T> {

    /** the underlying iterator. */
    private final Iterator<T> iterator;

    /** the offset in case we peek at the next elements. */
    private int offset;

    /** the remaining elements in the underlying iterator. */
    private int remaining;

    /** the number of elements on each side to keep track of. */
    private int n;

    /** the current position (element) of the iterator. */
    private T current;

    /** the previously seen elements. **/
    private final LinkedList<T> previous;

    /** the next elements we have taken a look at already. */
    private final LinkedList<T> next;


    /**
     * Constructs a new {@code AdjacencyIterator} based on a given
     * {@code Iterator}.
     *
     * @param iterator the iterator to base this iterator on
     * @param n the number of element on each side to provide access to
     */
    public AdjacencyIterator(final Iterator<T> iterator, final int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n needs to be greater than 0");
        }

        this.iterator = iterator;
        this.n = n;
        previous = new LinkedList<T>();
        next = new LinkedList<T>();
        current = null;
        remaining = 0;
    }

    /**
     * Convenience method for constructing a new {@code AdjacencyIterator}.
     *
     * @param <T> the type of the iterator's elements
     * @param iterator the iterator to base this iterator on
     * @param n the number of elements on each side to provide access to
     * @return a new AdjacencyIterator
     */
    public static <T> AdjacencyIterator<T> create(
            final Iterator<T> iterator, final int n) {
        return new AdjacencyIterator<T>(iterator, n);
    }

    @Override
    public boolean hasNext() {
        return ((offset > 0) && (remaining > 0)) || iterator.hasNext();
    }

    @Override
    public T next() {
        T result;
        if (previous.size() == n) {
            previous.removeFirst();
        }
        previous.add(getCurrent());
        if (offset > 0) {
            offset -= 1;
            remaining -= 1;
            result = next.poll();
        } else {
            result = iterator.next();
        }
        current = result;

        return result;
    }

    @Override
    public void remove() {
        iterator.remove();
    }

    /**
     * Returns a list of the {@code n} previous elements we have iterated
     * over. This will always be an {@code n}-sized array with elements
     * filled with {@code null} in case there are not enough predecessors
     * yet.
     *
     * @return list of predecessors
     */
    public List<T> peekPrevious() {
        final List<T> result = new ArrayList<T>(n);
        for (int i = previous.size(); i < n; i++) {
            result.add(null);
        }
        result.addAll(previous);

        return result;
    }

    /**
     * Returns a list of the {@code n} next elements without advancing
     * the iteration pointer. This will always be an {@code n}-sized array
     * with elements filled with {@code null} in case there are no more
     * successors.
     *
     * @return list of successors
     */
    public List<T> peekNext() {
        final List<T> result = new ArrayList<T>(n);
        for (int i = 0; i < n; i++) {
            if (next.isEmpty()) {
                if (iterator.hasNext()) {
                    result.add(iterator.next());
                    remaining += 1;
                } else {
                    result.add(null);
                }
            } else {
                result.add(next.poll());
            }
        }
        next.addAll(result);
        offset = n;

        return result;
    }

    /**
     * Returns the current element in the iteration.
     *
     * @return the current element
     */
    public T getCurrent() {
        return current;
    }

    /**
     * Returns a list of the {@code n} predecessors, the current element,
     * and the {@code n} successors.
     *
     * @return list made up of predecessors, the current element and successors
     */
    public List<T> getAdjacent() {
        final List<T> result = new ArrayList<T>(2 * n + 1);
        result.addAll(peekPrevious());
        result.add(getCurrent());
        result.addAll(peekNext());

        return result;
    }
}

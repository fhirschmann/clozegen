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

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Iterators;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

/**
 * Utility functions for Multisets.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class MultisetUtils {
    /** Constructor in utility class should not be called. */
    private MultisetUtils() {
    }

    /**
     * Sorts a multiset by its counts and returns a new {@link LinkedHashMultiset}.
     *
     * @param <E> the type of the multiset elements
     * @param multiset to multiset to sort
     * @return a new mutable sorted multiset
     */
    public static <E> LinkedHashMultiset<E> sortMultiSet(final Multiset<E> multiset) {
        final ImmutableMultiset<E> immutableSet = Multisets.
                copyHighestCountFirst(multiset);
        return LinkedHashMultiset.create(immutableSet);
    }

    /**
     * Returns a limited list of all (distinct) elements of a multiset ordered
     * by their counts.
     *
     * @param <E> the type of the multiset elements
     * @param multiset the multiset to work on
     * @param limit the maximum number of elements to return
     * @return a limited list of elements ordered by their count
     */
    public static <E> List<E> sortedElementList(final Multiset<E> multiset,
            final int limit) {
        final List<E> list = Lists.newLinkedList();
        final LinkedHashMultiset<E> sms = sortMultiSet(multiset);
        int newlimit = limit;

        if (newlimit > multiset.elementSet().size()) {
            throw new IllegalArgumentException(
                    "The multiset does not contain that many keys.");
        } else if (newlimit == -1) {
            newlimit = multiset.elementSet().size();
        }

        final Iterator<E> it = sms.iterator();

        E next;
        while (list.size() < newlimit) {
            next = it.next();
            if (!list.contains(next)) {
                list.add(next);
            }
        }
        return list;
    }

    /**
     * Returns a list of all (distinct) elements of a multiset ordered
     * by their counts.
     *
     * @param <E> the type of the multiset elements
     * @param multiset the multiset to work on
     * @return a limited list of elements ordered by their count
     */
    public static <E> List<E> sortedElementList(final Multiset<E> multiset) {
        return sortedElementList(multiset, -1);
    }

    /**
     * Merges two multisets.
     *
     * @param <E> the type of the elements of both multisets
     * @param multiset1 multiset to merge
     * @param multiset2 multiset to merge
     * @return a new merged multiset
     */
    public static <E> Multiset<E> mergeMultiSets(final Multiset<E> multiset1,
            final Multiset<E> multiset2) {
        final Multiset<E> multiset = LinkedHashMultiset.create(multiset1);
        Iterators.addAll(multiset, multiset2.iterator());
        return multiset;
    }
}

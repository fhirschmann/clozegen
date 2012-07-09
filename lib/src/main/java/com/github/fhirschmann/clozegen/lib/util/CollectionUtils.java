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

import java.util.Collection;
import java.util.List;

import org.javatuples.Triplet;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.google.common.collect.Sets;

/**
 * Collection of utility functions in order to deal with lists.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class CollectionUtils {
    /** Constructor in utility class should not be called. */
    private CollectionUtils() {
    }

    /**
     * Returns a view of a list made up of the n adjacent neighbors of an element
     * and the element itself.
     * <p>
     * For example, assuming the list is something like [1, 2, 3, 4, 5, 6],
     * then getAdjacentTo(3, 1) will yield [2, 3, 4].
     * </p>
     * <p>
     * The returned list is backed by this list, so non-structural changes in the
     * returned list are reflected in this list, and vice-versa. The returned list
     * supports all of the optional list operations supported by this list.
     * </p>
     * <p>
     *
     * @param <T> the type of the list elements
     * @param list the list to use
     * @param index the index of the element to get the adjacent neighbors for
     * @param num the number of neighbors (on each side) to include
     * @return a view based on the specified parameters
     */
    public static <T> List<T> getAdjacentTo(final List<T> list, final int index,
            final int num) {

        /* The num adjacent neighbors of an element intersected with the list's bounds */
        final Range<Integer> range = Ranges.closed(index - num, index + num).
                intersection(Ranges.closed(0, list.size() - 1));

        return list.subList(range.lowerEndpoint(), range.upperEndpoint() + 1);
    }

    /**
     * Returns a view of a list made up of the n adjacent neighbors of an element
     * and the element itself.
     *
     * If the element does not have any neighbors, null will be inserted.
     *
     * @param <T> the type of the list elements
     * @param list the list to use
     * @param index the index of the element to get the adjacent neighbors for
     * @param num the number of neighbors (on each side) to include
     * @return a view based on the specified parameters
     */
    public static <T> List<T> getNullPaddedAdjacentTo(final List<T> list, final int index,
            final int num) {
        final List<T> paddingNulls = Lists.newArrayList();
        for (int i = 0; i < num; i++) {
            paddingNulls.add(null);
        }
        return getAdjacentTo(Lists.newArrayList(
                Iterables.concat(paddingNulls, list, paddingNulls)), index + num, num);
    }

    /**
     * Check if two lists have the same distinct values.
     *
     * This is a convenience method to work around UIMA's insufficient type system.
     *
     * @param <T> the type of both lists
     * @param list1 list to compare
     * @param list2 list to compare
     * @return true if both lists have the same distinct values
     */
    public static <T> boolean listAsSetEquals(final List<T> list1,
            final List<T> list2) {
        return Sets.newHashSet(list1).equals(Sets.newHashSet(list2));
    }

    /**
     * Returns the maximum length of any element in a Collection.
     *
     * @param collection the collection in question
     * @return the length of the longest element
     */
    public static int getMaximumElementLength(final Collection<String> collection) {
        int max = 0;
        for (Object object : collection) {
            if (object.toString().length() > max) {
                max = object.toString().length();
            }
        }
        return max;
    }

    /**
     * Returns a triplet (A, x, B) where A and B are word sequences before and after x,
     * respectively.
     *
     * @param list the list to work on
     * @return a triplet
     */
    public static Triplet<String, String, String> triListJoin(final List<String> list) {
        final int middle = (int) Math.ceil(list.size() / 2);

        return Triplet.with(
                MiscUtils.WS_JOINER.join(list.subList(0, middle)),
                list.get(middle),
                MiscUtils.WS_JOINER.join(list.subList(middle + 1, list.size())));
    }
}

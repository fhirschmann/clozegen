/*
 * Copyright (C) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.github.fhirschmann.clozegen.lib.util;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import java.util.List;

/**
 * Collection of utility functions in order to deal with lists.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class ListUtils {
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
     * @param list the list to use
     * @param element the element to get the adjacent neighbors for
     * @param num the number of neighbors (on each side) to include
     * @return a view based on the specified parameters
     */
    public static <T> List<T> getAdjacentTo(final List<T> list, final T element,
            final int num) {
        final int index = list.indexOf(element);

        /* The num adjacent neighbors of an element intersected with the list's bounds */
        final Range<Integer> range = Ranges.closed(index - num, index + num).
                intersection(Ranges.closed(0, list.size() - 1));

        return list.subList(range.lowerEndpoint(), range.upperEndpoint() + 1);
    }
}

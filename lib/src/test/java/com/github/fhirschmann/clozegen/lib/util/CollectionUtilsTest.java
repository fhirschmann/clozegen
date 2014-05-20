/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschmann.email>
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class CollectionUtilsTest {

    private List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);

    @Test
    public void testAdjacentTo() {
        assertEquals(
                Lists.newArrayList(2, 3, 4),
                CollectionUtils.<Integer>getAdjacentTo(list, 2, 1));
    }

    @Test
    public void testAdjacentToUpperBound() {
        assertEquals(
                Lists.newArrayList(4, 5),
                CollectionUtils.<Integer>getAdjacentTo(list, 4, 1));
    }

    @Test
    public void testAdjacentToLowerBound() {
        assertEquals(
                Lists.newArrayList(1, 2),
                CollectionUtils.<Integer>getAdjacentTo(list, 0, 1));
    }

    @Test
    public void testListAsSetEquals() {
        assertTrue(CollectionUtils.listAsSetEquals(
                Lists.newArrayList("foo", "foo", "bar"),
                Lists.newArrayList("bar", "foo")));
    }

    @Test
    public void testNullPaddingLeft() {
        assertEquals(
                Lists.newArrayList(null, 1, 2),
                CollectionUtils.<Integer>getNullPaddedAdjacentTo(list, 0, 1));
    }

    @Test
    public void testNullPaddingRight() {
        assertEquals(
                Lists.newArrayList(4, 5, null),
                CollectionUtils.<Integer>getNullPaddedAdjacentTo(list, 4, 1));
    }

    @Test
    public void testNullPaddingRight2() {
        List<Integer> list2 = Lists.newArrayList(1, 2, 3, 4);
        assertEquals(
                Lists.newArrayList(1, 2, 3, 4, null),
                CollectionUtils.<Integer>getNullPaddedAdjacentTo(list2, 2, 2));
    }

    @Test
    public void testNullPaddingRight3() {
        assertEquals(
                Lists.newArrayList(2, 3, 4, 5, null, null, null),
                CollectionUtils.<Integer>getNullPaddedAdjacentTo(list, 4, 3));
    }
}

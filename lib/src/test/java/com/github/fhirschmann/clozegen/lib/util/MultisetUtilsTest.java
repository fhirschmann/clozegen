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

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class MultisetUtilsTest {

    private Multiset<String> multiset1;
    private Multiset<String> multiset2;

    @Before
    public void setUp() {
        multiset1 = LinkedHashMultiset.create();
        multiset1.add("foo", 4);
        multiset1.add("bar", 9);
        multiset2 = LinkedHashMultiset.create();
        multiset2.add("foo", 42);
        multiset2.add("bar2", 11);
    }

    @Test
    public void testMergeMultiset() {
        Multiset<String> ms = MultisetUtils.mergeMultiSets(multiset1, multiset2);
        assertEquals(46, ms.count("foo"));
        assertEquals(9, ms.count("bar"));
        assertEquals(11, ms.count("bar2"));
    }

    @Test
    public void testSortedElementList() {
        assertEquals(ImmutableList.of("bar", "foo"),
                MultisetUtils.sortedElementList(multiset1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSortedElementListArguments() {
        MultisetUtils.sortedElementList(multiset1, 20);
    }


    @Test
    public void testLimitedSortedElementList() {
        assertEquals(ImmutableList.of("bar"),
                MultisetUtils.sortedElementList(multiset1, 1));
    }
}

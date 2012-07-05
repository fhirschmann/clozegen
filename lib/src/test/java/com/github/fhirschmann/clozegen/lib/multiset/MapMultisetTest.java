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
package com.github.fhirschmann.clozegen.lib.multiset;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class MapMultisetTest {
    private MapMultiset<String, String> mms;

    @Before
    public void setUp() {
        mms = MapMultiset.create();
        mms.add("university", "of", 42);
        mms.add("university", "in", 4);
    }

    @Test
    public void testGetCount() {
        assertEquals(42, mms.get("university").count("of"));
        assertEquals(4, mms.get("university").count("in"));
    }

    @Test
    public void testZeroCount() {
        assertEquals(0, mms.get("asdf").count("asdf"));
    }

    @Test
    public void testAdd() {
        mms.add("foo", "bar", 12);
        mms.add("foo", "bar", 12);
        assertEquals(24, mms.get("foo").count("bar"));
    }

    @Test
    public void testTotal() {
        assertEquals(46, mms.total());
    }

    @Test
    public void testToString() {
        assertEquals("{university=[of x 42, in x 4]}", mms.toString());
    }

    @Test
    public void testSorted() {
        mms.add("foo","bar2", 40);
        mms.add("foo","bar1", 10);
        mms.add("foo","bar3", 50);

        assertEquals(ImmutableSet.of("bar3", "bar2", "bar1"),
                mms.getSorted("foo").elementSet());
    }
}

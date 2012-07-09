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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class AdjacencyIteratorTest {

    private List<String> list;
    private AdjacencyIterator<String> it;

    @Before
    public void setUp() {
        list = Arrays.asList("foo", "bar", "baz", "brr", "booh");
        it = AdjacencyIterator.create(list.iterator(), 2);
    }

    @Test
    public void testNext() {
        assertTrue(it.hasNext());
        assertEquals("foo", it.next());
        it.peekNext();
        it.peekPrevious();
        assertTrue(it.hasNext());
        assertEquals("bar", it.next());
        it.getAdjacent();
        assertTrue(it.hasNext());
        assertEquals("baz", it.next());
        it.peekNext();
        assertTrue(it.hasNext());
        assertEquals("brr", it.next());
        it.peekPrevious();
        assertTrue(it.hasNext());
        assertEquals("booh", it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testPeekPrevious() {
        assertEquals(Arrays.asList(null, null), it.peekPrevious());
        assertEquals(Arrays.asList(null, null), it.peekPrevious());
        it.next();
        assertEquals(Arrays.asList(null, null), it.peekPrevious());
        it.next();
        assertEquals(Arrays.asList(null, "foo"), it.peekPrevious());
        it.next();
        assertEquals(Arrays.asList("foo", "bar"), it.peekPrevious());
    }

    @Test
    public void testPeekNext() {
        assertEquals(Arrays.asList("foo", "bar"), it.peekNext());
        assertEquals(Arrays.asList("foo", "bar"), it.peekNext());
        it.next();
        assertEquals(Arrays.asList("bar", "baz"), it.peekNext());
        it.next();
        assertEquals(Arrays.asList("baz", "brr"), it.peekNext());
        it.next();
        assertEquals(Arrays.asList("brr", "booh"), it.peekNext());
        it.next();
        assertEquals(Arrays.asList("booh", null), it.peekNext());
        it.next();
        assertEquals(Arrays.asList(null, null), it.peekNext());
    }

    @Test
    public void testGetCurrent() {
        assertEquals(null, it.getCurrent());
        it.next();
        assertEquals("foo", it.getCurrent());
        it.next();
        assertEquals("bar", it.getCurrent());
        it.peekPrevious();
        it.next();
        assertEquals("baz", it.getCurrent());
        it.peekNext();
        it.next();
        assertEquals("brr", it.getCurrent());
        it.next();
        assertEquals("booh", it.getCurrent());
    }

    @Test
    public void testAdjacent() {
        assertEquals(Arrays.asList(null, null, null, "foo", "bar"), it.getAdjacent());
        it.next();
        assertEquals(Arrays.asList(null, null, "foo", "bar", "baz"), it.getAdjacent());
        it.next();
        assertEquals(Arrays.asList(null, "foo", "bar", "baz", "brr"), it.getAdjacent());
        it.next();
        assertEquals(Arrays.asList("foo", "bar", "baz", "brr", "booh"), it.getAdjacent());
        it.next();
        assertEquals(Arrays.asList("bar", "baz", "brr", "booh", null), it.getAdjacent());
        it.next();
        assertEquals(Arrays.asList("baz", "brr", "booh", null, null), it.getAdjacent());
        it.next();
        assertFalse(it.hasNext());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testArguments() {
        new AdjacencyIterator<String>(Arrays.asList("foo", "bar").iterator(), 0);
    }
}

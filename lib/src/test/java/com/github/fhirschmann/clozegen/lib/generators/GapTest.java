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
package com.github.fhirschmann.clozegen.lib.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

import org.junit.Before;
import org.junit.Test;

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.google.common.collect.Sets;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class GapTest {
    private Gap gap;
    private Gap gap2;

    @Before
    public void setUp() {
        gap = new Gap();
        gap.addValidAnswers("val1", "val2");
        gap.addInvalidAnswers("inval1", "inval2", "inval3");
        gap2 = new Gap();
        gap2 = Gap.with(Sets.newHashSet("val1", "val2"),
                Sets.newHashSet("inval1", "inval2", "inval3"));
        gap2.addValidAnswers("val1", "val2");
    }

    @Test
    public void testGetInvalidAnswers() {
        assertEquals(Sets.newHashSet("inval1", "inval2", "inval3"),
                gap.getInvalidAnswers());
    }

    @Test
    public void testGetValidAnswers() {
        assertEquals(Sets.newHashSet("val1", "val2"), gap.getValidAnswers());
    }

    @Test
    public void testGetAllAnswers() {
        assertEquals(Sets.newHashSet("inval1", "inval2", "inval3", "val1", "val2"),
                gap.getAllAnswers());
    }

    @Test
    public void testHashCode() {
        assertEquals(gap2.hashCode(), gap.hashCode());
    }

    @Test
    public void testEquals() {
        assertEquals(gap2, gap);
        assertFalse(gap.equals(null));
    }

    @Test
    public void testToString() {
        assertEquals("Gap{valid=[val1, val2], invalid=[inval3, inval2, inval1]}",
                gap.toString());
    }

    @Test
    public void testWith_String_StringArr() {
        Gap gap = Gap.with("foo", "bar", "bar2");
        assertThat(gap.getValidAnswers(), hasItem("foo"));
        assertThat(gap.getInvalidAnswers(), hasItems("bar", "bar2"));
    }
}

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
package com.github.fhirschmann.clozegen.lib.register;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class DescriptionRegisterEntryTest {
    private DescriptionRegisterEntry e1;
    private DescriptionRegisterEntry e2;
    private DescriptionRegisterEntry e3;

    @Before
    public void setUp() {
        e1 = new DescriptionRegisterEntry("foo", GapAnnotator.class);
        e2 = new DescriptionRegisterEntry("bar", GapAnnotator.class);
        e3 = new DescriptionRegisterEntry("foo", GapAnnotator.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIdentifier() {
        new DescriptionRegisterEntry("foo bar", GapAnnotator.class);
    }

    @Test
    public void testHashCode() {
        assertThat(e1.hashCode(), is(not(e2.hashCode())));
        assertThat(e1.hashCode(), is(e3.hashCode()));
    }

    @Test
    public void testEquals() {
        assertThat(e1, is(not(e2)));
        assertThat(e1, is(e3));
    }
}

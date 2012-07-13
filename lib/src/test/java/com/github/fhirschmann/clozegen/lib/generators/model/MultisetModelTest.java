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
package com.github.fhirschmann.clozegen.lib.generators.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.io.Resources;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class MultisetModelTest {
    URL unigrams;
    MultisetModel mm;

    @Before
    public void setUp() {
    unigrams = Resources.getResource("frequencies/collocation_prep/unigrams.txt");
    mm = new MultisetModel();
    }

    @Test
    public void testLoad() throws Exception {
        mm.load(unigrams);
        assertThat(mm.getMultiset().count("of"), is(29391));
    }

    @Test
    public void testToString() {
        Multiset<String> ms = HashMultiset.create();
        ms.add("foo");
        mm.setMultiset(ms);
        assertThat(mm.toString(), is("MultisetModel{multiset=[foo]}"));
    }
}

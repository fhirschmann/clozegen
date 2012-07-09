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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Multiset;
import com.google.common.io.Resources;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ReadMultisetTest {
    private URL trigrams;
    private URL bigrams;

    @Before
    public void setUp() {
        trigrams = Resources.getResource("frequencies/multiset/trigrams.txt");
        bigrams = Resources.getResource("frequencies/multiset/bigrams.txt");
    }

    @Test
    public void testParseMultiset() throws IOException, URISyntaxException {
        Multiset<String> ms = ReadMultisets.parseMultiset(trigrams);
        assertEquals(806, ms.count("one of the"));
        assertEquals(188, ms.count("and in the"));
        assertEquals(174, ms.count("because of the"));
    }

    @Test
    public void testParseMapMultiset() throws IOException, URISyntaxException {
        MapMultiset<String, String> mms = ReadMultisets.parseMapMultiset(bigrams, 0);
        assertEquals(1404, mms.get("one").count("of"));

        MapMultiset<String, String> mms2 = ReadMultisets.parseMapMultiset(bigrams, 1);
        assertEquals(436, mms2.get("of").count("because"));
    }
}

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.google.common.collect.Sets;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class AnnotatorRegisterTest {
    AnnotatorRegisterEntry e1;
    AnnotatorRegisterEntry e2;
    AnnotatorRegisterEntry e3;
    AnnotatorRegister r;

    @Before
    public void setUp() {
        r = new AnnotatorRegister();
        e1 = new AnnotatorRegisterEntry("foo", GapAnnotator.class);
        e1.setSupportedLanguages(Sets.newHashSet("de", "en"));
        e2 = new AnnotatorRegisterEntry("bar", GapAnnotator.class);
        e2.setSupportedLanguages(Sets.newHashSet("de"));
        e3 = new AnnotatorRegisterEntry("baz", GapAnnotator.class);
        e3.setSupportedLanguages(Sets.newHashSet("en"));
        Collections.addAll(r, e1, e2, e3);
    }

    @Test
    public void testContainsIdentifier() {
        assertTrue(r.containsIdentifier("foo"));
        assertTrue(r.containsIdentifier("bar"));
        assertTrue(r.containsIdentifier("baz"));
        assertFalse(r.containsIdentifier("asdf"));
    }

    @Test
    public void testAdd() {
        r.clear();
        assertThat(r.add(e1), is(true));
        assertThat(r, hasItem(e1));
        assertThat(r.add(e1), is(false));
    }

    @Test
    public void testGetAnnotatorsForLanguage() {
        assertThat(r.forLanguage("de"), hasItems(e1, e2));
    }

    @Test
    public void testGet() {
        assertThat(r.get("foo"), is(e1));
    }

    @Test
    public void testDelegate() {
        assertThat(r.delegate(), hasItems(e1, e2, e3));
    }

    @Test
    public void testLanguageCodes() {
        assertThat(r.languageCodes(), hasItems("de", "en"));
    }
}

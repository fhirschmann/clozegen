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

import com.github.fhirschmann.clozegen.lib.QGapGenerator;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.Collection;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Before;
import org.junit.Test;
import org.uimafit.factory.JCasFactory;
import org.uimafit.util.FSCollectionFactory;

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.java.quickcheck.generator.iterable.Iterables;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class UIMAUtilsTest {
    private JCas jcas;
    private GapAnnotation gap1;
    private GapAnnotation gap2;

    @Before
    public void setUp() throws UIMAException {
        jcas = JCasFactory.createJCas();
        jcas.setDocumentText("This is a sample text.");

        gap1 = UIMAUtils.createGapAnnotation(jcas,
                Sets.newHashSet("true1"), Sets.newHashSet("false1", "false2"));
        gap1.setBegin(0);
        gap1.setEnd(4);
        gap1.addToIndexes();

        gap2 = UIMAUtils.createGapAnnotation(jcas,
                Sets.newHashSet("true1", "true2"), Sets.newHashSet("false1", "false2"));
        gap2.setBegin(5);
        gap2.setEnd(7);
        gap2.addToIndexes();
    }

    @Test
    public void testCreateGapQ() throws UIMAException {
        for (Gap gap : Iterables.toIterable(new QGapGenerator())) {
            JCas jcasQ = JCasFactory.createJCas();

            assertThat(
                    UIMAUtils.createGap(
                    UIMAUtils.createGapAnnotation(jcasQ, gap)), is(gap));
        }
    }

    @Test
    public void testCreateGapAnnotationAll() throws UIMAException {
        Collection<String> list = FSCollectionFactory.create(gap1.getAllAnswers());
        assertThat(list, hasItems("true1", "false1", "false2"));
    }

    @Test
    public void testCreateGapAnnotationValid() throws UIMAException {
        Collection<String> list = FSCollectionFactory.create(gap2.getValidAnswers());
        assertThat(list, hasItems("true1", "true2"));
        assertThat(list, not(hasItems("false2")));
    }

    @Test
    public void testCreateGapAnnotation_3args() {
        Gap gap = Gap.with("bar", "foo");
        GapAnnotation an = UIMAUtils.createGapAnnotation(jcas, gap);
        assertThat(an.getValidAnswers().getHead(), is("bar"));
    }

    @Test
    public void testCreateGap() {
        Gap gap = Gap.with("foo", "bar");
        assertThat(gap, is(UIMAUtils.createGap(
                UIMAUtils.createGapAnnotation(jcas, gap))));
    }

    @Test
    public void testCopyBounds() {
        UIMAUtils.copyBounds(gap1, gap2);
        assertThat(gap1.getBegin(), is(gap2.getBegin()));
        assertThat(gap1.getEnd(), is(gap2.getEnd()));
    }

    @Test
    public void testGetAdjacentAnnotations() {
        List<Annotation> list = Lists.newLinkedList();
        list.add(gap1);
        list.add(gap2);
        List<GapAnnotation> result = UIMAUtils.getAdjacentAnnotations(
                GapAnnotation.class, list, 0, 1);
        List<GapAnnotation> expected = Lists.newArrayList(null, gap1, gap2);
        assertThat(result, is(expected));
    }

    @Test
    public void testGetAdjacentTokens() {
        List<Annotation> list = Lists.newLinkedList();
        list.add(gap1);
        list.add(gap2);
        List<String> result = UIMAUtils.getAdjacentTokens(
                GapAnnotation.class, list, 0, 1);
        List<String> expected = Lists.newArrayList("NULL", "This", "is");
        assertThat(result, is(expected));
    }

    @Test
    public void testCreateJCas() throws Exception {
        jcas = UIMAUtils.createJCas("foo", "en");
        assertThat(jcas.getDocumentLanguage(), is("en"));
    }

    @Test
    public void testHasSimilarAnnotation() {
        assertFalse(UIMAUtils.hasSimilarAnnotation(jcas, gap1));

        gap2.setBegin(gap1.getBegin());
        gap2.setEnd(gap1.getEnd());

        assertTrue(UIMAUtils.hasSimilarAnnotation(jcas, gap1));
    }
}

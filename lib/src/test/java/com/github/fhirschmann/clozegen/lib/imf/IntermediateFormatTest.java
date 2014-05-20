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
package com.github.fhirschmann.clozegen.lib.imf;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;
import org.uimafit.factory.JCasFactory;
import org.uimafit.util.FSCollectionFactory;
import org.uimafit.util.JCasUtil;

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.collect.Sets;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class IntermediateFormatTest {

    @Before
    public void setUp() throws UIMAException {
    }

    @Test
    public void testFormat() throws UIMAException {
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentLanguage("en");
        jcas.setDocumentText("He studies at the university.");
        GapAnnotation gap = UIMAUtils.createGapAnnotation(jcas,
                Sets.newHashSet("at"), Sets.newHashSet("at"));
        gap.setBegin(11);
        gap.setEnd(13);
        gap.addToIndexes();
        assertEquals("He studies {at}{at} the university.",
                IntermediateFormat.format(jcas));
    }

    @Test
    public void testParse() throws UIMAException {
        String str = "He studies {at}{in,on,of,at} the university."
                + "He can't think {of}{at, in , booh!} anything better than this.";
        JCas jcas = IntermediateFormat.parse(str);

        GapAnnotation gap1 = JCasUtil.selectByIndex(jcas, GapAnnotation.class, 0);
        assertEquals(Sets.newHashSet("in", "on", "of", "at"),
                Sets.newHashSet(FSCollectionFactory.create(gap1.getAllAnswers())));
        assertEquals(Sets.newHashSet("at"),
                Sets.newHashSet(FSCollectionFactory.create(gap1.getValidAnswers())));

        GapAnnotation gap2 = JCasUtil.selectByIndex(jcas, GapAnnotation.class, 1);
        assertEquals(Sets.newHashSet("at", "in", "booh!", "of"),
                Sets.newHashSet(FSCollectionFactory.create(gap2.getAllAnswers())));
        assertEquals(Sets.newHashSet("of"),
                Sets.newHashSet(FSCollectionFactory.create(gap2.getValidAnswers())));
    }

    @Test
    public void testFormatParse() throws UIMAException {
        String str = "He studies {at}{at} the university";
        JCas jcas = IntermediateFormat.parse(str);
        String result = IntermediateFormat.format(jcas);
        assertThat(result, is(str));
    }

    @Test
    public void testFormatParseDisabled() throws UIMAException {
        String str = "He studies {at}{in}d the university";
        JCas jcas = IntermediateFormat.parse(str);
        String result = IntermediateFormat.format(jcas);
        assertThat(result, is("He studies at the university"));
    }
}

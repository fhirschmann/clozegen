/*
 * The MIT License
 *
 * Copyright 2012 Fabian Hirschmann <fabian@hirschm.net>.
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
package com.github.fhirschmann.clozegen.lib.reader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.uimafit.factory.JCasFactory;

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.register.ReaderRegisterEntry;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.io.Resources;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class IntermediateFormatReaderTest {
    @Test
    public void testGetNext() throws Exception {
        ReaderRegisterEntry clz = new ReaderRegisterEntry(
                "clz", IntermediateFormatReader.class);
        clz.setName("IntermediateFormat Reader");
        CollectionReader reader = clz.getReader(
                Resources.getResource("clozetest/he.clz"), "en");
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentLanguage("en");

        reader.getNext(jcas.getCas());
        GapAnnotation annotation = (GapAnnotation) jcas.getAnnotationIndex(
                GapAnnotation.type).iterator().next();
        assertThat(UIMAUtils.createGap(annotation), is(Gap.with("of", "at", "on", "in")));
    }
}

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
package com.github.fhirschmann.clozegen.lib.components;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.io.File;
import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Before;
import org.junit.Test;

import com.github.fhirschmann.clozegen.lib.constraints.resources.PrepositionConstraintResource;
import com.github.fhirschmann.clozegen.lib.multiset.ReadMultisets;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.collect.Multiset;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class NGramWriterTest {
    File output;
    Pipeline pipeline;

    @Before
    public void setUp() throws IOException {
        output = File.createTempFile("cwtest", ".txt");
        pipeline = new Pipeline();
    }

    public static AnalysisEngineDescription createReader(final String output,
            final int n, final int min) throws ResourceInitializationException {
        AnalysisEngineDescription desc = createPrimitiveDescription(
                NGramWriter.class,
                NGramWriter.PARAM_N, n,
                NGramWriter.PARAM_MIN_FREQUENCY, min,
                NGramWriter.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                NGramWriter.PARAM_OUTPUT_PATH, output);
        return desc;
    }

    @Test
    public void testProcess0() throws ResourceInitializationException,
            UIMAException, IOException {
        pipeline.add(createReader(output.getAbsolutePath(), 0, 0));
        pipeline.run(UIMAUtils.createTestReader());
        Multiset<String> ms = ReadMultisets.parseMultiset(output.toURI().toURL());
        assertThat(ms.count("of"), is(224));
        assertThat(ms.count("at"), is(22));
    }

    @Test
    public void testProcessFreq() throws ResourceInitializationException,
            UIMAException, IOException {
        pipeline.add(createReader(output.getAbsolutePath(), 0, 23));
        pipeline.run(UIMAUtils.createTestReader());
        Multiset<String> ms = ReadMultisets.parseMultiset(output.toURI().toURL());
        assertThat(ms.count("at"), is(0));
    }

    @Test
    public void testProcess1() throws ResourceInitializationException,
            UIMAException, IOException {
        pipeline.add(createReader(output.getAbsolutePath(), 1, 0));
        pipeline.run(UIMAUtils.createTestReader());
        Multiset<String> ms = ReadMultisets.parseMultiset(output.toURI().toURL());
        assertThat(ms.count("some of the"), is(3));
        assertThat(ms.count("candidate in the"), is(2));
    }

    @Test
    public void testProcess2() throws ResourceInitializationException,
            UIMAException, IOException {
        pipeline.add(createReader(output.getAbsolutePath(), 2, 0));
        pipeline.run(UIMAUtils.createTestReader());
        Multiset<String> ms = ReadMultisets.parseMultiset(output.toURI().toURL());
        assertThat(ms.count("day schools for the deaf"), is(2));
    }

    @Test
    public void testProcessTail() throws ResourceInitializationException,
    UIMAException, IOException {
        AnalysisEngineDescription desc = createPrimitiveDescription(
                NGramWriter.class,
                NGramWriter.PARAM_N, 1,
                NGramWriter.PARAM_INCLUDE_HEAD, false,
                NGramWriter.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                NGramWriter.PARAM_OUTPUT_PATH, output);

        pipeline.add(desc);
        pipeline.run(UIMAUtils.createTestReader());
        Multiset<String> ms = ReadMultisets.parseMultiset(output.toURI().toURL());
        assertThat(ms.count("of the"), is(67));
        assertThat(ms.count("in the"), is(41));
    }

    @Test
    public void testProcessHead() throws ResourceInitializationException,
    UIMAException, IOException {
        AnalysisEngineDescription desc = createPrimitiveDescription(
                NGramWriter.class,
                NGramWriter.PARAM_N, 1,
                NGramWriter.PARAM_INCLUDE_TAIL, false,
                NGramWriter.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                NGramWriter.PARAM_OUTPUT_PATH, output);

        pipeline.add(desc);
        pipeline.run(UIMAUtils.createTestReader());
        Multiset<String> ms = ReadMultisets.parseMultiset(output.toURI().toURL());
        assertThat(ms.count("number of"), is(4));
        assertThat(ms.count("one of"), is(4));
    }
}

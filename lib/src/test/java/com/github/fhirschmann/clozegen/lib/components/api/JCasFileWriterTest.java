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
package com.github.fhirschmann.clozegen.lib.components.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.io.File;
import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.junit.Before;
import org.junit.Test;

import com.github.fhirschmann.clozegen.lib.formatters.IMFFormatter;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class JCasFileWriterTest {
    File output;
    Pipeline pipeline;

    @Before
    public void setUp() throws IOException {
        output = File.createTempFile("jcasw", ".txt");
        pipeline = new Pipeline();
    }

    @Test
    public void testProcess() throws Exception {
        AnalysisEngineDescription desc = createPrimitiveDescription(
                JCasFileWriter.class,
                JCasFileWriter.FORMATTER_KEY,
                createExternalResourceDescription(IMFFormatter.class),
                JCasFileWriter.PARAM_OUTPUT_FILE, output.getAbsolutePath());
        pipeline.add(desc);
        pipeline.run("I can't think of anything.", "en");
        assertThat(Files.readFirstLine(output, Charsets.UTF_8),
                is("I can't think of anything."));
    }

    @Test
    public void testToString() {
        JCasFileWriter writer = new JCasFileWriter();
        assertThat(writer.toString(),
                is("JCasFileWriter{outputFile=null, formatter=null}"));
    }
}

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
package com.github.fhirschmann.clozegen.lib.examples;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.uimafit.factory.CollectionReaderFactory;

import com.github.fhirschmann.clozegen.lib.components.CollocationWriter;
import com.github.fhirschmann.clozegen.lib.constraints.resources.PrepositionConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;

import de.tudarmstadt.ukp.dkpro.core.api.resources.DKProContext;
import de.tudarmstadt.ukp.dkpro.teaching.corpus.BrownCorpusReader;

/**
 * This example demonstrates the usage of {@link CollocationWriterExample}.
 *
 * <p>
 * The brown_tei corpus is expected in $DKPRO_HOME.
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationWriterExample {
    public static void main(String[] args) throws Exception {
        // START SNIPPET: colwrex1a
        Pipeline pipeline = new Pipeline();

        CollectionReader cr = CollectionReaderFactory.createCollectionReader(
                BrownCorpusReader.class,
                BrownCorpusReader.PARAM_PATH,
                DKProContext.getContext().getWorkspace("brown_tei_small").getAbsolutePath(),
                BrownCorpusReader.PARAM_PATTERNS, new String[] {"[+]*.xml"});

        AnalysisEngineDescription trigrams = createPrimitiveDescription(
                CollocationWriter.class,
                CollocationWriter.PARAM_MIN_FREQUENCY, 0,
                CollocationWriter.PARAM_N, 1,
                CollocationWriter.PARAM_INCLUDE_TAIL, false,
                CollocationWriter.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                CollocationWriter.PARAM_OUTPUT_PATH, "target/test.txt");

        pipeline.add(trigrams);
        pipeline.run(cr);
        // END SNIPPET: colwrex1a
    }

}

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
package com.github.fhirschmann.clozegen.lib.pipeline;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitive;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

/**
 * Utility functions for dealing with pipelines.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class PipelineFactory {
    /** Utility class. */
    private PipelineFactory() {
    }

    /**
     * Creates a pipeline utilizing Stanford Segmenter and Tree Tagger.
     *
     * @return a new default pipeline
     * @throws ResourceInitializationException on errors on initialization
     */
    public static Pipeline createDefaultPipeline()
            throws ResourceInitializationException {
        Pipeline pipeline = new Pipeline();
        final AnalysisEngine segmenter = createPrimitive(BreakIteratorSegmenter.class);
        pipeline.add(segmenter);

        //final AnalysisEngine tagger = createPrimitive(TreeTaggerPosLemmaTT4J.class);
        AnalysisEngine tagger = createPrimitive(OpenNlpPosTagger.class);
        pipeline.add(tagger);

        return pipeline;
    }
}

/*
 * Copyright (C) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.github.fhirschmann.clozegen.lib.pipeline;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
//import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitive;

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

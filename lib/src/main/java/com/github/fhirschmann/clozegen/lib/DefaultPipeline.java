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
package com.github.fhirschmann.clozegen.lib;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitive;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class DefaultPipeline extends Pipeline {
    public DefaultPipeline() throws ResourceInitializationException {
        super();
        final AnalysisEngine segmenter = createPrimitive(StanfordSegmenter.class);
        addStep(segmenter);

        // Produces some wird reflection errors in uimafit
        //AnalysisEngine tagger = createPrimitive(StanfordPosTagger.class,
        //        StanfordPosTagger.PARAM_VARIANT,
        //        "bidirectional-distsim-wsj-0-18");

        final AnalysisEngine tagger = createPrimitive(TreeTaggerPosLemmaTT4J.class);
        addStep(tagger);
    }
}

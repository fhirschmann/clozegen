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
package com.github.fhirschmann.clozegen.lib.adapters;

import com.github.fhirschmann.clozegen.lib.components.DebugWriter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.resources.PrepositionConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.JCasFactory2;
import java.io.IOException;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Test;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitive;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationAdapterTest {
    @Test
    public void testGenerator() throws ResourceInitializationException,
            UIMAException, IOException {
        AnalysisEngine test = createPrimitive(GapAnnotator.class,
                GapAnnotator.PARAM_ANSWER_COUNT, 5,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(
                CollocationAdapter.class,
                CollocationAdapter.PARAM_PATH,
                "frequencies/collocation_prep/trigrams.txt"));

        Pipeline pipeline = PipelineFactory.createDefaultPipeline();
        pipeline.addStep(test);
        JCas jcas = JCasFactory2.createTestJCas("I can't think of anything.", "en");
        pipeline.addStep(DebugWriter.class);
        pipeline.run(jcas);
        Annotation an = jcas.getAnnotationIndex(GapAnnotation.type).iterator().next();
        assertThat(an.getCoveredText(), is("of"));
    }
}

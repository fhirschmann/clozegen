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
import com.github.fhirschmann.clozegen.lib.constraints.resources.CoveredTextConstraintResource;
import com.github.fhirschmann.clozegen.lib.constraints.resources.TypeConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.JCasFactory2;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.NN;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
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
public class GenericSingleTokenInputAdapterTest {
    @Test
    public void testGenerator() throws ResourceInitializationException,
            UIMAException, IOException {
        AnalysisEngine test = createPrimitive(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                    CoveredTextConstraintResource.class,
                    CoveredTextConstraintResource.PARAM_STRING, "test",
                    TypeConstraintResource.PARAM_TYPE, Token.class.getName()),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(
                GenericSingleTokenInputAdapter.class,
                GenericSingleTokenInputAdapter.PARAM_GENERATOR_CLASS,
                "com.github.fhirschmann.clozegen.lib.generators.DummyGapGenerator"));

        Pipeline pipeline = PipelineFactory.createDefaultPipeline();
        pipeline.addStep(test);
        pipeline.addStep(DebugWriter.class);
        JCas jcas = JCasFactory2.createTestJCas("This is just a test.", "en");
        pipeline.run(jcas);
        Annotation an = jcas.getAnnotationIndex(GapAnnotation.type).iterator().next();
        assertThat(an.getCoveredText(), is("test"));
    }
}

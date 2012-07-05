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
package com.github.fhirschmann.clozegen.lib.adapters;

import com.github.fhirschmann.clozegen.lib.components.DebugWriter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.resources.CoveredTextConstraintResource;
import com.github.fhirschmann.clozegen.lib.constraints.resources.TypeConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.JCasFactory2;
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
        pipeline.add(test);
        pipeline.add(DebugWriter.class);
        JCas jcas = JCasFactory2.createTestJCas("This is just a test.", "en");
        pipeline.run(jcas);
        Annotation an = jcas.getAnnotationIndex(GapAnnotation.type).iterator().next();
        assertThat(an.getCoveredText(), is("test"));
    }
}

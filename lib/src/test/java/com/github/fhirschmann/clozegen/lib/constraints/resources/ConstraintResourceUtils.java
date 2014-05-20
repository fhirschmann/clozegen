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
package com.github.fhirschmann.clozegen.lib.constraints.resources;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.io.IOException;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.Resource;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.util.JCasUtil;

import com.github.fhirschmann.clozegen.lib.adapters.DummyAdapter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.api.ConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.collect.Lists;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class ConstraintResourceUtils {
    public static AnalysisEngineDescription create(
            final Class<? extends Resource> resource, final Object... data)
            throws ResourceInitializationException {
        AnalysisEngineDescription desc = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.ADAPTER_KEY,
                 createExternalResourceDescription(DummyAdapter.class),
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(resource, data));
        return desc;
    }

    public static List<GapAnnotation> getGapAnnotationsForConstraint(
            final String text, final String language,
            Class<? extends ConstraintResource> constraint, final Object... data)
            throws ResourceInitializationException, UIMAException, IOException {
        AnalysisEngineDescription desc = ConstraintResourceUtils.create(constraint, data);
        Pipeline pipeline = PipelineFactory.createDefaultPipeline();
        pipeline.add(desc);

        JCas jcas = UIMAUtils.createJCas(text, language);
        pipeline.run(jcas);
        List<GapAnnotation> annotations = Lists.newArrayList(
                JCasUtil.select(jcas, GapAnnotation.class));
        return annotations;
    }
}

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
package com.github.fhirschmann.clozegen.lib;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ExternalResourceDescription;

import com.github.fhirschmann.clozegen.lib.adapters.CollocationAdapter;
import com.github.fhirschmann.clozegen.lib.adapters.FrequencyAdapter;
import com.github.fhirschmann.clozegen.lib.adapters.GenericSingleTokenInputAdapter;
import com.github.fhirschmann.clozegen.lib.components.CollocationWriter;
import com.github.fhirschmann.clozegen.lib.components.DebugWriter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.resources.PrepositionConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Test {

    public static void main(String[] args) throws Exception {
        Pipeline pipeline = PipelineFactory.createDefaultPipeline();
        JCas j = UIMAUtils.createJCas("He can't think of anything", "en");

        AnalysisEngineDescription test = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.PARAM_ANSWER_COUNT, 5,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(
                CollocationAdapter.class,
                CollocationAdapter.PARAM_PATH, "frequencies/en/prepositions/trigrams.txt"));

        AnalysisEngineDescription testx = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.PARAM_ANSWER_COUNT, 5,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(
                FrequencyAdapter.class,
                FrequencyAdapter.PARAM_PATH, "frequencies/en/prepositions/unigrams.txt"));

        AnalysisEngineDescription writer = createPrimitiveDescription(
                CollocationWriter.class,
                CollocationWriter.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                CollocationWriter.PARAM_OUTPUT_PATH, "/home/fabian/test.txt");

        ExternalResourceDescription gen = createExternalResourceDescription(
                GenericSingleTokenInputAdapter.class);


        pipeline.add(testx);
        //pipeline.add(test2);
        pipeline.add(writer);
        pipeline.add(DebugWriter.class);
        pipeline.run(j);
    }
}

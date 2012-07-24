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
import org.apache.uima.resource.ResourceInitializationException;

import com.github.fhirschmann.clozegen.lib.adapters.DummyAdapter;
import com.github.fhirschmann.clozegen.lib.components.DebugWriter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.api.ConstraintResource;
import com.github.fhirschmann.clozegen.lib.constraints.resources.CoveredTextConstraintResource;
import com.github.fhirschmann.clozegen.lib.constraints.resources.PrepositionConstraintResource;
import com.github.fhirschmann.clozegen.lib.constraints.resources.TypeConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.ART;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * This example shows different ways of using constraints.
 *
 * <p> The output of this will be:
 *
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class ConstraintExample {
    /**
     * Example 1.
     *
     * <p> This example demonstrates the usage of {@link TypeConstraintResource}. The
     * argument given to {@link TypeConstraintResource} will result in
     * {@link GapAnnotator} being constrained to articles. </p>
     *
     * @return an analysis engine description
     * @throws ResourceInitializationException on errors during initialization
     */
    public static AnalysisEngineDescription example1()
            throws ResourceInitializationException {
        AnalysisEngineDescription desc = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                // START SNIPPET: constex1
                createExternalResourceDescription(
                TypeConstraintResource.class,
                TypeConstraintResource.PARAM_TYPE, ART.class.getName()),
                // END SNIPPET: constex1
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(DummyAdapter.class));
        return desc;
    }

    /**
     * Example 2.
     *
     * <p> This example demonstrates the usage of a custom {@link ConstraintResource}
     * which will restrict {@link GapAnnotator} to prepositions only </p>
     *
     * @return an analysis engine description
     * @throws ResourceInitializationException on errors during initialization
     */
    public static AnalysisEngineDescription example2()
            throws ResourceInitializationException {
        AnalysisEngineDescription desc = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(DummyAdapter.class));
        return desc;
    }

    /**
     * Example 3.
     *
     * <p> This example demonstrates the usage of {@link CoveredTextConstraintResource} by
     * matching the word "studies" and "university" of the type {@link Token}. </p>
     *
     * @return an analysis engine description
     * @throws ResourceInitializationException on errors during initialization
     */
    public static AnalysisEngineDescription example3()
            throws ResourceInitializationException {
        AnalysisEngineDescription desc = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                // START SNIPPET: constex3
                createExternalResourceDescription(
                CoveredTextConstraintResource.class,
                CoveredTextConstraintResource.PARAM_MATCH, "studies,university",
                TypeConstraintResource.PARAM_TYPE, Token.class.getName()),
                // END SNIPPET: constex3
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(DummyAdapter.class));
        return desc;
    }

    /**
     * Runs all examples.
     *
     * The output of this run will comprise of all constraint examples being visualized by
     * the DebugWriter.
     *
     * <p> The output of this will be:
     * <pre>
     * [Sentence] (0,29) He studies at the university.
     * [PR] (0,2) He [PRP]
     * [Token] (0,2) He
     * [NN] (3,10) studies [NNS]
     * [GapAnnotation] (3,10) studies [studies][studies]
     * [Token] (3,10) studies
     * [PP] (11,13) at [IN]
     * [GapAnnotation] (11,13) at [at][at]
     * [Token] (11,13) at
     * [GapAnnotation] (14,17) the [the][the]
     * [Token] (14,17) the
     * [ART] (14,17) the [DT]
     * [NN] (18,28) university [NN]
     * [GapAnnotation] (18,28) university [university][university]
     * [Token] (18,28) university
     * [PUNC] (28,29) . [.]
     * [Token] (28,29) .
     * [Sentence] (30,57) He can't think of anything.
     * [PR] (30,32) He [PRP]
     * [Token] (30,32) He
     * [V] (33,38) can't [MD]
     * [Token] (33,38) can't
     * [V] (39,44) think [VB]
     * [Token] (39,44) think
     * [PP] (45,47) of [IN]
     * [GapAnnotation] (45,47) of [of][of]
     * [Token] (45,47) of
     * [NN] (48,56) anything [NN]
     * [Token] (48,56) anything
     * [PUNC] (56,57) . [.]
     * [Token] (56,57) .
     * </pre>
     * See the comments below to correlate the annotations.
     * </p>
     *
     * @param args unused
     * @throws Exception on errors
     */
    public static void main(final String[] args) throws Exception {
        Pipeline pipeline = PipelineFactory.createDefaultPipeline();

        // The following line will result in the following annotations:
        //   [GapAnnotation] (11,13) at [at][at]
        //   [GapAnnotation] (14,17) the [the][the]
        pipeline.add(example1());

        // The following line will result in the following annotation:
        //   [GapAnnotation] (18,28) university [university][university]
        pipeline.add(example2());

        // The following line will result in the following annotation:
        //   [GapAnnotation] (45,47) of [of][of]
        pipeline.add(example3());
        pipeline.add(DebugWriter.class);
        pipeline.run("He studies at the university. He can't think of anything.", "en");
    }
}

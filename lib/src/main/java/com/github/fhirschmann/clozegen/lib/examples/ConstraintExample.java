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

import com.github.fhirschmann.clozegen.lib.adapters.DummyAdapter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.resources.PrepositionConstraintResource;
import com.github.fhirschmann.clozegen.lib.constraints.resources.TypeConstraintResource;
import com.github.fhirschmann.clozegen.lib.components.DebugWriter;
import com.github.fhirschmann.clozegen.lib.constraints.resources.CoveredTextConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.ART;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

/**
 * This example shows different ways of using constraints.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class ConstraintExample {
    /**
     * Example 1.
     *
     * <p>
     * This example demonstrates the usage of {@link TypeConstraintResource}. The argument
     * given to {@link TypeConstraintResource} will result in {@link GapAnnotator} being
     * constrained to articles.
     * </p>
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
     * <p>
     * This example demonstrates the usage of a custom {@link ConstraintResource}
     * which will restrict {@link GapAnnotator} to prepositions only
     * </p>
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
     * <p>
     * This example demonstrates the usage of {@link CoveredTextConstraintResource} by
     * matching the word "studies" of the type {@link POS}.
     * </p>
     *
     * @return an analysis engine description
     * @throws ResourceInitializationException on errors during initialization
     */
    public static AnalysisEngineDescription example3()
            throws ResourceInitializationException {
        AnalysisEngineDescription desc = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                    CoveredTextConstraintResource.class,
                    CoveredTextConstraintResource.PARAM_STRING, "studies",
                    TypeConstraintResource.PARAM_TYPE, POS.class.getName()),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(DummyAdapter.class));
        return desc;
    }

    /**
     * Runs all examples.
     *
     * @param args unused
     * @throws Exception on errors
     */
    public static void main(final String[] args) throws Exception {
        Pipeline pipeline = PipelineFactory.createDefaultPipeline();

        pipeline.add(example1());
        pipeline.add(example2());
        pipeline.add(example3());
        pipeline.add(DebugWriter.class);
        pipeline.run("He studies at the university.", "en");
    }
}

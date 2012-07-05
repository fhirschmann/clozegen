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

import com.github.fhirschmann.clozegen.lib.components.DebugWriter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.resources.TypeConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.ART;
import java.io.IOException;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

/**
 * Example demonstrating a simple description.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
/// START SNIPPET: wireex1
public class StupidArticleExample {
    public static void main(String[] args)
            throws ResourceInitializationException, UIMAException, IOException {
        AnalysisEngineDescription desc = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                    TypeConstraintResource.class,
                    TypeConstraintResource.PARAM_TYPE, ART.class.getName()),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(StupidArticleAdapter.class));

        Pipeline pipeline = PipelineFactory.createDefaultPipeline();

        pipeline.add(desc);
        pipeline.add(DebugWriter.class);
        pipeline.run(UIMAUtils.createJCas("He studies at the university.", "en"));
    }
}
/// END SNIPPET: wireex1

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
        pipeline.run(UIMAUtils.createJCas("He studies at the university.", "en"));
    }
}

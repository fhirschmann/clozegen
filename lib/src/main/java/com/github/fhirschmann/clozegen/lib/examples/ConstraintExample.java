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

import com.github.fhirschmann.clozegen.lib.adapter.DummyAdapter;
import com.github.fhirschmann.clozegen.lib.component.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraint.PrepositionConstraint;
import com.github.fhirschmann.clozegen.lib.constraint.TypeConstraint;
import com.github.fhirschmann.clozegen.lib.debug.DebugWriter;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.ART;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

/**
 * This example shows different ways of using constraints.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ConstraintExample {
    /**
     * Example 1.
     *
     * <p>
     * This example demonstrates the usage of {@link TypeConstraint}. The argument
     * given to {@link TypeConstraint} will result in {@link GapAnnotator} being
     * constrained to articles.
     * </p>
     * @return an analysis engine description
     * @throws ResourceInitializationException on errors during initialization
     */
    public static AnalysisEngineDescription example1()
            throws ResourceInitializationException {
        AnalysisEngineDescription desc1 = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                    TypeConstraint.class,
                    TypeConstraint.PARAM_TYPE, ART.class.getName()),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(DummyAdapter.class));
        return desc1;

    }

    /**
     * Example 2.
     *
     * <p>
     * This example demonstrates the usage of {@link TypeConstraint} in conjunction
     * with a predefined {@link Constraint}, which will restrict {@link GapAnnotator}
     * to prepositions only.
     * </p>
     * @return an analysis engine description
     * @throws ResourceInitializationException on errors during initialization
     */
    public static AnalysisEngineDescription example2()
            throws ResourceInitializationException {
        AnalysisEngineDescription desc1 = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraint.class),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(DummyAdapter.class));
        return desc1;

    }

    public static void main(String[] args) throws Exception {
        Pipeline pipeline = PipelineFactory.createDefaultPipeline();

        pipeline.addStep(example1());
        pipeline.addStep(example2());
        pipeline.addStep(DebugWriter.class);
        pipeline.run(UIMAUtils.createTestJCas());

    }
}

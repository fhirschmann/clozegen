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

import com.github.fhirschmann.clozegen.lib.adapters.TokenInputAdapter;
import com.github.fhirschmann.clozegen.lib.components.DebugWriter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.resources.TypeConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.ART;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.NN;
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
                createExternalResourceDescription(
                TokenInputAdapter.class,
                TokenInputAdapter.PARAM_GENERATOR_CLASS,
                "com.github.fhirschmann.clozegen.lib.examples.StupidArticleGapGenerator"));

        Pipeline pipeline = PipelineFactory.createDefaultPipeline();

        pipeline.addStep(desc);
        pipeline.addStep(DebugWriter.class);
        pipeline.run(UIMAUtils.createJCas("He studies at the university.", "en"));
    }
}
/// END SNIPPET: wireex1

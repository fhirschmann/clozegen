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
package com.github.fhirschmann.clozegen.lib;

import com.github.fhirschmann.clozegen.lib.adapters.SingleTokenInputAdapter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.resources.TypeConstraintResource;
import com.github.fhirschmann.clozegen.lib.generators.DummyGapGenerator;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.NN;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ExternalResourceDescription;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;
import static org.uimafit.factory.ExternalResourceFactory.bindResource;
import org.uimafit.factory.locator.JndiResourceLocator;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class BindResourceTest {
    public static void main(String[] args) throws Exception {
        Pipeline pipeline = PipelineFactory.createDefaultPipeline();
        JCas j = UIMAUtils.createTestJCas();

        ExternalResourceDescription ext = createExternalResourceDescription(
                SingleTokenInputAdapter.class);

        // bindResource does not eat ExternalResourceDescription:
        /*
        bindResource(ext,
                SingleTokenInputAdapter.RES_GENERATOR,
                JndiResourceLocator.class,
                JndiResourceLocator.PARAM_NAME, "DummyGapGenerator");
        */


        AnalysisEngineDescription test3 = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                    TypeConstraintResource.class,
                    TypeConstraintResource.PARAM_TYPE, NN.class.getName()),
                GapAnnotator.ADAPTER_KEY, ext);

        pipeline.addStep(test3);
        pipeline.run(j);
    }

}

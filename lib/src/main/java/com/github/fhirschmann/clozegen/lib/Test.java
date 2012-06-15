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

import com.github.fhirschmann.clozegen.lib.adapters.CollocationAdapter;
import com.github.fhirschmann.clozegen.lib.adapters.DummyAdapter;
import com.github.fhirschmann.clozegen.lib.adapters.SingleTokenInputAdapter;
import com.github.fhirschmann.clozegen.lib.adapters.StupidArticleAdapter;
import com.github.fhirschmann.clozegen.lib.components.CollocationWriter;
import com.github.fhirschmann.clozegen.lib.constraints.PrepositionConstraint;
import com.github.fhirschmann.clozegen.lib.constraints.TypeConstraint;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.ArticleConstraint;
import com.github.fhirschmann.clozegen.lib.constraints.WHConstraint;
import com.github.fhirschmann.clozegen.lib.debug.DebugWriter;
import com.github.fhirschmann.clozegen.lib.generators.DummyGapGenerator;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.ART;
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
public class Test {

    public static void main(String[] args) throws Exception {
        Pipeline pipeline = PipelineFactory.createDefaultPipeline();
        JCas j = UIMAUtils.createTestJCas();

        ExternalResourceDescription x = createExternalResourceDescription(StupidArticleAdapter.class);

        AnalysisEngineDescription test = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.PARAM_ANSWER_COUNT, 5,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraint.class),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(
                CollocationAdapter.class,
                CollocationAdapter.PARAM_PATH, "frequencies/en/prepositions/trigrams.txt"));

        AnalysisEngineDescription test2 = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                    TypeConstraint.class,
                    TypeConstraint.PARAM_TYPE, ART.class.getName()),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(StupidArticleAdapter.class));

        AnalysisEngineDescription test3 = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                    TypeConstraint.class,
                    TypeConstraint.PARAM_TYPE, NN.class.getName()),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(SingleTokenInputAdapter.class));

        AnalysisEngineDescription writer = createPrimitiveDescription(
                CollocationWriter.class,
                CollocationWriter.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraint.class),
                CollocationWriter.PARAM_OUTPUT_PATH, "/home/fabian/test.txt");

        ExternalResourceDescription gen = createExternalResourceDescription(
                SingleTokenInputAdapter.class);


        pipeline.addStep(test);
        //pipeline.addStep(test2);
        //pipeline.addStep(test3);
        pipeline.addStep(writer);
        pipeline.addStep(DebugWriter.class);
        pipeline.run(j);
    }
}

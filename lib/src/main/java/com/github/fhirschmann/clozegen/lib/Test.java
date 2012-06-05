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

import com.github.fhirschmann.clozegen.lib.adapter.PrepositionAdapter;
import com.github.fhirschmann.clozegen.lib.adapter.StupidArticleAdapter;
import com.github.fhirschmann.clozegen.lib.annotators.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.debug.DebugWriter;
import com.github.fhirschmann.clozegen.lib.pipeline.DefaultPipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.uimafit.factory.JCasFactory;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Test {

    public static void main(String[] args) throws Exception {
        Pipeline pipeline = new DefaultPipeline();

        JCas j = JCasFactory.createJCas();
        j.setDocumentLanguage("en");
        j.setDocumentText("He studies at the university. He can't think of anything.");

        DocumentMetaData x = DocumentMetaData.create(j);
        x.setDocumentId("test");

        AnalysisEngineDescription test = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.PARAM_ANSWER_COUNT, 2,
                GapAnnotator.WRAPPER_INTERFACE_KEY,
                createExternalResourceDescription(
                PrepositionAdapter.class,
                PrepositionAdapter.PARAM_PATH, "frequencies/en/prepositions"));

        AnalysisEngineDescription test2 = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.WRAPPER_INTERFACE_KEY,
                createExternalResourceDescription(StupidArticleAdapter.class));

        pipeline.addStep(test);
        pipeline.addStep(test2);
        pipeline.addStep(DebugWriter.class);
        pipeline.run(j);
    }
}

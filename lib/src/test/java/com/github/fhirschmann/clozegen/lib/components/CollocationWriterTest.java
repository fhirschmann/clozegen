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
package com.github.fhirschmann.clozegen.lib.components;

import com.github.fhirschmann.clozegen.lib.constraints.resources.PrepositionConstraintResource;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import java.io.IOException;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Test;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationWriterTest {

    @Test
    public void testProcess() throws ResourceInitializationException,
            UIMAException, IOException {
        AnalysisEngineDescription trigrams = createPrimitiveDescription(
                CollocationWriter.class,
                CollocationWriter.PARAM_MIN_FREQUENCY, 15,
                CollocationWriter.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                CollocationWriter.PARAM_OUTPUT_PATH, "target/cwtest.txt");
        Pipeline pipeline = PipelineFactory.createDefaultPipeline();

        pipeline.addStep(trigrams);
        pipeline.run(UIMAUtils.createTestReader());
    }
}
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

import java.io.IOException;
import java.util.ArrayList;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.factory.AnalysisEngineFactory;
import static org.uimafit.pipeline.SimplePipeline.runPipeline;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

/**
 * A pipeline is a chain of AnalysisEngines arranged so that each step will
 * be run sequentially with all pipeline steps working on the same CAS.
 * <p>
 * This class provides convenience methods to add pipeline steps from
 * engines, engine descriptions and engine components dynamically.
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Pipeline {

    private ArrayList<AnalysisEngine> pipeline = new ArrayList<AnalysisEngine>();

    /**
     * Adds a step to the pipeline.
     *
     * @param step
     */
    public void addStep(AnalysisEngine step) {
        pipeline.add(step);
    }

    /**
     * Adds a step to the pipeline.
     *
     * @param step
     * @throws ResourceInitializationException
     */
    public void addStep(AnalysisEngineDescription step) throws ResourceInitializationException {
        AnalysisEngine en;
        if (step.isPrimitive()) {
            en = AnalysisEngineFactory.createPrimitive(step);
        } else {
            en = AnalysisEngineFactory.createAggregate(step);
        }

        pipeline.add(en);
    }

    /**
     * Adds a step to the pipeline.
     *
     * @param step
     * @throws ResourceInitializationException
     */
    public void addStep(Class<? extends AnalysisComponent> step)
            throws ResourceInitializationException {

        addStep((AnalysisEngineDescription) createPrimitiveDescription(step));
    }

    /**
     * Runs the pipeline.
     *
     * The pipeline will start at the given CAS.
     *
     * @param jCas
     * @throws UIMAException
     * @throws IOException
     * @throws ClozegenException
     */
    public void run(JCas jCas) throws UIMAException, IOException, ClozegenException {
        runPipeline(jCas, (AnalysisEngine[]) pipeline.toArray(
                new AnalysisEngine[0]));
    }

    /**
     * Runs the pipeline.
     *
     * The pipeline will start at the given CollectionReader.
     *
     * @param reader
     * @throws UIMAException
     * @throws IOException
     */
    public void run(CollectionReader reader) throws UIMAException, IOException {
        runPipeline(reader, (AnalysisEngine[]) pipeline.toArray(
                new AnalysisEngine[0]));
    }
}
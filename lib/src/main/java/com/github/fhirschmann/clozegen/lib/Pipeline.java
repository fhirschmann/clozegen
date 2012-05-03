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

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
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
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Pipeline {

    private ArrayList<AnalysisEngine> pipeline = new ArrayList<AnalysisEngine>();
    private Class<? extends AnalysisComponent> segmenter = StanfordSegmenter.class;
    private Class<? extends AnalysisComponent> tagger = TreeTaggerPosLemmaTT4J.class;

    /**
     * Adds a step to the pipeline.
     *
     * @param step
     */
    public void addStep(AnalysisEngine step) {
        getPipeline().add(step);
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

        getPipeline().add(en);
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
        runPipeline(jCas, (AnalysisEngine[]) getPipeline().toArray(
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
        runPipeline(reader, (AnalysisEngine[]) getPipeline().toArray(
                new AnalysisEngine[0]));

    }

    /**
     * @return the pipeline
     */
    public ArrayList<AnalysisEngine> getPipeline() {
        return pipeline;
    }

    /**
     * @param segmenter the segmenter to set
     */
    public void setSegmenter(Class<? extends AnalysisComponent> segmenter) {
        this.segmenter = segmenter;
    }

    /**
     * @param tagger the tagger to set
     */
    public void setTagger(Class<? extends AnalysisComponent> tagger) {
        this.tagger = tagger;
    }
}
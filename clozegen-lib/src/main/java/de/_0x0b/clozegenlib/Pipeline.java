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
package de._0x0b.clozegenlib;

import java.io.IOException;
import java.util.ArrayList;
import lombok.ToString;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.pipeline.SimplePipeline.runPipeline;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
@ToString(callSuper = true, includeFieldNames = true)
public class Pipeline {
    private ArrayList<AnalysisEngineDescription> pipeline = new ArrayList<AnalysisEngineDescription>();
    private CollectionReader reader;

    public void addStep(AnalysisEngineDescription step) {
        getPipeline().add(step);
    }

    public void addStep(Class<? extends AnalysisComponent> step)
            throws ResourceInitializationException {

        addStep((AnalysisEngineDescription)createPrimitiveDescription(step));
    }

    public void run() throws UIMAException, IOException, ClozegenException {
        if (getReader() == null) {
            throw new ClozegenException("No reader set! Pipeline can't be executed.");
        }
        runPipeline(getReader(), (AnalysisEngineDescription[])getPipeline().toArray(
                new AnalysisEngineDescription[0]));
    }

    /**
     * @return the pipeline
     */
    public ArrayList<AnalysisEngineDescription> getPipeline() {
        return pipeline;
    }

    /**
     * @return the reader
     */
    public CollectionReader getReader() {
        return reader;
    }

    /**
     * @param reader the reader to set
     */
    public void setReader(CollectionReader reader) {
        this.reader = reader;
    }
}

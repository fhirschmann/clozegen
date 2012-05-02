package com.github.fhirschmann.clozegen.lib;

import com.github.fhirschmann.clozegen.lib.distractor.DistractorGenerator;
import com.github.fhirschmann.clozegen.lib.distractor.PrepositionBaselineDistractorGenerator;
import com.github.fhirschmann.clozegen.lib.io.DebugWriter;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import java.io.IOException;
import lombok.ToString;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.factory.AnalysisEngineFactory;
import static org.uimafit.factory.CollectionReaderFactory.createCollectionReader;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
@ToString(callSuper = true, includeFieldNames = true)
public class ClozeTestGenerator {

    private CollectionReader reader;
    private Class<? extends AnalysisComponent> segmenter = StanfordSegmenter.class;
    private Class<? extends AnalysisComponent> tagger = TreeTaggerPosLemmaTT4J.class;
    private Pipeline pipeline = new Pipeline();

    static {
        org.apache.log4j.BasicConfigurator.configure();
    }

    public void run() throws ResourceInitializationException, UIMAException,
            IOException, ClozegenException {
        getPipeline().addStep(getSegmenter());
        getPipeline().addStep(getTagger());

        CollectionReader cr = createCollectionReader(
                TextReader.class,
                TextReader.PARAM_PATH, "src/test/resources",
                TextReader.PARAM_LANGUAGE, "en",
                TextReader.PARAM_PATTERNS, new String[]{"[+]*.txt"});

        getPipeline().setReader(cr);
        getPipeline().addStep(DistractorAnnotator.class);
        getPipeline().addStep(DebugWriter.class);
        getPipeline().run();
    }

    public void generateClozeTest(String inputfile, String outputfile) throws
            ResourceInitializationException, UIMAException, IOException {
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

    /**
     * @return the segmenter
     */
    public Class<? extends AnalysisComponent> getSegmenter() {
        return segmenter;
    }

    /**
     * @param segmenter the segmenter to set
     */
    public void setSegmenter(Class<? extends AnalysisComponent> segmenter) {
        this.segmenter = segmenter;
    }

    /**
     * @return the tagger
     */
    public Class<? extends AnalysisComponent> getTagger() {
        return tagger;
    }

    /**
     * @param tagger the tagger to set
     */
    public void setTagger(Class<? extends AnalysisComponent> tagger) {
        this.tagger = tagger;
    }

    /**
     * @return the pipeline
     */
    public Pipeline getPipeline() {
        return pipeline;
    }

    /**
     * @param pipeline the pipeline to set
     */
    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }
}
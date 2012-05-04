package com.github.fhirschmann.clozegen.lib;

import com.github.fhirschmann.clozegen.lib.annotators.ArticleDistractorAnnotator;
import com.github.fhirschmann.clozegen.lib.annotators.PrepositionDistractorAnnotator;
import com.github.fhirschmann.clozegen.lib.io.DebugWriter;
import com.github.fhirschmann.clozegen.lib.io.InputReader;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import java.io.File;
import java.io.IOException;
import lombok.ToString;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.CollectionReaderFactory.createCollectionReader;
import org.uimafit.factory.JCasFactory;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
@ToString(callSuper = true, includeFieldNames = true)
public class ClozeTestGenerator {

    private Class<? extends AnalysisComponent> segmenter = StanfordSegmenter.class;
    private Class<? extends AnalysisComponent> tagger = TreeTaggerPosLemmaTT4J.class;
    private Pipeline pipeline = new Pipeline();

    static {
        org.apache.log4j.BasicConfigurator.configure();
    }

    public void run() throws ResourceInitializationException, UIMAException,
            IOException, ClozegenException {
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentText("I'd like a chicken!");
        jcas.setDocumentLanguage("en");

        getPipeline().addStep(getSegmenter());
        getPipeline().addStep(getTagger());


        getPipeline().addStep(PrepositionDistractorAnnotator.class);
        getPipeline().addStep(ArticleDistractorAnnotator.class);
        getPipeline().addStep(DebugWriter.class);
        getPipeline().run(jcas);
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
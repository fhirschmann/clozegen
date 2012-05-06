package com.github.fhirschmann.clozegen.lib;

import com.github.fhirschmann.clozegen.lib.annotators.ArticleDistractorAnnotator;
import com.github.fhirschmann.clozegen.lib.annotators.PrepositionDistractorAnnotator;
import com.github.fhirschmann.clozegen.lib.io.DebugWriter;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import java.io.IOException;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.factory.JCasFactory;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ClozeTestGenerator {
    /** The segmenter (sentence splitter) to use. */
    private Class<? extends AnalysisComponent> segmenter = StanfordSegmenter.class;

    /** The tagger to use. */
    private Class<? extends AnalysisComponent> tagger = TreeTaggerPosLemmaTT4J.class;

    /** The pipeline steps (segmenter, tagger, word class annotators). */
    private Pipeline pipeline = new Pipeline();

    static {
        org.apache.log4j.BasicConfigurator.configure();
    }

    public final void run() throws ResourceInitializationException, UIMAException,
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
    public final Class<? extends AnalysisComponent> getSegmenter() {
        return segmenter;
    }

    /**
     * @param segmenter the segmenter to set
     */
    public final void setSegmenter(final Class<? extends AnalysisComponent> segmenter) {
        this.segmenter = segmenter;
    }

    /**
     * @return the tagger
     */
    public final Class<? extends AnalysisComponent> getTagger() {
        return tagger;
    }

    /**
     * @param tagger the tagger to set
     */
    public final void setTagger(final Class<? extends AnalysisComponent> tagger) {
        this.tagger = tagger;
    }

    /**
     * @return the pipeline
     */
    public final Pipeline getPipeline() {
        return pipeline;
    }

    /**
     * @param pipeline the pipeline to set
     */
    public final void setPipeline(final Pipeline pipeline) {
        this.pipeline = pipeline;
    }
}
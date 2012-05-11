package com.github.fhirschmann.clozegen.lib;

import com.github.fhirschmann.clozegen.lib.annotators.en.ArticleGapGenerator;
import com.github.fhirschmann.clozegen.lib.io.DebugWriter;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import java.io.IOException;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.factory.JCasFactory;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitive;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ClozeTestGenerator {
    /** The segmenter (sentence splitter) to use. */
    private final Class<? extends AnalysisComponent> segmenter;

    /** The tagger to use. */
    private final AnalysisEngine tagger;

    /** The pipeline steps (segmenter, tagger, word class annotators). */
    private Pipeline pipeline = new Pipeline();

    public ClozeTestGenerator() throws ResourceInitializationException {
        segmenter = StanfordSegmenter.class;

        // Produces some wird reflection errors in uimafit
        //tagger = createPrimitive(StanfordPosTagger.class,
        //        StanfordPosTagger.PARAM_VARIANT,
        //        "bidirectional-distsim-wsj-0-18");

        tagger = createPrimitive(TreeTaggerPosLemmaTT4J.class);
    }

    static {
        org.apache.log4j.BasicConfigurator.configure();
    }

    /**
     * Runs the cloze test generation process.
     *
     * @throws ResourceInitializationException
     * @throws IOException
     * @throws UIMAException
     */
    public void run() throws ResourceInitializationException,
            IOException, UIMAException {
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentText("Let's go to a movie! I'd like a cookie!");
        jcas.setDocumentLanguage("en");

        getPipeline().addStep(segmenter);
        getPipeline().addStep(tagger);

        //getPipeline().addStep(PrepositionGapGenerator.class);
        getPipeline().addStep(ArticleGapGenerator.class);
        getPipeline().addStep(DebugWriter.class);
        getPipeline().run(jcas);
    }

    /**
     * @return the pipeline
     */
    public Pipeline getPipeline() {
        return pipeline;
    }
}

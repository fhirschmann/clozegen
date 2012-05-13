package com.github.fhirschmann.clozegen.lib;

import com.github.fhirschmann.clozegen.lib.pipeline.DefaultPipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
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
    /** The pipeline steps (segmenter, tagger, word class annotators). */
    private final Pipeline pipeline;

    public ClozeTestGenerator() throws ResourceInitializationException {
        pipeline = new DefaultPipeline();
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

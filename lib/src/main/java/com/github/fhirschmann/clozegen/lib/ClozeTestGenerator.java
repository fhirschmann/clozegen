package com.github.fhirschmann.clozegen.lib;

import com.github.fhirschmann.clozegen.lib.pipeline.DefaultPipeline;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.debug.DebugWriter;
import com.github.fhirschmann.clozegen.lib.register.Register;
import com.github.fhirschmann.clozegen.lib.register.RegisterFactory;

import java.io.IOException;
import org.apache.uima.UIMAException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.factory.JCasFactory;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ClozeTestGenerator {
    /** The pipeline steps (segmenter, tagger, word class annotators). */
    private Pipeline pipeline;

    /** The register of all annotator descriptions. */
    private Register register;

    public ClozeTestGenerator() throws ResourceInitializationException {
        pipeline = new DefaultPipeline();
        register = RegisterFactory.createDefaultRegister();

    }

    /**
     * Runs Cloze Test generation process.
     *
     * The pipeline will start at the given CAS.
     *
     * @param jCas the CAS to start the pipeline off with
     * @throws UIMAException on errors produced by UIMA
     * @throws IOException on errors when reading a file
     */
    public void run(final JCas jCas) throws UIMAException, IOException {
        pipeline.run(jCas);
    }

    /**
     * Runs Cloze Test generation process.
     *
     * The pipeline will start at the given CollectionReader.
     *
     * @param reader the Collection Reader to start the pipeline off with
     * @throws UIMAException when UIMA errors occur
     * @throws IOException when errors occur while reading from a file
     */
    public void run(final CollectionReader reader) throws UIMAException, IOException {
        pipeline.run(reader);
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
    public void setPipeline(final Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * @return the register
     */
    public Register getRegister() {
        return register;
    }

    /**
     * @param register the register to set
     */
    public void setRegister(final Register register) {
        this.register = register;
    }
}

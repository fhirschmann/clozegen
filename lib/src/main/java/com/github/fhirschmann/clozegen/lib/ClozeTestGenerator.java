package com.github.fhirschmann.clozegen.lib;

import com.github.fhirschmann.clozegen.lib.imf.IntermediateFormatWriter;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.register.DescriptionRegister;
import com.github.fhirschmann.clozegen.lib.register.ReaderRegister;
import com.github.fhirschmann.clozegen.lib.register.RegisterFactory;

import java.io.IOException;
import org.apache.uima.UIMAException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ClozeTestGenerator {
    /**
     * The pipeline.
     */
    private Pipeline pipeline;

    /**
     * The description register.
     */
    private DescriptionRegister descriptionRegister;

    /**
     * The reader register.
     */
    private ReaderRegister readerRegister;

    /**
     * Creates a new {@link ClozeTestGenerator} with empty registers.
     *
     * @throws ResourceInitializationException on errors during initialization
     */
    public ClozeTestGenerator() throws ResourceInitializationException {
        pipeline = new Pipeline();
        descriptionRegister = new DescriptionRegister();
        readerRegister = new ReaderRegister();
    }

    /**
     * Creates a new {@link ClozeTestGenerator with default registers.
     *
     * @return a new generator with default register
     * @throws ResourceInitializationException on errors during initialization
     */
    public static ClozeTestGenerator defaultGenerator()
            throws ResourceInitializationException {
        ClozeTestGenerator gen = new ClozeTestGenerator();
        gen.setPipeline(PipelineFactory.createDefaultPipeline());
        gen.setDescriptionRegister(RegisterFactory.createDefaultDescriptionRegister());
        gen.setReaderRegister(RegisterFactory.createDefaultReaderRegister());
        return gen;
    }

    /**
     * Runs the cloze test generation process.
     *
     * <p>
     * The pipeline will start at the reader associated with {@code input}
     * and write to {@code output}.
     * </p>
     *
     * @param input the input file
     * @param output the output file
     * @param languageCode the language
     * @throws UIMAException on UIMA errors
     * @throws IOException on errors reading or writing files
     */
    public void run(final String input, final String output, final String languageCode)
            throws UIMAException, IOException {
        CollectionReader reader = getReaderRegister().
                getReaderForFile(input, languageCode);

        getPipeline().addStep(createPrimitiveDescription(
                IntermediateFormatWriter.class,
                IntermediateFormatWriter.PARAM_OUTPUT_FILE, output));

        run(reader);
    }

    /**
     * Runs the cloze test generation process.
     *
     * <p>
     * The pipeline will start at the given CAS.
     * </p>
     *
     * @param jCas the CAS to start the pipeline off with
     * @throws UIMAException on errors produced by UIMA
     * @throws IOException on errors when reading a file
     */
    public void run(final JCas jCas) throws UIMAException, IOException {
        getPipeline().run(jCas);
    }

    /**
     * Runs the cloze test generation process.
     *
     * <p>
     * The pipeline will start at the given CollectionReader.
     * </p>
     *
     * @param reader the Collection Reader to start the pipeline off with
     * @throws UIMAException when UIMA errors occur
     * @throws IOException when errors occur while reading from a file
     */
    public void run(final CollectionReader reader) throws UIMAException, IOException {
        getPipeline().run(reader);
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
     * @return the descriptionRegister
     */
    public DescriptionRegister getDescriptionRegister() {
        return descriptionRegister;
    }

    /**
     * @param descriptionRegister the descriptionRegister to set
     */
    public void setDescriptionRegister(final DescriptionRegister descriptionRegister) {
        this.descriptionRegister = descriptionRegister;
    }

    /**
     * @return the readerRegister
     */
    public ReaderRegister getReaderRegister() {
        return readerRegister;
    }

    /**
     * @param readerRegister the readerRegister to set
     */
    public void setReaderRegister(final ReaderRegister readerRegister) {
        this.readerRegister = readerRegister;
    }
}
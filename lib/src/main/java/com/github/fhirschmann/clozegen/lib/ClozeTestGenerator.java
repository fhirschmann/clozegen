package com.github.fhirschmann.clozegen.lib;

import com.github.fhirschmann.clozegen.lib.imf.IntermediateFormatWriter;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.register.DescriptionRegister;
import com.github.fhirschmann.clozegen.lib.register.ReaderRegister;
import com.github.fhirschmann.clozegen.lib.register.RegisterFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.uima.UIMAException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

/**
 * Cloze Test Generator.
 *
 * <p>
 * This is the main entry point for the cloze test generation process.
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ClozeTestGenerator {
    /**
     * The pipeline.
     */
    private Pipeline pipeline;

    /**
     * The annotator register.
     */
    private DescriptionRegister annotatorRegister;

    /**
     * The reader register.
     */
    private ReaderRegister readerRegister;

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
     * Activate the given {@code generators}.
     *
     * @param generators the generators to activate
     * @throws ResourceInitializationException on errors during initialization
     */
    public void activate(final Map<String, Integer> generators)
            throws ResourceInitializationException {
        for (Entry<String, Integer> entry : generators.entrySet()) {
            activate(entry);
        }

    }

    /**
     * Activate the given {@code generator}.
     *
     * @param generator the generator to activate
     * @throws ResourceInitializationException on errors during initialization
     */
    public void activate(final Entry<String, Integer> generator)
            throws ResourceInitializationException {
        getPipeline().addStep(getAnnotatorRegister().
                get(generator.getKey()).getDescription());
    }

    /**
     * Returns the pipeline of the generation process. You can safely add
     * your own analysis engines to the pipeline.
     *
     * <p>
     * If no register has been set, a new default one will be created.
     * </p>
     *
     * @return the pipeline
     * @throws ResourceInitializationException on errors during initialization
     */
    public Pipeline getPipeline() throws ResourceInitializationException {
        if (pipeline == null) {
            setPipeline(PipelineFactory.createDefaultPipeline());
        }
        return pipeline;
    }

    /**
     * @param pipeline the pipeline to set
     */
    public void setPipeline(final Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * Returns the annotator register. This register holds all information
     * used to construct an analysis engine in order to generate gaps.
     *
     * <p>
     * If no register has been set, a new default one will be created.
     * </p>
     *
     * @return the annotatorRegister
     * @throws ResourceInitializationException on errors during initialization
     */
    public DescriptionRegister getAnnotatorRegister()
            throws ResourceInitializationException {
        if (annotatorRegister == null) {
            setAnnotatorRegister(RegisterFactory.createDefaultAnnotatorRegister());
        }
        return annotatorRegister;
    }

    /**
     * @param annotatorRegister the annotatorRegister to set
     */
    public void setAnnotatorRegister(final DescriptionRegister annotatorRegister) {
        this.annotatorRegister = annotatorRegister;
    }

    /**
     * Returns the reader register. This register maps file extensions to UIMA
     * readers. You can safely add new mappings to this register.
     *
     * <p>
     * If no register has been set, a new default one will be created.
     * </p>
     *
     * @return the readerRegister
     */
    public ReaderRegister getReaderRegister() {
        if (readerRegister == null) {
            return RegisterFactory.createDefaultReaderRegister();
        }
        return readerRegister;
    }

    /**
     * @param readerRegister the readerRegister to set
     */
    public void setReaderRegister(final ReaderRegister readerRegister) {
        this.readerRegister = readerRegister;
    }
}
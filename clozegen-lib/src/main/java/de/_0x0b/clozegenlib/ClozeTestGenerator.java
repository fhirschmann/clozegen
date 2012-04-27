package de._0x0b.clozegenlib;

import de._0x0b.clozegenlib.io.DebugWriter;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.xwriter.XWriter;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.CollectionReaderFactory.createCollectionReader;
import static org.uimafit.pipeline.SimplePipeline.runPipeline;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
@ToString(callSuper=true, includeFieldNames=true)
public class ClozeTestGenerator {
    private @Setter @Getter String language = "en";
	private @Setter @Getter Class<? extends AnalysisComponent> segmenter = StanfordSegmenter.class;
	private @Setter @Getter Class<? extends AnalysisComponent> tagger = TreeTaggerPosLemmaTT4J.class;

    static {
        org.apache.log4j.BasicConfigurator.configure();
    }

    public void runGeneratorPipeline(CollectionReader pipelineStart,
            AnalysisEngineDescription pipelineEnd) throws ResourceInitializationException,
            UIMAException, IOException {
          AnalysisEngineDescription seg = createPrimitiveDescription(getSegmenter());
          AnalysisEngineDescription tag = createPrimitiveDescription(getTagger());

          runPipeline(pipelineStart, seg, tag, pipelineEnd);
    }


    public void generateClozeTest(String inputfile, String outputfile) throws
            ResourceInitializationException, UIMAException, IOException {
          CollectionReader cr = createCollectionReader(
                TextReader.class,
                TextReader.PARAM_PATH, inputfile,
                TextReader.PARAM_LANGUAGE, "en",
                TextReader.PARAM_PATTERNS, new String[] {"[+]*.*"});


          AnalysisEngineDescription cc = createPrimitiveDescription(
                XWriter.class,
                XWriter.PARAM_OUTPUT_DIRECTORY_NAME, outputfile);

          runGeneratorPipeline(cr, cc);
    }

    public void generateTestClozeTest() throws ResourceInitializationException, UIMAException, IOException {
          CollectionReader cr = createCollectionReader(
                TextReader.class,
                TextReader.PARAM_PATH, "src/test/resources",
                TextReader.PARAM_LANGUAGE, "en",
                TextReader.PARAM_PATTERNS, new String[] {"[+]*.*"});
          AnalysisEngineDescription bar = createPrimitiveDescription(DebugWriter.class);

          runGeneratorPipeline(cr, bar);
    }
}
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
package com.github.fhirschmann.clozegen.lib.tools;

import com.github.fhirschmann.clozegen.lib.frequency.CollocationsExtractor;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import de.tudarmstadt.ukp.dkpro.core.io.web1t.Web1TFormatWriter;
import de.tudarmstadt.ukp.dkpro.teaching.corpus.BrownCorpusReader;
import java.io.IOException;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.factory.CollectionReaderFactory;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class WritePrepositionCollocations {
    public static void main(String[] args)
            throws ResourceInitializationException, UIMAException, IOException {
        Pipeline pipeline = new Pipeline();

        CollectionReader cr = CollectionReaderFactory.createCollectionReader(
                BrownCorpusReader.class,
                BrownCorpusReader.PARAM_PATH, "src/main/resources/corpora/brown_tei",
                BrownCorpusReader.PARAM_PATTERNS, new String[] {"[+]*.xml"});

        AnalysisEngineDescription ce = createPrimitiveDescription(
                CollocationsExtractor.class,
                CollocationsExtractor.PARAM_OUTPUT_DIRECTORY,
                "../models-en/src/main/resources/frequencies/en/prepositions");

        AnalysisEngineDescription ce2 = createPrimitiveDescription(
                Web1TFormatWriter.class,
                Web1TFormatWriter.PARAM_TARGET_LOCATION, "target/ngrams",
                Web1TFormatWriter.PARAM_MIN_NGRAM_LENGTH, 2,
                Web1TFormatWriter.PARAM_MAX_NGRAM_LENGTH, 3
                );

        pipeline.addStep(ce2);
        pipeline.run(cr);
    }

}

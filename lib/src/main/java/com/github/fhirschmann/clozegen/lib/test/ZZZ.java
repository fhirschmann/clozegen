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
package com.github.fhirschmann.clozegen.lib.test;

import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.annotators.en.PrepositionGapGenerator;
import com.github.fhirschmann.clozegen.lib.frequency.FrequencyStructure;
import com.github.fhirschmann.clozegen.lib.io.DebugWriter;
import de.tudarmstadt.ukp.dkpro.core.api.frequency.provider.FrequencyCountProvider;
import de.tudarmstadt.ukp.dkpro.core.frequency.resources.Web1TFrequencyCountResource;
import de.tudarmstadt.ukp.dkpro.core.toolbox.corpus.BrownTEICorpus;
import de.tudarmstadt.ukp.dkpro.core.toolbox.corpus.Corpus;
import de.tudarmstadt.ukp.dkpro.core.toolbox.corpus.CorpusReader;
import de.tudarmstadt.ukp.dkpro.teaching.corpus.BrownCorpusReader;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.tcas.Annotation;
import org.uimafit.descriptor.ExternalResource;
import org.uimafit.factory.CollectionReaderFactory;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ZZZ {

    public static final String FREQUENCY_COUNT_RESOURCE = "FrequencyProvider";
    @ExternalResource(key = FREQUENCY_COUNT_RESOURCE)
    private FrequencyCountProvider frequencyProvider;

    public ZZZ() throws Exception {
        long freq = frequencyProvider.getFrequency("of");
    }

    public static void main(String[] args) throws Exception {

        /*
         * bindResource( PrepositionGapGenerator.class, ZZZ.class,
         * Web1TFrequencyCountResource.class,
         * Web1TFrequencyCountResource.PARAM_MIN_NGRAM_LEVEL, "1",
         * Web1TFrequencyCountResource.PARAM_MAX_NGRAM_LEVEL, "1",
         * Web1TFrequencyCountResource.PARAM_INDEX_PATH, "" );
         */

        Pipeline pipeline = new Pipeline();
        CollectionReader cr = CollectionReaderFactory.createCollectionReader(
                BrownCorpusReader.class,
                BrownCorpusReader.PARAM_PATH, "src/test/resources/test_corpora/brown",
                BrownCorpusReader.PARAM_PATTERNS, new String[] {"[+]*.xml"});

        pipeline.addStep(PrepositionCounter.class);
        pipeline.run(cr);
        FrequencyStructure fs = new FrequencyStructure();
    }
}

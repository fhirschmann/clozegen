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
package com.github.fhirschmann.clozegen.lib.io;

import com.github.fhirschmann.clozegen.lib.util.MultisetUtils;
import com.github.fhirschmann.clozegen.lib.util.RangeUtils;
import com.google.common.collect.*;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.PP;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.NGram;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.ngrams.NGramIterable;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.JCasConsumer_ImplBase;
import static org.uimafit.util.JCasUtil.selectCovered;
import static org.uimafit.util.JCasUtil.select;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationsExtractor extends JCasConsumer_ImplBase {

    private HashSet<Range> prepositions;
    private Multiset<String> before;
    private Multiset<String> after;
    private Multiset<String> trigrams;
    private Multiset<String> unigrams;

    private String output = "src/main/resources/frequency/prepositions";

    @Override
    public void initialize(final UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        prepositions = Sets.newHashSet();
        before = HashMultiset.create();
        after = HashMultiset.create();
        trigrams = HashMultiset.create();
        unigrams = HashMultiset.create();
    }

    @Override
    public void collectionProcessComplete() {
        try {
            MultisetUtils.writeSortedMultiSet(trigrams, new File(output, "trigrams.txt"));
            MultisetUtils.writeSortedMultiSet(after, new File(output, "after.txt"));
            MultisetUtils.writeSortedMultiSet(before, new File(output, "before.txt"));
            MultisetUtils.writeSortedMultiSet(unigrams, new File(output, "unigrams.txt"));
        } catch (IOException ex) {
            Logger.getLogger(CollocationsExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void process(final JCas aJCas) throws AnalysisEngineProcessException {
        for (POS subject : select(aJCas, PP.class)) {
            prepositions.add(Ranges.closed(subject.getBegin(), subject.getEnd()));
            unigrams.add(subject.getCoveredText().toLowerCase());
        }


        Range range;
        String[] tokens;

        for (Sentence s : select(aJCas, Sentence.class)) {
            for (NGram ngram : NGramIterable.create(selectCovered(Token.class, s), 3)) {

                range = Ranges.closed(ngram.getBegin(), ngram.getEnd());
                tokens = ngram.getCoveredText().toLowerCase().split(" ");

                for (Range preposition : prepositions) {
                    if (range.encloses(preposition)) {
                        if (tokens.length == 2) {
                            if (RangeUtils.lowerEndpointsEqual(range, preposition)) {
                                after.add(ngram.getCoveredText().toLowerCase());
                            } else {
                                before.add(ngram.getCoveredText().toLowerCase());
                            }
                        } else if ((tokens.length == 3)
                                && !RangeUtils.anyEndpointsEqual(range, preposition)) {
                            trigrams.add(ngram.getCoveredText().toLowerCase());
                        }
                    }
                }
            }
        }
    }
}

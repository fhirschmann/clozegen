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
import com.github.fhirschmann.clozegen.lib.util.StringUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.PeekingIterator;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.PP;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.descriptor.ConfigurationParameter;
import org.uimafit.util.JCasUtil;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationsExtractor extends JCasConsumer_ImplBase {

    public static final String PARAM_OUTPUT_DIRECTORY = "OutputDirectory";
    @ConfigurationParameter(name = PARAM_OUTPUT_DIRECTORY, mandatory = true)
    private String outputDirectory;

    private Multiset<String> before;
    private Multiset<String> after;
    private Multiset<String> trigrams;
    private Multiset<String> unigrams;
    private final static Joiner joiner = Joiner.on(" ");

    @Override
    public void initialize(final UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        before = HashMultiset.create();
        after = HashMultiset.create();
        trigrams = HashMultiset.create();
        unigrams = HashMultiset.create();
    }

    public static void addToMultiset(final Multiset<String> multiset,
            final POS... tokens) {
        String[] sTokens = new String[tokens.length];
        for (int i=0; i < tokens.length; i++) {
            if ((tokens[i] == null)
                    || (StringUtils.isSentenceDelimiter(tokens[0].getCoveredText()))) {
                sTokens[i] = "NULL";
            } else {
                sTokens[i] = tokens[i].getCoveredText().toLowerCase();
            }
        }
        multiset.add(joiner.join(sTokens));
    }

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
            final List<POS> pos = JCasUtil.selectCovered(aJCas, POS.class, sentence);
            final PeekingIterator<POS> it = Iterators.peekingIterator(pos.iterator());

            POS previous;
            POS next;
            POS current = null;

            while (it.hasNext()) {
                previous = current;
                current = it.next();
                next = it.hasNext() ? it.peek() : null;

                if (current instanceof PP) {
                    addToMultiset(unigrams, current);
                    addToMultiset(before, previous, current);
                    addToMultiset(after, current, next);
                    addToMultiset(trigrams, previous, current, next);
                }
            }
        }
    }

    @Override
    public void collectionProcessComplete() {
        try {
            MultisetUtils.writeSortedMultiSet(trigrams,
                    new File(outputDirectory, "trigrams.txt"));
            MultisetUtils.writeSortedMultiSet(after,
                    new File(outputDirectory, "after.txt"));
            MultisetUtils.writeSortedMultiSet(before,
                    new File(outputDirectory, "before.txt"));
            MultisetUtils.writeSortedMultiSet(unigrams,
                    new File(outputDirectory, "unigrams.txt"));
        } catch (IOException ex) {
            Logger.getLogger(CollocationsExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

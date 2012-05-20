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

import com.github.fhirschmann.clozegen.lib.annotators.AbstractPosTrigramAnnotator;
import com.github.fhirschmann.clozegen.lib.util.MultisetUtils;
import com.github.fhirschmann.clozegen.lib.util.WordFilterFunction;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.descriptor.ConfigurationParameter;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationsExtractor extends AbstractPosTrigramAnnotator {

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

    @Override
    public void processTrigram(JCas aJCas, List<POS> pos) {
        List<String> strings = Lists.newArrayList(Collections2.
                transform(pos, new WordFilterFunction()));
        unigrams.add(strings.get(1));
        after.add(joiner.join(strings.get(0), strings.get(1)));
        before.add(joiner.join(strings.get(1), strings.get(2)));
        trigrams.add(joiner.join(strings));
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

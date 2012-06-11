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
package com.github.fhirschmann.clozegen.lib.generator;

import com.github.fhirschmann.clozegen.lib.generator.Gap;
import com.github.fhirschmann.clozegen.lib.generator.api.GapGenerator;
import com.github.fhirschmann.clozegen.lib.util.MultisetUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Sets;
import java.util.Set;

/**
 *
 * Generates gaps for prepositions
 *
 * <p>This implementation is based on the paper
 * <i>Automatic Generation of Cloze Items for Prepositions</i> [1]
 * by Lee et all.
 *
 * <p>Three collocation data files which describe the frequency of preposition
 * collocations need to be present in the model directory:
 * <ul>
 * <li>trigrams.txt: prepositions located in the middle
 * <li>before.txt: preposition located on the left-hand side
 * <li>after.txt: preposition located on the right-hand side
 * </ul>
 * The space separated word sequence and the corresponding counts need
 * to be separated by the tab-character. For more detail on the format,
 * please consult the documentation on {@link MultisetReader#parseMultiset}
 * and {@link MultisetReader#parseMapMultiset}, which describe the format
 * for {before|after}.txt and trigrams.txt, respectively.
 *
 * <p>[1] <b>J. Lee and S. Seneff</b>.<br/>
 * Automatic generation of cloze items for prepositions.<br/>
 * <i>In Eight Annual Conference of the International Speech Communication
 * Association, 2007</i>.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class PrepositionGapGenerator implements GapGenerator {
    /** The word A in the trigram (A, p, B). */
    private String A;

    /** The preposition p in the trigram (A, p, B). */
    private String p;

    /** The word B in the trigram (A, p, B). */
    private String B;

    /** The model for this generator. */
    private PrepositionGapGeneratorModel model;

    private final static Joiner JOINER = Joiner.on(" ");

    /**
     * Creates a gap generator for prepositions.
     *
     * @param A the word A in the trigram (A, p, B)
     * @param p the preposition p in the trigram (A, p, B)
     * @param B the word B in the trigram (A, p, B)
     * @param model the model for this generator.
     */
    public PrepositionGapGenerator(final String A, final String p, final String B,
            final PrepositionGapGeneratorModel model) {
        this.A = A;
        this.p = p;
        this.B = B;
        this.model = model;
    }

    /**
     * Creates a gap generator for prepositions.
     *
     * @param A the word A in the trigram (A, p, B)
     * @param p the preposition p in the trigram (A, p, B)
     * @param B the word B in the trigram (A, p, B)
     * @param model the model for this generator.
     * @return a new {@link PrepositionGapGenerator} instance.
     */
    public static PrepositionGapGenerator create(final String A, final String p,
            final String B, final PrepositionGapGeneratorModel model) {
        return new PrepositionGapGenerator(A, p, B, model);
    }

    @Override
    public Gap generate(int count) {
        Gap gap = new Gap();
        gap.setValidAnswers(p);

        // Collect a list of possible candidates for this gap
        final Multiset<String> candidates = ConcurrentHashMultiset.create(
                MultisetUtils.mergeMultiSets(model.getBefore().get(B),
                model.getAfter().get(A)));

        // Remove candidates p* which appear in the context (A, p*, B)
        for (Entry<String> entry : candidates.entrySet()) {
            if (model.getTrigrams().contains(
                    JOINER.join(A, entry.getElement(), B))) {
                candidates.remove(entry.getElement(), entry.getCount());
            }
        }

        // Remove the correct answer from the candidate set
        candidates.remove(p, candidates.count(p));

        if (candidates.elementSet().size() > count - 2) {
            final Set<String> invalidAnswers = Sets.newHashSet(
                    MultisetUtils.sortedElementList(candidates, count - 1));
            gap.setInvalidAnswers(invalidAnswers);
        } else {
            gap = null;
        }

        return gap;
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("trigram", String.format("(%s,%s,%s)", A, p, B));
        str.add("model", model.toString());
        return str.toString();
    }
}

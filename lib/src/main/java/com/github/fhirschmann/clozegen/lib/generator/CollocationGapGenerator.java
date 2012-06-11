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

import com.github.fhirschmann.clozegen.lib.generator.api.GapGenerator;
import com.github.fhirschmann.clozegen.lib.util.CollectionUtils;
import com.github.fhirschmann.clozegen.lib.util.MultisetUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Lists;
import org.javatuples.Triplet;

/**
 * Generates gaps based on collocations.
 *
 * <p>This implementation is based on the paper
 * <i>Automatic Generation of Cloze Items for Prepositions</i> [1]
 * by Lee et all.
 *
 * <p>[1] <b>J. Lee and S. Seneff</b>.<br/>
 * Automatic generation of cloze items for prepositions.<br/>
 * <i>In Eight Annual Conference of the International Speech Communication
 * Association, 2007</i>.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationGapGenerator implements GapGenerator {
    /** The index of the element in the middle. */
    private int index;

    /** The model for this generator. */
    private CollocationModel model;

    /** The triplet (A, x, B) where x is the subject in question. */
    private Triplet<String, String, String> triplet;

    private final static Joiner JOINER = Joiner.on(" ");

    /**
     * Creates a new collocation-based gap generator. The <code>tuple</code>
     * must have an odd number of elements and the element in the middle of the
     * list is the word a gap is generated for.
     *
     * @param tuple the tuple of words
     * @param model the model for this generator
     */
    public CollocationGapGenerator(final List<String> tuple,
            final CollocationModel model) {
        this.model = checkNotNull(model);
        triplet = CollectionUtils.triListJoin(tuple);
    }

    /**
     * Creates a new collocation-based gap generator. The <code>tuple</code>
     * must have an odd number of elements and the element in the middle of the
     * list is the word a gap is generated for.
     *
     * @param A a in (A, x, B)
     * @param x x in (A, x, B)
     * @param B B in (A, x, B)
     * @param model the model for this generator
     */
    public CollocationGapGenerator(final String A, final String x, final String B,
            final CollocationModel model) {
        this(Lists.newArrayList(A, x, B), model);
    }

    @Override
    public Gap generate(final int count) {
        Gap gap = new Gap();
        gap.setValidAnswers(triplet.getValue1());

        // Collect a list of possible candidates for this gap
        final Multiset<String> candidates = ConcurrentHashMultiset.create(
                MultisetUtils.mergeMultiSets(model.getBefore().get(triplet.getValue2()),
                model.getAfter().get(triplet.getValue0())));

        // Remove candidates p* which appear in the context (A, p*, B)
        for (Entry<String> entry : candidates.entrySet()) {
            if (model.getNGrams().contains(
                    JOINER.join(triplet.getValue0(), entry.getElement(), triplet.getValue2()))) {
                candidates.remove(entry.getElement(), entry.getCount());
            }
        }

        // Remove the correct answer from the candidate set
        candidates.remove(triplet.getValue0(), candidates.count(triplet.getValue0()));

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
        str.add("tuple", triplet.toString());
        str.add("model", model.toString());
        return str.toString();
    }
}

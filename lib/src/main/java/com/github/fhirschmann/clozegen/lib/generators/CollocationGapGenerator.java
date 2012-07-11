/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.fhirschmann.clozegen.lib.generators;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Set;

import org.javatuples.Triplet;

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.generators.api.GapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.model.CollocationModel;
import com.github.fhirschmann.clozegen.lib.util.CollectionUtils;
import com.github.fhirschmann.clozegen.lib.util.MiscUtils;
import com.github.fhirschmann.clozegen.lib.util.MultisetUtils;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Optional;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Sets;

/**
 * Generates gaps based on collocations.
 *
 * <p>
 * The argument to {@link CollocationGapGenerator#initialize(java.util.List)}
 * must have an odd number of elements and the element in the middle of the
 * list is the word a gap is generated for.
 * </p>
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
    /**
     * The model for this generator.
     */
    private CollocationModel model;

    /**
     * The triplet (A, x, B) where x is the subject in question.
     */
    private Triplet<String, String, String> triplet;

    /**
     * Creates a new collocation-based gap generator.
     *
     * @param ngram a list of tokens with the word in the center position
     * @param model the model
     */
    public CollocationGapGenerator(final List<String> ngram,
            final CollocationModel model) {
        triplet = CollectionUtils.triListJoin(ngram);
        this.model = checkNotNull(model);
    }

    @Override
    public Optional<Gap> generate(final int count) {
        checkNotNull(model);
        Gap gap = new Gap();
        gap.addValidAnswers(triplet.getValue1());

        // Collect a list of possible candidates for this gap
        final Multiset<String> candidates = ConcurrentHashMultiset.create(
                MultisetUtils.mergeMultiSets(model.getTails().get(triplet.getValue2()),
                model.getHeads().get(triplet.getValue0())));

        // Remove the correct answer from the candidate set
        candidates.remove(triplet.getValue1(), candidates.count(triplet.getValue1()));

        // Remove candidates p* which appear in the context (A, p*, B)
        for (Entry<String> entry : candidates.entrySet()) {
            if (model.getMultiset().contains(MiscUtils.WS_JOINER.join(
                    triplet.getValue0(), entry.getElement(), triplet.getValue2()))) {
                candidates.remove(entry.getElement(), entry.getCount());
            }
        }

        if (candidates.elementSet().size() > count - 2) {
            final Set<String> invalidAnswers = Sets.newHashSet(
                    MultisetUtils.sortedElementList(candidates, count - 1));
            gap.addInvalidAnswers(invalidAnswers);
            return Optional.of(gap);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("tuple", triplet);
        str.add("model", model);
        return str.toString();
    }
}

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

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.generators.api.GapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.model.MultisetModel;
import com.github.fhirschmann.clozegen.lib.util.MultisetUtils;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Optional;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

/**
 * Suggests distractors with a frequency similar to that of the input word.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class FrequencyGapGenerator implements GapGenerator {
    /**
     * The model to use.
     */
    private MultisetModel model;

    /**
     * The word to generate a gap for.
     */
    private String token;

    /**
     * Creates a new Gap Generator.
     *
     * @param token the token to generate a gap for
     * @param model the model
     */
    public FrequencyGapGenerator(final String token, final MultisetModel model) {
        this.model = checkNotNull(model);
        this.token = checkNotNull(token);
    }

    @Override
    public Optional<Gap> generate(final int count) {
        if (model.getMultiset().contains(token)) {
            int tokenCount = model.getMultiset().count(token);
            Multiset<String> ms = HashMultiset.create();

            // compute a multiset with counts(x) = |count(x) - count(token)|
            for (Entry<String> entry : model.getMultiset().entrySet()) {
                ms.add(entry.getElement(), Math.abs(entry.getCount() - tokenCount));
            }

            if (ms.elementSet().size() < count - 1) {
                // not enough data to create as many answer options as requested
                return Optional.absent();
            } else {
                return Optional.of(
                        Gap.with(token, Lists.reverse(
                        MultisetUtils.sortedElementList(ms)).subList(0, count - 1)));
            }
        } else {
            // we have no knowledge of the word in question
            return Optional.absent();
        }
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("token", token.toString());
        str.add("model", model.toString());
        return str.toString();
    }
}

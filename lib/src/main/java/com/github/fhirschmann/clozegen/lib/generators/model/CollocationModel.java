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
package com.github.fhirschmann.clozegen.lib.generators.model;

import java.util.Arrays;

import org.javatuples.Triplet;

import com.github.fhirschmann.clozegen.lib.multiset.MapMultiset;
import com.github.fhirschmann.clozegen.lib.util.CollectionUtils;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

/**
 * Represents a model for the collocation-based gap generator.
 * This minimum n in n-gram for this model is 3 and n needs to be an odd number!
 *
 * <p>
 * Let's assume you load the following frequencies from a file:
 *
 * <pre>
 * one of the   200
 * because of the  100
 * members of the   50
 * </pre>
 *
 * Then you'd end up with a model like this:
 * <pre>
 * CollocationModel{
 *     tails={the=[of x 350]},
 *     heads={because=[of x 100], one=[of x 200],
 *     members=[of x 50]}, ngrams=[members of the x 50,
 *         because of the x 100, one of the x 200]}
 * </pre>
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationModel extends MultisetModel {
    /**
     * The head ngrams.
     *
     * Please see the documentation above.
     */
    private MapMultiset<String, String> heads;

    /**
     * The tail ngrams.
     *
     * Please see the documentation above.
     */
    private MapMultiset<String, String> tails;

    /**
     * Creates a new empty {@link CollocationModel}.
     */
    public CollocationModel() {
        super();
        heads = MapMultiset.create();
        tails = MapMultiset.create();
    }

    @Override
    public void setMultiset(final Multiset<String> multiset) {
        super.setMultiset(multiset);
        Triplet<String, String, String> triplet;

        for (Entry<String> entry : getMultiset().entrySet()) {
            triplet = CollectionUtils.triListJoin(
                    Arrays.asList(entry.getElement().split(" ")));
            heads.add(triplet.getValue0(), triplet.getValue1(), entry.getCount());
            tails.add(triplet.getValue2(), triplet.getValue1(), entry.getCount());
        }
    }

    /**
     * @return the ngrams where the subject word comes last
     */
    public MapMultiset<String, String> getHeads() {
        return heads;
    }

    /**
     * @return the ngrams where the subject word comes first
     */
    public MapMultiset<String, String> getTails() {
        return tails;
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("tails", getTails().toString());
        str.add("heads", getHeads().toString());
        str.add("ngrams", getMultiset().toString());
        return str.toString();
    }
}

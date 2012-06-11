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

import com.github.fhirschmann.clozegen.lib.multiset.MapMultiset;
import com.github.fhirschmann.clozegen.lib.multiset.ReadMultisets;
import com.github.fhirschmann.clozegen.lib.util.CollectionUtils;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import org.javatuples.Triplet;

/**
 * Represents a model for the collocation-based gap generator.
 *
 * <p>Three collocation data files which describe the frequency of collocations
 * can be used in conjunction with{@link CollocationModel#load(URL, URL, URL)}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationModel {
    /** A multiset of ngrams (x_i-n, x_i-1, x, x_i+1, x_i+n). */
    private Multiset<String> ngrams;

    /** A multiset of bigrams (x_i-n, x_i-1, x). */
    private MapMultiset<String, String> heads;

    /** A multiset of bigrams (x, x_i+1, x_i+n). */
    private MapMultiset<String, String> tails;

    /**
     * Loads the model from a single ngram file. Please see
     * {@link MultisetReader} for details on the file format.
     * <p>
     * The ngrams in the files must have an odd number of words.
     * </p>
     *
     * @param ngrams the URL to the ngrams (x_i-n, x_i+1, x, x_i+1, x_i+n)
     * @param n the n in n-gram
     * @throws IOException on errors reading a file
     */
    public void load(final URL ngrams, final int n) throws IOException {
        this.ngrams = ReadMultisets.parseMultiset(ngrams);

        heads = MapMultiset.create();
        tails = MapMultiset.create();

        Triplet<String, String, String> triplet;

        for (Entry<String> entry : this.ngrams.entrySet()) {
            triplet = CollectionUtils.triListJoin(
                    Arrays.asList(entry.getElement().split(" ")));
            tails.add(triplet.getValue0(), triplet.getValue1(), entry.getCount());
            heads.add(triplet.getValue2(), triplet.getValue1(), entry.getCount());
        }
    }

    /**
     * @return the ngrams (x_i-n, x_i-1, x, x_i+1, x_i+n).
     */
    public Multiset<String> getNGrams() {
        return ngrams;
    }

    /**
     * @param ngrams the ngrams to set
     */
    public void setNGrams(final Multiset<String> ngrams) {
        this.ngrams = ngrams;
    }

    /**
     * @return the ngrams where the subject word comes last
     */
    public MapMultiset<String, String> getHeads() {
        return heads;
    }

    /**
     * @param heads the ngrams where the subject word comes last
     */
    public void setHeads(final MapMultiset<String, String> heads) {
        this.heads = heads;
    }

    /**
     * @return the ngrams where the subject word comes first
     */
    public MapMultiset<String, String> getTails() {
        return tails;
    }

    /**
     * @param tails the ngrams where the subject word comes first
     */
    public void setTails(final MapMultiset<String, String> tails) {
        this.tails = tails;
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("tails", tails.toString());
        str.add("heads", heads.toString());
        str.add("ngrams", ngrams.toString());
        return str.toString();
    }
}

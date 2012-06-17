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
package com.github.fhirschmann.clozegen.lib.generators.model;

import com.github.fhirschmann.clozegen.lib.multiset.MapMultiset;
import com.github.fhirschmann.clozegen.lib.util.CollectionUtils;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import java.util.Arrays;
import org.javatuples.Triplet;

/**
 * Represents a model for the collocation-based gap generator.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationModel extends MultisetModel {
    /** A multiset of bigrams (x_i-n, x_i-1, x). */
    private MapMultiset<String, String> heads;

    /** A multiset of bigrams (x, x_i+1, x_i+n). */
    private MapMultiset<String, String> tails;

    /**
     * Creates a new empty {@link CollectionModel}.
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
            tails.add(triplet.getValue0(), triplet.getValue1(), entry.getCount());
            heads.add(triplet.getValue2(), triplet.getValue1(), entry.getCount());
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

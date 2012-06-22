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
    /** The head ngrams. */
    private MapMultiset<String, String> heads;

    /** The tail ngrams. */
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

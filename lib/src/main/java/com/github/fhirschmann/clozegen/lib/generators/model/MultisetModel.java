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

import java.io.IOException;
import java.net.URL;

import com.github.fhirschmann.clozegen.lib.multiset.ReadMultisets;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple multiset-based model. The frequencies will be loaded from a file.
 * Please see {@link ReadMultisets#parseMultiset(URL)} for information on
 * the file format.
 *
 * <p>
 * Please note that the frequency file needs to be sorted already!
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class MultisetModel implements URLBasedModel {
    /**
     * The underlying multiset.
     */
    private Multiset<String> multiset;

    /**
     * Creates a new empty {@link MultisetModel}.
     */
    public MultisetModel() {
        multiset = HashMultiset.create();
    }

    @Override
    public void load(final URL url) throws IOException {
        setMultiset(ReadMultisets.parseMultiset(url));
    }

    /**
     * @return the multiset
     */
    public Multiset<String> getMultiset() {
        return multiset;
    }

    /**
     * @param multiset the multiset to set
     */
    public void setMultiset(final Multiset<String> multiset) {
        this.multiset = checkNotNull(multiset);
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("multiset", multiset.toString());
        return str.toString();
    }
}

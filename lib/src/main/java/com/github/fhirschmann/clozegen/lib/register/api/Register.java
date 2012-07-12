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
package com.github.fhirschmann.clozegen.lib.register.api;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Map;

import com.github.fhirschmann.clozegen.lib.register.DescriptionRegisterEntry;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.Maps;
import com.github.fhirschmann.clozegen.lib.register.api.Register;

/**
 * Base class for all registers.
 *
 * @param <T> the entry type
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Register<T extends RegisterEntry> extends ForwardingCollection<T> {
    /**
     * This maps identifier → Entry.
     */
    protected final Map<String, T> register;

    /**
     * Creates a new empty Register.
     */
    public Register() {
        super();
        register = Maps.newHashMap();
    }

    /**
     * Adds the specified {@link DescriptionRegisterEntry} to the register.
     *
     * @param entry the {@link DescriptionRegisterEntry} to add.
     * @return whether this collection changed
     */
    @Override
    public boolean add(final T entry) {
        boolean changed = !register.containsKey(checkNotNull(entry).getIdentifier());
        register.put(entry.getIdentifier(), entry);
        return changed;
    }

    /**
     * Returns the {@link DescriptionRegisterEntry} identified by {@code identifier}
     * or {@code null} if it doesn't exist.
     *
     * @param identifier the identifier of the entry in question
     * @return the entry identified by a given {@code identifier}
     */
    public T get(final String identifier) {
        return register.get(checkNotNull(identifier));
    }

    /**
     * Indicates whether this {@link Register} contains an entry identified
     * by {@code identifier}.
     *
     * @param identifier the identifier in question
     * @return true if this register contains an entry for a given identifier
     */
    public boolean containsIdentifier(final String identifier) {
        return register.containsKey(checkNotNull(identifier));
    }

    @Override
    public void clear() {
        register.clear();
    }

    @Override
    public Collection<T> delegate() {
        return register.values();
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("entries", register.values().toString());
        return str.toString();
    }
}

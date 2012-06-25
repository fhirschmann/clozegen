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
package com.github.fhirschmann.clozegen.lib.register;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import java.util.Collection;
import java.util.Map;

/**
 * A collection of all {@link DescriptionRegisterEntry}.
 *
 * @param <T> the type of the entries
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class DescriptionRegister<T extends DescriptionRegisterEntry>
        extends ForwardingCollection<T> {

    /** This maps identifier → Entry. */
    protected final Map<String, T> register;

    /**
     * Creates a new empty DescriptionRegister.
     */
    public DescriptionRegister() {
        super();
        register = HashBiMap.create();
    }

    /**
     * Adds the specified {@link DescriptionRegisterEntry} to the register.
     *
     * @param entry the {@link DescriptionRegisterEntry} to add.
     * @return whether this collection changed
     */
    @Override
    public boolean add(final T entry) {
        final boolean changed = !register.containsKey(entry.getIdentifier());
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
        return register.get(identifier);
    }

    /**
     * Indicates whether this {@link DescriptionRegister} contains an entry identified
     * by {@code identifier}.
     *
     * @param identifier the identifier in question
     * @return true if this register contains an entry for a given identifier
     */
    public boolean containsIdentifier(final String identifier) {
        return register.containsKey(identifier);
    }

    @Override
    public void clear() {
        register.clear();
    }

    @Override
    protected Collection<T> delegate() {
        return register.values();
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("entries", register.values().toString());
        return str.toString();
    }
}
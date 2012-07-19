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
package com.github.fhirschmann.clozegen.lib.multiset;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

/**
 * Object which maps a key to a {@link Multiset}. Additionally, it provides
 * convenience functions like
 * {@link MapMultiset#add(java.lang.Object, java.lang.Object, int)}.
 *
 * <p>This object map delegates most of its methods to the underlying
 * map, so you can use the common map interface.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of values for {@link Multiset}
 */
public class MapMultiset<K, V> extends ForwardingMap<K, Multiset<V>> {
    /** The underlying map. */
    private Map<K, Multiset<V>> map;

    /**
     * Creates a new empty {@link MapMultiset}.
     *
     * @param <K> the type of keys maintained by this map
     * @param <V> the type of values for {@link Multiset}
     * @return a new empty {@link MapMultiset}
     */
    public static <K, V> MapMultiset<K, V> create() {
        return new MapMultiset<K, V>();
    }

    /**
     * Creates a new empty {@link MapMultiset}.
     */
    public MapMultiset() {
        super();
        map = Maps.newHashMap();
    }

    /**
     * Returns a copy of the {@link Multiset} identified by {@code key} as an
     * {@link ImmutableMultiset} whose order is highest count first, with ties broken by
     * the iteration order of the original multiset.
     *
     * @param key the key to identify the multiset by
     * @return a new {@link ImmutableMultiset} whose order is highest count first
     */
    public ImmutableMultiset<V> getSorted(final K key) {
        return Multisets.copyHighestCountFirst(checkNotNull(get(key)));
    }

    /**
     * Adds a value to the {@link Multiset} identified by {@code key}.
     *
     * <p>If the multiset does not exist yet, it will be created.
     *
     * @param key the key to identify the multiset by
     * @param value the value of the multiset to add
     * @param count the number to add
     */
    public void add(final K key, final V value, final int count) {
        get(key).add(checkNotNull(value), count);
    }

    /**
     * Gets the {@link Multiset} identified by {@code key}.
     *
     * <p>If there is no multiset present which can be identified by a given key,
     * a new empty one is created and returned.
     *
     * @param key the key to identify the multiset by
     * @return the {link Multiset} identified by key or a new empty one
     */
    @Override
    @SuppressWarnings("unchecked")
    public Multiset<V> get(final Object key) {
        Multiset<V> multiset;

        final K kKey = (K) checkNotNull(key);

        if (containsKey(kKey)) {
            multiset = super.get(key);
        } else {
            multiset = HashMultiset.create();
            put((K) key, multiset);
        }
        return multiset;
    }

    /**
     * Returns to total number of entries in all {@link Multiset} objects
     * combined.
     *
     * @return total number of entries
     */
    public long total() {
        long total = 0;
        for (Entry<K, Multiset<V>> entry : this.entrySet()) {
            total += entry.getValue().size();
        }
        return total;
    }

    @Override
    protected Map<K, Multiset<V>> delegate() {
        return map;
    }
}

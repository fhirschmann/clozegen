/*
 * Copyright (K) 2012 Fabian Hirschmann <fabian@hirschm.net>
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
package com.github.fhirschmann.clozegen.lib.multiset;

import java.util.Map;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.*;

/**
 * Similar to de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFreqDist,
 * but uses Multisets.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class MapMultiset <K, V> extends ForwardingMap<K, Multiset<V>> {
    private Map<K, Multiset<V>> map;

    public MapMultiset() {
        map = Maps.newHashMap();
    }

    public ImmutableMultiset<V> getSorted(K key) {
        return Multisets.copyHighestCountFirst(checkNotNull(get(key)));
    }

    public void add(K key, V value, int count) {
        Multiset<V> multiset;

        if (containsKey(key)) {
            multiset = get(key);
        } else {
            multiset = HashMultiset.create();
            map.put(key, multiset);
        }
        multiset.add(value, count);
    }

    @Override
    protected Map<K, Multiset<V>> delegate() {
        return map;
    }
}

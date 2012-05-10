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
package com.github.fhirschmann.clozegen.lib.frequency;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import com.github.fhirschmann.clozegen.lib.util.ListUtils;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class FrequencyStructure<V> {
    private HashMap<V,Integer> hashMap = Maps.newHashMap();
    private EventList<FrequencyPair<V>> basicList = new BasicEventList();
    private EventList<FrequencyPair<V>> sortedList = new SortedList(basicList);


    public static void main(String[] args) {
        FrequencyStructure<String> f = new FrequencyStructure<String>();
        f.add("foo", 4);
        System.out.println(f.getFrequency("fo"));

    }

    public boolean add(final V value, final int frequency) {
        if (hashMap.containsKey(value)) {
            return false;
        }
        basicList.add(new FrequencyPair(value, frequency));
        hashMap.put(value, basicList.size() - 1);

        return true;
    }

    public FrequencyPair<V> getFrequencyPair(V value) {
        if (!hashMap.containsKey(value)) {
            return null;
        }
        final int index = hashMap.get(value);

        return basicList.get(index);
    }

    public int getFrequency(V value) {
        FrequencyPair<V> frequencyPair = getFrequencyPair(value);

        if (frequencyPair == null) {
            return 0;
        }

        return frequencyPair.getFrequency();
    }

    public int size() {
        return basicList.size();
    }

    public List<FrequencyPair<V>> getAdjacentTo(V value, final int num) {
        FrequencyPair<V> frequencyPair = getFrequencyPair(value);

        if (frequencyPair == null) {
            return null;
        }

        return ListUtils.getAdjacentTo(sortedList, frequencyPair, num);
    }
}

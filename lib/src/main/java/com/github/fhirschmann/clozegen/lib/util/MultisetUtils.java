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
package com.github.fhirschmann.clozegen.lib.util;

import com.google.common.collect.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Utility functions for Multisets.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class MultisetUtils {
    public static <E> LinkedHashMultiset<E> sortMultiSet(Multiset<E> multiset) {
        ImmutableMultiset immutableSet = Multisets.copyHighestCountFirst(multiset);
        return LinkedHashMultiset.create(immutableSet);
    }

    public static <E> List<E> sortedElementList(Multiset<E> multiset, int limit) {
        List<E> list = Lists.newLinkedList();
        LinkedHashMultiset<E> sms = sortMultiSet(multiset);

        if (limit == -1) {
            limit = multiset.elementSet().size();
        }

        Iterator<E> it = Iterators.limit(sms.iterator(), limit);

        while (it.hasNext()) {
            E next = it.next();
            list.add(next);
        }
        return list;
    }

    public static <E> List<E> sortedElementList(Multiset<E> multiset) {
        return sortedElementList(multiset, -1);
    }

    public static void writeMultiSet(Multiset<String> multiset, File file) throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        for (Multiset.Entry entry : multiset.entrySet()) {
            bufferedWriter.write(
                    String.format("%s\t%d%n",
                    entry.getElement().toString(), entry.getCount()));
        }
        bufferedWriter.close();
    }

    public static void writeSortedMultiSet(Multiset<String> multiSet, File file) throws IOException {
        ImmutableMultiset<String> im = Multisets.copyHighestCountFirst(multiSet);
        writeMultiSet(im, file);
    }

    public static <E> Multiset<E> mergeMultiSets(Multiset<E> multiset1, Multiset<E> multiset2) {
        Multiset<E> multiset = LinkedHashMultiset.create(multiset1);
        Iterators.addAll(multiset, multiset2.iterator());
        return multiset;
    }
}

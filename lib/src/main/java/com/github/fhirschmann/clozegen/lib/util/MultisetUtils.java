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

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.OutputSupplier;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public static void writeMultiSet(Multiset<String> multiset, File file) throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        for (Multiset.Entry entry : multiset.entrySet()) {
            bufferedWriter.write(
                    String.format("%s\t%d%n",
                    entry.getElement().toString(), entry.getCount()));
        }
    }

    public static void writeSortedMultiSet(Multiset<String> multiSet, File file) throws IOException {
        ImmutableMultiset<String> im = Multisets.copyHighestCountFirst(multiSet);
        writeMultiSet(im, file);
    }
}

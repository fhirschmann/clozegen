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
package com.github.fhirschmann.clozegen.lib.multiset;

import com.github.fhirschmann.clozegen.lib.util.MultisetUtils;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class MultisetWriter {
    private MultisetWriter() {
    }

    /**
     * Writes a multiset to a file. The elements will be written to a tab-separated
     * file with the string representation of the elements on the left-hand side and
     * the corresponding counts on the right-hand side.
     *
     * @param multiset the multiset to write from
     * @param file the file to write to
     * @throws IOException on errors opening/writing to the file
     */
    public static void writeMultiSet(final Multiset<String> multiset, final File file)
            throws IOException {
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        for (Multiset.Entry entry : multiset.entrySet()) {
            bufferedWriter.write(
                    String.format("%s\t%d%n",
                    entry.getElement().toString(), entry.getCount()));
        }
        bufferedWriter.close();
    }

    /**
     * Sorts a Multiset by its counts before writing it to a file using
     * {@link MultisetUtils#writeMultiSet}.
     *
     * @param multiset the multiset to write
     * @param file the file to write to
     * @throws IOException on errors opening/writing to the file
     */
    public static void writeSortedMultiSet(final Multiset<String> multiset,
            final File file) throws IOException {
        final ImmutableMultiset<String> im = Multisets.copyHighestCountFirst(multiset);
        writeMultiSet(im, file);
    }


}

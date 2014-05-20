/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschmann.email>
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

/**
 * Utility functions to write {@link Multiset}s.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public final class WriteMultisets {
    /** Constructor in utility class should not be called. */
    private WriteMultisets() {
    }

    /**
     * Writes a multiset to a file. The elements will be written to a tab-separated
     * file with the string representation of the elements on the left-hand side and
     * the corresponding counts on the right-hand side.
     *
     * @param multiset the multiset to write from
     * @param minFrequency the minimum frequency a word must have in order to be written
     * @param file the file to write to
     * @throws IOException on errors opening/writing to the file
     */
    public static void writeMultiSet(final Multiset<String> multiset,
            final int minFrequency, final File file)
            throws IOException {
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            if (entry.getCount() >= minFrequency) {
                writeMultiSetLine(bufferedWriter, entry.getElement().toString(),
                        entry.getCount());
            }
        }
        bufferedWriter.close();
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
        writeMultiSet(multiset, 1, file);
    }


    /**
     * Writes a line (entry and frequency) to a {@link Writer}. Does not close
     * the writer.
     *
     * @param writer the writer to write to
     * @param str the string to write
     * @param count the frequency of the string
     * @throws IOException on errors writing to file
     */
    public static void writeMultiSetLine(final Writer writer,
            final String str, final int count) throws IOException {
            writer.write(String.format("%s\t%d%n", str, count));
    }

    /**
     * Sorts a Multiset by its counts before writing it to a file using
     * {@link WriteMultisets#writeMultiSet(Multiset, File)}.
     *
     * @param multiset the multiset to write
     * @param minFrequency the minimum frequency a word must have in order to be written
     * @param file the file to write to
     * @throws IOException on errors opening/writing to the file
     */
    public static void writeSortedMultiSet(final Multiset<String> multiset,
            final int minFrequency, final File file) throws IOException {
        final ImmutableMultiset<String> im = Multisets.copyHighestCountFirst(multiset);
        writeMultiSet(im, minFrequency, file);
    }

    /**
     * Sorts a Multiset by its counts before writing it to a file using
     * {@link WriteMultisets#writeMultiSet(Multiset, File)}.
     *
     * @param multiset the multiset to write
     * @param file the file to write to
     * @throws IOException on errors opening/writing to the file
     */
    public static void writeSortedMultiSet(final Multiset<String> multiset,
            final File file) throws IOException {
        writeSortedMultiSet(multiset, 1, file);
    }

}

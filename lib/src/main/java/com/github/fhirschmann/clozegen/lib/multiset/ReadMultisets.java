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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.io.Resources;

/**
 * Utility class for parsing frequencies from plain-text files.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class ReadMultisets {
    /** Constructor in utility class should not be called. */
    private ReadMultisets() {
    }

    /**
     * Parses frequencies from a URL. The subject and the count for a subject
     * need to be delimited by {@code \t} with the count on the right-hand
     * side.
     *
     * <p>For example, assuming your frequency file contains the following lines:
     * <pre>
     * one of the   200
     * because of the  100
     * members of the   50
     * </pre>
     * Then getting the count of "because of the" will yield 100.
     *
     * @param url the URL to the file to parse
     * @param charset the charset of the file
     * @return the parsed frequencies
     * @throws IOException on errors reading from the file
     */
    public static Multiset<String> parseMultiset(final URL url, final Charset charset)
            throws IOException {
        final Multiset<String> multiset = LinkedHashMultiset.create();
        final List<String> lines = Resources.readLines(checkNotNull(url), charset);

        for (String line : lines) {
            final String[] tokens = line.split("\t");
            multiset.add(tokens[0], Integer.parseInt(tokens[1]));
        }

        return multiset;
    }

    /**
     * Parses UTF-8 encoded frequencies from a URL. The subject and the count for a
     * subject need to be delimited by {@code \t} with the count on the right-hand
     * side.
     *
     * <p>For example, assuming your frequency file contains the following lines:
     * <pre>
     * one of the   200
     * because of the  100
     * members of the   50
     * </pre>
     * Then getting the count of "because of the" will yield 100.
     *
     * @param url the URL to the file to parse
     * @return the parsed frequencies
     * @throws IOException on errors reading from the file
     */
    public static Multiset<String> parseMultiset(final URL url) throws IOException {
        return parseMultiset(url, Charsets.UTF_8);
    }

    /**
     * Parses conditional frequencies from a URL. The subject and the count for a subject
     * need to be delimited by {@code \t} with the count on the right-hand side.
     *
     * <p>Additionally, the remaining words on the left-hand side are split using the
     * whitespace as delimiter. The {@code key} parameter identifies the condition
     * by which the rest of the word sequence (with the key itself removed) is identified
     * by.
     *
     * <p>For example, assuming your frequency file contains the following lines:
     * <pre>
     * one of   200
     * because of   100
     * members of   50
     * </pre>
     * Then selecting {@code 0} as key will yield a count of 100 when
     * looking up "of" given the the key (condition) is "because".
     *
     * @param url the URL to the file to parse
     * @param key the key to identify the word sequence by
     * @param charset the charset of the file
     * @return the parsed frequencies
     * @throws IOException on errors reading from the file
     * @throws URISyntaxException on errors during URI conversion
     */
    public static MapMultiset<String, String> parseMapMultiset(
            final URL url, final int key, final Charset charset)
            throws IOException, URISyntaxException {
        final MapMultiset<String, String> mms = MapMultiset.create();
        final List<String> lines = Resources.readLines(checkNotNull(url), charset);

        for (String line : lines) {
            final String[] tokens = line.split("\t");
            final String[] ngram = tokens[0].split(" ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ngram.length; i++) {
                if (i != key) {
                    sb.append(ngram[i]);
                }
            }
            mms.add(ngram[key], sb.toString(), Integer.parseInt(tokens[1]));
        }
        return mms;
    }

    /**
     * Parses UTF-8 encoded conditional frequencies from a URL. The subject and the
     * count for a subject need to be delimited by {@code \t} with the count on the
     * right-hand side.
     *
     * <p>Additionally, the remaining words on the left-hand side are split using the
     * whitespace as delimiter. The {@code key} parameter identifies the condition
     * by which the rest of the word sequence (with the key itself removed) is identified
     * by.
     *
     * <p>For example, assuming your frequency file contains the following lines:
     * <pre>
     * one of   200
     * because of   100
     * members of   50
     * </pre>
     * Then selecting {@code 0} as key will yield a count of 100 when
     * looking up "of" given the the key (condition) is "because".
     *
     * @param url the URL to the file to parse
     * @param key the key to identify the word sequence by
     * @return the parsed frequencies
     * @throws IOException on errors reading from the file
     * @throws URISyntaxException on errors during URI conversion
     */
    public static MapMultiset<String, String> parseMapMultiset(
            final URL url, final int key)
            throws IOException, URISyntaxException {
        return parseMapMultiset(url, key, Charsets.UTF_8);
    }
}

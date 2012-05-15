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
package com.github.fhirschmann.clozegen.lib.parser;

import com.github.fhirschmann.clozegen.lib.multiset.MapMultiset;
import com.google.common.base.Charsets;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class FrequencyParser {
    public static Multiset<String> parseMultiset(final URL url) throws IOException {
        final Multiset<String> multiset = LinkedHashMultiset.create();
        final List<String> lines = Resources.readLines(url, Charsets.UTF_8);

        for (String line : lines) {
            final String[] tokens = line.split("\t");
            multiset.add(tokens[0], Integer.parseInt(tokens[1]));
        }

        return multiset;
    }

    public static MapMultiset<String, String> parseMapMultiset(
            final URL url, final int key) throws IOException {
        final MapMultiset<String, String> mms = MapMultiset.create();
        final List<String> lines = Resources.readLines(url, Charsets.UTF_8);

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
}

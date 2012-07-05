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
package com.github.fhirschmann.clozegen.cli;

import java.util.Map;

import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.google.common.collect.Maps;

/**
 * Utility class for the CLI.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class Utils {
    /** Utility classes should not be instantiated. */
    private Utils() {
    }

    /**
     * Extracts a map where K → V corresponds to k₁/v₁,k₂/v₂,k₃/-1,k_n/v_n.
     *
     * @param str the string to parse
     * @return a map created from the string
     */
    public static Map<String, Integer> parseGapClasses(final String str) {
        Map<String, Integer> map = Maps.newHashMap();
        for (String clazz : str.split(",")) {
            if (clazz.contains("/")) {
                String[] opts = clazz.split("/");
                map.put(opts[0], Integer.parseInt(opts[1]));
            } else {
                map.put(clazz, GapAnnotator.DEFAULT_ANSWER_COUNT);
            }
        }

        return map;
    }

}

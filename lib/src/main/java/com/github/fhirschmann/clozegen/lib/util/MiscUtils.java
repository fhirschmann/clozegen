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
package com.github.fhirschmann.clozegen.lib.util;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URL;

import com.google.common.base.Joiner;

/**
 * Miscellaneous utility functions.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class MiscUtils {
    /**
     * Utility class.
     */
    private MiscUtils() {
    }

    /**
     * Joins a string using a single whitespace.
     */
    public static final Joiner WS_JOINER = Joiner.on(" ");

    /**
     * Returns the file extension of {@code filename}.
     *
     * @param filename the filename in question
     * @return the filename's extension
     */
    public static String getFileExtension(final String filename) {
        checkNotNull(filename);
        checkArgument(filename.contains("."), "Filename does not contain a period.");
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * Returns the filename without the absolut path to the file.
     *
     * @param url the url in question
     * @return filename
     */
    public static String filenameFromURL(final URL url) {
        checkNotNull(url);
        return url.getFile().substring(pathFromURL(url).length());
    }

    /**
     * Returns the path to a file without the filename.
     *
     * @param url the url in question
     * @return the path to the file
     */
    public static String pathFromURL(final URL url) {
        checkNotNull(url);
        checkArgument(url.getFile().contains(System.getProperty("file.separator")),
                "Path does not contain a file separator.");
        return url.getFile().substring(0, url.getFile().lastIndexOf("/") + 1);
    }
}

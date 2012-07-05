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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Additional preconditions.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class Preconditions2 {
    /**
     * Checks if a file is exists and is a file.
     *
     * @param url the url to check
     * @return the input url
     * @throws URISyntaxException on errors during URI conversion
     * @throws IOException on file errors
     */
    public static URL checkFile(final URL url) throws URISyntaxException, IOException {
        File file = new File(checkNotNull(url).toURI());
        if (!file.exists()) {
            throw new IOException("File does not exist: " + file.getAbsolutePath());
        }
        if (!file.isFile()) {
            throw new IOException("Not a file: " + file.getAbsolutePath());
        }
        return url;
    }
}

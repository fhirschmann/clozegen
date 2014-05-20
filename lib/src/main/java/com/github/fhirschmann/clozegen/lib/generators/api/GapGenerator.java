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
package com.github.fhirschmann.clozegen.lib.generators.api;

import com.google.common.base.Optional;

/**
 * This is the basic interface which Gap Generators must implement.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public interface GapGenerator {
    /**
     * Called for each word for which a gap should be generated.
     *
     * <p>
     * This method should return an {@link Optional} Gap. {@link Optional} is a way
     * of replacing a nullable Gap reference with a non-null value in order to
     * prevent the ambiguity that comes with null.
     * </p>
     *
     * @param count the number of answers to generate
     * @return a new gap
     */
    Optional<Gap> generate(int count);
}

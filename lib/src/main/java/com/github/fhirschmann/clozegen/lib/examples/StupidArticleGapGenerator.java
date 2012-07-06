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
package com.github.fhirschmann.clozegen.lib.examples;

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.generators.api.GapGenerator;
import com.google.common.base.Optional;

/**
 * This is a sample implementation of a generator for gaps for articles. This
 * is only for demonstration purposes!
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
// START SNIPPET: stupidart
public class StupidArticleGapGenerator implements GapGenerator {
    /** The valid answer for this gap. */
    private String validAnswer;

    /**
     * Creates a new Gap Generator.
     *
     * @param token the token to generate a gap for
     */
    public StupidArticleGapGenerator(final String token) {
        this.validAnswer = token;
    }

    @Override
    public Optional<Gap> generate(final int count) {
        return Optional.of(Gap.with(validAnswer, "a", "an", "the"));
    }
}
// END SNIPPET: stupidart

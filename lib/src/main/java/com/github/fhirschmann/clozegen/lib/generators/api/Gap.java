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
package com.github.fhirschmann.clozegen.lib.generators.api;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * A gap object contains valid and invalid answers.
 *
 * <p>
 * A gap provides multiple convenience methods. The following gaps are all equal:
 * <blockquote><pre>
 * {@code
 * Gap gap1 = new Gap.with("in", "of", "at");
 *
 * Gap gap2 = new Gap();
 * gap2.addValidAnswer("in");
 * gap2.addInvalidAnswers("of", "at");
 * }
 * </pre></blockquote>
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Gap {
    /**
     * The set of invalid answers.
     */
    private Set<String> invalidAnswers;

    /**
     * The set of valid answers.
     */
    private Set<String> validAnswers;

    /**
     * Constructs a new empty gap.
     */
    public Gap() {
        invalidAnswers = Sets.newHashSet();
        validAnswers = Sets.newHashSet();
    }

    /**
     * Returns the set of invalid answers.
     *
     * @return set of invalid answers.
     */
    public Set<String> getInvalidAnswers() {
        return invalidAnswers;
    }

    /**
     * Adds invalid answers to this gap.
     *
     * @param invalidAnswers the invalid answers to set
     */
    public void addInvalidAnswers(final Collection<String> invalidAnswers) {
        this.invalidAnswers.addAll(checkNotNull(invalidAnswers));
    }

    /**
     * Adds invalid answers to this gap.
     *
     * @param invalidAnswers the invalid answers to set
     */
    public void addInvalidAnswers(final String... invalidAnswers) {
        addInvalidAnswers(Sets.newHashSet(invalidAnswers));
    }

    /**
     * Returns the set of valid answers.
     *
     * @return set of valid answers.
     */
    public Set<String> getValidAnswers() {
        return validAnswers;
    }

    /**
     * Sets the valid answers.
     *
     * @param validAnswers the valid answers to set
     */
    public void addValidAnswers(final Collection<String> validAnswers) {
        this.validAnswers.addAll(checkNotNull(validAnswers));
    }

    /**
     * Sets the valid answers.
     *
     * @param validAnswers the invalid answers to set
     */
    public void addValidAnswers(final String... validAnswers) {
        addValidAnswers(Sets.newHashSet(validAnswers));
    }

    /**
     * Sets the valid answer to a single answer.
     *
     * @param validAnswer the valid answer to set
     */
    public void addValidAnswer(final String validAnswer) {
        addValidAnswers(new String[] {checkNotNull(validAnswer)});
    }

    /**
     * Returns an unmodifiable view of all answers (the union of invalid and valid
     * answers).
     *
     * @return set of invalid and valid answers
     */
    public Set<String> getAllAnswers() {
        return Sets.union(getValidAnswers(), getInvalidAnswers());
    }

    /**
     * Convenience method for generating gaps.
     *
     * @param validAnswer the valid answers of this gap
     * @return a new gap based upon the parameters provided
     */
    public static Gap with(final String validAnswer) {
        Gap gap = new Gap();
        gap.addValidAnswer(validAnswer);

        return gap;
    }

    /**
     * Convenience method for generating gaps.
     *
     * @param validAnswer the valid answers of this gap
     * @param invalidAnswers the invalid answers of this gap
     * @return a new gap based upon the parameters provided
     */
    public static Gap with(final String validAnswer, final String... invalidAnswers) {
        Gap gap = Gap.with(validAnswer);
        gap.addInvalidAnswers(invalidAnswers);

        return gap;
    }

    /**
     * Convenience method for generating gaps.
     *
     * @param validAnswer the valid answers of this gap
     * @param invalidAnswers the invalid answers of this gap
     * @return a new gap based upon the parameters provided
     */
    public static Gap with(final String validAnswer,
            final Collection<String> invalidAnswers) {
        Gap gap = Gap.with(validAnswer);
        gap.addInvalidAnswers(invalidAnswers);

        return gap;
    }

    /**
     * Convenience method for generating gaps.
     *
     * @param validAnswers set of valid answers
     * @param invalidAnswers set of invalid answers
     * @return a new gap based upon the parameters provided
     */
    public static Gap with(final Collection<String> validAnswers,
            final Collection<String> invalidAnswers) {
        Gap gap = new Gap();
        gap.addValidAnswers(validAnswers);
        gap.addInvalidAnswers(invalidAnswers);

        return gap;
    }

    /**
     * Convenience method for generating gaps.
     *
     * @param invalidAnswers set of invalid answers
     * @param validAnswers set of valid answers
     * @return a new gap based upon the parameters provided
     */
    public static Gap with(final Iterable<String> validAnswers,
            final Iterable<String> invalidAnswers) {
        Gap gap = new Gap();
        Iterables.addAll(gap.getValidAnswers(), validAnswers);
        Iterables.addAll(gap.getInvalidAnswers(), invalidAnswers);

        return gap;
    }


    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("valid", getValidAnswers().toString());
        str.add("invalid", getInvalidAnswers().toString());

        return str.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getInvalidAnswers(), getValidAnswers());
    }

    @Override
    public boolean equals(final Object obj) {
        if ((obj == null) || (!getClass().equals(obj.getClass()))) {
            return false;
        }
        final Gap other = (Gap) obj;

        return Objects.equal(getInvalidAnswers(), other.getInvalidAnswers())
                && Objects.equal(getValidAnswers(), other.getValidAnswers());
    }
}

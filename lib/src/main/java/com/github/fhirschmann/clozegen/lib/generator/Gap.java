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
package com.github.fhirschmann.clozegen.lib.generator;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Sets;
import java.util.Set;

/**
 * A gap object contains valid and invalid answers.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Gap {
    /** The set of invalid answers. */
    private Set<String> invalidAnswers;

    /** The set of valid answers. */
    private Set<String> validAnswers;

    /**
     * Constructs a new empty gap.
     */
    public Gap() {
        invalidAnswers = Sets.newHashSet();
        validAnswers = Sets.newHashSet();
    }

    /**
     * Constructs a gap prefilled with the given answers.
     *
     * @param invalidAnswers set of invalid answers
     * @param validAnswers set of valid answers
     */
    public Gap(final Set<String> validAnswers, final Set<String> invalidAnswers) {
        this.validAnswers.addAll(validAnswers);
        this.invalidAnswers.addAll(invalidAnswers);
    }

    /**
     * Returns the set of invalid answers.
     *
     * @return set of invalid answers.
     */
    public Set getInvalidAnswers() {
        return invalidAnswers;
    }

    /**
     * Sets the invalid answers.
     *
     * @param invalidAnswers the invalid answers to set
     */
    public void setInvalidAnswers(final Set invalidAnswers) {
        this.invalidAnswers = invalidAnswers;
    }

    /**
     * Sets the invalid answers.
     *
     * @param invalidAnswers the invalid answers to set
     */
    public void setInvalidAnswers(final String... invalidAnswers) {
        this.invalidAnswers.addAll(Sets.newHashSet(invalidAnswers));
    }

    /**
     * Returns the set of valid answers.
     *
     * @return set of valid answers.
     */
    public Set getValidAnswers() {
        return validAnswers;
    }

    /**
     * Sets the valid answers.
     *
     * @param validAnswers the valid answers to set
     */
    public void setValidAnswers(final Set validAnswers) {
        this.validAnswers = validAnswers;
    }

    /**
     * Sets the valid answers.
     *
     * @param validAnswers the invalid answers to set
     */
    public void setValidAnswers(final String... validAnswers) {
        this.validAnswers.addAll(Sets.newHashSet(validAnswers));
    }

    /**
     * Returns an unmodifiable view of all answers (the union
     * of invalid and valid answers).
     *
     * @return set of invalid and valid answers
     */
    public Set getAllAnswers() {
        return Sets.union(getValidAnswers(), getInvalidAnswers());
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("valid", getValidAnswers().toString());
        str.add("invalid", getInvalidAnswers().toString());

        return str.toString();
    }
}

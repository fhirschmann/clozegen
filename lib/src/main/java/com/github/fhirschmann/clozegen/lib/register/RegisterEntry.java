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
package com.github.fhirschmann.clozegen.lib.register;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;

/**
 * Represents an RegisterEntry of a {@link Register}.
 *
 * @see Register
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class RegisterEntry {
    /** The description of this entry. */
    private AnalysisEngineDescription description;

    /** The name of this entry. */
    private String name;

    /** The unique identifier of this entry. */
    private String identifier;

    /** The languages supported by this entry. */
    private Set<String> supportedLanguages;

    /** The pattern <code>identifier</code> must match. */
    public static final Pattern PATTERN = Pattern.compile("[A-Za-z0-9_]*");

    /**
     * Creates a new register entry.
     *
     * @param identifier the identifier of this entry
     */
    public RegisterEntry(final String identifier) {
        setIdentifier(identifier);
    }

    /**
     * Returns the description of this entry.
     *
     * @return the description
     */
    public AnalysisEngineDescription getDescription() {
        return description;
    }

    /**
     * Sets the description of this entry.
     *
     * @param description the description to set
     */
    public void setDescription(final AnalysisEngineDescription description) {
        this.description = description;
    }

    /**
     * Returns the name of this entry.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this entry.
     *
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns the identifier for this entry.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier for this entry. The identifier must match [A-Za-z0-9_]*
     *
     * @param identifier the identifier to set
     */
    private void setIdentifier(final String identifier) {
        if (!PATTERN.matcher(identifier).matches()) {
            throw new IllegalArgumentException(
                    String.format("identifier '%s' must match %s.",
                    identifier, PATTERN.toString()));
        }
        this.identifier = identifier;
    }

    /**
     * Returns the supported languages of the UIMA Description of this entry.
     *
     * @return the supported languages
     */
    public Set<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    /**
     * Sets the supported languages of the UIMA Description of this entry.
     *
     * @param supportedLanguages the supported languages
     */
    public void setSupportedLanguages(final Set<String> supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("identifier", getIdentifier());
        str.add("name", getName());
        return str.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIdentifier());
    }

    @Override
    public boolean equals(final Object obj) {
        if ((obj == null) || (!getClass().equals(obj.getClass()))) {
            return false;
        }
        final RegisterEntry other = (RegisterEntry) obj;

        return Objects.equal(getIdentifier(), other.getIdentifier());
    }
}

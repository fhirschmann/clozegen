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
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

/**
 * Represents a DescriptionRegisterEntry of a {@link Register}.
 *
 * @see Register
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class DescriptionRegisterEntry {
    /** The name of this entry. */
    private String name;

    /** The unique identifier of this entry. */
    private String identifier;

    /** The languages supported by this entry. */
    private Set<String> supportedLanguages;

    /** The component class (used for creating the description). */
    private Class<? extends AnalysisComponent> componentClass;

    /** The configuration data (used for creating the description). */
    private Object[] configurationData;

    /** The pattern <code>identifier</code> must match. */
    public static final Pattern PATTERN = Pattern.compile("[A-Za-z0-9_]*");

    /**
     * Creates a new register entry.
     *
     * @param identifier the identifier of this entry
     * @param componentClass the component class for the primitive description
     */
    public DescriptionRegisterEntry(final String identifier,
            final Class<? extends AnalysisComponent> componentClass) {
        setIdentifier(identifier);
        this.componentClass = componentClass;
    }

    /**
     * Creates a new register entry.
     *
     * @param identifier the identifier of this entry
     * @param componentClass the component class for the primitive description
     * @param configurationData the configuration data for the primitive description
     */
    public DescriptionRegisterEntry(final String identifier,
            final Class<? extends AnalysisComponent> componentClass,
            final Object... configurationData) {
        this(identifier, componentClass);
        this.configurationData = configurationData;
    }

    /**
     * Creates a new description for this entry from the given
     * <code>configurationData</code>.
     *
     * @param configurationData the configuration data for the description
     * @return the description
     * @throws ResourceInitializationException on errors constructing the description
     */
    private AnalysisEngineDescription getDescriptionFor(final Object[] configurationData)
            throws ResourceInitializationException {
        AnalysisEngineDescription desc;
        if (configurationData == null) {
            desc = createPrimitiveDescription(componentClass);
        } else {
            desc = createPrimitiveDescription(componentClass, configurationData);

        }
        return desc;
    }

    /**
     * Creates a new description for this entry based on the configuration data
     * supplied when this class was constructed.
     *
     * @return the description
     * @throws ResourceInitializationException on errors constructing the description
     */
    public AnalysisEngineDescription getDescription()
            throws ResourceInitializationException {
        return getDescriptionFor(configurationData);
    }

    /**
     * Creates a new description for this entry based on the configuration data
     * supplied when this class was constructed in addition to
     * <code>additionalConfigurationData</code>.
     *
     * @param additionalConfigurationData any additional configuration data
     * @return the description
     * @throws ResourceInitializationException on errors constructing the description
     */
    public AnalysisEngineDescription getDescription(
            final Object[] additionalConfigurationData)
            throws ResourceInitializationException {
        List<Object> data = Lists.newArrayList(configurationData);
        data.addAll(Arrays.asList(additionalConfigurationData));

        return getDescriptionFor(configurationData);
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
        final DescriptionRegisterEntry other = (DescriptionRegisterEntry) obj;

        return Objects.equal(getIdentifier(), other.getIdentifier());
    }
}

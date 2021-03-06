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
package com.github.fhirschmann.clozegen.lib.register;

import static com.google.common.base.Preconditions.checkArgument;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import com.github.fhirschmann.clozegen.lib.register.api.Register;
import com.github.fhirschmann.clozegen.lib.register.api.RegisterEntry;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Lists;

/**
 * Represents an entry of a {@link Register}.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class DescriptionRegisterEntry implements RegisterEntry {
    /**
     * The name of this entry.
     *
     * This is used to display its name in the User Interfaces.
     */
    private String name;

    /**
     * The unique identifier of this entry.
     *
     * This is also used the command line interface to activate an entry.
     */
    private String identifier;

    /**
     * The component class.
     *
     * This is used for creating the description.
     */
    private Class<? extends AnalysisComponent> componentClass;

    /** The configuration data
     *
     * This is used for creating the description.
     */
    private List<Object> configurationData;

    /**
     * The pattern {@code identifier} must match.
     */
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
        setComponentClass(componentClass);
        configurationData = Lists.newArrayList();
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
        setConfigurationData(Arrays.asList(configurationData));
    }

    /**
     * Creates a new description for this entry from the given
     * {@code configurationData}.
     *
     * @param configurationData the configuration data for the description
     * @return the description
     * @throws ResourceInitializationException on errors constructing the description
     */
    private AnalysisEngineDescription getDescriptionFor(
            @Nullable final List<Object> configurationData)
            throws ResourceInitializationException {
        AnalysisEngineDescription desc;
        if (configurationData == null) {
            desc = createPrimitiveDescription(getComponentClass());
        } else {
            desc = createPrimitiveDescription(
                    getComponentClass(), configurationData.toArray());
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
        return getDescriptionFor(getConfigurationData());
    }

    /**
     * Creates a new description for this entry based on the configuration data
     * supplied when this class was constructed in addition to
     * {@code additionalConfigurationData}.
     *
     * @param additionalConfigurationData any additional configuration data
     * @return the description
     * @throws ResourceInitializationException on errors constructing the description
     */
    public AnalysisEngineDescription getDescription(
            final Object... additionalConfigurationData)
            throws ResourceInitializationException {
        List<Object> data = Lists.newArrayList(getConfigurationData());
        data.addAll(Arrays.asList(additionalConfigurationData));

        return getDescriptionFor(data);
    }

    @Override
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

    @Override
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier for this entry. The identifier must match [A-Za-z0-9_]*
     *
     * @param identifier the identifier to set
     */
    private void setIdentifier(final String identifier) {
        checkArgument(PATTERN.matcher(identifier).matches(),
                String.format("identifier '%s' must match %s.",
                identifier, PATTERN.toString()));
        this.identifier = identifier;
    }

    /**
     * Returns the component class of this entry.
     *
     * @return the component class
     */
    public Class<? extends AnalysisComponent> getComponentClass() {
        return componentClass;
    }

    /**
     * Sets the component class for this entry.
     *
     * @param componentClass the component class to set
     */
    public void setComponentClass(
            final Class<? extends AnalysisComponent> componentClass) {
        this.componentClass = componentClass;
    }

    /**
     * Returns the configuration data for this entry.
     *
     * @return the configuration data for this entry
     */
    public List<Object> getConfigurationData() {
        return configurationData;
    }

    /**
     * Sets the configuration data for this entry.
     *
     * @param configurationData the configuration data to set
     */
    public void setConfigurationData(final List<Object> configurationData) {
        this.configurationData = configurationData;
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

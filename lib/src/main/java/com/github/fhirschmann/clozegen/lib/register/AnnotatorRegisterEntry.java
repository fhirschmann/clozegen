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
package com.github.fhirschmann.clozegen.lib.register;

import java.util.Set;

import org.apache.uima.analysis_component.AnalysisComponent;

import com.google.common.collect.Sets;

/**
 * An entry of an {@link AnnotatorRegister}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class AnnotatorRegisterEntry extends DescriptionRegisterEntry {
    /** The languages supported by this entry. */
    private Set<String> supportedLanguages;

    /**
     * Creates a new register entry.
     *
     * @param identifier the identifier of this entry
     * @param componentClass the component class for the primitive description
     */
    public AnnotatorRegisterEntry(final String identifier,
            final Class<? extends AnalysisComponent> componentClass) {
        super(identifier, componentClass);
    }

    /**
     * Creates a new register entry.
     *
     * @param identifier the identifier of this entry
     * @param componentClass the component class for the primitive description
     * @param configurationData the configuration data for the primitive description
     */
    public AnnotatorRegisterEntry(final String identifier,
            final Class<? extends AnalysisComponent> componentClass,
            final Object... configurationData) {
        super(identifier, componentClass, configurationData);
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

    /**
     * Sets the supported languages of the UIMA Description of this entry.
     *
     * @param supportedLanguages the supported languages
     */
    public void setSupportedLanguages(final String... supportedLanguages) {
        this.supportedLanguages = Sets.newHashSet(supportedLanguages);
    }

    /**
     * Sets the supported language of the UIMA Description of this entry.
     *
     * @param supportedLanguage the supported language
     */
    public void setSupportedLanguage(final String supportedLanguage) {
        setSupportedLanguages(new String[] {supportedLanguage});
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getIdentifier(), getName());
    }
}

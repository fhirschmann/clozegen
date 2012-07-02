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

import com.google.common.collect.Sets;
import java.util.Set;
import org.apache.uima.analysis_component.AnalysisComponent;

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

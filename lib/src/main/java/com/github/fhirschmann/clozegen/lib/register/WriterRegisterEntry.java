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

import com.github.fhirschmann.clozegen.lib.components.api.OutputFileWriter;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class WriterRegisterEntry extends DescriptionRegisterEntry {
    /**
     * Creates a new register entry.
     *
     * @param identifier the identifier of this entry
     * @param componentClass the component class for the primitive description
     */
    public WriterRegisterEntry(final String identifier,
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
    public WriterRegisterEntry(final String identifier,
            final Class<? extends AnalysisComponent> componentClass,
            final Object... configurationData) {
        super(identifier, componentClass, configurationData);
    }

    /**
     * Creates a new writer description which will write to {@code outputFile}.
     *
     * @param outputFile the file to write to
     * @return a new writer description
     * @throws ResourceInitializationException on errors during initialization
     */
    public AnalysisEngineDescription getWriterDescription(final String outputFile)
            throws ResourceInitializationException {
        return getDescription(OutputFileWriter.PARAM_OUTPUT_FILE, outputFile);
    }
}

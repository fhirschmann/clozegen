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
package com.github.fhirschmann.clozegen.lib.components.api;

import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.descriptor.ConfigurationParameter;

/**
 * A consumer which writes to an output file.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public abstract class OutputFileWriter extends JCasConsumer_ImplBase {
    /**
     * <em>[mandatory]</em>
     *
     * The file to which the collocation n-grams will be written to.
     */
    public static final String PARAM_OUTPUT_FILE = "OutputFile";
    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, mandatory = true)
    protected String outputFile;
}

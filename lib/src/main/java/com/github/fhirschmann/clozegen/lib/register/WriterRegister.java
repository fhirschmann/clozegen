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

import com.github.fhirschmann.clozegen.lib.util.MiscUtils;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import static com.google.common.base.Preconditions.checkArgument;
import org.apache.uima.resource.ResourceInitializationException;
import java.net.URL;

/**
 * A register of mappings from file extensions to {@link WriterRegisterEntry}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class WriterRegister extends DescriptionRegister<WriterRegisterEntry> {
    /**
     * Returns the output writer associated with the given {@code output} file.
     *
     * @param output the output file
     * @return a new output writer
     * @throws ResourceInitializationException on errors during initialization
     */
    public AnalysisEngineDescription getWriterFor(final URL output)
            throws ResourceInitializationException {
        String outExt = MiscUtils.getFileExtension(output.getFile());
        checkArgument(register.containsKey(
                outExt), "Unknown output file type: " + outExt);
        return register.get(outExt).getWriterDescription(output.getFile());
    }
}

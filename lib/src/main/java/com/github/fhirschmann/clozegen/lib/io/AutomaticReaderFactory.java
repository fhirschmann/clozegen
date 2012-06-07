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
package com.github.fhirschmann.clozegen.lib.io;

import de.tudarmstadt.ukp.dkpro.core.io.pdf.PdfReader;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import java.io.File;
import java.util.Arrays;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ResourceInitializationException;
import static org.uimafit.factory.CollectionReaderFactory.createCollectionReader;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class AutomaticReaderFactory {
    /** Utility class. */
    private AutomaticReaderFactory() {
    }

    public static CollectionReader choose(final File file, final String languageCode)
            throws ResourceInitializationException {
        CollectionReader reader;
        String extension = file.getAbsolutePath().substring(
                file.getAbsolutePath().lastIndexOf(".") + 1);
        String[] pattern = new String[] {String.format("[+]%s", file.getName())};
        if (extension.equals("pdf")) {
            reader = createCollectionReader(
                    PdfReader.class,
                    PdfReader.PARAM_LANGUAGE, languageCode,
                    PdfReader.PARAM_PATH, file.getParent(),
                    PdfReader.PARAM_PATTERNS, pattern);
        } else if (Arrays.asList("text", "txt").contains(extension)) {
            reader = createCollectionReader(
                    TextReader.class,
                    PdfReader.PARAM_LANGUAGE, languageCode,
                    TextReader.PARAM_PATH, file.getParent(),
                    TextReader.PARAM_PATTERNS, pattern);
        } else {
            throw new UnsupportedOperationException("File format not supported!");
        }

        return reader;
    }
}

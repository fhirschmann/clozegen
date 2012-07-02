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
import static com.google.common.base.Objects.firstNonNull;
import org.apache.uima.resource.ResourceInitializationException;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Registers {
    private static AnnotatorRegister annotators;
    private static ReaderRegister readers;
    private static WriterRegister writers;

    /**
     * Returns the annotator register. This register holds all information
     * used to construct an analysis engine in order to generate gaps.
     *
     * <p>
     * If no register has been set, a new default one will be created.
     * </p>
     *
     * @return the annotator Register
     * @throws ResourceInitializationException on errors during initialization
     */
    public static AnnotatorRegister annotators() throws ResourceInitializationException {
        annotators = firstNonNull(annotators,
                RegisterFactory.createDefaultAnnotatorRegister());
        return annotators;
    }

    /**
     * Returns the writer register. This register holds all information
     * used to construct a consumer which writes the cloze test to a file.
     *
     * <p>
     * If no register has been set, a new default one will be created.
     * </p>
     *
     * @return the writer register
     * @throws ResourceInitializationException on errors during initialization
     */
    public static WriterRegister writers() {
        writers = firstNonNull(writers,
                RegisterFactory.createDefaultWriterRegister());
        return writers;
    }

    /**
     * Returns the reader register. This register maps file extensions to UIMA
     * readers. You can safely add new mappings to this register.
     *
     * <p>
     * If no register has been set, a new default one will be created.
     * </p>
     *
     * @return the readerRegister
     */
    public static ReaderRegister readers() {
        readers = firstNonNull(readers,
                RegisterFactory.createDefaultReaderRegister());
        return readers;
    }
}

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
package com.github.fhirschmann.clozegen.gui;

import com.github.fhirschmann.clozegen.lib.register.ReaderRegisterEntry;
import com.github.fhirschmann.clozegen.lib.register.Registers;
import com.github.fhirschmann.clozegen.lib.register.WriterRegisterEntry;
import java.io.File;
import java.util.Map.Entry;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * {@link FileFilter} utility functions.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class FileFilterUtils {

    /**
     * Utility class.
     */
    private FileFilterUtils() {
    }

    /**
     * Sets the accepted files for a given {@code fileChooser} to the ones
     * supported by {@link Registers#reader()}.
     *
     * @param fileChooser the {@link JFileChooser}
     */
    public static void setInputFileFilter(final JFileChooser fileChooser) {
        fileChooser.setAcceptAllFileFilterUsed(false);
        for (final ReaderRegisterEntry entry : Registers.reader()) {
            fileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(final File file) {
                    return (file.isDirectory()
                            || file.getName().endsWith("." + entry.getIdentifier()));
                }

                @Override
                public String getDescription() {
                    return entry.getName() + " (*." + entry.getIdentifier() + ")";
                }
            });
        }
    }

    /**
     * Sets the accepted files for a given {@code fileChooser} to the ones
     * supported by {@link Registers#writer()}.
     *
     * @param fileChooser the {@link JFileChooser}
     */
    public static void setOutputFileFilter(final JFileChooser fileChooser) {
        fileChooser.setAcceptAllFileFilterUsed(false);
        for (final WriterRegisterEntry entry : Registers.writer()) {
            fileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(final File file) {
                    return (file.isDirectory()
                            || file.getName().endsWith("." + entry.getIdentifier()));
                }

                @Override
                public String getDescription() {
                    return entry.getName() + " (*." + entry.getIdentifier() + ")";
                }
            });
        }
    }
}

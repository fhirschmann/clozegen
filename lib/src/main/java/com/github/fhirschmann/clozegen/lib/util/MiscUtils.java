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
package com.github.fhirschmann.clozegen.lib.util;

import com.google.common.base.Joiner;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Miscellaneous utility functions.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class MiscUtils {
    /**
     * Utility class.
     */
    private MiscUtils() {
    }

    /**
     * Joins a string using a single whitespace.
     */
    public static final Joiner WS_JOINER = Joiner.on(" ");

    /**
     * Returns the file extension of {@code filename}.
     *
     * @param filename the filename in question
     * @return the filename's extension
     */
    public static String getFileExtension(final String filename) {
        checkArgument(filename.contains("."), "Filename does not contain a period.");
        return checkNotNull(filename).substring(filename.lastIndexOf(".") + 1);
    }
}

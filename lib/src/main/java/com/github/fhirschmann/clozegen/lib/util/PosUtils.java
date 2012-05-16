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

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Utility functions for dealing with {@link POS} (part of speech) annotations.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class PosUtils {
    private PosUtils() {}

    /**
     * Returns the text covered by a {@link POS} annotation in lowercase.
     * If the annotation is <code>null</code> or represents a punctuation mark,
     * <pre>"NULL"</pre> will be returned.
     *
     * @param pos the {@POS} (part of speech) token
     * @return covered text in lowercase or "NULL"
     */
    public static String loweredWordOrNULL(final POS pos) {
        if (pos == null) {
            return "NULL";
        } else if (pos.getPosValue().equals("pct")) {
            return "NULL";
        } else {
            return pos.getCoveredText().toLowerCase();
        }
    }

    /**
     * Maps {@link #loweredWordOrNULL(POS)} to all {@link POS} annotations.
     *
     * @param pos one or more {@link POS} annotations
     * @return array of results
     */
    public static String[] loweredWordsOrNULL(final POS... pos) {
        String[] result = new String[pos.length];

        for (int i = 0; i < pos.length; i++) {
            result[i] = loweredWordOrNULL(pos[i]);
        }

        return result;
    }
}

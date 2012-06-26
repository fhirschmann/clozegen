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

import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.uimafit.factory.JCasFactory;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Additional utility functions for {@link JCas}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class JCasFactory2 {
    /**
     * Utility class.
     */
    private JCasFactory2() {
    }

    /**
     * Creates a new JCas with the given {@code text} in the given {@code language}.
     *
     * @param text the text of the document
     * @param language the language of the document
     * @return a new JCas
     * @throws UIMAException on errors during JCas creation.
     */
    public static JCas createTestJCas(final String text, final String language)
            throws UIMAException {
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentText(checkNotNull(text));
        jcas.setDocumentLanguage(checkNotNull(language));
        return jcas;
    }
}

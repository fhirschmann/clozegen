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

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import java.util.Collection;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A register of gap annotators.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class AnnotatorRegister extends DescriptionRegister<AnnotatorRegisterEntry> {
    /**
     * Returns a live view of the set of Descriptions which support
     * the given {@code language}.
     *
     * @param language the language(code)
     * @return live view of filtered descriptions
     */
    public Collection<AnnotatorRegisterEntry> getAnnotatorsForLanguage(
            final String language) {
        return Collections2.filter(register.values(),
                new Predicate<AnnotatorRegisterEntry>() {
            @Override
            public boolean apply(final AnnotatorRegisterEntry input) {
                return input.getSupportedLanguages().contains(checkNotNull(language));
            }
        });
    }
}
/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.fhirschmann.clozegen.lib.register;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Set;

import com.github.fhirschmann.clozegen.lib.register.api.Register;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

/**
 * A register of gap annotators.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class AnnotatorRegister extends Register<AnnotatorRegisterEntry> {
    /**
     * Returns a live view of the set of Descriptions which support
     * the given {@code language}.
     *
     * @param language the language(code)
     * @return live view of filtered descriptions
     */
    public Collection<AnnotatorRegisterEntry> forLanguage(
            final String language) {
        return Collections2.filter(register.values(),
                new Predicate<AnnotatorRegisterEntry>() {
            @Override
            public boolean apply(final AnnotatorRegisterEntry input) {
                return input.getSupportedLanguages().contains(checkNotNull(language));
            }
        });
    }

    /**
     * Returns a set of all language codes present in any of the registered
     * annotators.
     *
     * @return a set of language codes
     */
    public Set<String> languageCodes() {
        Set<String> set = Sets.newHashSet();
        for (AnnotatorRegisterEntry entry : this) {
            set.addAll(entry.getSupportedLanguages());
        }
        return set;
    }
}

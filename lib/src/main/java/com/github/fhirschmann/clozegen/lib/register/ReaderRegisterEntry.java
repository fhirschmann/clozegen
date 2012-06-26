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

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase;
import java.io.File;
import org.apache.uima.collection.CollectionReader;
import static org.uimafit.factory.CollectionReaderFactory.createCollectionReader;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * An entry of {@link ReaderRegister}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ReaderRegisterEntry {
    /** The class of the collection reader of this entry. */
    private Class<? extends CollectionReader> readerClass;

    /**
     * Create a new {@link ReaderRegisterEntry} based on the given
     * {@code readerClass}.
     *
     * @param readerClass the reader's class for this entry.
     */
    public ReaderRegisterEntry(final Class<? extends CollectionReader> readerClass) {
        this.readerClass = checkNotNull(readerClass);
    }

    /**
     * Convenience method for creating {@link CollectionReader} patterns which
     * should work on only one input file.
     *
     * @param file the file in question
     * @return a new pattern
     */
    public static String[] createPatterns(final File file) {
        String[] patterns = new String[] {String.format("[+]%s",
                checkNotNull(file).getName())};
        return patterns;
    }

    /**
     * Returns a new {@link CollectionReader} based on the input {@code url} and
     * {@code languageCode}.
     *
     * @param file the input file
     * @param languageCode the language of the input file
     * @param additionalConfigurationData any additional configuration data
     * @throws ResourceInitializationException on errors during initialization
     * @return a new collection reader
     */
    public CollectionReader getReader(final File file, final String languageCode,
            final Object... additionalConfigurationData)
            throws ResourceInitializationException {
        List<Object> data = Lists.newArrayList();
        Collections.addAll(data,
            ResourceCollectionReaderBase.PARAM_LANGUAGE, checkNotNull(languageCode),
            ResourceCollectionReaderBase.PARAM_PATH, checkNotNull(file).getParent(),
            ResourceCollectionReaderBase.PARAM_PATTERNS, createPatterns(file));
        Collections.addAll(data, additionalConfigurationData);
        return createCollectionReader(getReaderClass(), data.toArray());
    }

    /**
     * Returns a new {@link CollectionReader} based on the input {@code url} and
     * {@code languageCode}.
     *
     * @param file the input file
     * @param languageCode the language of the input file
     * @throws ResourceInitializationException on errors during initialization
     * @return a new collection reader
     */
    public CollectionReader getReader(final File file, final String languageCode)
            throws ResourceInitializationException {
        return getReader(checkNotNull(file), checkNotNull(languageCode), new Object[] {});
    }

    /**
     * @return the reader class
     */
    public Class<? extends CollectionReader> getReaderClass() {
        return readerClass;
    }

    /**
     * @param readerClass the reader class to set
     */
    public void setReaderClass(final Class<? extends CollectionReader> readerClass) {
        this.readerClass = readerClass;
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("class", getReaderClass().getName());
        return str.toString();
    }
}

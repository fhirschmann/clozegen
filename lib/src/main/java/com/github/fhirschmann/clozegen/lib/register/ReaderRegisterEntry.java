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

import com.github.fhirschmann.clozegen.lib.register.api.RegisterEntry;
import com.github.fhirschmann.clozegen.lib.util.MiscUtils;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase;
import org.apache.uima.collection.CollectionReader;
import static org.uimafit.factory.CollectionReaderFactory.createCollectionReader;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Lists;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * An entry of {@link ReaderRegister}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ReaderRegisterEntry implements RegisterEntry {
    /**
     * The class of the collection reader of this entry.
     */
    private Class<? extends CollectionReader> readerClass;

    /**
     * The identifier of this entry.
    */
    private String identifier;

    /**
     * The name of this entry.
     */
    private String name;

    /**
     * Create a new {@link ReaderRegisterEntry} based on the given
     * {@code readerClass}.
     *
     * @param identifier the identifier of this entry
     * @param readerClass the reader's class for this entry.
     */
    public ReaderRegisterEntry(final String identifier,
            final Class<? extends CollectionReader> readerClass) {
        this.identifier = identifier;
        this.readerClass = checkNotNull(readerClass);
    }

    /**
     * Convenience method for creating {@link CollectionReader} patterns which
     * should work on only one input file.
     *
     * @param filename the filename in question
     * @return a new pattern
     */
    public static String[] createPatterns(final String filename) {
        String[] patterns = new String[] {String.format("[+]%s",
                checkNotNull(filename))};
        return patterns;
    }

    /**
     * Returns a new {@link CollectionReader} based on the input {@code url} and
     * {@code languageCode}.
     *
     * @param url the input file
     * @param languageCode the language of the input file
     * @param additionalConfigurationData any additional configuration data
     * @throws ResourceInitializationException on errors during initialization
     * @return a new collection reader
     */
    public CollectionReader getReader(final URL url, final String languageCode,
            final Object... additionalConfigurationData)
            throws ResourceInitializationException {
        return getReader(MiscUtils.pathFromURL(url),
                createPatterns(MiscUtils.filenameFromURL(url)),
                languageCode, additionalConfigurationData);
    }

    /**
     * Returns a new {@link CollectionReader} based on the input {@code path},
     * {@code patterns} and {@code languageCode}.
     *
     * @param path the path of the file
     * @param patterns the patterns
     * @param languageCode the language of the input file
     * @param additionalConfigurationData any additional configuration data
     * @throws ResourceInitializationException on errors during initialization
     * @return a new collection reader
     */
    public CollectionReader getReader(final String path, final String[] patterns,
            final String languageCode, final Object... additionalConfigurationData)
            throws ResourceInitializationException {
        List<Object> data = Lists.newArrayList();
        Collections.addAll(data,
            ResourceCollectionReaderBase.PARAM_LANGUAGE, checkNotNull(languageCode),
            ResourceCollectionReaderBase.PARAM_PATH, checkNotNull(path),
            ResourceCollectionReaderBase.PARAM_PATTERNS, checkNotNull(patterns));
        Collections.addAll(data, additionalConfigurationData);
        return createCollectionReader(getReaderClass(), data.toArray());
    }

    /**
     * Returns a new {@link CollectionReader} based on the input {@code url} and
     * {@code languageCode}.
     *
     * @param url the input file
     * @param languageCode the language of the input file
     * @throws ResourceInitializationException on errors during initialization
     * @return a new collection reader
     */
    public CollectionReader getReader(final URL url, final String languageCode)
            throws ResourceInitializationException {
        return getReader(checkNotNull(url), checkNotNull(languageCode), new Object[] {});
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

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("class", getReaderClass().getName());
        return str.toString();
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }
}

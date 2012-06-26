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
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;
import java.io.File;
import java.util.Map;
import org.apache.uima.collection.CollectionReader;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A register of mappings from file extensions to {@link ReaderRegisterEntry}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ReaderRegister extends ForwardingMap<String, ReaderRegisterEntry> {
    /**
     * The entry register.
     */
    private Map<String, ReaderRegisterEntry> register;

    /**
     * Creates a new empty register.
     */
    public ReaderRegister() {
        register = Maps.newHashMap();
    }

    /**
     * Returns the collection reader associated with the given input file.
     *
     * @param input the input file
     * @param languageCode the language of the input file
     * @return a new {@link CollectionReader}
     */
    public CollectionReader getReaderForFile(final String input,
            final String languageCode) {
        String inExt = input.substring(checkNotNull(input).lastIndexOf(".") + 1);
        checkArgument(register.containsKey(inExt), "Input file type is unknown!");
        File file = new File(input);
        return get(inExt).get(file, languageCode);
    }

    @Override
    protected Map<String, ReaderRegisterEntry> delegate() {
        return register;
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("entries", register.keySet().toString());
        return str.toString();
    }
}

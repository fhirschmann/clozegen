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

import java.io.File;
import javax.annotation.Nullable;
import org.apache.uima.collection.CollectionReader;

/**
 * An entry of {@link ReaderRegister}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public interface ReaderRegisterEntry {
    /**
     * Returns a new {@link CollectionReader} based on the input {@code url} and
     * {@code languageCode}.
     *
     * @param file the input file
     * @param languageCode the language of the input file
     * @return a new collection reader
     */
    CollectionReader get(File file, String languageCode);

    /**
    * Indicates whether another object is equal to this function.
    *
    * <p>Most implementations will have no reason to override the behavior of
    * {@link Object#equals}. However, an implementation may also choose to return
    * {@code true} whenever {@code object} is a {@link ReaderRegisterEntry} that it
    * considers <i>interchangeable</i> with this one. "Interchangeable" <i>typically</i>
    * means that {@code Objects.equal(this.get(f), that.get(f))} is true for all
    * {@code f} of type. Note that a {@code false} result from this method does not imply
    * that the functions are known <i>not</i> to be interchangeable.
    *
    * @param object the object to compare with
    * @return true if object equals
    */
    @Override
    boolean equals(@Nullable Object object);
}

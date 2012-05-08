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
package com.github.fhirschmann.clozegen.lib.frequency;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nu.xom.*;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public abstract class AbstractFrequencyList<T> {
    private final List<ValueCount<T>> frequencies = new ArrayList<ValueCount<T>>();

    abstract void processChild(Element child);

    public void load(final String xmlFile)
            throws ParsingException, ValidityException, IOException {
        final InputStream stream = this.getClass().getClassLoader().
                getResourceAsStream(xmlFile);

        final Builder builder = new Builder();
        final Document doc = builder.build(stream);
        final Element root = doc.getRootElement();
        for (int i = 0; i < root.getChildCount(); i++) {
            final Element child = root.getChildElements().get(i);
            processChild(child);
        }
        Collections.sort(getFrequencies());
    }

    /**
     * @return the frequencies
     */
    public List<ValueCount<T>> getFrequencies() {
        return frequencies;
    }
}

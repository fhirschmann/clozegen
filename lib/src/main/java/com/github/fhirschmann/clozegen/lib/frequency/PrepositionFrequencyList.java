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
import nu.xom.*;

/**
 * This class represents a frequency list for prepositions.
 *
 * <p>
 * By default, it will use the Word Frequencies in Written and Spoken English (see below).
 * The script used to produce the XML version of the list can be found in the
 * resources directory.
 * </p>
 * <p>
 * [1] http://ucrel.lancs.ac.uk/bncfreq/
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 * @see <a href="http://ucrel.lancs.ac.uk/bncfreq/">Word Frequencies in Written and Spoken English</a>
 */
public class PrepositionFrequencyList extends AbstractFrequencyList<String> {
    /**
     * Loads a Preposition Frequency list from an XML file.
     *
     * @param xmlFile the XML file to load
     * @throws ParsingException on errors parsing the XML file
     * @throws IOException on errors accessing the XML file
     */
    public PrepositionFrequencyList(final String xmlFile)
            throws ParsingException, IOException {
        super();
        load(xmlFile);
    }

    /**
     * Loads the default Preposition Frequency list.
     *
     * @throws ParsingException on errors parsing the XML file
     * @throws IOException on errors accessing the XML file
     */
    public PrepositionFrequencyList() throws ParsingException, IOException {
        super();
        load("frequency/prepositions.xml");
    }

    public static void main(String[] args) throws Exception {
        PrepositionFrequencyList f = new PrepositionFrequencyList();
        System.out.println(f.getFrequencies());
    }

    @Override
    void processChild(final Element child) {
            final String word = child.getAttribute("sofa").getValue();
            final int count = Integer.parseInt(
                    child.getAttribute("value").getValue());

            getFrequencies().add(new ValueCount(word, count));
    }
}

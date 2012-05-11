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
package com.github.fhirschmann.clozegen.lib.io;

import java.io.File;
import java.io.IOException;
import org.apache.uima.UIMAException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Progress;
import org.uimafit.factory.JCasFactory;
import org.uimafit.component.JCasCollectionReader_ImplBase;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class InputReader extends JCasCollectionReader_ImplBase {

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Progress[] getProgress() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

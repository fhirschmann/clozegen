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
package de._0x0b.clozegenlib.io;

import java.util.Iterator;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.util.Level;
import org.uimafit.component.JCasConsumer_ImplBase;

/**
 * Logs a document by displaying its token's types, names
 * and values.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class DebugWriter extends JCasConsumer_ImplBase {

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s%n", jcas.getDocumentText()));

        for (Iterator<Annotation> i = jcas.getAnnotationIndex().iterator(); i.hasNext();) {
            Annotation a = i.next();
            sb.append(String.format("[%s] (%s,%s) %s%n", a.getType().getShortName(),
                    a.getBegin(), a.getEnd(), a.getCoveredText()));
        }
        getContext().getLogger().log(Level.INFO, sb.toString());
    }
}

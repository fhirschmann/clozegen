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
package com.github.fhirschmann.clozegen.lib.annotators;

import com.github.fhirschmann.clozegen.lib.util.PosUtils;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import java.util.Arrays;
import java.util.List;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.util.JCasUtil;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public abstract class AbstractPosTrigramAnnotator extends JCasAnnotator_ImplBase {
    public abstract void processTrigram(POS[] parts);

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        POS[] parts = new POS[3];
        String[] strings = new String[3];

        for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
            final List<POS> pos = JCasUtil.selectCovered(aJCas, POS.class, sentence);
            final PeekingIterator<POS> it = Iterators.peekingIterator(pos.iterator());

            Arrays.fill(parts, null);

            while (it.hasNext()) {
                parts[0] = parts[1];
                parts[1] = it.next();
                if (it.hasNext()) {
                    parts[2] = it.peek();
                }

                strings = PosUtils.loweredWordsOrNULL(parts);
                processTrigram(parts);
            }
        }
    }
}

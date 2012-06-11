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

import com.github.fhirschmann.clozegen.lib.util.AdjacencyIterator;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.PP;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import java.util.List;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.util.JCasUtil;

/**
 * An abstract annotator which calls {@link AbstractPosTrigramAnnotator#processTrigram}
 * for each {@link POS} annotation.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public abstract class AbstractPosTrigramAnnotator extends JCasAnnotator_ImplBase {
    /**
     * This method is called for each {@link POS} annotation in the
     * {@link JCas}. The previous, current and next word can be found
     * in the <code>pos</code> list. If the current word has no previous
     * or next word, the array will contain <code>null</code>.
     *
     * @param aJCas the JCas the POS belongs to
     * @param pos the previous, current and next word (or token)
     */
    public abstract void processTrigram(JCas aJCas, List<POS> pos);

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
            final AdjacencyIterator it = AdjacencyIterator.create(JCasUtil.selectCovered(
                    aJCas, POS.class, sentence).iterator(), 1);
            while (it.hasNext()) {
                it.next();
                List<POS> adjacent = it.getAdjacent();

                if (adjacent.get(1) instanceof PP) {
                    processTrigram(aJCas, adjacent);
                }
            }
        }
    }
}

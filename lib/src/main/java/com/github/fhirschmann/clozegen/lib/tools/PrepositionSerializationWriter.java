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
package com.github.fhirschmann.clozegen.lib.tools;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.github.fhirschmann.clozegen.lib.frequency.FrequencyStructure;
import com.google.common.collect.*;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.PP;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.JCasConsumer_ImplBase;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class PrepositionSerializationWriter extends JCasConsumer_ImplBase {
    private Multiset<String> ms;

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
        ms = HashMultiset.create();
    }

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        for (final Iterator<Annotation> i = aJCas.getAnnotationIndex(
                PP.type).iterator(); i.hasNext();) {
            final Annotation subject = i.next();
            final PP pp = (PP) subject;
            ms.add(pp.getCoveredText().toString().toLowerCase());
        }
    }

    @Override
    public void collectionProcessComplete() {
        Kryo kryo = new Kryo();
        try {
            Output output = new Output(new FileOutputStream("src/main/resources/frequency/prepositions.bin"));
            ImmutableMultiset sm = Multisets.copyHighestCountFirst(ms);
            kryo.writeObject(output, LinkedHashMultiset.create(sm));
            output.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrepositionSerializationWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

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
package com.github.fhirschmann.clozegen.lib.adapter;

import com.github.fhirschmann.clozegen.lib.adapter.api.GeneratorAdapter;
import com.github.fhirschmann.clozegen.lib.component.api.AbstractResource;
import com.github.fhirschmann.clozegen.lib.generator.CollocationGapGenerator;
import com.github.fhirschmann.clozegen.lib.generator.api.GapGenerator;
import com.github.fhirschmann.clozegen.lib.generator.CollocationModel;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.io.Resources;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import java.io.IOException;
import java.util.List;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.util.Level;
import org.uimafit.descriptor.ConfigurationParameter;

/**
 * This adapter is backed by {@link CollocationGapGenerator} and injects
 * a {@link CollocationModel} from {@link CollocationAdapter#PARAM_PATH}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationAdapter extends AbstractResource implements GeneratorAdapter {

    /** The path to the collocation file. */
    public static final String PARAM_PATH = "Path";
    @ConfigurationParameter(name = PARAM_PATH, mandatory = true)
    private String path;

    /** The n in n-gram. */
    public static final String PARAM_N = "N";
    @ConfigurationParameter(name = PARAM_N, mandatory = false, defaultValue = "3")
    private int n;

    /** The collocation model. */
    private CollocationModel model;

    @Override
    public boolean initialize() {
        model = new CollocationModel();
        try {
            model.load(Resources.getResource(path));
            return true;
        } catch (IOException ex) {
            this.getUimaContext().getLogger().log(Level.SEVERE, ex.getMessage());
            return false;
        }
    }

    @Override
    public GapGenerator generator(
            final List<Annotation> annotationList, final int offset) {
        List<String> tokens = UIMAUtils.getAdjacentTokens(POS.class,
                annotationList, offset, 1);

        GapGenerator generator = new CollocationGapGenerator(tokens, model);
        return generator;
    }
}

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
package com.github.fhirschmann.clozegen.lib.formatters;

import com.github.fhirschmann.clozegen.lib.components.api.JCasFormatter;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.CollectionUtils;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.Resource_ImplBase;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Strings;
import org.uimafit.util.FSCollectionFactory;
import org.uimafit.util.JCasUtil;

/**
 * Formats a cloze test as plain text.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class PlainTextFormatter extends Resource_ImplBase implements JCasFormatter {
    @Override
    public String format(final JCas jcas) {
        StringBuilder sb = new StringBuilder();
        String text = checkNotNull(jcas).getDocumentText();
        int position = 0;
        for (GapAnnotation gap : JCasUtil.select(jcas, GapAnnotation.class)) {
            sb.append(text.substring(position, gap.getBegin()));
            sb.append(Strings.repeat("_", CollectionUtils.getMaximumElementLength(
                    FSCollectionFactory.create(gap.getAllAnswers()))));
            sb.append(FSCollectionFactory.create(gap.getAllAnswers()).toString());
            position = gap.getEnd();
        }
        sb.append(text.substring(position));

        return sb.toString();
    }
}

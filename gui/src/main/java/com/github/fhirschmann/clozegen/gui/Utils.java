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
package com.github.fhirschmann.clozegen.gui;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * GUI Utilities.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class Utils {
    /**
     * Utility class.
     */
    private Utils() {
    }

    /**
     * Shows an error dialog.
     *
     * @param component the main component (frame)
     * @param msg  the message to show
     */
    public static void error(final Component component, final Object msg) {
        Logger.getLogger(component.getClass().getName()).log(Level.SEVERE, null, msg);
        JOptionPane.showMessageDialog(component, msg.toString(), "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}

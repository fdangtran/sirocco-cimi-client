/**
 *
 * SIROCCO
 * Copyright (C) 2011 France Telecom
 * Contact: sirocco@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 *  $Id$
 *
 */
package org.ow2.sirocco.apis.rest.cimi.sdk;

import org.ow2.sirocco.apis.rest.cimi.domain.CimiSystemMachine;

public class SystemMachine extends Resource<CimiSystemMachine> {
    private Machine machine;

    public SystemMachine() {
        super(null, new CimiSystemMachine());
    }

    SystemMachine(final CimiClient cimiClient, final String id) {
        super(cimiClient, new CimiSystemMachine());
        this.cimiObject.setHref(id);
    }

    SystemMachine(final CimiClient cimiClient, final CimiSystemMachine cimiObject) throws CimiException {
        super(cimiClient, cimiObject);
        this.machine = Machine.getMachineByReference(cimiClient, cimiObject.getMachine().getHref(), null);
    }

    public Machine getMachine() {
        return this.machine;
    }
}

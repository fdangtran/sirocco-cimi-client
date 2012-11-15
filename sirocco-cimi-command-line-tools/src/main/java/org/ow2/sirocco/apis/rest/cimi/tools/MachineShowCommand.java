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
package org.ow2.sirocco.apis.rest.cimi.tools;

import java.util.List;

import org.nocrala.tools.texttablefmt.Table;
import org.ow2.sirocco.apis.rest.cimi.sdk.CimiClient;
import org.ow2.sirocco.apis.rest.cimi.sdk.CimiException;
import org.ow2.sirocco.apis.rest.cimi.sdk.Disk;
import org.ow2.sirocco.apis.rest.cimi.sdk.Machine;
import org.ow2.sirocco.apis.rest.cimi.sdk.MachineNetworkInterface;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

@Parameters(commandDescription = "show machine")
public class MachineShowCommand implements Command {
    @Parameter(names = "-id", description = "id of the machine", required = true)
    private String machineId;

    @ParametersDelegate
    private ResourceSelectExpandParams showParams = new ResourceSelectExpandParams();

    @Override
    public String getName() {
        return "machine-show";
    }

    @Override
    public void execute(final CimiClient cimiClient) throws CimiException {
        Machine machine = Machine.getMachineByReference(cimiClient, this.machineId, this.showParams.getQueryParams());
        MachineShowCommand.printMachine(machine, this.showParams);
    }

    public static void printMachine(final Machine machine, final ResourceSelectExpandParams showParams) throws CimiException {
        Table table = CommandHelper.createResourceShowTable(machine, showParams);

        if (showParams.isSelected("state")) {
            table.addCell("state");
            table.addCell(machine.getState().toString());
        }
        if (showParams.isSelected("cpu")) {
            table.addCell("cpu");
            table.addCell(Integer.toString(machine.getCpu()));
        }
        if (showParams.isSelected("memory")) {
            table.addCell("memory");
            table.addCell(Integer.toString(machine.getMemory()));
        }

        if (showParams.isSelected("disks")) {
            table.addCell("disks");
            StringBuffer sb = new StringBuffer();
            List<Disk> disks = machine.getDisks();
            for (int i = 0; i < disks.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(disks.get(i).getCapacity());
            }
            table.addCell((sb.toString()));
        }

        if (showParams.isSelected("networkInterfaces")) {
            table.addCell("IP addresses");
            StringBuffer sb = new StringBuffer();
            for (MachineNetworkInterface nic : machine.getNetworkInterfaces()) {
                if (!nic.getAddresses().isEmpty()) {
                    sb.append(nic.getType() + "=" + nic.getAddresses().get(0).getIp() + " ");
                }
            }
            table.addCell(sb.toString());
        }

        System.out.println(table.render());
    }

}

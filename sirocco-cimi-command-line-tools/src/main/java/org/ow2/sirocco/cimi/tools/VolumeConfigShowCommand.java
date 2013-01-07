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
package org.ow2.sirocco.cimi.tools;

import java.util.List;

import org.nocrala.tools.texttablefmt.Table;
import org.ow2.sirocco.cimi.sdk.CimiClient;
import org.ow2.sirocco.cimi.sdk.CimiClientException;
import org.ow2.sirocco.cimi.sdk.VolumeConfiguration;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

@Parameters(commandDescription = "show volume config")
public class VolumeConfigShowCommand implements Command {
    @Parameter(description = "<volume config id>", required = true)
    private List<String> volumeConfigIds;

    @ParametersDelegate
    private ResourceSelectExpandParams showParams = new ResourceSelectExpandParams();

    @Override
    public String getName() {
        return "volumeconfig-show";
    }

    @Override
    public void execute(final CimiClient cimiClient) throws CimiClientException {
        VolumeConfiguration volumeConfig = null;
        if (CommandHelper.isResourceIdentifier(this.volumeConfigIds.get(0))) {
            volumeConfig = VolumeConfiguration.getVolumeConfigurationByReference(cimiClient, this.volumeConfigIds.get(0),
                this.showParams.getQueryParams());
        } else {
            List<VolumeConfiguration> volumeConfigs = VolumeConfiguration.getVolumeConfigurations(cimiClient, this.showParams
                .getQueryParams().toBuilder().filter("name='" + this.volumeConfigIds.get(0) + "'").build());
            if (volumeConfigs.isEmpty()) {
                java.lang.System.err.println("No volume config with name " + this.volumeConfigIds.get(0));
                java.lang.System.exit(-1);
            }
            volumeConfig = volumeConfigs.get(0);
        }
        VolumeConfigShowCommand.printVolumeConfig(volumeConfig, this.showParams);
    }

    public static void printVolumeConfig(final VolumeConfiguration volumeConfig, final ResourceSelectExpandParams showParams)
        throws CimiClientException {
        Table table = CommandHelper.createResourceShowTable(volumeConfig, showParams);

        if (showParams.isSelected("type")) {
            table.addCell("type");
            table.addCell(volumeConfig.getType());
        }
        if (showParams.isSelected("format")) {
            table.addCell("format");
            table.addCell(volumeConfig.getFormat());
        }
        if (showParams.isSelected("capacity")) {
            table.addCell("capacity");
            table.addCell(CommandHelper.printKilobytesValue(volumeConfig.getCapacity()));
        }
        System.out.println(table.render());
    }

}

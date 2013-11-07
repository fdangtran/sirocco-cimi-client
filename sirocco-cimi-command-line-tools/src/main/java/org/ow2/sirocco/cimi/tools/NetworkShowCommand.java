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
import org.ow2.sirocco.cimi.sdk.Network;
import org.ow2.sirocco.cimi.sdk.ProviderInfo;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

@Parameters(commandDescription = "show network")
public class NetworkShowCommand implements Command {
    @Parameter(description = "<network id>", required = true)
    private List<String> networkIds;

    @ParametersDelegate
    private ResourceSelectExpandParams showParams = new ResourceSelectExpandParams();

    @Override
    public String getName() {
        return "network-show";
    }

    @Override
    public void execute(final CimiClient cimiClient) throws CimiClientException {
        Network net = Network.getNetworkByReference(cimiClient, this.networkIds.get(0), this.showParams.getQueryParams());
        NetworkShowCommand.printNetwork(net, this.showParams);
    }

    public static void printNetwork(final Network net, final ResourceSelectExpandParams showParams) throws CimiClientException {
        Table table = CommandHelper.createResourceShowTable(net, showParams);

        if (showParams.isSelected("provider")) {
            if (net.getProviderInfo() != null) {
                ProviderInfo info = net.getProviderInfo();
                table.addCell("provider");
                table.addCell("account id=" + info.getProviderAccountId() + " (" + info.getProviderName() + ")");
                table.addCell("provider-assigned id");
                table.addCell(info.getProviderAssignedId());
            }
        }

        if (showParams.isSelected("state") && net.getState() != null) {
            table.addCell("state");
            table.addCell(net.getState().toString());
        }
        if (showParams.isSelected("networkType") && net.getNetworkType() != null) {
            table.addCell("network type");
            table.addCell(net.getNetworkType());
        }
        System.out.println(table.render());
    }

}

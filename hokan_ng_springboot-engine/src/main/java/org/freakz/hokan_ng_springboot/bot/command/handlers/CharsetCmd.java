package org.freakz.hokan_ng_springboot.bot.command.handlers;

import org.freakz.hokan_ng_springboot.bot.command.HelpGroup;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.martiansoftware.jsap.JSAPResult;

/**
 * Created by Petri Airio on 29.3.2016. -
 */
@Component
@Scope("prototype")
@HelpGroups(helpGroups = { HelpGroup.SYSTEM })
public class CharsetCmd extends Cmd {

	@Override
	public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results)
			throws HokanException {

		ServiceResponse serviceResponse =
				doServicesRequest(ServiceRequestType.CHARSET_REQUEST, request.getIrcEvent(), "");
		String[] output = serviceResponse.getCharsetResponse();
		if (output != null && output.length > 0) {
			String out = output[0];
			int idx1 = out.lastIndexOf(":") + 1;
			int idx2 = out.lastIndexOf(",");
			response.addResponse("%s", out.substring(idx1, idx2).trim());
		} else {
			response.addResponse("%s: no idea!!", getName());
		}

	}

}

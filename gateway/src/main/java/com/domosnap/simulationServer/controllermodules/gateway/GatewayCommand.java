package com.domosnap.simulationServer.controllermodules.gateway;

/*
 * #%L
 * DomoSnap Legrand Simulation Gateway GatewayModule
 * %%
 * Copyright (C) 2011 - 2017 A. de Giuli
 * %%
 * This file is part of MyDomo done by A. de Giuli (arnaud.degiuli(at)free.fr).
 * 
 *     MyDomo is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     MyDomo is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with MyDomo.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */


import java.text.MessageFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.domosnap.engine.connector.impl.openwebnet.connector.OpenWebNetConstant;
import com.domosnap.engine.connector.impl.openwebnet.conversion.core.dimension.DimensionValue;
import com.domosnap.engine.connector.impl.openwebnet.conversion.gateway.GatewayStatusConverter;
import com.domosnap.engine.connector.impl.openwebnet.conversion.gateway.dimension.Date;
import com.domosnap.engine.controller.what.impl.DateState;
import com.domosnap.simulationServer.ControllerStateManagement;

public class GatewayCommand {
	
	// OSGi Shell Function	
	static final String[] functions = {
		"date", 			"datetime",
		"time"};

	/**
	 * Change the date of the gateway
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public String date(int day, int month, int year) {
		Calendar c = new GregorianCalendar(year, month, day);
		Date d = new Date();
		d.setStateValue(new DateState(c.getTime()));
		return ControllerStateManagement.executeCommand(
				MessageFormat.format(
						OpenWebNetConstant.DIMENSION_COMMAND,
						new Object[] {
								GatewayStatusConverter.OPEN_WEB_WHO, 
								"", 
								GatewayStatusConverter.GatewayDimension.DATE.getCode(), 
								formatDimension(d.getValueList())
						}
				)
		);
	}
	
	/**
	 * Change the date and time of the gateway
	 * @param address
	 * @param sec
	 * @param min
	 * @param hour
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public String datetime(int sec, int min, int hour, int day, int month, int year) {

		Calendar c = new GregorianCalendar(year, month, day, hour, min, sec);
		Date d = new Date();
		d.setStateValue(new DateState(c.getTime()));

		return ControllerStateManagement.executeCommand(
				MessageFormat.format(
						OpenWebNetConstant.DIMENSION_COMMAND,
						new Object[] {
								GatewayStatusConverter.OPEN_WEB_WHO,
								"",
								GatewayStatusConverter.GatewayDimension.DATETIME.getCode(),
								formatDimension(d.getValueList())
						}
				)
		);
	}

	/**
	 * Change the time of the gateway!
	 * @param address
	 * @param sec
	 * @param min
	 * @param hour
	 * @return
	 */
	public String time(String address, int sec, int min, int hour) {

		Calendar c = new GregorianCalendar();
		c.set(Calendar.SECOND, sec);
		c.set(Calendar.HOUR_OF_DAY, sec);
		c.set(Calendar.DAY_OF_MONTH, sec);
		Date d = new Date();
		d.setStateValue(new DateState(c.getTime()));

		return ControllerStateManagement.executeCommand(
				MessageFormat.format(
						OpenWebNetConstant.DIMENSION_COMMAND,
						new Object[] {
								GatewayStatusConverter.OPEN_WEB_WHO,
								"",
								GatewayStatusConverter.GatewayDimension.TIME.getCode(),
								formatDimension(d.getValueList())
						}
				)
		);
	}
	
	private String formatDimension(List<DimensionValue> valueList) {
		StringBuilder sb = new StringBuilder();
		for (DimensionValue value : valueList) {
			sb.append(value.getValue());
			sb.append(OpenWebNetConstant.DIMENSION_SEPARATOR);
		}
		
		if (sb.length() > 0) {
			sb.setLength(sb.length()-1);
		}
		
		return sb.toString();
	}
}

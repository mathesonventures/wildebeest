// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

package co.zd.wb;

/**
 * Provides information about this release of Wildebeest.
 * 
 * @author                                      Brendon Matheson
 * @since                                       3.0
 */
public class About
{
	/**
	 * Gets the project name.
	 */
	public String getProjectName()
	{
		return "@meta.project.identity.name@";
	}
	
	/**
	 * Gets the project title.
	 */
	public String getProjectTitle()
	{
		return "@meta.project.identity.title@";
	}
	
	/**
	 * Gets the full project version in dotted format.
	 */
	public String getVersionFullDotted()
	{
		return "@meta.project.version.full.dotted@";
	}
	
	/**
	 * Gets the copyright assertion for this project.
	 */
	public String getCopyrightAssertion()
	{
		return "Copyright (c) @meta.project.copyright.years@, @meta.project.copyright.owner@";
	}
}

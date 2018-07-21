// Wildebeest Migration Framework
// Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
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

package co.mv.wb;

/**
 * Provides information about this release of Wildebeest.
 *
 * @since 3.0
 */
public class About
{
	private final String projectName;
	private final String projectTitle;
	private final String versionFullDotted;
	private final String copyrightAssertion;

	public About()
	{
		this.projectName = "@meta.project.identity.name@";
		this.projectTitle = "@meta.project.identity.title@";
		this.versionFullDotted = "@meta.project.version.full.dotted@";
		this.copyrightAssertion = "Copyright (c) @meta.project.copyright.years@, @meta.project.copyright.owner@";
	}

	/**
	 * Gets the project name.
	 *
	 * @return the name of this project - i.e. Wildebeest
	 * @since 3.0
	 */
	public String getProjectName()
	{
		return this.projectName;
	}

	/**
	 * Gets the project title.
	 *
	 * @return the title of this project
	 * @since 3.0
	 */
	public String getProjectTitle()
	{
		return this.projectTitle;
	}

	/**
	 * Gets the full project version in dotted format.
	 *
	 * @return the full project version in dotted format
	 * @since 3.0
	 */
	public String getVersionFullDotted()
	{
		return this.versionFullDotted;
	}

	/**
	 * Gets the copyright assertion for this project.
	 *
	 * @return the copyright assertion for this project
	 * @since 3.0
	 */
	public String getCopyrightAssertion()
	{
		return this.copyrightAssertion;
	}
}

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

import java.util.List;

/**
 * An internal service for managing and accessing plugins.
 *
 * @since 4.0
 */
public interface PluginManager
{
	/**
	 * Returns a list of the plugin groups known to Wildebeest.
	 *
	 * @return a list of the plugin groups known to Wildebeest.
	 * @since 4.0
	 */
	List<PluginGroup> getPluginGroups();

	/**
	 * Returns a list of MigrationTypeInfo's representing all available migrations.
	 *
	 * @return a list of MigrationTypeInfo's representing all available migrations.
	 * @since 4.0
	 */
	List<MigrationTypeInfo> getMigrationTypeInfos();

	/**
	 * Returns a list of AssertionType's representing all assertions known to Wildebeest.
	 *
	 * @return a list of AssertionType's representing all available assertions.
	 * @since 4.0
	 */
	List<AssertionType> getAssertionTypes();
}

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

package co.mv.wb.plugin.fake.dom;

import co.mv.wb.Migration;
import co.mv.wb.plugin.base.dom.BaseDomMigrationBuilder;
import co.mv.wb.plugin.fake.SetTagMigration;

import java.io.File;
import java.util.UUID;

/**
 * DOM-based {@link co.mv.wb.MigrationBuilder} for the Fake plugin implementation.
 *
 * @since 1.0
 */
public class DomSetTagMigrationBuilder extends BaseDomMigrationBuilder
{
	@Override
	public Migration build(
		UUID migrationId,
		String fromState,
		String toState,
		File baseDir)
	{
		String tag = this.getElement().getChildNodes().item(0).getTextContent();

		return new SetTagMigration(migrationId, fromState, toState, tag);
	}
}

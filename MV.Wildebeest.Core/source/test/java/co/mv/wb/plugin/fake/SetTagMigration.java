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

package co.mv.wb.plugin.fake;

import co.mv.wb.Migration;
import co.mv.wb.MigrationType;
import co.mv.wb.ResourceType;
import co.mv.wb.plugin.base.BaseMigration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * {@link Migration} for the Fake plugin implementation.
 *
 * @since                                       1.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:Fake",
	uri = "co.mv.wb.fake:SetTag",
	description = "Sets the tag on a FakeInstance to the supplied value",
	example =
		""
)
public class SetTagMigration extends BaseMigration
{
	private String tag = null;
	private boolean tagSet = false;

	public SetTagMigration(
		UUID migrationId,
		Optional<UUID> fromStateId,
		Optional<UUID> toStateId,
		String tag)
	{
		super(migrationId, fromStateId, toStateId);
		
		this.setTag(tag);
	}

	public String getTag() {
		if(!tagSet) {
			throw new IllegalStateException("tag not set.  Use the HasTag() method to check its state before accessing it.");
		}
		return tag;
	}

	private void setTag(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("tag cannot be null");
		}
		boolean changing = !tagSet || !tag.equals(value);
		if(changing) {
			tagSet = true;
			tag = value;
		}
	}

	private void clearTag() {
		if(tagSet) {
			tagSet = true;
			tag = null;
		}
	}

	private boolean hasTag() {
		return tagSet;
	}

	// </editor-fold>
	
	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			FakeConstants.Fake);
	}
}

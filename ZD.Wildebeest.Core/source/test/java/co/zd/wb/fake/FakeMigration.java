// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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

package co.zd.wb.fake;

import co.zd.wb.ModelExtensions;
import co.zd.wb.Instance;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.Resource;
import co.zd.wb.plugin.base.BaseMigration;
import java.util.UUID;

public class FakeMigration extends BaseMigration
{
	public FakeMigration(
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId,
		String tag)
	{
		super(migrationId, fromStateId, toStateId);
		
		this.setTag(tag);
	}
	
	// <editor-fold desc="Tag" defaultstate="collapsed">

	private String m_tag = null;
	private boolean m_tag_set = false;

	public String getTag() {
		if(!m_tag_set) {
			throw new IllegalStateException("tag not set.  Use the HasTag() method to check its state before accessing it.");
		}
		return m_tag;
	}

	private void setTag(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("tag cannot be null");
		}
		boolean changing = !m_tag_set || m_tag != value;
		if(changing) {
			m_tag_set = true;
			m_tag = value;
		}
	}

	private void clearTag() {
		if(m_tag_set) {
			m_tag_set = true;
			m_tag = null;
		}
	}

	private boolean hasTag() {
		return m_tag_set;
	}

	// </editor-fold>
	
	@Override public boolean canPerformOn(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		
		return ModelExtensions.As(resource, FakeInstance.class) != null;
	}
	
	@Override public void perform(Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		FakeInstance fake = ModelExtensions.As(instance, FakeInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must of type FakeResource"); }
	
		fake.setTag(this.getTag());
		
		fake.setStateId(this.getToStateId());
	}
}

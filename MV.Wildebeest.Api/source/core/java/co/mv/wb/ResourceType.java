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
 * Identifies a type of {@link Resource}.  The resource's type is identified in the XML file, and is used to verify
 * which {@link Assertion}'s and {@link Migration}'s can be applied to the Resource.
 *
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class ResourceType
{
	public ResourceType(
		String uri,
		String name)
	{
		this.setUri(uri);
		this.setName(name);
	}

	// <editor-fold desc="Uri" defaultstate="collapsed">

	private String _uri = null;
	private boolean _uri_set = false;

	public String getUri() {
		if(!_uri_set) {
			throw new IllegalStateException("uri not set.");
		}
		if(_uri == null) {
			throw new IllegalStateException("uri should not be null");
		}
		return _uri;
	}

	private void setUri(
			String value) {
		if(value == null) {
			throw new IllegalArgumentException("uri cannot be null");
		}
		boolean changing = !_uri_set || _uri != value;
		if(changing) {
			_uri_set = true;
			_uri = value;
		}
	}

	private void clearUri() {
		if(_uri_set) {
			_uri_set = true;
			_uri = null;
		}
	}

	private boolean hasUri() {
		return _uri_set;
	}

	// </editor-fold>

	// <editor-fold desc="Name" defaultstate="collapsed">

	private String _name = null;
	private boolean _name_set = false;

	public String getName() {
		if(!_name_set) {
			throw new IllegalStateException("name not set.");
		}
		if(_name == null) {
			throw new IllegalStateException("name should not be null");
		}
		return _name;
	}

	private void setName(
			String value) {
		if(value == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		boolean changing = !_name_set || _name != value;
		if(changing) {
			_name_set = true;
			_name = value;
		}
	}

	private void clearName() {
		if(_name_set) {
			_name_set = true;
			_name = null;
		}
	}

	private boolean hasName() {
		return _name_set;
	}

	// </editor-fold>
}

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

package co.zd.wb.service;

import java.util.ArrayList;
import java.util.List;

public class Messages
{
	public Messages()
	{
		this.setMessages(new ArrayList<String>());
	}
	
	// <editor-fold desc="Messages" defaultstate="collapsed">

	private List<String> _messages = null;
	private boolean _messages_set = false;

	public List<String> getMessages() {
		if(!_messages_set) {
			throw new IllegalStateException("messages not set.  Use the HasMessages() method to check its state before accessing it.");
		}
		return _messages;
	}

	private void setMessages(List<String> value) {
		if(value == null) {
			throw new IllegalArgumentException("messages cannot be null");
		}
		boolean changing = !_messages_set || _messages != value;
		if(changing) {
			_messages_set = true;
			_messages = value;
		}
	}

	private void clearMessages() {
		if(_messages_set) {
			_messages_set = true;
			_messages = null;
		}
	}

	private boolean hasMessages() {
		return _messages_set;
	}

	// </editor-fold>
	
	public void addMessage(String message)
	{
		if (message == null) { throw new IllegalArgumentException("message cannot be null"); }
		if ("".equals(message.trim())) { throw new IllegalArgumentException("message cannot be empty"); }
		
		this.getMessages().add(message);
	}
	
	public void addMessage(String format, Object... args)
	{
		if (format == null) { throw new IllegalArgumentException("format cannot be null"); }
		if ("".equals(format.trim())) { throw new IllegalArgumentException("format cannot be empty"); }
		
		String message = String.format(format, args);
		this.addMessage(message);
	}
	
	public int size()
	{
		return this.getMessages().size();
	}
}

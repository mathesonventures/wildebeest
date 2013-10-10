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

public class MessagesException extends Exception
{
	public MessagesException(Messages messages)
	{
		this.setMessages(messages);
	}
	
	// <editor-fold desc="Messages" defaultstate="collapsed">

	private Messages _messages = null;
	private boolean _messages_set = false;

	public Messages getMessages() {
		if(!_messages_set) {
			throw new IllegalStateException("messages not set.  Use the HasMessages() method to check its state before accessing it.");
		}
		return _messages;
	}

	private void setMessages(
		Messages value) {
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
}

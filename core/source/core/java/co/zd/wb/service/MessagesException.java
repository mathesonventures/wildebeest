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

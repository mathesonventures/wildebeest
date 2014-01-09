package co.zd.wb.plugin.base;

import co.zd.wb.Instance;
import java.util.UUID;

/**
 * Provides a base implementation of {@link Instance}
 * 
 * @author          brendonm
 */
public abstract class BaseInstance implements Instance
{
	protected BaseInstance(
		UUID instanceId)
	{
		this.setInstanceId(instanceId);
	}
	
	// <editor-fold desc="InstanceId" defaultstate="collapsed">

	private UUID _instanceId = null;
	private boolean _instanceId_set = false;

	public UUID getInstanceId() {
		if(!_instanceId_set) {
			throw new IllegalStateException("instanceId not set.  Use the HasInstanceId() method to check its state before accessing it.");
		}
		return _instanceId;
	}

	private void setInstanceId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("instanceId cannot be null");
		}
		boolean changing = !_instanceId_set || _instanceId != value;
		if(changing) {
			_instanceId_set = true;
			_instanceId = value;
		}
	}

	private void clearInstanceId() {
		if(_instanceId_set) {
			_instanceId_set = true;
			_instanceId = null;
		}
	}

	private boolean hasInstanceId() {
		return _instanceId_set;
	}

	// </editor-fold>
}

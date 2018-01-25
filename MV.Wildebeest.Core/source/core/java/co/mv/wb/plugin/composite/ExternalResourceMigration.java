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

package co.mv.wb.plugin.composite;

import co.mv.wb.AssertionFailedException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.Interface;
import co.mv.wb.Logger;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.Resource;
import co.mv.wb.framework.Try;
import co.mv.wb.framework.TryResult;
import co.mv.wb.plugin.base.BaseMigration;
import co.mv.wb.service.MessagesException;
import java.io.File;
import java.util.UUID;

public class ExternalResourceMigration extends BaseMigration
{
	public ExternalResourceMigration(
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId,
		File baseDir,
		Logger logger,
		String fileName,
		String target)
	{
		super(migrationId, fromStateId, toStateId);

		this.setBaseDir(baseDir);
		this.setLogger(logger);
		this.setFileName(fileName);
		this.setTarget(target);
	}

	// <editor-fold desc="Logger" defaultstate="collapsed">

	private Logger _logger = null;
	private boolean _logger_set = false;

	private Logger getLogger() {
		if(!_logger_set) {
			throw new IllegalStateException("logger not set.");
		}
		if(_logger == null) {
			throw new IllegalStateException("logger should not be null");
		}
		return _logger;
	}

	private void setLogger(
		Logger value) {
		if(value == null) {
			throw new IllegalArgumentException("logger cannot be null");
		}
		boolean changing = !_logger_set || _logger != value;
		if(changing) {
			_logger_set = true;
			_logger = value;
		}
	}

	private void clearLogger() {
		if(_logger_set) {
			_logger_set = true;
			_logger = null;
		}
	}

	private boolean hasLogger() {
		return _logger_set;
	}

	// </editor-fold>

	// <editor-fold desc="BaseDir" defaultstate="collapsed">

	private File _baseDir = null;
	private boolean _baseDir_set = false;

	public File getBaseDir() {
		if(!_baseDir_set) {
			throw new IllegalStateException("baseDir not set.");
		}
		if(_baseDir == null) {
			throw new IllegalStateException("baseDir should not be null");
		}
		return _baseDir;
	}

	private void setBaseDir(
		File value) {
		if(value == null) {
			throw new IllegalArgumentException("baseDir cannot be null");
		}
		boolean changing = !_baseDir_set || _baseDir != value;
		if(changing) {
			_baseDir_set = true;
			_baseDir = value;
		}
	}

	private void clearBaseDir() {
		if(_baseDir_set) {
			_baseDir_set = true;
			_baseDir = null;
		}
	}

	private boolean hasBaseDir() {
		return _baseDir_set;
	}

	// </editor-fold>

	// <editor-fold desc="FileName" defaultstate="collapsed">

	private String _fileName = null;
	private boolean _fileName_set = false;

	public String getFileName() {
		if(!_fileName_set) {
			throw new IllegalStateException("fileName not set.");
		}
		if(_fileName == null) {
			throw new IllegalStateException("fileName should not be null");
		}
		return _fileName;
	}

	private void setFileName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("fileName cannot be null");
		}
		boolean changing = !_fileName_set || _fileName != value;
		if(changing) {
			_fileName_set = true;
			_fileName = value;
		}
	}

	private void clearFileName() {
		if(_fileName_set) {
			_fileName_set = true;
			_fileName = null;
		}
	}

	private boolean hasFileName() {
		return _fileName_set;
	}

	// </editor-fold>

	// <editor-fold desc="Target" defaultstate="collapsed">

	private String _target = null;
	private boolean _target_set = false;

	public String getTarget() {
		if(!_target_set) {
			throw new IllegalStateException("target not set.");
		}
		if(_target == null) {
			throw new IllegalStateException("target should not be null");
		}
		return _target;
	}

	private void setTarget(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("target cannot be null");
		}
		boolean changing = !_target_set || _target != value;
		if(changing) {
			_target_set = true;
			_target = value;
		}
	}

	private void clearTarget() {
		if(_target_set) {
			_target_set = true;
			_target = null;
		}
	}

	private boolean hasTarget() {
		return _target_set;
	}

	// </editor-fold>

	@Override public boolean canPerformOn(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }

		return true;
	}

	@Override public void perform(
		Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		
		// Load the resource
		Resource externalResource = null;
		try
		{
			File file = new File(this.getBaseDir(), this.getFileName());
			externalResource = Interface.loadResource(this.getLogger(), file);
		}
		catch(MessagesException e)
		{
			throw new MigrationFailedException(this.getMigrationId(), "Unable to load");
		}

		// TODO: replace with monadic chain once support for Java < 8 is dropped
		UUID targetStateId = null;
		TryResult<UUID> targetStateIdTryResult = Try.tryParseUuid(this.getTarget());
		if (targetStateIdTryResult.hasValue())
		{
			targetStateId = targetStateIdTryResult.getValue();
		}
		else
		{
			targetStateId = externalResource.stateIdForLabel(this.getTarget());
		}
		
		try
		{
			externalResource.migrate(this.getLogger(), instance, targetStateId);
		}
		catch (IndeterminateStateException ex)
		{
			throw new MigrationFailedException(
				this.getMigrationId(),
				"Indeterminate exception in external resource");
		}
		catch (AssertionFailedException ex)
		{
			throw new MigrationFailedException(
				this.getMigrationId(),
				"Assertion failed in external resource");
		}
		catch (MigrationNotPossibleException ex)
		{
			throw new MigrationFailedException(
				this.getMigrationId(),
				"Migration not possible in external resource");
		}
	}
}

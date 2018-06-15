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

import co.mv.wb.MigrationType;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.plugin.base.BaseMigration;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Applies a migration in an external resource definition, and tracks both the external resource state and the composite
 * state.
 *
 * @since                                       4.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:Composite",
	uri = "co.mv.wb.composite:ExternalResourceMigration",
	description =
		"Applies a migration in another resource definition, and tracks both the external resource state and the " +
			"composite resource state",
	example =
		"<migration\n" +
		"    type=\"ExternalResourceMigration\"\n" +
		"    id=\"dd7a708f-bf6d-4e18-aaec-c1fdf3de965d\"\n" +
		"    fromState=\"d364f2ba-3972-4a76-944d-d9767cbfc2d7\"\n" +
		"    toState=\"f488f17b-71d3-4672-9216-afd1e73884cf\">\n" +
		"    <filename>AnotherResource.wbr</filename>\n" +
		"    <target>SomeExternalTarget</target>\n" +
		"</migration>"
)
public class ExternalResourceMigration extends BaseMigration
{
	private File baseDir = null;
	private boolean baseDirSet = false;
	private String fileName = null;
	private boolean filenameSet = false;
	private Optional<String> target = null;
	private boolean targetSet = false;


	public ExternalResourceMigration(
		UUID migrationId,
		Optional<String> fromState,
		Optional<String> toState,
		File baseDir,
		String fileName,
		Optional<String> target)
	{
		super(migrationId, fromState, toState);

		this.setBaseDir(baseDir);
		this.setFileName(fileName);
		this.setTarget(target);
	}

	public File getBaseDir() {
		if(!baseDirSet) {
			throw new IllegalStateException("baseDir not set.");
		}
		if(baseDir == null) {
			throw new IllegalStateException("baseDir should not be null");
		}
		return baseDir;
	}

	private void setBaseDir(
		File value) {
		if(value == null) {
			throw new IllegalArgumentException("baseDir cannot be null");
		}
		boolean changing = !baseDirSet || baseDir != value;
		if(changing) {
			baseDirSet = true;
			baseDir = value;
		}
	}

	private void clearBaseDir() {
		if(baseDirSet) {
			baseDirSet = true;
			baseDir = null;
		}
	}

	private boolean hasBaseDir() {
		return baseDirSet;
	}

	public String getFileName() {
		if(!filenameSet) {
			throw new IllegalStateException("fileName not set.");
		}
		if(fileName == null) {
			throw new IllegalStateException("fileName should not be null");
		}
		return fileName;
	}

	private void setFileName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("fileName cannot be null");
		}
		boolean changing = !filenameSet || !fileName.equals(value);
		if(changing) {
			filenameSet = true;
			fileName = value;
		}
	}

	private void clearFileName() {
		if(filenameSet) {
			filenameSet = true;
			fileName = null;
		}
	}

	private boolean hasFileName() {
		return filenameSet;
	}

	public Optional<String> getTarget() {
		if(!targetSet) {
			throw new IllegalStateException("target not set.");
		}
		if(target == null) {
			throw new IllegalStateException("target should not be null");
		}
		return target;
	}

	private void setTarget(
		Optional<String> value) {
		if(value == null) {
			throw new IllegalArgumentException("target cannot be null");
		}
		boolean changing = !targetSet || !target.equals(value);
		if(changing) {
			targetSet = true;
			target = value;
		}
	}

	private void clearTarget() {
		if(targetSet) {
			targetSet = true;
			target = null;
		}
	}

	private boolean hasTarget() {
		return targetSet;
	}


	@Override
	public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			Wildebeest.MySqlDatabase,
			Wildebeest.PostgreSqlDatabase,
			Wildebeest.SqlServerDatabase);
	}
}

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

import java.io.File;
import java.util.List;

/**
 * The main programmatic API for working with Wildebeest.
 *
 * @since 4.0
 */
public interface WildebeestApi
{
	/**
	 * Deserializes a {@link Resource} from the specified descriptor file.
	 *
	 * @param resourceFile the descriptor file from which the Resource should be deserialized
	 * @return a deserialized Resource object with it's plugin.
	 * @throws FileLoadException    if the specified file cannot be loaded.
	 * @throws LoaderFault          if the resource fails to be loaded.
	 * @throws PluginBuildException if any plugin required while loading the resource fails to build.
	 * @since 1.0
	 */
	Resource loadResource(
		File resourceFile) throws
		FileLoadException,
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException,
		XmlValidationException;

	/**
	 * Deserializes an {@link Instance} from the specified descriptor file.
	 *
	 * @param instanceFile the descriptor file from which the Instance should be deserialized
	 * @return a deserialized Resource object.
	 * @throws FileLoadException    if the specified file cannot be loaded.
	 * @throws LoaderFault          if the instance fails to be loaded.
	 * @throws PluginBuildException if any plugin required while loading the instance fails to build.
	 * @since 1.0
	 */
	Instance loadInstance(
		File instanceFile) throws
		FileLoadException,
		LoaderFault,
		PluginBuildException,
		XmlValidationException;

	/**
	 * Performs the assertions for the instance's current state.
	 *
	 * @param resource the {@link Resource} to be asserted.
	 * @param instance the {@link Instance} to be asserted.
	 * @return a {@link java.util.List} of the {@link AssertionResult}s
	 * that were generated for the Assertions for this Resource's current
	 * state.
	 * @throws IndeterminateStateException when the current state of the resource cannot be determined clearly.
	 * @throws AssertionFailedException    if one or more assertions of the current state fails
	 * @since 1.0
	 */
	List<AssertionResult> assertState(
		Resource resource,
		Instance instance) throws
		AssertionFailedException,
		IndeterminateStateException,
		PluginNotFoundException;

	/**
	 * Checks the state of an instance of a resource.
	 *
	 * @param resource the descriptor file for the resource.
	 * @param instance the descriptor file for the instance.
	 * @throws IndeterminateStateException if the current state of the resource cannot be determined.
	 * @throws AssertionFailedException    if one or more assertions of the current state fails
	 * @since 1.0
	 */
	void state(
		Resource resource,
		Instance instance) throws
		AssertionFailedException,
		IndeterminateStateException,
		PluginNotFoundException;

	/**
	 * Migrates an instance of a resource to a particular state.
	 *
	 * @param resource    the resource.
	 * @param instance    the instance.
	 * @param targetState the optional name or unique ID of the state to which the instance should
	 *                    be migrated.  If none is supplied then Wildebeest will use the default
	 *                    target if one is set on the Resource.  Otherwise a
	 *                    NoTargetSpecifiedException is thrown.
	 * @throws AssertionFailedException       if one or more assertions of the target state fail after migration
	 * @throws MigrationFailedException       if the migration operation fails for any reason.
	 * @throws MigrationNotPossibleException  if the requested migration is not possible due to the lack of a
	 *                                        migration path
	 * @throws IndeterminateStateException    if the current state of the resource cannot be determined prior to
	 *                                        migrating.
	 * @throws InvalidStateSpecifiedException if the specified state is not a valid state identifier.
	 * @throws TargetNotSpecifiedException    if no target state is specified and the resource does not have a default
	 *                                        target.
	 * @throws UnknownStateSpecifiedException if the specified state does not exist in the resource.
	 * @since 1.0
	 */
	void migrate(
		Resource resource,
		Instance instance,
		String targetState) throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidReferenceException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		PluginNotFoundException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException;

	/**
	 * Jumps the recorded state of the specified instance to the supplied target state.  This can be useful when you are
	 * bringing a pre-existing resource under Wildebeest management, or if you need to align the tracked state with the
	 * actual state of the resource if you make manual changes to it.
	 *
	 * @param resource    the resource.
	 * @param instance    the instance to jump the tracked state on.
	 * @param targetState the state to jump to.
	 * @throws AssertionFailedException       if one or more assertions of the target state fail after jumping state.
	 * @throws IndeterminateStateException    if the current state of the instance cannot be determined.
	 * @throws InvalidStateSpecifiedException if the specified state is not a valid state identifier.
	 * @throws JumpStateFailedException       if the jumpstate operation fails for any reason.
	 * @throws UnknownStateSpecifiedException if the specified state does not exist in the resource.
	 */
	void jumpstate(
		Resource resource,
		Instance instance,
		String targetState) throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		JumpStateFailedException,
		PluginNotFoundException,
		UnknownStateSpecifiedException;

	/**
	 * Produces an XML description of the plugins known to this instance of Wildebeest.
	 *
	 * @since 4.0
	 */
	String describePlugins();
}

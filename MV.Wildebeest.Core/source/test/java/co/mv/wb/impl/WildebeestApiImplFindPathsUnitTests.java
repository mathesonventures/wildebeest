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

package co.mv.wb.impl;

import co.mv.wb.InvalidReferenceException;
import co.mv.wb.Migration;
import co.mv.wb.Resource;
import co.mv.wb.fixture.TestContext_ResourceAndInstance;
import co.mv.wb.fixture.TestContext_SimpleFakeResource_Builder;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests to verify the {@link WildebeestApiImpl#findPaths(Resource, UUID, UUID)}} method.
 *
 * @since 4.0
 */
public class WildebeestApiImplFindPathsUnitTests
{

	/**
	 * Refer to image FindPathsTestReferences/single_path.png
	 */
	@Test
	public void findPaths_singlePathFromSourceToTarget_succeeds() throws InvalidReferenceException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_SimpleFakeResource_Builder
			.create()
			.withDummyStates(5)
			.withDefaultTarget("state4")
			.withMigration(0, 1)
			.withMigration(0, 4)
			.withMigration(1, 2)
			.withMigration(2, 3)
			.build();

		UUID fromStateId = context.resource.getStates().get(0).getStateId();
		UUID targetStateId = context.resource.getStates().get(3).getStateId();

		// Execute
		List<List<Migration>> paths = WildebeestApiImpl.findPaths(
			context.resource,
			fromStateId,
			targetStateId);

		// Verify
		assertTrue(paths.size() == 1);

		Migration m0to1 = context.resource.getMigrations().get(0);
		Migration m1to2 = context.resource.getMigrations().get(2);
		Migration m2to3 = context.resource.getMigrations().get(3);

		List<Migration> expectedMigrations = Arrays.asList(m0to1, m1to2, m2to3);

		assertEquals(paths.get(0), expectedMigrations);
	}

	/**
	 * Refer to image FindPathsTestReferences/basic_multiple_paths.png
	 */
	@Test
	public void findPaths_multiplePathsFromSourceToTarget_succeeds() throws InvalidReferenceException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_SimpleFakeResource_Builder
			.create()
			.withDummyStates(5)
			.withDefaultTarget("state5")
			.withMigration(0, 1)
			.withMigration(1, 2)
			.withMigration(1, 3)
			.withMigration(2, 4)
			.withMigration(3, 4)
			.build();

		UUID fromStateId = context.resource.getStates().get(0).getStateId();
		UUID targetStateId = context.resource.getStates().get(4).getStateId();

		// Execute
		List<List<Migration>> paths = WildebeestApiImpl.findPaths(
			context.resource,
			fromStateId,
			targetStateId);

		// Verify
		assertTrue(paths.size() == 2);

		Migration m0to1 = context.resource.getMigrations().get(0);
		Migration m1to2 = context.resource.getMigrations().get(1);
		Migration m1to3 = context.resource.getMigrations().get(2);
		Migration m2to4 = context.resource.getMigrations().get(3);
		Migration m3to4 = context.resource.getMigrations().get(4);

		List<Migration> expectedPath0 = Arrays.asList(m0to1, m1to2, m2to4);
		assertEquals(paths.get(0), expectedPath0);

		List<Migration> expectedPath1 = Arrays.asList(m0to1, m1to3, m3to4);
		assertEquals(paths.get(1), expectedPath1);
	}

	/**
	 * Refer to image FindPathsTestReferences/non_existent_multiple_paths.png
	 */
	@Test
	public void findPaths_multiplePathsFromNonExistentSourceToExistingTarget_succeeds() throws InvalidReferenceException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_SimpleFakeResource_Builder
			.create()
			.withDummyStates(4)
			.withDefaultTarget("state3")
			.withMigration(null, 0)
			.withMigration(0, 2)
			.withMigration(2, 3)
			.withMigration(null, 1)
			.withMigration(1, 2)
			.build();

		UUID fromStateId = null;
		UUID targetStateId = context.resource.getStates().get(3).getStateId();

		// Execute
		List<List<Migration>> paths = WildebeestApiImpl.findPaths(
			context.resource,
			fromStateId,
			targetStateId);

		// Verify
		assertTrue(paths.size() == 2);

		Migration m_to0 = context.resource.getMigrations().get(0);
		Migration m0to2 = context.resource.getMigrations().get(1);
		Migration m2to3 = context.resource.getMigrations().get(2);
		Migration m_to1 = context.resource.getMigrations().get(3);
		Migration m1to2 = context.resource.getMigrations().get(4);

		List<Migration> expectedMigrationSet1 = Arrays.asList(m_to0, m0to2, m2to3);
		assertEquals(paths.get(0), expectedMigrationSet1);

		List<Migration> expectedMigrationSet2 = Arrays.asList(m_to1, m1to2, m2to3);
		assertEquals(paths.get(1), expectedMigrationSet2);
	}

	/**
	 * Refer to image FindPathsTestReferences/state_to_non_existent_state.png
	 */
	@Test
	public void findPaths_multiplePathsFromSourceStateToNonExistentState_succeeds() throws InvalidReferenceException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_SimpleFakeResource_Builder
			.create()
			.withDummyStates(4)
			.withDefaultTarget("state_")
			.withMigration(0, 1)
			.withMigration(1, 2)
			.withMigration(2, null)
			.withMigration(1, 3)
			.withMigration(3, null)
			.build();

		UUID fromStateId = context.resource.getStates().get(0).getStateId();
		UUID targetStateId = null;

		// Execute
		List<List<Migration>> paths = WildebeestApiImpl.findPaths(
			context.resource,
			fromStateId,
			targetStateId);

		// Verify
		assertTrue(paths.size() == 2);

		Migration m1to2 = context.resource.getMigrations().get(0);
		Migration m2to3 = context.resource.getMigrations().get(1);
		Migration m3to_ = context.resource.getMigrations().get(2);
		Migration m2to4 = context.resource.getMigrations().get(3);
		Migration m4to_ = context.resource.getMigrations().get(4);

		List<Migration> expectedMigrationsSet1 = Arrays.asList(m1to2, m2to3, m3to_);
		assertEquals(paths.get(0), expectedMigrationsSet1);

		List<Migration> expectedMigrationsSet2 = Arrays.asList(m1to2, m2to4, m4to_);
		assertEquals(paths.get(1), expectedMigrationsSet2);
	}

	/**
	 * Refer to image FindPathsTestReferences/no_path_found.png
	 */
	@Test
	public void findPaths_noPathFromSourceToTarget_succeeds() throws InvalidReferenceException
	{
		// Execute
		TestContext_ResourceAndInstance context = TestContext_SimpleFakeResource_Builder
			.create()
			.withDummyStates(4)
			.withDefaultTarget("state3")
			.withMigration(null, 0)
			.withMigration(0, 1)
			.withMigration(1, 3)
			.withMigration(null, 2)
			.withMigration(2, 3)
			.build();

		UUID currentStateId = context.resource.getStates().get(0).getStateId();
		UUID targetStateId = context.resource.getStates().get(2).getStateId();

		// Execute
		List<List<Migration>> paths = WildebeestApiImpl.findPaths(
			context.resource,
			currentStateId,
			targetStateId);

		// Verify
		assertTrue(paths.size() == 0);
	}

	/**
	 * Refer to image FindPathsTestReferences/circular_example.png
	 */
	@Test
	public void findPaths_circular_succeeds() throws InvalidReferenceException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_SimpleFakeResource_Builder
			.create()
			.withDummyStates(5)
			.withDefaultTarget("state5")
			.withMigration(0, 1)
			.withMigration(1, 2)
			.withMigration(2, 3)
			.withMigration(3, 1)
			.withMigration(2, 4)
			.build();

		Migration m1to2 = context.resource.getMigrations().get(0);
		Migration m2to3 = context.resource.getMigrations().get(1);
		Migration m3to5 = context.resource.getMigrations().get(4);

		UUID fromStateId = context.resource.getStates().get(0).getStateId();
		UUID targetStateId = context.resource.getStates().get(4).getStateId();

		// Execute
		List<List<Migration>> paths = WildebeestApiImpl.findPaths(context.resource, fromStateId, targetStateId);

		// Verify
		assertTrue(paths.size() == 1);

		List<Migration> expectedMigrationsSet1 = Arrays.asList(m1to2, m2to3, m3to5);
		assertEquals(paths.get(0), expectedMigrationsSet1);
	}
}

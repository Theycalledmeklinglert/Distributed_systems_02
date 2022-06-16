/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/*
package de.fhws.fiw.fds.exam02.client;

import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestYourApi
	{

		@Test
		public void load_existing_project_by_id_status200( ) throws IOException
		{
			final WebApiClient client = new WebApiClient( );
			ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
			final WebApiResponse postResponse = client.postProject(project);
			final WebApiResponse response = client.loadById(postResponse.getIdFromHeaderString() );

			final Optional<ProjectView> result = response.getResponseData( ).stream( ).findFirst( );
			final ProjectView gotProject = result.get( );

			assertEquals( 200, response.getLastStatusCode( ) );
			assertEquals( 1, response.getResponseData( ).size( ) );

			assertEquals( "TestProject", gotProject.getName( ) );
			assertEquals( "This is a test Project", gotProject.getDescription( ) );
			assertEquals("2022ws", gotProject.getSemester( ));
			assertEquals("testProject", gotProject.getType( ));

			client.deleteProjectById(postResponse.getIdFromHeaderString());
		}

		@Test
		public void load_project_by_invalid_id_status404( ) throws IOException
		{
			final WebApiClient client = new WebApiClient( );
			ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
			final WebApiResponse postResponse = client.postProject(project);

			final long invalidID = 0l;
			final WebApiResponse loadResponse = client.loadById(invalidID);
			Optional<ProjectView> gotProjectOpt = loadResponse.getResponseData().stream().findFirst();

			assertEquals( 404, loadResponse.getLastStatusCode( ) );
			assertEquals( 0, loadResponse.getResponseData( ).size( ) );

			client.deleteProjectById(postResponse.getIdFromHeaderString());
		}

    @Test
    public void load_existing_project_by_name_status200( ) throws IOException
    {
        final WebApiClient client = new WebApiClient( );
		ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
		final WebApiResponse postResponse = client.postProject(project);
        final WebApiResponse loadResponse = client.loadAllProjectsByName("TestProject");

        final Optional<ProjectView> result = loadResponse.getResponseData( ).stream( ).findFirst( );
        final ProjectView gotProject = result.get( );

        assertEquals( 200, loadResponse.getLastStatusCode( ) );
        assertEquals(1, loadResponse.getResponseData().size());
        assertEquals( "TestProject", gotProject.getName() );
		assertEquals( "This is a test Project", gotProject.getDescription() );
		assertEquals( "2022ws", gotProject.getSemester() );
		assertEquals( "testProject", gotProject.getType() );
		assertEquals(null, gotProject.getStudents());
		assertEquals(null, gotProject.getSupervisors());

		client.deleteProjectById(postResponse.getIdFromHeaderString());
	}

	@Test
	public void load_non_existing_project_by_name_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
		final WebApiResponse postResponse = client.postProject(project);
		final WebApiResponse loadResponse = client.loadAllProjectsByName("Muster Project");

		final Optional<ProjectView> result = loadResponse.getResponseData( ).stream( ).findFirst( );

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(0, loadResponse.getResponseData().size());

		client.deleteProjectById(postResponse.getIdFromHeaderString());
	}

	@Test
	public void load_existing_project_by_semester_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
		final WebApiResponse postResponse = client.postProject(project);
		final WebApiResponse loadResponse = client.loadAllProjectsBySemester("2022ws");

		final Optional<ProjectView> result = loadResponse.getResponseData( ).stream( ).findFirst( );
		assertTrue( result.isPresent( ) );
		final ProjectView gotProject = result.get( );

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(1, loadResponse.getResponseData().size());
		assertEquals( "TestProject", gotProject.getName() );
		assertEquals( "This is a test Project", gotProject.getDescription() );
		assertEquals( "2022ws", gotProject.getSemester() );
		assertEquals( "testProject", gotProject.getType() );
		assertEquals(null, gotProject.getStudents());
		assertEquals(null, gotProject.getSupervisors());

		client.deleteProjectById(postResponse.getIdFromHeaderString());
	}

	@Test
	public void load_non_existing_project_by_semester_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
		final WebApiResponse postResponse = client.postProject(project);
		final WebApiResponse loadResponse = client.loadAllProjectsBySemester("2020ws");

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(0, loadResponse.getResponseData().size());

		client.deleteProjectById(postResponse.getIdFromHeaderString());
	}

	@Test
	public void load_existing_project_by_type_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project1 = new ProjectView("TestProject1", "This is a test Project", "2022ws", "testProject");
		ProjectView project2 = new ProjectView("TestProject2", "This is a test Project", "2024ws", "programming Project");
		ProjectView project3 = new ProjectView("TestProject3", "This is a test Project", "2025ws", "thesis");

		final WebApiResponse postResponse1 = client.postProject(project1);
		final WebApiResponse postResponse2 = client.postProject(project2);
		final WebApiResponse postResponse3 = client.postProject(project3);

		final WebApiResponse loadResponse = client.loadAllProjectsByType("testProject");

		List<ProjectView> list = loadResponse.getResponseData().stream().collect(Collectors.toList());
		final ProjectView firstResult = list.get(0);

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(1, loadResponse.getResponseData().size());
		assertEquals( "TestProject1", firstResult.getName() );
		assertEquals( "This is a test Project", firstResult.getDescription() );
		assertEquals( "2022ws", firstResult.getSemester() );
		assertEquals( "testProject", firstResult.getType() );
		assertEquals(null, firstResult.getStudents());
		assertEquals(null, firstResult.getSupervisors());

		client.deleteProjectById(postResponse1.getIdFromHeaderString());
		client.deleteProjectById(postResponse2.getIdFromHeaderString());
		client.deleteProjectById(postResponse3.getIdFromHeaderString());
	}

	@Test
	public void load_non_existing_project_by_type_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project1 = new ProjectView("TestProject1", "This is a test Project", "2022ws", "testProject");
		ProjectView project2 = new ProjectView("TestProject2", "This is a test Project", "2022ws", "testProject");
		ProjectView project3 = new ProjectView("TestProject3", "This is a test Project", "2025ws", "testProject");

		final WebApiResponse postResponse1 = client.postProject(project1);
		final WebApiResponse postResponse2 = client.postProject(project2);
		final WebApiResponse postResponse3 = client.postProject(project3);

		final WebApiResponse loadResponse = client.loadAllProjectsByType("programming project");

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(0, loadResponse.getResponseData().size());

		client.deleteProjectById(postResponse1.getIdFromHeaderString());
		client.deleteProjectById(postResponse2.getIdFromHeaderString());
		client.deleteProjectById(postResponse3.getIdFromHeaderString());
	}

	@Test
	public void load_existing_project_by_name_and_semester_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project1 = new ProjectView("TestProject1", "This is a test Project", "2022ws", "testProject");
		ProjectView project2 = new ProjectView("TestProject2", "This is a test Project", "2022ws", "testProject");
		ProjectView project3 = new ProjectView("TestProject3", "This is a test Project", "2025ws", "testProject");

		final WebApiResponse postResponse1 = client.postProject(project1);
		final WebApiResponse postResponse2 = client.postProject(project2);
		final WebApiResponse postResponse3 = client.postProject(project3);

		final WebApiResponse loadResponse = client.loadAllProjectsByNameANDSemesterANDType("TestProject1", "", "2022ws");

		final Optional<ProjectView> result = loadResponse.getResponseData( ).stream( ).findFirst( );
		assertTrue( result.isPresent( ) );
		final ProjectView gotProject = result.get( );

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(1, loadResponse.getResponseData().size());
		assertEquals( "TestProject1", gotProject.getName() );
		assertEquals( "This is a test Project", gotProject.getDescription() );
		assertEquals( "2022ws", gotProject.getSemester() );
		assertEquals( "testProject", gotProject.getType() );
		assertEquals(null, gotProject.getStudents());
		assertEquals(null, gotProject.getSupervisors());

		client.deleteProjectById(postResponse1.getIdFromHeaderString());
		client.deleteProjectById(postResponse2.getIdFromHeaderString());
		client.deleteProjectById(postResponse3.getIdFromHeaderString());
	}

	@Test
	public void load_non_existing_project_by_name_and_semester_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project1 = new ProjectView("TestProject1", "This is a test Project", "2022ws", "testProject");
		ProjectView project2 = new ProjectView("TestProject2", "This is a test Project", "2022ws", "testProject");
		ProjectView project3 = new ProjectView("TestProject3", "This is a test Project", "2022ws", "testProject");

		final WebApiResponse postResponse1 = client.postProject(project1);
		final WebApiResponse postResponse2 = client.postProject(project2);
		final WebApiResponse postResponse3 = client.postProject(project3);

		final WebApiResponse loadResponse = client.loadAllProjectsByNameANDSemesterANDType("TestProject", "", "2025ws");

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(0, loadResponse.getResponseData().size());

		client.deleteProjectById(postResponse1.getIdFromHeaderString());
		client.deleteProjectById(postResponse2.getIdFromHeaderString());
		client.deleteProjectById(postResponse3.getIdFromHeaderString());
	}

	@Test
	public void load_existing_project_by_name_and_type_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project1 = new ProjectView("TestProject1", "This is a test Project", "2022ws", "testProject");
		ProjectView project2 = new ProjectView("TestProject2", "This is another test Project", "2022ws", "testProject");
		ProjectView project3 = new ProjectView("Programmierprojekt", "This is a test Project", "2025ws", "programming project");

		final WebApiResponse postResponse1 = client.postProject(project1);
		final WebApiResponse postResponse2 = client.postProject(project2);
		final WebApiResponse postResponse3 = client.postProject(project3);

		final WebApiResponse loadResponse = client.loadAllProjectsByNameANDSemesterANDType("TestProject1", "testProject", "");

		final Optional<ProjectView> result = loadResponse.getResponseData( ).stream( ).findFirst( );
		assertTrue( result.isPresent( ) );
		final ProjectView gotProject = result.get( );

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(1, loadResponse.getResponseData().size());
		assertEquals( "TestProject1", gotProject.getName() );
		assertEquals( "This is a test Project", gotProject.getDescription() );
		assertEquals( "2022ws", gotProject.getSemester() );
		assertEquals( "testProject", gotProject.getType() );
		assertEquals(null, gotProject.getStudents());
		assertEquals(null, gotProject.getSupervisors());

		client.deleteProjectById(postResponse1.getIdFromHeaderString());
		client.deleteProjectById(postResponse2.getIdFromHeaderString());
		client.deleteProjectById(postResponse3.getIdFromHeaderString());
	}

	@Test
	public void load_non_existing_project_by_name_and_type_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project1 = new ProjectView("TestProject1", "This is a test Project", "2022ws", "testProject");
		ProjectView project2 = new ProjectView("TestProject2", "This is a test Project", "2022ws", "testProject");
		ProjectView project3 = new ProjectView("TestProject3", "This is a test Project", "2025ws", "testProject");

		final WebApiResponse postResponse1 = client.postProject(project1);
		final WebApiResponse postResponse2 = client.postProject(project2);
		final WebApiResponse postResponse3 = client.postProject(project3);

		final WebApiResponse loadResponse = client.loadAllProjectsByNameANDSemesterANDType("Project", "testProject", "");

		final Optional<ProjectView> result = loadResponse.getResponseData( ).stream( ).findFirst( );

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(0, loadResponse.getResponseData().size());

		client.deleteProjectById(postResponse1.getIdFromHeaderString());
		client.deleteProjectById(postResponse2.getIdFromHeaderString());
		client.deleteProjectById(postResponse3.getIdFromHeaderString());
	}

	@Test
	public void load_existing_projects_by_type_and_semester_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project1 = new ProjectView("TestProject1", "This is a test Project", "2022ws", "testProject");
		ProjectView project2 = new ProjectView("TestProject2", "This is a test Project", "2024ws", "testProject");
		ProjectView project3 = new ProjectView("TestProject3", "This is a test Project", "2025ws", "testProject");

		final WebApiResponse postResponse1 = client.postProject(project1);
		final WebApiResponse postResponse2 = client.postProject(project2);
		final WebApiResponse postResponse3 = client.postProject(project3);

		final WebApiResponse loadResponse = client.loadAllProjectsByNameANDSemesterANDType("", "testProject", "2022ws");

		List<ProjectView> list = loadResponse.getResponseData().stream().collect(Collectors.toList());
		final ProjectView firstResult = list.get(0);

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(1, loadResponse.getResponseData().size());
		assertEquals( "TestProject1", firstResult.getName() );
		assertEquals( "This is a test Project", firstResult.getDescription() );
		assertEquals( "2022ws", firstResult.getSemester() );
		assertEquals( "testProject", firstResult.getType() );
		assertEquals(null, firstResult.getStudents());
		assertEquals(null, firstResult.getSupervisors());

		client.deleteProjectById(postResponse1.getIdFromHeaderString());
		client.deleteProjectById(postResponse2.getIdFromHeaderString());
		client.deleteProjectById(postResponse3.getIdFromHeaderString());
	}

	@Test
	public void load_non_existing_project_by_type_and_semester_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project1 = new ProjectView("TestProject1", "This is a test Project", "2022ws", "testProject");
		ProjectView project2 = new ProjectView("TestProject2", "This is a test Project", "2022ws", "testProject");
		ProjectView project3 = new ProjectView("TestProject3", "This is a test Project", "2022ws", "testProject");

		final WebApiResponse postResponse1 = client.postProject(project1);
		final WebApiResponse postResponse2 = client.postProject(project2);
		final WebApiResponse postResponse3 = client.postProject(project3);

		final WebApiResponse loadResponse = client.loadAllProjectsByNameANDSemesterANDType("", "testProject", "2025ws");

		final Optional<ProjectView> result = loadResponse.getResponseData( ).stream( ).findFirst( );

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(0, loadResponse.getResponseData().size());

		client.deleteProjectById(postResponse1.getIdFromHeaderString());
		client.deleteProjectById(postResponse2.getIdFromHeaderString());
		client.deleteProjectById(postResponse3.getIdFromHeaderString());
	}

	@Test
	public void load_existing_project_by_name_type_and_semester_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project1 = new ProjectView("TestProject1", "This is a test Project", "2022ws", "testProject");
		ProjectView project2 = new ProjectView("TestProject2", "This is a test Project", "2022ws", "testProject");
		ProjectView project3 = new ProjectView("TestProject3", "This is a test Project", "2022ws", "testProject");

		final WebApiResponse postResponse1 = client.postProject(project1);
		final WebApiResponse postResponse2 = client.postProject(project2);
		final WebApiResponse postResponse3 = client.postProject(project3);

		final WebApiResponse loadResponse = client.loadAllProjectsByNameANDSemesterANDType("TestProject1", "testProject", "2022ws");

		final Optional<ProjectView> result = loadResponse.getResponseData( ).stream( ).findFirst( );

		final ProjectView gotProject = result.get( );

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(1, loadResponse.getResponseData().size());
		assertEquals( "TestProject1", gotProject.getName() );
		assertEquals( "This is a test Project", gotProject.getDescription() );
		assertEquals( "2022ws", gotProject.getSemester() );
		assertEquals( "testProject", gotProject.getType() );
		assertEquals(null, gotProject.getStudents());
		assertEquals(null, gotProject.getSupervisors());

		client.deleteProjectById(postResponse1.getIdFromHeaderString());
		client.deleteProjectById(postResponse2.getIdFromHeaderString());
		client.deleteProjectById(postResponse3.getIdFromHeaderString());
	}

	@Test
	public void load_non_existing_project_by_name_type_and_semester_status200( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project1 = new ProjectView("TestProject1", "This is a test Project", "2022ws", "testProject");
		ProjectView project2 = new ProjectView("TestProject2", "This is a test Project", "2022ws", "testProject");
		ProjectView project3 = new ProjectView("TestProject3", "This is a test Project", "2022ws", "testProject");

		final WebApiResponse postResponse1 = client.postProject(project1);
		final WebApiResponse postResponse2 = client.postProject(project2);
		final WebApiResponse postResponse3 = client.postProject(project3);
		final WebApiResponse loadResponse = client.loadAllProjectsByNameANDSemesterANDType("TestProject5", "programming Project", "2022ws");

		final Optional<ProjectView> result = loadResponse.getResponseData( ).stream( ).findFirst( );

		assertEquals( 200, loadResponse.getLastStatusCode( ) );
		assertEquals(0, loadResponse.getResponseData().size());

		client.deleteProjectById(postResponse1.getIdFromHeaderString());
		client.deleteProjectById(postResponse2.getIdFromHeaderString());
		client.deleteProjectById(postResponse3.getIdFromHeaderString());
	}

		@Test
		public void load_multiple_existing_projects_by_name_type_and_semester_status200( ) throws IOException
		{
			final WebApiClient client = new WebApiClient( );
			ProjectView project1 = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
			ProjectView project2 = new ProjectView("TestProject", "This is a second Project", "2022ws", "testProject");
			ProjectView project3 = new ProjectView("TestProject", "This is a third Project", "2025ws", "testProject");

			final WebApiResponse postResponse1 = client.postProject(project1);
			final WebApiResponse postResponse2 = client.postProject(project2);
			final WebApiResponse postResponse3 = client.postProject(project3);

			final WebApiResponse loadResponse = client.loadAllProjectsByNameANDSemesterANDType("TestProject", "testProject", "2022ws");

			List<ProjectView> firstResult = loadResponse.getResponseData().stream().filter(p -> (p.getDescription().equals("This is a test Project") )).collect(Collectors.toList());
			List<ProjectView> secondResult = loadResponse.getResponseData().stream().filter(p -> (p.getDescription().equals("This is a second Project") )).collect(Collectors.toList());

			ProjectView firstProject = firstResult.get(0);
			ProjectView secondProject = secondResult.get(0);

			assertEquals( 200, loadResponse.getLastStatusCode( ) );
			assertEquals(2, loadResponse.getResponseData().size());

			assertEquals( "TestProject", firstProject.getName() );
			assertEquals( "This is a test Project", firstProject.getDescription() );
			assertEquals( "2022ws", firstProject.getSemester() );
			assertEquals( "testProject", firstProject.getType() );
			assertEquals(null, firstProject.getStudents());
			assertEquals(null, firstProject.getSupervisors());

			assertEquals( "TestProject", secondProject.getName() );
			assertEquals( "This is a second Project", secondProject.getDescription() );
			assertEquals( "2022ws", secondProject.getSemester() );
			assertEquals( "testProject", secondProject.getType() );
			assertEquals(null, secondProject.getStudents());
			assertEquals(null, secondProject.getSupervisors());

			client.deleteProjectById(postResponse1.getIdFromHeaderString());
			client.deleteProjectById(postResponse2.getIdFromHeaderString());
			client.deleteProjectById(postResponse3.getIdFromHeaderString());
		}

	@Test
	public void test_post_project_status201( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
		final WebApiResponse response = client.postProject(project);

		final WebApiResponse loadResponse = client.loadById(response.getIdFromHeaderString());
		Optional<ProjectView> gotProjectOptional = loadResponse.getResponseData().stream().findFirst();

		ProjectView gotProject = gotProjectOptional.get();

		assertEquals( 201, response.getLastStatusCode( ) );
		assertEquals( 1, loadResponse.getResponseData( ).size( ) );
		assertEquals("TestProject", gotProject.getName());
		assertEquals("This is a test Project", gotProject.getDescription());
		assertEquals("2022ws", gotProject.getSemester());
		assertEquals("testProject", gotProject.getType());

		client.deleteProjectById(response.getIdFromHeaderString());

	}

	@Test
	public void test_post_invalid_project_status422( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project = new ProjectView("TestProject", "This is a test Project", "asdasdsa", "testProject");
		final WebApiResponse postResponse = client.postProject(project);

		assertEquals( 422, postResponse.getLastStatusCode( ) );
		assertEquals(Collections.EMPTY_LIST, postResponse.getResponseData( ) );

		WebApiResponse loadResponse = client.loadAllProjects();

		assertEquals(200, loadResponse.getLastStatusCode());
		assertEquals(0, loadResponse.getResponseData().size());
	}

	@Test
	public void test_post_empty_project_status422( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project = new ProjectView();
		final WebApiResponse postResponse = client.postProject(project);

		assertEquals( 422, postResponse.getLastStatusCode( ) );
		assertEquals(Collections.EMPTY_LIST, postResponse.getResponseData( ) );

		WebApiResponse loadResponse = client.loadAllProjects();
		Optional<ProjectView> gotProjectOptional = loadResponse.getResponseData().stream().findFirst();

		assertEquals(200, loadResponse.getLastStatusCode());
		assertEquals(0, loadResponse.getResponseData().size());

	}

	@Test
	public void test_put_with_existent_project_status204( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
		final WebApiResponse postResponse = client.postProject(project);

		final long id = postResponse.getIdFromHeaderString();
		final WebApiResponse loadResponse = client.loadById(id);

		ProjectView update = new ProjectView("Updated TestProject", "This is an updated test Project", "2023ws", "updatedTestProject");
		WebApiResponse putResponse = client.updateProject(id, update);

		final WebApiResponse updatedLoadResponse = client.loadById(id);
		Optional<ProjectView> updatedProjectOptional = updatedLoadResponse.getResponseData().stream().findFirst();

		ProjectView updatedProject = updatedProjectOptional.get();

		assertEquals(1, updatedLoadResponse.getResponseData().size());
		assertEquals(200, loadResponse.getLastStatusCode());

		assertEquals(204, putResponse.getLastStatusCode());
		assertEquals("Updated TestProject", updatedProject.getName());
		assertEquals("This is an updated test Project", updatedProject.getDescription());
		assertEquals("2023ws", updatedProject.getSemester());
		assertEquals("updatedTestProject", updatedProject.getType());
		assertEquals(null, updatedProject.getStudents());
		assertEquals(null, updatedProject.getSupervisors());

		client.deleteProjectById(id);
	}

	@Test
	public void test_put_with_non_existent_project_status404( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );

		final long id = 1l;
		ProjectView update = new ProjectView("Updated TestProject", "This is an updated test Project", "2023ws", "updatedTestProject");
		WebApiResponse putResponse = client.updateProject(id, update);

		final WebApiResponse updatedLoadResponse = client.loadById(id);
		Optional<ProjectView> updatedProjectOptional = updatedLoadResponse.getResponseData().stream().findFirst();

		assertEquals(0, updatedLoadResponse.getResponseData().size());
		assertEquals(404, updatedLoadResponse.getLastStatusCode());
		assertEquals(404, putResponse.getLastStatusCode());

		client.deleteProjectById(id);
	}

	@Test
	public void test_put_with_unchanged_existent_project_status400( ) throws IOException
	{
		final WebApiClient client = new WebApiClient( );
		ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
		final WebApiResponse postResponse = client.postProject(project);

		final long id = postResponse.getIdFromHeaderString();

		ProjectView update = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
		WebApiResponse putResponse = client.updateProject(id, update);

		final WebApiResponse updatedLoadResponse = client.loadById(id);
		Optional<ProjectView> updatedProjectOptional = updatedLoadResponse.getResponseData().stream().findFirst();

		ProjectView updatedProject = updatedProjectOptional.get();

		assertEquals(200, updatedLoadResponse.getLastStatusCode());
		assertEquals(1, updatedLoadResponse.getResponseData().size());

		assertEquals(204, putResponse.getLastStatusCode());
		assertEquals("TestProject", updatedProject.getName());
		assertEquals("This is a test Project", updatedProject.getDescription());
		assertEquals("2022ws", updatedProject.getSemester());
		assertEquals("testProject", updatedProject.getType());
		assertEquals(null, updatedProject.getStudents());
		assertEquals(null, updatedProject.getSupervisors());

		client.deleteProjectById(id);
	}

		@Test
		public void test_put_with_invalid_update_to_existent_project_status400( ) throws IOException
		{
			final WebApiClient client = new WebApiClient( );
			ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
			final WebApiResponse postResponse = client.postProject(project);

			final long id = postResponse.getIdFromHeaderString();

			ProjectView update = new ProjectView("", "", "sadsad", "testProject");
			WebApiResponse putResponse = client.updateProject(id, update);

			final WebApiResponse updatedLoadResponse = client.loadById(id);
			Optional<ProjectView> updatedProjectOptional = updatedLoadResponse.getResponseData().stream().findFirst();

			ProjectView updatedProject = updatedProjectOptional.get();

			assertEquals(200, updatedLoadResponse.getLastStatusCode());
			assertEquals(1, updatedLoadResponse.getResponseData().size());

			assertEquals(422, putResponse.getLastStatusCode());
			assertEquals("TestProject", updatedProject.getName());
			assertEquals("This is a test Project", updatedProject.getDescription());
			assertEquals("2022ws", updatedProject.getSemester());
			assertEquals("testProject", updatedProject.getType());
			assertEquals(null, updatedProject.getStudents());
			assertEquals(null, updatedProject.getSupervisors());

			client.deleteProjectById(id);
		}


	@Test
	public void test_delete_with_existing_id_status204() throws IOException
	{
		final WebApiClient client = new WebApiClient();
		ProjectView project = new ProjectView("TestProject", "This is a test Project", "2022ws", "testProject");
		final WebApiResponse postResponse = client.postProject(project);
		final long id = postResponse.getIdFromHeaderString();

		final WebApiResponse deleteResponse = client.deleteProjectById(id);
		final WebApiResponse loadResponse = client.loadById(id);

		Optional<ProjectView> gotProjectOpt = loadResponse.getResponseData().stream().findFirst();

		assertEquals(204, deleteResponse.getLastStatusCode());
		assertEquals(404, loadResponse.getLastStatusCode());
		assertEquals(0, loadResponse.getResponseData().size());
	}

	@Test
	public void test_delete_with_non_existing_id_status204() throws IOException
	{
		final WebApiClient client = new WebApiClient();
		final long id = 1l;
		final WebApiResponse deleteResponse = client.deleteProjectById(id);
		final WebApiResponse loadResponse = client.loadById(id);

		Optional<ProjectView> gotProjectOpt = loadResponse.getResponseData().stream().findFirst();

		assertEquals(204, deleteResponse.getLastStatusCode());
		assertEquals(404, loadResponse.getLastStatusCode());
		assertEquals(0, loadResponse.getResponseData().size());
	}

}

 */
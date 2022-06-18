
package de.fhws.fiw.fds.sutton;

import de.fhws.fiw.fds.sutton.client.WebApiClient;
import de.fhws.fiw.fds.sutton.client.WebApiResponse;
import de.fhws.fiw.fds.sutton.models.Student;
import de.fhws.fiw.fds.sutton.models.StudyTrip;
import okhttp3.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestYourApi
	{
		@BeforeAll
		public void before() throws IOException {
			final WebApiClient client = new WebApiClient( );

			ArrayList<StudyTrip> trips = new ArrayList<>(Arrays.asList(new StudyTrip("TestTrip0", LocalDate.parse("1999-12-01"), LocalDate.parse("2000-01-01"), "FHWS", "Wuerzburg", "Germany"),
					new StudyTrip("TestTrip1", LocalDate.parse("2000-01-01"), LocalDate.parse("2000-02-01"), "FHWS", "Wuerzburg", "Germany"),
					new StudyTrip("TestTrip2", LocalDate.parse("2000-02-01"), LocalDate.parse("2000-03-01"), "FHWS", "Frankfurt", "Germany"),
					new StudyTrip("TestTrip3", LocalDate.parse("2000-03-01"), LocalDate.parse("2000-04-01"), "FHWS", "Wuerzburg", "Germany"),
					new StudyTrip("TestTrip4", LocalDate.parse("2000-04-01"), LocalDate.parse("2000-05-01"), "FHWS", "Wuerzburg", "Germany"),
					new StudyTrip("TestTrip5", LocalDate.parse("2000-05-01"), LocalDate.parse("2000-06-01"), "FHWS", "Wuerzburg", "Germany"),
					new StudyTrip("TestTrip6", LocalDate.parse("2000-06-01"), LocalDate.parse("2000-07-01"), "FHWS", "Wuerzburg", "Germany"),
					new StudyTrip("TestTrip7", LocalDate.parse("2000-07-01"), LocalDate.parse("2000-08-01"), "FHWS", "Wuerzburg", "Germany"),
					new StudyTrip("TestTrip8", LocalDate.parse("2000-08-01"), LocalDate.parse("2000-09-01"), "FHWS", "Wuerzburg", "Germany"),
					new StudyTrip("TestTrip9", LocalDate.parse("2000-09-01"), LocalDate.parse("2000-10-01"), "FHWS", "Wuerzburg", "Germany"),
					new StudyTrip("TestTrip10", LocalDate.parse("2000-09-01"), LocalDate.parse("2000-10-01"), "FHWS", "Wuerzburg", "Germany")));

			for(StudyTrip t : trips)
			{
				client.callDispatcher();
				client.getStudyTripsByAttributes("", "", "", "", "", 0);
				client.postStudyTrip(t);
			}

			ArrayList<Student> students = new ArrayList<>(Arrays.asList(new Student(2,"Test", "Student", "BIN", 4, 5120011, "test@gmail.com"),
					new Student(2,"Test", "Student", "BIN", 4, 5120007, "test@gmail.com"),
					new Student(2,"Test", "Student", "BIN", 4, 5120008, "test@gmail.com"),
					new Student(2,"Test", "Student", "BIN", 4, 5120009, "test@gmail.com"),
					new Student(2,"Test", "Student", "BIN", 4, 5120006, "test@gmail.com"),
					new Student(2,"Test", "Student", "BIN", 4, 5120005, "test@gmail.com"),
					new Student(2,"Test", "Student", "BIN", 4, 5120004, "test@gmail.com"),
					new Student(2,"Test", "Student", "BIN", 4, 5120003, "test@gmail.com"),
					new Student(2,"Test", "Student", "BIN", 4, 5120002, "test@gmail.com"),
					new Student(2,"Test", "Student", "BIN", 4, 5120001, "test@gmail.com"),
					new Student(2,"Test", "Student", "BIN", 4, 5120012, "test@gmail.com")));

			for(Student t : students)
			{
				System.out.println(t.toString());
				client.callDispatcher();
				client.getAllStudents(0);
				client.postStudent(t);
			}
		}


		@AfterAll
		public void after() throws IOException {
			final WebApiClient client = new WebApiClient( );
			client.callDispatcher();

			Collection<StudyTrip> trips = client.getStudyTripsByAttributes("", "", "", "", "", 0).values().stream().findFirst().get();
			for(StudyTrip t : trips)
			{
				client.callDispatcher();
				client.getStudyTripsByAttributes("", "", "", "", "", 0);
				client.getSingleStudyTripByID(t.getId());
				client.deleteStudyTripById(t.getId());
			}
		}

		@Test
		public void testPagingOfStudyTrips( ) throws IOException
		{
			final WebApiClient client = new WebApiClient( );
			client.callDispatcher();

			Map<Response, ArrayList<StudyTrip>> getAllResponse = client.getStudyTripsByAttributes("", "", "", "", "", 0);
			Response response = getAllResponse.keySet().stream().findFirst().get();
			ArrayList<StudyTrip> trips = getAllResponse.get(response);

			assertEquals( 200, response.code() );
			assertEquals( 10, trips.size( ) );

			System.out.println(client.lastResponseHyperLinks.values());
			client.callDispatcher();
			System.out.println(client.lastResponseHyperLinks.values());

			trips = client.getStudyTripsByAttributes("", "", "", "", "", 2).values().stream().findFirst().get();

			assertEquals( 200, response.code() );
			assertEquals( 1, trips.size( ) );
		}

		@Test
		public void testPagingOfStudents( ) throws IOException
		{
			final WebApiClient client = new WebApiClient( );
			client.callDispatcher();

			Map<Response, ArrayList<Student>> getAllResponse = client.getAllStudents(0);
			Response response = getAllResponse.keySet().stream().findFirst().get();
			ArrayList<Student> students = getAllResponse.get(response);

			assertEquals( 200, response.code() );
			assertEquals( 10, students.size( ) );

			System.out.println(client.lastResponseHyperLinks.values());
			client.callDispatcher();
			System.out.println(client.lastResponseHyperLinks.values());

			students = client.getAllStudents(2).values().stream().findFirst().get();

			assertEquals( 200, response.code() );
			assertEquals( 1, students.size( ) );
		}


}


package de.fhws.fiw.fds.sutton.client;

import de.fhws.fiw.fds.sutton.models.StudyTrip;

import java.io.IOException;
import java.time.LocalDate;

public class MyClientTest {
    public static void main(String[] args) throws IOException {
        WebApiClient client = new WebApiClient();
        StudyTrip testTrip = new StudyTrip("TestTrip", LocalDate.parse("2022-12-12"), LocalDate.parse("2023-12-12"), "JMU", "Wuerzburg", "Germany");
        WebApiResponse response = client.callDispatcher();
        client.postStudyTrip(testTrip);

    }
}

package de.fhws.fiw.fds.exam02;

import com.owlike.genson.Genson;
import de.fhws.fiw.fds.sutton.server.models.StudyTrip;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

public class test {
    public static void main(String[] args) throws IOException {
       /*  final Genson genson = new Genson();
         final OkHttpClient client = new OkHttpClient();
         final MediaType MEDIA_TYPE_JSON = MediaType.parse( "application/json" );
         final String URL = "http://localhost:8080/exam2/api/studyTrips";

        StudyTrip studyTrip = new StudyTrip((long) 10, "TestTrip", LocalDate.of(2020, 1, 1), LocalDate.of(2022, 1, 1), "FHWS", "Wuerzburg", "Germany", new HashSet<Long>());

        String json = genson.serialize(studyTrip);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);

        final Request request = new Request.Builder( )
                .url( URL )
                .post( body)
                .build( );

        Response response = client.newCall( request ).execute( );

        */

        System.out.println(LocalDate.of(2000, 1,1));


    }
}

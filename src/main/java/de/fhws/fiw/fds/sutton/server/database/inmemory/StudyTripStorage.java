package de.fhws.fiw.fds.sutton.server.database.inmemory;

/*


import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.models.StudyTrip;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudyTripStorage extends AbstractInMemoryStorage
{
    private static StudyTripStorage INSTANCE;

    public static StudyTripStorage getInstance( )
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new StudyTripStorage( );
        }

        return INSTANCE;
    }

    public Collection<StudyTrip> findByName(final String name)
    {
        return (Collection<StudyTrip>) readByPredicate(byName(name));
    }

    public Collection<StudyTrip> findByStartAndEndDate(final String startDate, final String endDate)
    {
        Collection<StudyTrip> firstRes = (Collection<StudyTrip>) readByPredicate(byStartDate(LocalDateTime.from(LocalDate.parse(startDate))));  // ??
        Collection<StudyTrip> secondRes = (Collection<StudyTrip>) readByPredicate(byStartDate(LocalDateTime.from(LocalDate.parse(endDate))));  // ??

        firstRes.stream().filter(t -> !secondRes.contains(t)).forEach(t -> secondRes.add(t));
        return (Collection<StudyTrip>) secondRes;
    }

    public Collection<StudyTrip> findByCity(final String city)
    {
        return (Collection<StudyTrip>) readByPredicate(byCity(city));
    }

    public Collection<StudyTrip> findByCountry(final String country)
    {
        return (Collection<StudyTrip>) readByPredicate(byCountry(country));
    }

    private Predicate<StudyTrip> byName(final String name )
    {
        return trip -> StringUtils.isEmpty( name ) || trip.getName( ).contains( name );
    }

    private Predicate<StudyTrip> byStartDate( final LocalDateTime startDate)
    {
        return trip -> startDate == null || trip.getFirstDate().isBefore(startDate) || trip.getFirstDate().isEqual(startDate);
    }

    private Predicate<StudyTrip> byEndDate(final LocalDateTime endDate )
    {
        return trip -> endDate == null || trip.getLastDate().isAfter(endDate) || trip.getLastDate().isEqual(endDate);
    }

    private Predicate<StudyTrip> byCity( final String city )
    {
        return trip -> StringUtils.isEmpty( city ) || trip.getCity().contains( city );
    }

    private Predicate<StudyTrip> byCountry( final String country )
    {
        return trip -> StringUtils.isEmpty( country ) || trip.getCountry().contains( country );
    }

}
*/
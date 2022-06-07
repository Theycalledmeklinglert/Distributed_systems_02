package de.fhws.fiw.fds.sutton.server.database;

import de.fhws.fiw.fds.sutton.server.models.Student;

import java.util.Set;

public interface StudentDao extends IDatabaseAccessObject<Student>
{
    public Set<Long> getImmatricNums();

}


package de.fhws.fiw.fds.exam02.database;

import de.fhws.fiw.fds.exam02.database.models.Student;

import java.util.Set;

public interface StudentDao extends IDatabaseAccessObject<Student>
{
    public Set<Long> getImmatricNums();

}


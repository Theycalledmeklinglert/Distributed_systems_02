package de.fhws.fiw.fds.exam02.server.database.models;

import de.fhws.fiw.fds.exam02.server.database.DaoFactory;

public class ValidityCheck {

    public static boolean checkStudyTrip(StudyTrip studyTrip)
    {
        if(isMissingAttributes(studyTrip))
        {
            return false;
        }
        if(isLetter(studyTrip.getName()) && isLetter(studyTrip.getPartnerUni()) && isLetter(studyTrip.getCity()) && isLetter(studyTrip.getCountry()))
        {
            if(studyTrip.getFirstDate().isBefore(studyTrip.getLastDate()) || studyTrip.getFirstDate().isEqual(studyTrip.getLastDate()))
                {
                    return true;
                }
        }
        return false;
    }


    public static boolean checkStudent(Student student) {
        if(isMissingAttributes(student))
        {
            return false;
        }
        if(isLetter(student.getFirstname()) && isLetter(student.getLastname()) && isLetter(student.getCourse()) && isValidImmatricNum(student.getImmatricNum()) && isValidEMail(student.getEmail()))
        {
            if(student.getSemester() > 0 && student.getSemester() < 8)
                    {
                        if(isUniqueImmatricNum(student.getImmatricNum()))
                        {
                            return true;
                        }
                    }
                }
        return false;
    }

    private static boolean isMissingAttributes(StudyTrip studyTrip)
    {
        return studyTrip.getName() == null || studyTrip.getFirstDate() == null || studyTrip.getLastDate() == null || studyTrip.getPartnerUni() == null || studyTrip.getCity() == null || studyTrip.getCountry() == null;
    }
    private static boolean isMissingAttributes(Student student)
    {
        return student.getFirstname() == null || student.getLastname() == null || student.getCourse() == null || student.getSemester() == 0 || student.getImmatricNum() == 0 || student.getEmail() == null;
    }

    private static boolean isValidImmatricNum(Long immatricNum)
    {
        return String.valueOf(immatricNum).length() >= 7;
    }

    private static boolean isLetter(String string)
    {
        return string.matches("^[\\s A-Za-z0-9]+$");
    }

    private static boolean isValidEMail(String eMail)
    {
        return eMail.matches("[a-zA-Z0-9.]+[@][a-zA-Z0-9]+[.][a-zA-Z0-9]+");
    }

    private static boolean isUniqueImmatricNum(long immatricNum)
    {
        return !DaoFactory.getInstance().getStudentDao().getImmatricNums().contains(immatricNum);
    }

}

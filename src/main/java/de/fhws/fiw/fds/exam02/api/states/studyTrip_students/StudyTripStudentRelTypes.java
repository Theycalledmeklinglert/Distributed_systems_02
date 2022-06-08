package de.fhws.fiw.fds.exam02.api.states.studyTrip_students;

public interface StudyTripStudentRelTypes
{
	String CREATE_STUDENT = "createStudentOfStudyTrip";
	String GET_ALL_LINKED_STUDENTS = "getAllStudentsOfStudyTrip";
	String GET_ALL_STUDENTS = "getAllLinkableStudents";
	String UPDATE_SINGLE_STUDENT = "updateStudentsOfStudyTrip";
	String CREATE_LINK_FROM_STUDYTRIP_TO_STUDENT = "linkStudyTripToStudent";
	String DELETE_LINK_FROM_STUDYTRIP_TO_STUDENT = "unlinkStudyTripToStudent";
	String GET_SINGLE_STUDENT = "getStudentOfStudyTrip";
}

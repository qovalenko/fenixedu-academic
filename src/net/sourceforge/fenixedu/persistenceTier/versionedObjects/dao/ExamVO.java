/*
 * ExamOJB.java
 *
 * Created on 2003/03/19
 */

package net.sourceforge.fenixedu.persistenceTier.versionedObjects.dao;

/**
 * 
 * @author Luis Cruz & Sara Ribeiro
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.sourceforge.fenixedu.domain.Exam;
import net.sourceforge.fenixedu.domain.ExecutionYear;
import net.sourceforge.fenixedu.domain.IAttends;
import net.sourceforge.fenixedu.domain.IEvaluation;
import net.sourceforge.fenixedu.domain.IExam;
import net.sourceforge.fenixedu.domain.IExecutionCourse;
import net.sourceforge.fenixedu.domain.IExecutionPeriod;
import net.sourceforge.fenixedu.domain.IExecutionYear;
import net.sourceforge.fenixedu.domain.IRoom;
import net.sourceforge.fenixedu.domain.IRoomOccupation;
import net.sourceforge.fenixedu.domain.IStudent;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.IPersistentExam;
import net.sourceforge.fenixedu.persistenceTier.versionedObjects.VersionedObjectsBase;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class ExamVO extends VersionedObjectsBase implements IPersistentExam {

    public Collection readAll(final Class clazz) {
        final Collection evaluations = super.readAll(clazz);
        CollectionUtils.filter(evaluations, new Predicate() {
            public boolean evaluate(Object arg0) {
                final IEvaluation evaluation = (IEvaluation) arg0;
                return evaluation.getOjbConcreteClass().equals(Exam.class.getName());
            }});
        return evaluations;
    }

    public List readBy(Calendar day, Calendar beginning) throws ExcepcaoPersistencia {

        List<IExam> exams = (List<IExam>) readAll(Exam.class);
        List<IExam> result = new ArrayList();
        for (IExam exam : exams) {
            if (exam.getBeginning().equals(beginning) && exam.getDay().equals(day)) {
                result.add(exam);
            }

        }
        return result;
    }

    public List readByRoomAndExecutionPeriod(String roomName, String executionPeriodName, String year)
            throws ExcepcaoPersistencia {
        List<IExecutionYear> executionYears = (List<IExecutionYear>) readAll(ExecutionYear.class);
        IExecutionYear executionYearResult = null;
        IExecutionPeriod executionPeriodResult = null;

        for (IExecutionYear executionYear : executionYears) {
            if (executionYear.getYear().equals(year)) {
                executionYearResult = executionYear;
                break;
            }
        }

        List<IExecutionPeriod> executionPeriods = executionYearResult.getExecutionPeriods();
        for (IExecutionPeriod executionPeriod : executionPeriods) {
            if (executionPeriod.getName().equalsIgnoreCase(executionPeriodName)) {
                executionPeriodResult = executionPeriod;
                break;
            }
        }

        List<IExecutionCourse> executionCourses = executionPeriodResult.getAssociatedExecutionCourses();
        List<IExam> result = new ArrayList();

        HashSet hashSet = new HashSet(75);

        for (IExecutionCourse executionCourse : executionCourses) {
            List<IExam> exams = new ArrayList();
            List<IEvaluation> associatedEvaluations = executionCourse.getAssociatedEvaluations();
            for(IEvaluation evaluation : associatedEvaluations){
                if (evaluation instanceof Exam){
                    exams.add((IExam) evaluation);
                }
            }
            for (IExam exam : exams) {
                List<IRoom> rooms = exam.getAssociatedRooms();
                for (IRoom room : rooms) {
                    if (room.getNome().equalsIgnoreCase(roomName)) {

                        if (!hashSet.contains(exam.getIdInternal())) {
                            result.add(exam);
                            hashSet.add(exam.getIdInternal());
                        }

                    }
                }
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ServidorPersistente.IPersistentExam#readBy(java.util.Calendar,
     *      java.util.Calendar, java.util.Calendar)
     */
    public List readBy(Calendar day, Calendar beginning, Calendar end) throws ExcepcaoPersistencia {
        List<IExam> exams = (List<IExam>) readAll(Exam.class);
        List<IExam> result = new ArrayList();
        if (beginning != null) {
            if (end != null) {
                for (IExam exam : exams) {
                    if (exam.getBeginning().equals(beginning) && exam.getDay().equals(day)
                            && exam.getEnd().equals(end)) {
                        result.add(exam);
                    }
                }
            } else {
                for (IExam exam : exams) {
                    if (exam.getBeginning().equals(beginning) && exam.getDay().equals(day)) {
                        result.add(exam);
                    }
                }
            }
        } else {
            if (end != null) {
                for (IExam exam : exams) {
                    if (exam.getDay().equals(day) && exam.getEnd().equals(end)) {
                        result.add(exam);
                    }
                }
            } else {
                for (IExam exam : exams) {
                    if (exam.getDay().equals(day)) {
                        result.add(exam);
                    }
                }
            }
        }
        return result;

    }

    public List readByRoomAndWeek(String roomName, Calendar day) throws ExcepcaoPersistencia {
        List<IExam> result = new ArrayList();
        List<IExam> exams = (List<IExam>) readAll(Exam.class);

        // day.add(Calendar.DATE, Calendar.MONDAY -
        // day.get(Calendar.DAY_OF_WEEK));
        Calendar endDay = Calendar.getInstance();
        endDay.setTime(day.getTime());
        endDay.add(Calendar.DATE, 6);

        for (IExam exam : exams) {
            if (!(exam.getDay().before(day) || exam.getDay().after(endDay))) {
                List<IRoomOccupation> roomOccupations = exam.getAssociatedRoomOccupation();
                for (IRoomOccupation roomOccupation : roomOccupations) {
                    if (roomOccupation.getRoom().getNome().equals(roomName)) {
                        result.add(exam);

                    }
                }
            }
        }
        return result;
    }

    public boolean isExamOfExecutionCourseTheStudentAttends(Integer examOID, String studentsUsername)
            throws ExcepcaoPersistencia {
        IExam exam = (IExam) readByOID(Exam.class, examOID);

        for (IExecutionCourse executionCourse : exam.getAssociatedExecutionCourses()) {
            for (final IAttends attends : executionCourse.getAttends()) {
                final IStudent student = attends.getAluno();
                if (student.getPerson().getUsername().equals(studentsUsername)) {
                    return true;
                }
            }

        }
        return false;
    }

}
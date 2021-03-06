/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Academic.
 *
 * FenixEdu Academic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Academic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.academic.ui.faces.bean.student.enrolment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.fenixedu.academic.domain.Evaluation;
import org.fenixedu.academic.domain.Exam;
import org.fenixedu.academic.domain.ExecutionCourse;
import org.fenixedu.academic.domain.ExecutionSemester;
import org.fenixedu.academic.domain.WrittenTest;
import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.domain.student.Registration;
import org.fenixedu.academic.dto.InfoExecutionPeriod;
import org.fenixedu.academic.predicate.IllegalDataAccessException;
import org.fenixedu.academic.service.services.commons.ReadCurrentExecutionPeriod;
import org.fenixedu.academic.service.services.commons.ReadNotClosedExecutionPeriods;
import org.fenixedu.academic.service.services.exceptions.FenixServiceException;
import org.fenixedu.academic.service.services.student.enrolment.ReadStudentByUsername;
import org.fenixedu.academic.ui.faces.bean.base.FenixBackingBean;
import org.fenixedu.academic.util.Bundle;
import org.fenixedu.bennu.core.i18n.BundleUtil;

import pt.ist.fenixframework.FenixFramework;

public class DisplayEvaluationsForStudentToEnrol extends FenixBackingBean {

    private static final ComparatorChain comparatorChain = new ComparatorChain();
    static {
        comparatorChain.addComparator(new ReverseComparator(new BeanComparator("isInEnrolmentPeriod")));
        comparatorChain.addComparator(new BeanComparator("dayDate"));
    }

    protected static final Integer ALL = Integer.valueOf(0);
    protected static final Integer EXAMS = Integer.valueOf(1);
    protected static final Integer WRITTENTESTS = Integer.valueOf(2);

    private String executionPeriodID;
    protected Integer evaluationType;
    private ExecutionSemester executionSemester;
    private List<SelectItem> executionPeriodsLabels;
    private List<SelectItem> evaluationTypes;
    private Registration student;
    private List<Evaluation> allNotEnroledEvaluations;
    private List<Evaluation> notEnroledEvaluations;
    private List<Evaluation> enroledEvaluations;
    private List<Evaluation> evaluationsWithoutEnrolmentPeriod;
    private Map<String, List<ExecutionCourse>> executionCourses;

    public List<SelectItem> getExecutionPeriodsLabels() {
        if (this.executionPeriodsLabels == null) {
            this.executionPeriodsLabels = new ArrayList();

            final List<InfoExecutionPeriod> infoExecutionPeriods = getExecutionPeriods();
            final ComparatorChain comparatorChain = new ComparatorChain();
            comparatorChain.addComparator(new ReverseComparator(new BeanComparator("infoExecutionYear.year")));
            comparatorChain.addComparator(new ReverseComparator(new BeanComparator("semester")));
            Collections.sort(infoExecutionPeriods, comparatorChain);
            for (final InfoExecutionPeriod infoExecutionPeriod : infoExecutionPeriods) {
                final SelectItem selectItem = new SelectItem();
                selectItem.setValue(infoExecutionPeriod.getExternalId());
                selectItem.setLabel(infoExecutionPeriod.getName() + " - " + infoExecutionPeriod.getInfoExecutionYear().getYear());
                this.executionPeriodsLabels.add(selectItem);
            }
        }
        return this.executionPeriodsLabels;
    }

    public List<SelectItem> getEvaluationTypes() {
        if (this.evaluationTypes == null) {
            this.evaluationTypes = new ArrayList(4);
            final String allEvaluations = BundleUtil.getString(Bundle.STUDENT, "link.all");
            evaluationTypes.add(new SelectItem(ALL, allEvaluations));
            final String exams = BundleUtil.getString(Bundle.STUDENT, "link.exams.enrolment");
            evaluationTypes.add(new SelectItem(EXAMS, exams));
            final String writtenTests = BundleUtil.getString(Bundle.STUDENT, "link.writtenTests.enrolment");
            evaluationTypes.add(new SelectItem(WRITTENTESTS, writtenTests));
        }
        return this.evaluationTypes;
    }

    public List<Evaluation> getAllNotEnroledEvaluations() {
        if (this.allNotEnroledEvaluations == null) {
            this.allNotEnroledEvaluations = new ArrayList();

            processAllEvaluations();
        }
        return this.allNotEnroledEvaluations;
    }

    public List<Evaluation> getNotEnroledEvaluations() {
        if (this.notEnroledEvaluations == null) {
            this.notEnroledEvaluations = new ArrayList();

            processNotEnroledEvaluations();
        }
        return this.notEnroledEvaluations;
    }

    public void setNotEnroledEvaluations(List<Evaluation> notEnroledEvaluations) {
        this.notEnroledEvaluations = notEnroledEvaluations;
    }

    public List<Evaluation> getEnroledEvaluations() {
        if (this.enroledEvaluations == null) {
            this.enroledEvaluations = new ArrayList();
            processEnroledEvaluations();
        }
        return this.enroledEvaluations;
    }

    public void setEnroledEvaluations(List<Evaluation> enroledEvaluations) {
        this.enroledEvaluations = enroledEvaluations;
    }

    public List<Evaluation> getEvaluationsWithoutEnrolmentPeriod() {
        if (this.evaluationsWithoutEnrolmentPeriod == null) {
            this.evaluationsWithoutEnrolmentPeriod = new ArrayList();
        }
        return this.evaluationsWithoutEnrolmentPeriod;
    }

    public void setEvaluationsWithoutEnrolmentPeriod(List<Evaluation> evaluationsWithoutEnrolmentPeriod) {
        this.evaluationsWithoutEnrolmentPeriod = evaluationsWithoutEnrolmentPeriod;
    }

    private void processEnroledEvaluations() {
        if (getEvaluationType().equals(ALL) || getEvaluationType().equals(EXAMS)) {
            for (final Registration registration : getStudent().getStudent().getRegistrationsSet()) {
                for (final Exam exam : registration.getEnroledExams(getExecutionPeriod())) {
                    if (exam.isExamsMapPublished()) {
                        try {
                            exam.isInEnrolmentPeriod();
                            this.enroledEvaluations.add(exam);
                        } catch (final DomainException e) {
                            getEvaluationsWithoutEnrolmentPeriod().add(exam);
                        } finally {
                            getExecutionCourses().put(exam.getExternalId(), exam.getAttendingExecutionCoursesFor(registration));
                        }
                    }
                }
            }
        }
        if (getEvaluationType().equals(ALL) || getEvaluationType().equals(WRITTENTESTS)) {
            for (final Registration registration : getStudent().getStudent().getRegistrationsSet()) {
                for (final WrittenTest writtenTest : registration.getEnroledWrittenTests(getExecutionPeriod())) {
                    try {
                        writtenTest.isInEnrolmentPeriod();
                        this.enroledEvaluations.add(writtenTest);
                    } catch (final DomainException e) {
                        getEvaluationsWithoutEnrolmentPeriod().add(writtenTest);
                    } finally {
                        getExecutionCourses().put(writtenTest.getExternalId(),
                                writtenTest.getAttendingExecutionCoursesFor(registration));
                    }
                }
            }
        }
        Collections.sort(this.enroledEvaluations, comparatorChain);
    }

    private void processAllEvaluations() {
        if (getEvaluationType().equals(ALL) || getEvaluationType().equals(EXAMS)) {
            for (final Registration registration : getStudent().getStudent().getRegistrationsSet()) {
                for (final Exam exam : registration.getUnenroledExams(getExecutionPeriod())) {
                    if (exam.isExamsMapPublished()) {
                        this.allNotEnroledEvaluations.add(exam);
                        getExecutionCourses().put(exam.getExternalId(), exam.getAttendingExecutionCoursesFor(registration));
                    }
                }
            }
        }
        if (getEvaluationType().equals(ALL) || getEvaluationType().equals(WRITTENTESTS)) {
            for (final Registration registration : getStudent().getStudent().getRegistrationsSet()) {
                for (final WrittenTest writtenTest : registration.getUnenroledWrittenTests(getExecutionPeriod())) {

                    this.allNotEnroledEvaluations.add(writtenTest);
                    getExecutionCourses().put(writtenTest.getExternalId(),
                            writtenTest.getAttendingExecutionCoursesFor(registration));

                }
            }
        }
        Collections.sort(this.allNotEnroledEvaluations, comparatorChain);
    }

    private void processNotEnroledEvaluations() {
        if (getEvaluationType().equals(ALL) || getEvaluationType().equals(EXAMS)) {
            for (final Registration registration : getStudent().getStudent().getRegistrationsSet()) {
                for (final Exam exam : registration.getUnenroledExams(getExecutionPeriod())) {
                    if (exam.isExamsMapPublished()) {
                        try {
                            exam.isInEnrolmentPeriod();
                            this.notEnroledEvaluations.add(exam);
                        } catch (final DomainException e) {
                            getEvaluationsWithoutEnrolmentPeriod().add(exam);
                        } finally {
                            getExecutionCourses().put(exam.getExternalId(), exam.getAttendingExecutionCoursesFor(registration));
                        }
                    }
                }
            }
        }
        if (getEvaluationType().equals(ALL) || getEvaluationType().equals(WRITTENTESTS)) {
            for (final Registration registration : getStudent().getStudent().getRegistrationsSet()) {
                for (final WrittenTest writtenTest : registration.getUnenroledWrittenTests(getExecutionPeriod())) {
                    try {
                        writtenTest.isInEnrolmentPeriod();
                        this.notEnroledEvaluations.add(writtenTest);
                    } catch (final DomainException e) {
                        getEvaluationsWithoutEnrolmentPeriod().add(writtenTest);
                    } finally {
                        getExecutionCourses().put(writtenTest.getExternalId(),
                                writtenTest.getAttendingExecutionCoursesFor(registration));
                    }
                }
            }
        }
        Collections.sort(this.notEnroledEvaluations, comparatorChain);
    }

    public void changeExecutionPeriod(ValueChangeEvent event) {
        clearAttributes();
    }

    public void changeEvaluationType(ValueChangeEvent event) {
        clearAttributes();
    }

    protected void clearAttributes() {
        setNotEnroledEvaluations(null);
        setEnroledEvaluations(null);
        setEvaluationsWithoutEnrolmentPeriod(null);
        setExecutionCourses(null);
    }

    private List<InfoExecutionPeriod> getExecutionPeriods() {
        try {

            return ReadNotClosedExecutionPeriods.run();
        } catch (FenixServiceException e) {
        }
        return new ArrayList();
    }

    private InfoExecutionPeriod getCurrentExecutionPeriod() {
        return ReadCurrentExecutionPeriod.run();
    }

    protected ExecutionSemester getExecutionPeriod() {
        return executionSemester == null && getExecutionPeriodID() != null ? FenixFramework
                .<ExecutionSemester> getDomainObject(getExecutionPeriodID()) : executionSemester;
    }

    protected Registration getStudent() {
        if (this.student == null) {
            try {

                this.student = ReadStudentByUsername.run(getUserView().getUsername());
            } catch (IllegalDataAccessException e) {
            } catch (FenixServiceException e) {
            }
        }
        return this.student;
    }

    public String getExecutionPeriodID() {
        if (getRequestParameter("executionPeriodID") != null) {
            this.executionPeriodID = getRequestParameter("executionPeriodID");
        }
        if (this.executionPeriodID == null) {
            this.executionPeriodID = getCurrentExecutionPeriod().getExternalId();
        }
        return executionPeriodID;
    }

    public void setExecutionPeriodID(String executionPeriodID) {
        this.executionPeriodID = executionPeriodID;
    }

    public Integer getEvaluationType() {
        if (this.evaluationType == null) {
            this.evaluationType = ALL;
        }
        return this.evaluationType;
    }

    public String getEvaluationTypeString() {
        final Integer type = getEvaluationType();
        if (type != null && type.equals(EXAMS)) {
            return "org.fenixedu.academic.domain.Exam";
        } else if (type != null && type.equals(WRITTENTESTS)) {
            return "org.fenixedu.academic.domain.WrittenTest";
        }
        return "";
    }

    public void setEvaluationType(Integer evaluationType) {
        this.evaluationType = evaluationType;
    }

    public Map<String, List<ExecutionCourse>> getExecutionCourses() {
        if (this.executionCourses == null) {
            this.executionCourses = new HashMap<String, List<ExecutionCourse>>();
        }
        return this.executionCourses;
    }

    public void setExecutionCourses(Map<String, List<ExecutionCourse>> executionCourses) {
        this.executionCourses = executionCourses;
    }
}
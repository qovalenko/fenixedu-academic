// Generated by impart OJB Generator
// www.impart.ch matthias.roth@impart.ch
// created at 30 Sep 2003 12:17:16 GMT

package middleware.middlewareDomain;


public class MWEnrolment extends MWDomainObject
{
  private Integer branchcode;
  private String coursecode;
  private Integer curricularcoursesemester;
  private Integer curricularcourseyear;
  private Integer degreecode;
  private Integer enrolmentyear;
  private java.sql.Date examdate;
  private String grade;
  private Integer idinternal;
  private Integer number;
  private String remarks;
  private Integer season;
  private Integer teachernumber;
  private String universitycode;



  public Integer getBranchcode()
  {
     return this.branchcode;
  }
  public void setBranchcode(Integer param)
  {
    this.branchcode = param;
  }



  public Integer getCurricularcoursesemester()
  {
     return this.curricularcoursesemester;
  }
  public void setCurricularcoursesemester(Integer param)
  {
    this.curricularcoursesemester = param;
  }


  public Integer getCurricularcourseyear()
  {
     return this.curricularcourseyear;
  }
  public void setCurricularcourseyear(Integer param)
  {
    this.curricularcourseyear = param;
  }


  public Integer getDegreecode()
  {
     return this.degreecode;
  }
  public void setDegreecode(Integer param)
  {
    this.degreecode = param;
  }


  public Integer getEnrolmentyear()
  {
     return this.enrolmentyear;
  }
  public void setEnrolmentyear(Integer param)
  {
    this.enrolmentyear = param;
  }


  public java.sql.Date getExamdate()
  {
     return this.examdate;
  }
  public void setExamdate(java.sql.Date param)
  {
    this.examdate = param;
  }


  public String getGrade()
  {
     return this.grade;
  }
  public void setGrade(String param)
  {
    this.grade = param;
  }


  public Integer getIdinternal()
  {
     return this.idinternal;
  }
  public void setIdinternal(Integer param)
  {
    this.idinternal = param;
  }


  public Integer getNumber()
  {
     return this.number;
  }
  public void setNumber(Integer param)
  {
    this.number = param;
  }


  public String getRemarks()
  {
     return this.remarks;
  }
  public void setRemarks(String param)
  {
    this.remarks = param;
  }


  public Integer getSeason()
  {
     return this.season;
  }
  public void setSeason(Integer param)
  {
    this.season = param;
  }


  public Integer getTeachernumber()
  {
     return this.teachernumber;
  }
  public void setTeachernumber(Integer param)
  {
    this.teachernumber = param;
  }


  public String getUniversitycode()
  {
     return this.universitycode;
  }
  public void setUniversitycode(String param)
  {
    this.universitycode = param;
  }


  public String toString(){
	StringBuffer stringBuffer = new StringBuffer();
	stringBuffer.append("\n")
				.append(this.getClass().getName())
				.append(":\n")
				.append("IdInternal: [")
				.append(idinternal)
				.append("]\n")
				.append("Number: [")
				.append(number)
				.append("]\n")
				.append("BranchCode: [")
				.append(branchcode)
				.append("]\n")
				.append("CourseCode: [")
				.append(coursecode)
				.append("]\n")
				.append("CurricularCourseSemester: [")
				.append(curricularcoursesemester)
				.append("]\n")
				.append("CurricularCourseYear: [")
				.append(curricularcourseyear)
				.append("]\n")
				.append("DegreeCode: [")
				.append(degreecode)
				.append("]\n")
				.append("EnrolmentYear: [")
				.append(enrolmentyear)
				.append("]\n")
				.append("ExamDate: [")
				.append(examdate)
				.append("]\n")
				.append("Grade: [")
				.append(grade)
				.append("]\n")
				.append("Remarks: [")
				.append(remarks)
				.append("]\n")
				.append("Season: [")
				.append(season)
				.append("]\n")
				.append("TeacherNumber: [")
				.append(teachernumber)
				.append("]\n")
				.append("UniversityCode: [")
				.append(universitycode)
				.append("]\n");

	return stringBuffer.toString();
//    return  " [branchCode] " + branchcode + " [courseCode] " + coursecode + " [curricularCourseSemester] " + curricularcoursesemester + " [curricularCourseYear] " + curricularcourseyear + " [degreeCode] " + degreecode + " [enrolmentYear] " + enrolmentyear + " [examDate] " + examdate + " [grade] " + grade + " [idinternal] " + idinternal + " [number] " + number + " [remarks] " + remarks + " [season] " + season + " [teacherNumber] " + teachernumber + " [universityCode] " + universitycode;
  }
  
  public String toFlatString(){
	StringBuffer stringBuffer = new StringBuffer();
	stringBuffer.append("Number: [")
				.append(number)
				.append("], ")
				.append("DegreeCode: [")
				.append(degreecode)
				.append("], ")
				.append("CourseCode: [")
				.append(coursecode)
				.append("], ")
				.append("BranchCode: [")
				.append(branchcode)
				.append("], ")
				.append("CurricularCourseSemester: [")
				.append(curricularcoursesemester)
				.append("], ")
				.append("CurricularCourseYear: [")
				.append(curricularcourseyear)
				.append("], ")
				.append("EnrolmentYear: [")
				.append(enrolmentyear)
				.append("], ")
				.append("ExamDate: [")
				.append(examdate)
				.append("], ")
				.append("Grade: [")
				.append(grade)
				.append("], ")
				.append("Remarks: [")
				.append(remarks)
				.append("], ")
				.append("Season: [")
				.append(season)
				.append("], ")
				.append("TeacherNumber: [")
				.append(teachernumber)
				.append("], ")
				.append("UniversityCode: [")
				.append(universitycode)
				.append("]");

	return stringBuffer.toString();
  }
  
	/**
	 * @return
	 */
	public String getCoursecode() {
		return coursecode;
	}
	
	/**
	 * @param coursecode
	 */
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

}

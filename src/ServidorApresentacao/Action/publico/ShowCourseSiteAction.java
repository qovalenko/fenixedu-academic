package ServidorApresentacao.Action.publico;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import DataBeans.InfoCurriculum;
import DataBeans.InfoExecutionPeriod;
import ServidorAplicacao.GestorServicos;
import ServidorAplicacao.Servico.exceptions.FenixServiceException;
import ServidorAplicacao.Servico.exceptions.NonExistingServiceException;
import ServidorApresentacao.Action.base.FenixContextDispatchAction;
import ServidorApresentacao.Action.exceptions.FenixActionException;
import ServidorApresentacao.Action.sop.utils.SessionConstants;

/**
 * @author T�nia Pous�o Create on 20/Nov/2003
 */
public class ShowCourseSiteAction extends FenixContextDispatchAction
{
    public ActionForward showCurricularCourseSite(
        ActionMapping mapping,
        ActionForm actionForm,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception
    {
        ActionErrors errors = new ActionErrors();

        HttpSession session = request.getSession(true);

        Integer executionPeriodOId = getFromRequest("executionPeriodOId", request);
        Integer degreeId = getFromRequest("degreeId", request);
        Integer curricularCourseId = getFromRequest("curricularCourseId", request);

        GestorServicos gestorServicos = GestorServicos.manager();
        Object[] args = { curricularCourseId, executionPeriodOId };

        InfoCurriculum infoCurriculum = null;
        try
        {
            infoCurriculum =
                (InfoCurriculum) gestorServicos.executar(
                    null,
                    "ReadCurriculumByCurricularCourseCode",
                    args);
        } catch (NonExistingServiceException e)
        {
            errors.add(
                "chosenCurricularCourse",
                new ActionError("error.coordinator.chosenCurricularCourse"));
            saveErrors(request, errors);
        } catch (FenixServiceException e)
        {
            if (e.getMessage().equals("nullCurricularCourse"))
            {
                errors.add("nullCode", new ActionError("error.coordinator.noCurricularCourse"));
                saveErrors(request, errors);
            } else
            {
                throw new FenixActionException(e);
            }
        }
        if (infoCurriculum == null)
        {
            errors.add("noCurriculum", new ActionError("error.coordinator.noCurriculum"));
            saveErrors(request, errors);
        }

        //order list of execution courses by name
        if (infoCurriculum.getInfoCurricularCourse() != null
            && infoCurriculum.getInfoCurricularCourse().getInfoAssociatedExecutionCourses() != null)
        {
            Collections.sort(
                infoCurriculum.getInfoCurricularCourse().getInfoAssociatedExecutionCourses(),
                new BeanComparator("nome"));
        }
        
        
		//order list by year, semester
		if (infoCurriculum.getInfoCurricularCourse() != null && infoCurriculum.getInfoCurricularCourse().getCurricularCourseExecutionScope() != null)
		{
			ComparatorChain comparatorChain = new ComparatorChain();
			comparatorChain.addComparator(
				new BeanComparator("infoCurricularSemester.infoCurricularYear.year"));
			comparatorChain.addComparator(new BeanComparator("infoCurricularSemester.semester"));
			comparatorChain.addComparator(new BeanComparator("infoCurricularCourse.name"));
			Collections.sort(infoCurriculum.getInfoCurricularCourse().getInfoScopes(), comparatorChain);
		}
        

        //		read execution period for display the school year
        Object[] args2 = { executionPeriodOId };

        InfoExecutionPeriod infoExecutionPeriod = null;
        try
        {
            infoExecutionPeriod =
                (InfoExecutionPeriod) gestorServicos.executar(null, "ReadExecutionPeriodByOID", args2);
        } catch (FenixServiceException e)
        {
            errors.add("impossibleCurricularPlan", new ActionError("error.impossibleCurricularPlan"));
            saveErrors(request, errors);
        }

        request.setAttribute(SessionConstants.EXECUTION_PERIOD, executionPeriodOId);
        if (infoExecutionPeriod != null && infoExecutionPeriod.getInfoExecutionYear() != null)
        {
            request.setAttribute("schoolYear", infoExecutionPeriod.getInfoExecutionYear().getYear());
        }

        request.setAttribute("infoCurriculum", infoCurriculum);
        request.setAttribute("degreeId", degreeId);
        return mapping.findForward("showCurricularCourseSite");
    }

    public ActionForward showExecutionCourseSite(
        ActionMapping mapping,
        ActionForm actionForm,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception
    {

        return mapping.findForward("showExecutionCourseSite");
    }

    private Integer getFromRequest(String parameter, HttpServletRequest request)
    {
        Integer parameterCode = null;
        String parameterCodeString = request.getParameter(parameter);
        if (parameterCodeString == null)
        {
            parameterCodeString = (String) request.getAttribute(parameter);
        }
        if (parameterCodeString != null)
        {
            parameterCode = new Integer(parameterCodeString);
        }
        return parameterCode;
    }
}

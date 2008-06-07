package net.sourceforge.fenixedu.presentationTier.Action.resourceAllocationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.dataTransferObject.InfoCurricularYear;
import net.sourceforge.fenixedu.dataTransferObject.InfoExecutionDegree;
import net.sourceforge.fenixedu.presentationTier.Action.resourceAllocationManager.base.FenixDateAndTimeDispatchAction;
import net.sourceforge.fenixedu.presentationTier.Action.resourceAllocationManager.utils.SessionConstants;
import net.sourceforge.fenixedu.presentationTier.Action.utils.ContextUtils;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import pt.ist.fenixWebFramework.security.UserView;

/**
 * @author Luis Cruz & Sara Ribeiro
 *  
 */
public class ChooseContextDA extends FenixDateAndTimeDispatchAction {

    public ActionForward prepare(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ContextUtils.prepareChangeExecutionDegreeAndCurricularYear(request);

        return mapping.findForward("ShowChooseForm");
    }

    public ActionForward choose(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        DynaActionForm chooseScheduleContext = (DynaActionForm) form;

        IUserView userView = UserView.getUser();

        /* Determine Selected Curricular Year */
        Integer anoCurricular = new Integer((String) chooseScheduleContext.get("curricularYear"));

        Object argsReadCurricularYearByOID[] = { anoCurricular };
        InfoCurricularYear infoCurricularYear = (InfoCurricularYear) executeService("ReadCurricularYearByOID", argsReadCurricularYearByOID);

        request.setAttribute(SessionConstants.CURRICULAR_YEAR, infoCurricularYear);

        /* Determine Selected Execution Degree */
        Integer executionDegreeOID = new Integer((String) chooseScheduleContext
                .get("executionDegreeOID"));

        Object argsReadExecutionDegreeByOID[] = { executionDegreeOID };
        InfoExecutionDegree infoExecutionDegree = (InfoExecutionDegree) executeService("ReadExecutionDegreeByOID", argsReadExecutionDegreeByOID);

        if (infoExecutionDegree == null) {
            ActionErrors actionErrors = new ActionErrors();
            actionErrors.add("errors.invalid.execution.degree", new ActionError(
                    "errors.invalid.execution.degree"));
            saveErrors(request, actionErrors);
            return mapping.getInputForward();
        }
        request.setAttribute(SessionConstants.EXECUTION_DEGREE, infoExecutionDegree);
        return mapping.findForward("ManageSchedules");

    }

}
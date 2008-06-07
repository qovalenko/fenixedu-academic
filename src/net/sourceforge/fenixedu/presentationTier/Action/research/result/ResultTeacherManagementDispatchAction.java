package net.sourceforge.fenixedu.presentationTier.Action.research.result;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.domain.Teacher;
import net.sourceforge.fenixedu.domain.research.result.ResearchResult;
import net.sourceforge.fenixedu.domain.research.result.ResultParticipation;
import net.sourceforge.fenixedu.domain.research.result.ResultTeacher;
import net.sourceforge.fenixedu.presentationTier.Action.base.FenixDispatchAction;
import net.sourceforge.fenixedu.presentationTier.Action.resourceAllocationManager.utils.ServiceUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.security.UserView;

public class ResultTeacherManagementDispatchAction extends FenixDispatchAction {

    public ActionForward readTeacherResults(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        Teacher teacher = getUserView(request).getPerson().getTeacher();
        String type = request.getParameter("typeResult");

        List<ResearchResult> results = new ArrayList<ResearchResult>();
        for (ResultTeacher resultTeacher : teacher.getTeacherResults()) {
            if (resultTeacher.getPublicationArea().getName().equals(type) && resultTeacher.getResult()!=null)
                results.add(resultTeacher.getResult());
        }

        request.setAttribute("teacherResults", results);

        if (type.equalsIgnoreCase("Didatic"))
            return mapping.findForward("showTeacherDidaticResults");
        else
            return mapping.findForward("showTeacherCientificResults");
    }

    public ActionForward readResultsParticipation(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        String type = request.getParameter("typeResult");
        Teacher teacher = getUserView(request).getPerson().getTeacher();

        List<ResearchResult> resultsInTeacherSheet = new ArrayList<ResearchResult>();
        for (ResultTeacher resultTeacher : teacher.getTeacherResults()) {
            resultsInTeacherSheet.add(resultTeacher.getResult());
        }

        List<ResearchResult> resultsNotInTeacherSheet = new ArrayList<ResearchResult>();
        for (ResultParticipation participation : teacher.getPerson().getResultParticipations()) {
            if (!resultsInTeacherSheet.contains(participation.getResult())) {
                resultsNotInTeacherSheet.add(participation.getResult());
            }
        }

        request.setAttribute("resultsNotInTeacherSheet", resultsNotInTeacherSheet);

        if (type.equalsIgnoreCase("Didatic"))
            return mapping.findForward("addTeacherDidaticResult");
        else
            return mapping.findForward("addTeacherCientificResult");
    }

    public ActionForward insertResultTeacher(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        IUserView userView = UserView.getUser();
        String type = request.getParameter("typeResult");
        Integer resultId = getRequestParameterAsInteger(request, "resultId");

        try {
            Object[] args = { userView.getPerson().getTeacher(), resultId, type };
            ServiceUtils.executeService("AddResultToTeacherInformationSheet", args);
        } catch (Exception ex) {
            addActionMessage(request, ex.getMessage());
        }

        return readTeacherResults(mapping, form, request, response);
    }
    
    public ActionForward deleteResultTeacher(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        IUserView userView = UserView.getUser();
        Integer resultId = getRequestParameterAsInteger(request, "resultId");

        try {
            Object[] args = { userView.getPerson().getTeacher(), resultId };
            ServiceUtils.executeService("RemoveResultFromTeacherInformationSheet", args);
        } catch (Exception ex) {
            addActionMessage(request, ex.getMessage());
        }
        return readTeacherResults(mapping, form, request, response);
    }
}

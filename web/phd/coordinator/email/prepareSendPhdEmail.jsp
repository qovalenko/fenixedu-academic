<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>
<%@ taglib uri="/WEB-INF/phd.tld" prefix="phd" %>

<html:xhtml/>

<%@page import="net.sourceforge.fenixedu.domain.phd.email.PhdProgramEmailBean" %>
<%@page import="net.sourceforge.fenixedu.domain.phd.PhdIndividualProgramProcess" %>

<logic:present role="COORDINATOR">


<%-- ### Title #### --%>
<em><bean:message  key="label.phd.coordinator.breadcrumb" bundle="PHD_RESOURCES"/></em>
<h2><bean:message key="label.phd.manage.emails.create" bundle="PHD_RESOURCES" /></h2>
<%-- ### End of Title ### --%>

<%--  ###  Return Links / Steps Information(for multistep forms)  ### --%>
<%-- <html:link action="/phdIndividualProgramProcess.do?method=choosePhdEmailRecipients" paramId="phdProgramId" paramName="phdEmailBean" paramProperty="phdProgram.externalId">
	� <bean:message bundle="PHD_RESOURCES" key="label.back"/>
</html:link>--%>

<%--  ### Return Links / Steps Information (for multistep forms)  ### --%>
<p><jsp:include page="createEmailStepsBreadcrumb.jsp?step=2"></jsp:include></p>

<%--  ### Error Messages  ### --%>
<jsp:include page="/phd/errorsAndMessages.jsp" />
<%--  ### End of Error Messages  ### --%>

<style>
	ul ul {
		margin-bottom: 5px !important;
		margin-left: 20px !important;
	}
	
	div.compose-email table th { width: 150px; }
	div.compose-email table td { width: 700px; }
	
	table td.xpto table {
		width: auto !important;
		border-collapse: collapse;
	}
	table td.xpto table td {
		border: none;
		white-space: nowrap;
		padding-right: 20px;
		width: 10px !important;
	}
	
	table td.xpto div {
		overflow-y: scroll;
		overflow-x: visible;
		height: 200px;
		width: auto;
	}
</style>

<bean:define id="phdProgramId" name="phdEmailBean" property="phdProgram.externalId"/>


<fr:form id="emailForm" action="<%="/phdIndividualProgramProcess.do?phdProgramId=" + phdProgramId.toString() %>">
	<input type="hidden" id="methodId" name="method" value="choosePhdEmailRecipients"/>
	<input type="hidden" id="skipValidationId" name="skipValidation" value="true"/>
	
	<fr:edit id="phdEmailBean" name="phdEmailBean" visible="false" />
			
	<div class="compose-email">
		
		<table class="tstyle5 thlight thright mtop05 mbottom0 ulnomargin">
			<tr>
				<th>Destinat�rios:</th>
				<td class="xpto">
	
				<div>

				<fr:view name="phdEmailBean" property="selectedElements">
					<fr:schema bundle="PHD_RESOURCES" type="<%= PhdIndividualProgramProcess.class.getName() %>">
						<fr:slot name="phdIndividualProcessNumber.number"/>
						<fr:slot name="person.name" />
						<fr:slot name="executionYear.year"/>
						<fr:slot name="phdProgram.acronym" />
					</fr:schema>
					<fr:layout name="tabular">
						<fr:property name="classes" value=""/>
						<fr:property name="columnClasses" value=""/>
						<fr:property name="nullLabel" value="" />
					</fr:layout>
				</fr:view>
				
				</div>
			
				</td>
			</tr>

		</table>
		
	
		<fr:edit id="phdEmailBean.individuals" name="phdEmailBean" >
			<fr:schema bundle="PHD_RESOURCES" type="<%= PhdProgramEmailBean.class.getName() %>">
				<fr:slot name="bccs" bundle="MESSAGING_RESOURCES" key="label.receiversOfCopy" validator="net.sourceforge.fenixedu.presentationTier.Action.phd.validator.EmailListValidator">
					<fr:property name="size" value="60" />
				</fr:slot>
			</fr:schema>
			
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle5 thlight thright mvert0 tgluetop"/>
				<fr:property name="columnClasses" value="col1,col2,tdclear tderror1"/>
			</fr:layout>
		</fr:edit> 
		
		<fr:edit id="phdEmailBean.create" name="phdEmailBean" >
			
			<fr:schema bundle="PHD_RESOURCES" type="<%= PhdProgramEmailBean.class.getName() %>">
				<fr:slot name="subject" bundle="MANAGER_RESOURCES" key="label.email.subject" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
					<fr:property name="size" value="60" />
				</fr:slot>
				<fr:slot name="message" bundle="MANAGER_RESOURCES" key="label.email.message" layout="longText" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
					<fr:property name="columns" value="80"/>
					<fr:property name="rows" value="10"/>
				</fr:slot>
			</fr:schema>
			
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle5 thlight thright mvert0 tgluetop"/>
				<fr:property name="columnClasses" value="col1,col2,tdclear tderror1"/>
				<fr:property name="requiredMarkShown" value="true" />
			</fr:layout>
				
		</fr:edit>

		<html:submit bundle="HTMLALT_RESOURCES" altKey="cancel.cancel" onclick="this.form.method.value='choosePhdEmailRecipients';">
			<bean:message bundle="APPLICATION_RESOURCES" key="label.back" />
		</html:submit>	
	  	<html:submit bundle="HTMLALT_RESOURCES" altKey="submit.submit" onclick="<%= "javascript:document.getElementById('skipValidationId').value='false';javascript:document.getElementById('methodId').value='confirmSendPhdEmail';javascript:document.getElementById('emailForm').submit();" %>">
			<bean:message bundle="APPLICATION_RESOURCES" key="label.continue" />
		</html:submit>

	  	
  	</div>
  	
</fr:form> 




</logic:present>
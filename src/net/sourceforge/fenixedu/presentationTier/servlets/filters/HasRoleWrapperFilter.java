package net.sourceforge.fenixedu.presentationTier.servlets.filters;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.domain.person.RoleType;
import pt.ist.fenixWebFramework.security.UserView;
import pt.ist.fenixWebFramework.servlets.filters.RequestWrapperFilter;

public class HasRoleWrapperFilter extends RequestWrapperFilter {

    @Override
    public FenixHttpServletRequestWrapper getFenixHttpServletRequestWrapper(final HttpServletRequest httpServletRequest) {
	return new FenixHttpServletRequestWrapperWithRoles(httpServletRequest);
    }

    public static class FenixHttpServletRequestWrapperWithRoles extends FenixHttpServletRequestWrapper {

	public FenixHttpServletRequestWrapperWithRoles(final HttpServletRequest request) {
	    super(request);
	}

	@Override
	public boolean isUserInRole(String role) {
	    final IUserView userView = UserView.getUser();
	    return userView == null ? false : userView.hasRoleType(RoleType.valueOf(role));
	}

	@Override
	public String getRemoteUser() {
	    final IUserView userView = UserView.getUser();
	    return userView == null ? super.getRemoteUser() : userView.getUtilizador();
	}
    }

}
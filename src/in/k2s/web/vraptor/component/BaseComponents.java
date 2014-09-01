package in.k2s.web.vraptor.component;

import in.k2s.web.profile.Profile;
import in.k2s.web.vraptor.bundle.MessageBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.Result;

@RequestScoped
public class BaseComponents {
	
//	private Profile profile;
	private MessageBundle bundle;
	private HttpServletRequest request;
	private Result result;
	private HttpServletResponse response;
	private HttpSession session;
	private Profile profile;
	
	public BaseComponents() {
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public BaseComponents(MessageBundle bundle, HttpServletRequest request, Result result, HttpServletResponse response, HttpSession session, Profile profile) {
		super();
		this.bundle   = bundle;
		this.request  = request;
		this.result   = result;
		this.response = response;
		this.session  = session;
		this.profile  = profile;
	}

	public Profile getProfile() {
		return profile;
	}

	public MessageBundle getBundle() {
		return bundle;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public Result getResult() {
		return result;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return session;
	}

}
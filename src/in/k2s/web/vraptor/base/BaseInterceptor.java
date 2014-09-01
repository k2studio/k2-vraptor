package in.k2s.web.vraptor.base;

import in.k2s.core.serialization.strategy.ExcludeAnnotationExclusionStrategy;
import in.k2s.web.profile.Profile;
import in.k2s.web.vraptor.bundle.MessageBundle;
import in.k2s.web.vraptor.component.BaseComponents;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.Result;

import com.google.gson.GsonBuilder;

public abstract class BaseInterceptor {
	
	private final Mirror mirror;
//	private final Profile profile;
	private final MessageBundle bundle;
	private final HttpServletRequest request;
	private final Result result;
	private final HttpServletResponse response;
	private final HttpSession session;
	
	
	public BaseInterceptor(BaseComponents components) {
		this.mirror   = new Mirror();
		this.result   = components.getResult();
		this.request  = components.getRequest();
		this.bundle   = components.getBundle();
//		this.profile  = components.getProfile();
		this.session  = components.getSession();
		this.response = components.getResponse();
	}
	
	protected void include(String param, Object value) {
		if(result != null) {
			result.include(param, value);
		}
	}
	
	protected void includeJSON(String arg0, Object arg1) {
		String json = new GsonBuilder().addSerializationExclusionStrategy(new ExcludeAnnotationExclusionStrategy()).create().toJson(arg1);
		result.include(arg0, json);
	}
	
//	protected Profile getProfile() {
//		return this.profile;
//	}
	
	protected Mirror getMirror() {
		return mirror;
	}

	protected MessageBundle getBundle() {
		return bundle;
	}

	protected HttpServletRequest getRequest() {
		return request;
	}

	protected Result getResult() {
		return result;
	}

	protected HttpServletResponse getResponse() {
		return response;
	}

	protected HttpSession getSession() {
		return session;
	}

}

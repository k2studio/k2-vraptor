package in.k2s.web.vraptor.base;

import static br.com.caelum.vraptor.view.Results.http;
import in.k2s.core.interfaces.View;
import in.k2s.core.serialization.strategy.ExcludeAnnotationExclusionStrategy;
import in.k2s.web.profile.Profile;
import in.k2s.web.view.BaseView;
import in.k2s.web.vraptor.bundle.MessageBundle;
import in.k2s.web.vraptor.component.BaseComponents;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.view.Results;

import com.google.gson.GsonBuilder;

public abstract class BaseController {
	
	private Boolean success = true;
	private final Mirror mirror;
	private BaseComponents components;

	
	public BaseController(BaseComponents components) {
		this.mirror     = new Mirror();
		this.components = components;
	}
	
	/**
	 * Adiciona o objeto ao request
	 * @param param
	 * @param value
	 */
	protected void include(String param, Object value) {
		if(components.getResult() != null) {
			components.getResult().include(param, value);
		}
	}
	
	protected void serialize() {
		serialize("{\"success\":"+this.success+"}");
	}
	
	protected void serialize(String arg0) {
		components.getResult().use(http()).addHeader("Content-Type", "application/json; charset=utf-8").body(arg0);
	}
	
	protected void includeJSON(String arg0, Object arg1) {
		String json = new GsonBuilder().addSerializationExclusionStrategy(new ExcludeAnnotationExclusionStrategy()).create().toJson(arg1);
		components.getResult().include(arg0, json);
	}
	
	protected void serialize(List<? extends View> list) {
		this.serialize(list, true, false, true);
	}
	
	protected void serialize(List<? extends View> list, Boolean withoutRoot, Boolean indented, Boolean recursive) {
		JSONSerialization json = components.getResult().use(Results.json());
		if(indented) json = json.indented();
		if(withoutRoot) json = (JSONSerialization) json.withoutRoot();
		if(recursive) json.from(list).recursive().serialize();
		else json.from(list).serialize();
	}
	
	protected void serialize(BaseView view) {
		this.serialize(view, true, false, true);
	}
	
	protected void serialize(BaseView view, Boolean withoutRoot, Boolean indented, Boolean recursive) {
		JSONSerialization json = components.getResult().use(Results.json());
		if(indented) json = json.indented();
		if(withoutRoot) json = (JSONSerialization) json.withoutRoot();
		
		if(recursive) json.from(view).recursive().serialize();
		else json.from(view).serialize();
	}
	
	protected Mirror getMirror() {
		return mirror;
	}

	protected Profile getProfile() {
		return components.getProfile();
	}

	protected MessageBundle getBundle() {
		return components.getBundle();
	}

	protected HttpServletRequest getRequest() {
		return components.getRequest();
	}

	protected Result getResult() {
		return components.getResult();
	}

	protected HttpServletResponse getResponse() {
		return components.getResponse();
	}

	protected HttpSession getSession() {
		return components.getSession();
	}

}
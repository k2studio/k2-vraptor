package in.k2s.web.vraptor.interceptor;

import static br.com.caelum.vraptor.view.Results.representation;
import in.k2s.core.interfaces.Message;
import in.k2s.web.vraptor.component.BaseComponents;
import in.k2s.web.vraptor.security.annotation.Logado;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

@Intercepts
@RequestScoped
public class AcessoInterceptor {

	@Inject
	private BaseComponents components;
	
	@Accepts
	public boolean accepts(ControllerMethod method) {
		if(isAnnotationPresent(method, Logado.class)) return true;
		return false;
	}

	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		if(!components.getProfile().isLoggedIn()) {
			components.getResponse().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			components.getResult().use(representation()).from(new ArrayList<Message>(), "errors").recursive().serialize();
		}
		stack.next();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean isAnnotationPresent(ControllerMethod method, Class annotation) {
		if(method.getMethod().getAnnotation(annotation) != null) return true;
		if(method.getMethod().getDeclaringClass().getAnnotation(annotation) != null) return true;
		return false;
	}

}
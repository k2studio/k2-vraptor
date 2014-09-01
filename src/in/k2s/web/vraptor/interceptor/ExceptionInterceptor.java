package in.k2s.web.vraptor.interceptor;

import static br.com.caelum.vraptor.view.Results.representation;
import in.k2s.web.validation.ValidationException;
import in.k2s.web.vraptor.component.BaseComponents;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import net.vidageek.mirror.exception.ReflectionProviderException;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

@Intercepts
@RequestScoped
public class ExceptionInterceptor {

	@Inject
	private BaseComponents components;

	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		try {
			stack.next();
		} catch (Exception e) {
			ValidationException vEx = null;
			if(e instanceof ValidationException) {
				vEx = (ValidationException) e;
			} else if(e.getCause() instanceof ValidationException) {
				vEx = (ValidationException) e.getCause();
			} else if(e.getCause() instanceof ReflectionProviderException) {
				ReflectionProviderException ex = (ReflectionProviderException) e.getCause();
				if(ex.getCause() instanceof ValidationException) {
					vEx = (ValidationException) ex.getCause();
				}
			}
			if(vEx != null) {
				components.getResponse().setStatus(HttpServletResponse.SC_CONFLICT);
				components.getResult().use(representation()).from(vEx.getMessages(), "errors").recursive().serialize();
			} else {
				e.printStackTrace();
			}
		}
	}

}
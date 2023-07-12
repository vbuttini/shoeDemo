package com.vbuttini.demo.shoe.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * Custom implementation of ViewResolver for Spring and Velocity
 *
 * @author Vinicius Buttini
 */
@Getter
@Setter
public class SpringVelocityViewResolver implements ViewResolver {

    private final VelocityEngine velocityEngine;

    private final ServletConfig servletConfig;

    private boolean cache;
    private String prefix;
    private String suffix;
    private String contentType;

    public SpringVelocityViewResolver(final VelocityEngine velocityEngine,final ServletConfig servletConfig) {
        this.velocityEngine = velocityEngine;
        this.servletConfig = servletConfig;
    }

    @Override
    public View resolveViewName(final String viewName,final Locale locale) throws Exception {
        final VelocityView velocityView = new VelocityView(servletConfig);
        velocityView.setVelocityEngine(this.velocityEngine);
        return new SpringVelocityView(velocityView, viewName, locale, cache, prefix, suffix, contentType);
    }
    @Getter
    @Setter
    class SpringVelocityView implements View {

        private final VelocityView velocityView;
        private final String viewName;
        private final Locale locale;

        private final boolean cache;
        private final String prefix;
        private final String suffix;
        private final String contentType;

        private Template template;

        public SpringVelocityView(final VelocityView velocityView,final String viewName,final Locale locale,final boolean cache, final String prefix,final String suffix,final String contentType) {
            this.velocityView = velocityView;
            this.viewName = viewName;
            this.locale = locale;
            this.cache = cache;
            this.prefix = prefix;
            this.suffix = suffix;
            this.contentType = contentType;
        }

        @Override
        public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
            final Context context = this.velocityView.createContext(request, response);
            context.put("locale", locale);
            for (Map.Entry<String, ?> entry : model.entrySet()){
                context.put(entry.getKey(), entry.getValue());
            }
            // get the template
            if(template == null || !this.isCache()) {
                template = this.velocityView.getTemplate(this.prefix + this.viewName + this.suffix);
            }

            // merge the template and context into the response
            this.velocityView.merge(template, context, response.getWriter());
        }

        public boolean isCache() {
            return cache;
        }
    }

}
package com.favmovie.controller.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Clase RequestInterceptor
 * <p>
 * Esta clase se utilizará para interceptar las peticiones a la aplicación
 * antes de ser procesada por el controlador.
 *
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {
    /**
     * Método preHandle
     * <p>
     * Este método es llamado antes de manejar una solicitud y se utiliza para implementar middlewares.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Aquí se implementan los middlewares
        return Middleware.checkAuthenticationCookie(request, response);
    }
}

/**
 * Clase RequestInterceptorAppConfig
 * <p>
 * Esta clase representa la configuración para el uso del interceptor RequestInterceptor en la aplicación.
 */
@Component
class RequestInterceptorAppConfig extends WebMvcConfigurationSupport {
    private final RequestInterceptor defaultInterceptor;

    /**
     * Constructor
     * <p>
     * inicializa el interceptor por defecto.
     *
     * @param defaultInterceptor - El interceptor por defecto.
     */
    public RequestInterceptorAppConfig(RequestInterceptor defaultInterceptor) {
        this.defaultInterceptor = defaultInterceptor;
    }

    /**
     * Método addResourceHandlers
     * <p>
     * Este método se utiliza para agregar manejadores de recursos en la aplicación.
     * Sin este método no se pueden acceder a los archivos estáticos.
     *
     * @param registry - El registro de manejadores de recursos.
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }

    /**
     * Método addInterceptors
     * <p>
     * Este método se utiliza para agregar interceptores en la aplicación.
     * Excluye el manejo de las peticiones a la siguientes rutas
     *
     * @param registry - El registro de interceptores.
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultInterceptor)
                .excludePathPatterns("/", "/error", "/favicon.ico", "/login", "/registro");
    }
}

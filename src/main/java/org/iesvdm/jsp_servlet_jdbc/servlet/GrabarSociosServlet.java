package org.iesvdm.jsp_servlet_jdbc.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAO;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAOImpl;
import org.iesvdm.jsp_servlet_jdbc.model.Socio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

// HTML5 SOLO SOPORTA GET Y POST, solo tratamos get y post

// API REST UTILIZANDO CODIGO DE CLEINTE JS: GET, POST, PUT, DELETE, (PATCH menos utilizado)
// Primera aprox patron M(dao, model y bbdd), V(jsp) & C(servlet)
@WebServlet(name = "GrabarSociosServlet", value = "/GrabarSociosServlet")
public class GrabarSociosServlet extends HttpServlet {
    private SocioDAO socioDAO = new SocioDAOImpl();

    // Método para rutas GET /GrabarSociosServlet
    // Para la ruta /GrabarSociosServlet va a mostrar la JSP de formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * redireccion interna en el servidor
         * OJO ruta JSP ha cambiado y esta dentreo de /WEB-INF/ no es accesible directamente, solo através de Servlet
         * usando un getRequestDispatcher
         * */

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioSocio.jsp");

        /*
         * siempre que hagamos un getRequestDispatcher debe materializarse en un fordward
         * se le pasan los objetos request y response para hacer efectiva
         * */
        dispatcher.forward(request, response); // la redireccion interna en el servidor a una JSP o vista.

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //                                    A
        //                                    |
        // fiajrse en que estamos injectando un request

        RequestDispatcher dispatcher = null;
        // Aqui se crea el socio
        Optional<Socio> optionalSocio = UtilServlet.validaGrabar(request);

        if (optionalSocio.isPresent()) {

            Socio socio = optionalSocio.get();
            this.socioDAO.create(socio);

            // prepara un atributo de listado de socio
            List<Socio> listado = this.socioDAO.getAll();
            request.setAttribute("listado", listado);

            /*
            * cuando yo haga un request dispacher hacia una pagina
            * en esa página el request original sigue estando vivo
            * */

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/pideNumeroSocio.jsp");
        } else {
            // en caso de que no valide, se informa que hay error de validación simple
            // vuelvo al formualriosocio atraves de redireccion interna
            request.setAttribute("error", "Error de validación!");
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioSocio.jsp");
        }
        // request se sigue redirigiendo en el fordward y va a estar disponible en mi pideNumerSocio
        // ventaja de psarlo por el request es que una vez que la peticion se termine el objeto se borra utomaticamente
        // no hace flata hacer un remove
        dispatcher.forward(request,response);

    }

}
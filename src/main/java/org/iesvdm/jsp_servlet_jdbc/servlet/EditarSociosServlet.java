package org.iesvdm.jsp_servlet_jdbc.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAO;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAOImpl;
import org.iesvdm.jsp_servlet_jdbc.model.Socio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "EditarSociosServlet", value = "/EditarSociosServlet")
public class EditarSociosServlet extends HttpServlet {

    private SocioDAO socioDAO = new SocioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * redireccion interna en el servidor
         * OJO ruta JSP ha cambiado y esta dentreo de /WEB-INF/ no es accesible
         * directamente, solo através de Servlet usando un getRequestDispatcher
         */

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioSocioB.jsp");

        dispatcher.forward(request, response); // la redireccion interna en el servidor a una JSP o vista.

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //                                      A
        //                                      |
        // fijarse en que estamos injectando un request

        RequestDispatcher dispatcher = null;
        // Aqui se crea el socio

        Optional<Socio> optionalSocio = UtilServlet.validaGrabar(request);

        // SI OPTIONAL CON SOCIO PRESENTE <--> VALIDA OK
        if (optionalSocio.isPresent()) {

            // ACCEDO AL VALOR DE OPTIONAL DE SOCIO
            Socio socio = optionalSocio.get();

            // PERSITO EL SOCIO NUEVO EN BBDD
            this.socioDAO.update(socio);

            // prepara un atributo de listado de socio
            // CARGO TODO EL LISTADO DE SOCIOS DE BBDD CON EL NUEVO
            List<Socio> listado = this.socioDAO.getAll();


            request.setAttribute("listado", listado);
            /*
             * cuando yo haga un request dispacher hacia una pagina
             * en esa página el request original sigue estando vivo
             */

            // POR ÚLTIMO, REDIRECCIÓN INTERNA PARA LA URL /GrabarSocioServlet A pideNumeroSocio.jsp
            // |
            // V
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listadoSociosB.jsp");
            // en caso de que no valide, se informa que hay error de validación simple
            // vuelvo al formualriosocio atraves de redireccion interna
        } else {

            // El OPTIONAL ESTÁ VACÍO (EMPTY)
            // PREPARO MENSAJE DE ERROR EN EL ÁMBITO DEL REQUEST PARA LA VISTA JSP
            // |
            // V
            request.setAttribute("error", "Error de validación!");

            // POR ÚLTIMO, REDIRECCIÓN INTERNA PARA LA URL /GrabarSocioServlet A
            // formularioSocio.jsp
            // |
            // V
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioEditarSocio.jsp");
        }

        // SIEMPRE PARA HACER EFECTIVA UNA REDIRECCIÓN INTERNA DEL SERVIDOR
        // TENEMOS QUE HACER FORWARD CON LOS OBJETOS request Y response
        dispatcher.forward(request, response);

    }
}

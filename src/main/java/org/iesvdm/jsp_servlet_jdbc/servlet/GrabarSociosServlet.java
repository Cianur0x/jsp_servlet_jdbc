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

//                      v--NOMBRE DEL SERVLET           v--RUTAS QUE ATIENDE, PUEDE SER UN ARRAY {"/GrabarSociosServlet", "/grabar-socio"}
@WebServlet(name = "GrabarSociosServlet", value = "/GrabarSociosServlet")
public class GrabarSociosServlet extends HttpServlet {

    // EL SERVLET TIENE INSTANCIADO EL DAO PARA ACCESO A BBDD A LA TABLA SOCIO
    private SocioDAO socioDAO = new SocioDAOImpl();


    // MÉTODO PARA RUTAS GET /GrabarSociosServlet
    // PARA LA RUTA /GrabarSociosServlet VA A MOSTRAR LA JSP DE formularioSocio.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * redirección interna en el servidor
         * OJO ruta JSP ha cambiado y esta dentro de /WEB-INF/ no es accesible directamente,
         * solo através de Servlet usando un getRequestDispatcher
         */

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioSocioB.jsp");

        /*
         * siempre que hagamos un getRequestDispatcher debe materializarse en un fordward
         *             -------------------------------------------------------------|
         *             V      v---------v-----SE LE PASAN LOS OBJETOS request Y response PARA HACER EFECTIVA
         */
        dispatcher.forward(request, response); // la redireccion interna en el servidor a una JSP o vista.

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = null;

        // Aqui se verifica si los datos del socio están correctos
        Optional<Socio> optionalSocio = UtilServlet.validaGrabar(request);

        // SI OPTIONAL CON SOCIO PRESENTE <--> VALIDA OK
        if (optionalSocio.isPresent()) {


            Socio socio = optionalSocio.get();

            this.socioDAO.create(socio);

            List<Socio> listado = this.socioDAO.getAll();

            request.setAttribute("listado", listado);

            //ESTABLEZCO EL ATRIBUTO DE newSocioID EN EL ÁMBITO DE REQUEST
            //PARA LANZAR UN MODAL Y UN EFECTO SCROLL EN LA VISTA JSP
            request.setAttribute("newSocioID", socio.getSocioId() );

            //POR ÚLTIMO, REDIRECCIÓN INTERNA PARA LA URL /GrabarSocioServlet A listadoSociosB.jsp
            //                                                                      |
            //                                                                      V
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listadoSociosB.jsp");
            // en caso de que no valide, se informa que hay error de validación simple
            // vuelvo al formualriosocio atraves de redireccion interna
        } else {

            // El OPTIONAL  (EMPTY)
            // PREPARO MENSAJE DE ERROR EN EL ÁMBITO DEL REQUEST PARA LA VISTA JSP
            // |
            // V
            request.setAttribute("error", "Error de validación!");

            // POR ÚLTIMO, REDIRECCIÓN INTERNA PARA LA URL /GrabarSocioServlet A  formularioSocio.jsp
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioSocioB.jsp");
        }

        // SIEMPRE PARA HACER EFECTIVA UNA REDIRECCIÓN INTERNA DEL SERVIDOR
        // TENEMOS QUE HACER FORWARD CON LOS OBJETOS request Y response
        dispatcher.forward(request, response);

    }

}
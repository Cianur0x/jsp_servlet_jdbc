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

@WebServlet(name = "RecibeCodigoServlet", value = "/RecibeCodigoServlet")

public class RecibeCodigoServlet extends HttpServlet {

    private SocioDAO socioDAO = new SocioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioEditarSocio.jsp");

        dispatcher.forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //                                      A
        //                                      |
        // fijarse en que estamos injectando un request

        RequestDispatcher dispatcher = null;

        Integer socioABuscar = UtilServlet.validaIDSocio(request);

        // SI OPTIONAL CON SOCIO PRESENTE <--> VALIDA OK
        if (socioABuscar != -1) {

            Optional<Socio> findSocio = this.socioDAO.find(socioABuscar);

            findSocio.ifPresent(socio -> request.setAttribute("socioAEditar", socio.getSocioId()));

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioEditarSocio.jsp");
            // en caso de que no valide, se informa que hay error de validación simple
            // vuelvo al formualriosocio atraves de redireccion interna
        } else {

            // El OPTIONAL ESTÁ VACÍO (EMPTY)
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

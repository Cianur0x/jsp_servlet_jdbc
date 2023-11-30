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

// BorrarSociosServlet - sólo tendrá la operación de POST para borrar.
// Utiliza la capa DAO para socio proporcionada.
@WebServlet(name = "BorrarSociosServlet", value = "/BorrarSociosServlet")
public class BorrarSociosServlet extends HttpServlet {
    private SocioDAO socioDAO = new SocioDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        Optional<Socio> optionalSocio = UtilServlet.validaBorrar(request);
        // SI OPTIONAL CON SOCIO PRESENTE <--> VALIDA OK
        if (optionalSocio.isPresent()) {

            // ACCEDO AL VALOR DE OPTIONAL DE SOCIO
            Socio socio = optionalSocio.get();

            // BORRO EL SOCIO EN BBDD
            this.socioDAO.delete(socio.getSocioId());
            List<Socio> listado = this.socioDAO.getAll();
            request.setAttribute("listado", listado);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listadoSociosB.jsp");

        } else {
            request.setAttribute("error", "Error Socio no encontrado!");
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioSocioB.jsp");
        }
        dispatcher.forward(request, response);
    }

}

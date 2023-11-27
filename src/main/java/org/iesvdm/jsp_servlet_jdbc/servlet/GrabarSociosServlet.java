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

// HTML5 SOLO SOPORTA GET Y POST, solo tratamos get y post

// API REST UTILIZANDO CODIGO DE CLEINTE JS: GET, POST, PUT, DELETE, (PATCH menos utilizado)
//PLANTILLA DE CÓDIGO PARA SERVLETs EN INTELLIJ
//https://www.jetbrains.com/help/idea/creating-and-configuring-web-application-elements.html

//1A APROX. PATRÓN MVC -> M(dao, model y bbdd), V(jsp) & C(servlet)

//                      v--NOMBRE DEL SERVLET           v--RUTAS QUE ATIENDE, PUEDE SER UN ARRAY {"/GrabarSociosServlet", "/grabar-socio"}
@WebServlet(name = "GrabarSociosServlet", value = "/GrabarSociosServlet")
public class GrabarSociosServlet extends HttpServlet {

    // EL SERVLET TIENE INSTANCIADO EL DAO PARA ACCESO A BBDD A LA TABLA SOCIO
    // |
    // V
    private SocioDAO socioDAO = new SocioDAOImpl();

    // HTML5 SÓLO SOPORTA GET Y POST
    // FRENTE A API REST UTLIZANDO CÓDIGO DE CLIENTE JS HTTP: GET, POST, PUT,
    // DELETE, PATCH

    // MÉTODO PARA RUTAS GET /GrabarSociosServlet
    // PARA LA RUTA /GrabarSociosServlet VA A MOSTRAR LA JSP DE formularioSocio.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * redireccion interna en el servidor
         * OJO ruta JSP ha cambiado y esta dentreo de /WEB-INF/ no es accesible
         * directamente, solo através de Servlet
         * usando un getRequestDispatcher
         */

        // SE TRATA DE UNA REDIRECCIÓN INTERNA EN EL SERVIDOR
        // FIJÉMONOS QUE LA RUTA DE LA JSP HA CAMBIADO A DENTRO DE /WEB-INF/
        // POR LO TANTO NO ES ACCESIBLE DIRECTAMENTE, SÓLO A TRAVÉS DE SERVLET
        // MEDIANTE UN RequestDispatcher ----------------v
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioSocioB.jsp");

        /*
         * siempre que hagamos un getRequestDispatcher debe materializarse en un fordward
         *             -------------------------------------------------------------|
         *             V      v---------v-----SE LE PASAN LOS OBJETOS request Y response PARA HACER EFECTIVA
         */
        dispatcher.forward(request, response); // la redireccion interna en el servidor a una JSP o vista.

    }

    // MÉTODO PARA RUTAS POST /GrabarSociosServlet
    // PARA LA RUTA POST /GrabarSociosServlet HAY 2 OPCIONES DE REDIRECCIÓN INTERNA A JSP
    // 1a CASO DE QUE SE VALIDE CORRECTAMENTE --> pideNumeroSocio.jsp
    // 2o CASO DE QUE NO SE VALIDE CORRECTAMENTE --> formularioSocio.jsp CON INFORME DE ERROR
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //                                      A
        //                                      |
        // fijarse en que estamos injectando un request

        RequestDispatcher dispatcher = null;
        // Aqui se crea el socio
        // CÓDIGO DE VALIDACIÓN ENCAPSULADO EN UN MÉTODO DE UTILERÍA
        // SI OK ==> OPTIONAL CON SOCIO |
        // SI FAIL ==> EMPTY OPTIONAL |
        //                        V
        Optional<Socio> optionalSocio = UtilServlet.validaGrabar(request);

        // SI OPTIONAL CON SOCIO PRESENTE <--> VALIDA OK
        if (optionalSocio.isPresent()) {

            // ACCEDO AL VALOR DE OPTIONAL DE SOCIO
            Socio socio = optionalSocio.get();

            // PERSITO EL SOCIO NUEVO EN BBDD
            this.socioDAO.create(socio);

            // prepara un atributo de listado de socio
            // CARGO TODO EL LISTADO DE SOCIOS DE BBDD CON EL NUEVO
            List<Socio> listado = this.socioDAO.getAll();

            // PREPARO ATRIBUTO EN EL ÁMBITO DE REQUEST PARA PASAR A JSP EL LISTADO
            // A RENDERIZAR. UTILIZO EL ÁMBITO DEL REQUEST DADO QUE EN EL FORWARD A
            // LA JSP SIGUE "VIVO" Y NO NECESITO ACCEDER AL ÁMBITO DE SESIÓN QUE REQUERIRÍA
            // DE UN CONTROL DE BORRADO DEL ATRIBUTO DESPUÉS DE SU USO.
            // EN request HAY UN Map<String, Object> DONDE PREPARO EL ATRIBUTO PARA LA VISTA
            // JSP
            // |
            // V
            request.setAttribute("listado", listado);
            /*
             * cuando yo haga un request dispacher hacia una pagina
             * en esa página el request original sigue estando vivo
             */

            // POR ÚLTIMO, REDIRECCIÓN INTERNA PARA LA URL /GrabarSocioServlet A
            // pideNumeroSocio.jsp
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
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioSocioB.jsp");
        }

        // request se sigue redirigiendo en el fordward y va a estar disponible en mi
        // pideNumerSocio
        // ventaja de psarlo por el request es que una vez que la peticion se termine el
        // objeto se borra utomaticamente
        // no hace flata hacer un remove

        // SIEMPRE PARA HACER EFECTIVA UNA REDIRECCIÓN INTERNA DEL SERVIDOR
        // TENEMOS QUE HACER FORWARD CON LOS OBJETOS request Y response
        dispatcher.forward(request, response);

    }

}
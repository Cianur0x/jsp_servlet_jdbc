package org.iesvdm.jsp_servlet_jdbc.servlet;

import jakarta.servlet.http.HttpServletRequest;
import org.iesvdm.jsp_servlet_jdbc.model.Socio;

import java.util.Objects;
import java.util.Optional;

public class UtilServlet {

    public static Optional<Socio> validaGrabar(HttpServletRequest request) {

        //CÓDIGO DE VALIDACIÓN
        // int socioID = -1;
        String nombre = null;
        int estatura = -1;
        int edad = -1;
        String localidad = null;
        try {

            //UTILIZO LOS CONTRACTS DE LA CLASE Objects PARA LA VALIDACIÓN
            //             v---- LANZA NullPointerException SI EL PARÁMETRO ES NULL
            Objects.requireNonNull(request.getParameter("nombre"));
            //CONTRACT nonBlank..
            //UTILIZO isBlank SOBRE EL PARÁMETRO DE TIPO String PARA CHEQUEAR QUE NO ES UN PARÁMETRO VACÍO "" NI CADENA TO_DO BLANCOS "    "
            //          |                                EN EL CASO DE QUE SEA BLANCO LO RECIBIDO, LANZO UNA EXCEPCIÓN PARA INVALIDAR EL PROCESO DE VALIDACIÓN
            //          -------------------------v                      v---------------------------------------|
            if (request.getParameter("nombre").isBlank()) throw new RuntimeException("Parámetro vacío o todo espacios blancos.");
            nombre = request.getParameter("nombre");


            estatura = Integer.parseInt(request.getParameter("estatura"));

            edad = Integer.parseInt(request.getParameter("edad"));

            //UTILIZO LOS CONTRACTS DE LA CLASE Objects PARA LA VALIDACIÓN
            //             v---- LANZA NullPointerException SI EL PARÁMETRO ES NULL
            Objects.requireNonNull(request.getParameter("localidad"));

            //CONTRACT nonBlank
            //UTILIZO isBlank SOBRE EL PARÁMETRO DE TIPO String PARA CHEQUEAR QUE NO ES UN PARÁMETRO VACÍO "" NI CADENA TO_DO BLANCOS "    "
            //          |                                EN EL CASO DE QUE SEA BLANCO LO RECIBIDO, LANZO UNA EXCEPCIÓN PARA INVALIDAR EL PROCESO DE VALIDACIÓN
            //          -------------------------v                      v---------------------------------------|
            if (request.getParameter("localidad").isBlank()) throw new RuntimeException("Parámetro vacío o todo espacios blancos.");
            localidad = request.getParameter("localidad");

            // Devuelve un optional de socio con los nuevos parametros de socio recogidos mediante request
            // ¿socio ID es -1?, no tiene relevancia es la primera creación de ese socio
            return Optional.of(new Socio(-1, nombre, estatura, edad, localidad));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //FIN CÓDIGO DE VALIDACIÓN
        return Optional.empty();

    }

    public static Optional<Socio> validaBorrar(HttpServletRequest request) {

        //CÓDIGO DE VALIDACIÓN
        boolean valida = true;
        int socioID = -1;
        String nombre = null;
        int estatura = -1;
        int edad = -1;
        String localidad = null;
        try {

            Objects.requireNonNull(request.getParameter("codigo"));
            if (request.getParameter("codigo").isBlank()) throw new RuntimeException("Parámetro vacío o todo espacios blancos.");
            socioID = Integer.parseInt( request.getParameter("codigo"));


            // Devuelve un optional de socio, para comprobar que el ide que nos han pasado es correcto
            return Optional.of(new Socio(socioID, nombre, estatura, edad, localidad));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //FIN CÓDIGO DE VALIDACIÓN
        return Optional.empty();

    }
    public static Optional<Socio> validaEditar(HttpServletRequest request) {
        // Se usa valida grabar para compribar los datos introducidos
        Optional<Socio> optSocio = UtilServlet.validaGrabar(request);
        if (optSocio.isPresent()) {
            Socio socio = optSocio.get();
            try {
                // Se recoge el id, en caso de que el usuario haya podido cambiarlo
                // y se setea al nuevo usuario
                socio.setSocioId(Integer.parseInt(request.getParameter("codigo")));
                return optSocio;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return Optional.empty();
    }

    public static Integer validaIDSocio(HttpServletRequest request) {

        //CÓDIGO DE VALIDACIÓN
        int socioID = -1;
        try {
            socioID = Integer.parseInt(request.getParameter("codigo"));

            return socioID;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //FIN CÓDIGO DE VALIDACIÓN
        return socioID;

    }
}

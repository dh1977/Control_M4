package Control_M4;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;

@WebServlet("/Acciones")
public class Acciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Connection dbConn=null;
		
		try {
			dbConn = DbConn.iniciarConexion();
		}
		catch (Exception e) {
			response.getWriter().append(
					"!No se pudo hacer conexión con la base de datos.\n" + e.getMessage() );
			return;
		}
		
        String accion = request.getParameter("accion");
        int title_no = Integer.parseInt( request.getParameter("title_no") );
        String title = request.getParameter("title");
        String ret = "";


        response.setContentType("txt/html");
        // Los signos de exclamación en 'ret' tienen un significado: 
        // ver comentario en el 'ajax' de 'index.html'
        try {
        	// Este mensaje se mantiene al haber error y, en caso contrario,
        	// se cambia de acuerdo a cada acción. 
        	//ret = "!No se pudo " + accion + " el cargo '" + title + "'";

        	if ( accion.equals("crear") ) {
            	DtoTitles.insert( dbConn, title );
            	ret = "¡Se creó el cargo '" + title + "'"; 
            }

            else if ( accion.equals("modificar") ) {
            	DtoTitles.update( dbConn, title_no, title);
            	ret = "¡Se modificó el cargo '" + title_no + "'"; 
            }
            else if ( accion.equals("eliminar") ) {
            	DtoTitles.delete( dbConn, title_no );
            	ret = "¡Se eliminó el cargo '" + title_no + "'"; 
            }
            else { 
            	// Consulta.
            	ret = "!"; 
        		Titles titles = DtoTitles.select(dbConn,title_no);
        		if ( titles == null )
        			ret = "!No se encontró el cargo '" + title_no + "'";
        		else 
        			// Datos
        			ret = titles.getTitle_no() + ";" + titles.getTitle();
            }
        }
        catch (Exception e) {
       		ret = "!" + e.getMessage();
        }

        response.getWriter().append(ret);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet( request, response );
	}
}

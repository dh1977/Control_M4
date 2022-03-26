package Control_M4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.Exception;

public class DtoTitles {
	// Todos los métodos obligan a utilizar 'try... catch' al invocarlos.
	
	public static Titles select(Connection dbConn, int title_no) throws Exception 
	{
		PreparedStatement pst;
		ResultSet rs;
		Titles ret = null;

		try {
			pst = dbConn.prepareStatement("Select title_no, title From title_types Where title_no=?");
			pst.setInt(1, title_no);
			rs = pst.executeQuery();
			
			if ( rs.next() )
				// En caso de no ser encontrado, 'ret' vuelve 'null': lo controlo en el servlet
				ret = new Titles( rs.getInt("title_no"), rs.getString("title") );
		}
		catch (Exception  e) {
			throw new Exception("No se pudo leer la info del cargo '"+title_no+"'\n" + e.getMessage());
		}
		
		return ret;
	}
	

	public static void insert(Connection dbConn, String title) throws Exception
	{
		PreparedStatement pst;

		try {
			pst = dbConn.prepareStatement("INSERT INTO title_types (title) VALUES (?)");
			pst.setString(1, title);
			pst.executeUpdate();
		}
		catch (Exception  e) {
			throw new Exception("No se pudo crear el cargo '"+title+"'\n" + e.getMessage() );
		}
	}
	

	public static void update(Connection dbConn, int title_no, String title) throws Exception
	{
		PreparedStatement pst;

		try {
			pst = dbConn.prepareStatement("UPDATE title_types SET title=? WHERE title_no = ?");
			pst.setString(1, title);
			pst.setInt(2, title_no);
			if ( pst.executeUpdate() == 0 ) {
				// Este mensaje se une al del Exception aquí abajo
				throw new Exception("No se encontró.\n");
			}
		}
		catch (Exception  e) {
			throw new Exception("No se pudo modificar el cargo '"+title_no+"'\n" + e.getMessage() );
		}
	}

	
	public static void delete(Connection dbConn, int title_no) throws Exception
	{
		PreparedStatement pst;

		try {
			pst = dbConn.prepareStatement("DELETE FROM title_types WHERE title_no = ?");
			pst.setInt(1, title_no);
			if ( pst.executeUpdate() == 0 )
				// Este mensaje se une al del Exception aquí abajo
				throw new Exception("No se encontró.\n");
		}
		catch (Exception  e) {
			throw new Exception("No se pudo eliminar el cargo '"+title_no+"'\n" + e.getMessage() );
		}
	}

}

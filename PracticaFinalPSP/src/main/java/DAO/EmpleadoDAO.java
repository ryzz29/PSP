package DAO;

import models.Empleado;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Dao que hace de intermediario entre el servidor y la bbdd pasando datos desde y hacia Empleado
 */
public class EmpleadoDAO extends AbstractDao {

    /**
     * Método constructor que habilita la conexión
     */
    public EmpleadoDAO() {
        super.conectar();
    }

    /**
     * Método que nos permite comprobar el login hecho en la clase Cajero que hace de cliente, comprueba si existe un empleado con la id introducida por parámetros
     * en cuyo caso lo devuelve en el return, en caso contrario, devuelve un empleado null
     * @param idEmpleado
     * @return
     */
    public Empleado comprobarLogin(int idEmpleado) {

        Empleado empleado = null;

        try {
            String query = "SELECT * from empleado where ID_Empleado = ?";
            st = conexion.prepareStatement(query);
            st.setInt(1, idEmpleado);
            rs = st.executeQuery();
            while (rs.next()){
                empleado = new Empleado(rs.getInt("ID_Empleado"),rs.getDate("Ultima_Sesion"),rs.getDate("Fecha_Contratacion"));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return empleado; //Esto devuelve el id del usuario y ya con esto podemos formar a los usuarios
    }

    /**
     * Método que nos permite actualizar la última conexión de un empleado, utilizando la id y la fecha ambos introducidos por parámetros
     * @param idEmpleado
     * @param fecha
     */
    public void actualizarUltimaSesion(int idEmpleado, Date fecha){
        try {
            String query = "UPDATE empleado set Ultima_Sesion = ? where ID_Empleado = ?";
            st = conexion.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.setDate(1, fecha);
            st.setInt(2,idEmpleado);
            st.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

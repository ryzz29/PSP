package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Dao que hace de intermediario entre el servidor y la bbdd pasando datos desde y hacia Producto
 */
public class ProductoDAO extends AbstractDao {

    /**
     * Método constructor que habilita la conexión
     */
    public ProductoDAO() {
        super.conectar();
    }

    /**
     * Método que nos muestra la cantidad de stock del producto y nos la devuelve en el return
     * Utilizando la id del producto introducida por parámetros
     * @param idproducto
     * @return
     * @throws Exception
     */
    public int checkStock(int idproducto) throws Exception {
        int stockProducto = 0;
        boolean encontrado = false;

        try {
            Statement stm = conexion.createStatement();
            rs = stm.executeQuery("SELECT * from Producto where ID_Producto = " + idproducto);

            while (rs.next()) {
                encontrado = true;
                stockProducto = rs.getInt("Cantidad_Stock");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (encontrado = false){
            throw new Exception("Parece que no se encontró ningún producto con esa ID para encontrar su cantidad en stock");
        }
        return stockProducto;
    }

    /**
     * Método que nos muestra el precio del proveedor de dicho producto producto y nos la devuelve en el return
     * Utilizando la id del producto introducida por parámetros
     * @param idproducto
     * @return
     * @throws Exception
     */
    public int checkPrecioProveedor(int idproducto) throws Exception {
        int precio = 0;
        boolean encontrado = false;

        try {
            Statement stm = conexion.createStatement();
            rs = stm.executeQuery("SELECT * from Producto where ID_Producto = " + idproducto);

            while (rs.next()) {
                encontrado = true;
                precio = rs.getInt("Precio_Proveedor");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (encontrado = false){
            throw new Exception("Parece que no se encontró ningún producto con esa ID para encontrar su cantidad en stock");
        }
        return precio;
    }

    /**
     * Método que nos actualiza el stock de un producto calculando la cantidad final del mismo que introduciremos en la base de datos
     * Restando a la cantidad devuelta por el método checkStock() la cantidadPedida que introducimos por parámetros
     * y finalmente introduciendo en la base de datos dicha cantidad.
     * Además devolverá un boolean a TRUE siempre y cuando el resultado final sea 0 o inferior, para avisarnos de que hace falta reponer stock
     * @param idproducto
     * @param cantidadPedida
     * @return
     * @throws Exception
     */
    public boolean actualizarStock(int idproducto, int cantidadPedida) throws Exception {
        boolean sinStock = false;
        int cantidadFinal = (checkStock(idproducto) - cantidadPedida);

        if (cantidadFinal <= 0){
            sinStock = true;
        }

        try {
            String query = "UPDATE producto set Cantidad_Stock = ? where ID_Producto = ?";
            st = conexion.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.setInt(1, cantidadFinal);
            st.setInt(2, idproducto);
            st.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sinStock;
    }
}

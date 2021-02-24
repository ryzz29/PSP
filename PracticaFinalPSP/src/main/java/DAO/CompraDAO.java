package DAO;

import Utils.Turbofecheador;

import java.sql.*;

/**
 * Dao que hace de intermediario entre el servidor y la bbdd pasando datos desde y hacia Compras (y en su defecto la tabla intermedia pedido)
 */
public class CompraDAO extends AbstractDao {

    /**
     * Método constructor que habilita la conexión
     */
    public CompraDAO() {
        super.conectar();
    }

    /**
     * Método que nos permite insertar una compra utilizando la id del producto, la id del empleado que realiza la compra, la caja donde se realiza,
     * y la fecha en que se realiza dicha compra.
     * Además inserta en la tabla intermedia pedido la información necesaria (debido a la relación de n:m entre compra y producto)
     * @param idproducto
     * @param id_empleado
     * @param cantidad
     * @param caja
     * @param fecha
     */
    public void realizarCompra(int idproducto, int id_empleado, int cantidad, int caja, Date fecha){

        try {
            String queryCompra = "Insert into compra (Fecha,ID_Empleado,Caja) values(?,?,?)";
            st = conexion.prepareStatement(queryCompra, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setDate(1, fecha);
            st.setInt(2, id_empleado);
            st.setInt(3, caja);
            st.executeUpdate();

            rs = st.getGeneratedKeys();
            rs.next();
            int idCompra = rs.getInt(1);

            String queryPedido = "Insert into pedido (ID_Producto,ID_Compra,Cantidad_Producto) values(?,?,?)";
            st = conexion.prepareStatement(queryPedido);
            st.setInt(1, idproducto);
            st.setInt(2, idCompra);
            st.setInt(3, cantidad);
            st.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Método que nos devuelve un int con la cantidad (en euros) de dinero que se ha hecho en caja ese mismo día, utilizando la fecha introducida por parámetros
     * @param fecha
     * @return
     */
    public int obtenerCajaDelDiaTotal(Date fecha) {
        int cajaTotal = 0;

        try {

            Statement stm = conexion.createStatement();
            rs = stm.executeQuery("SELECT * FROM compra left join pedido on compra.ID_Compra=pedido.ID_Compra left join producto on pedido.ID_Producto=producto.ID_Producto where Fecha ='" + fecha + "'");

            while (rs.next()) {
                int precioVenta = rs.getInt("Precio_Venta");
                int precioProveedor = rs.getInt("Precio_Proveedor");
                int cantidadProducto = rs.getInt("Cantidad_Producto");
                cajaTotal += ((precioVenta-precioProveedor)*cantidadProducto);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cajaTotal;
    }

    /**
     * Método que nos devuelve un int con la cantidad (en euros) de dinero que se ha hecho en esa caja concreta ese mismo día,
     * utilizando la fecha y la caja introducidas por parámetros
     * @param caja
     * @param fecha
     * @return
     */
    public int obtenerCajaDelDiaParcial(int caja, Date fecha) {
        int cajaParcial = 0;

        try {

            Statement stm = conexion.createStatement();
            rs = stm.executeQuery("SELECT * FROM compra left join pedido on compra.ID_Compra=pedido.ID_Compra left join producto on pedido.ID_Producto=producto.ID_Producto where fecha = '" + fecha + "' and caja = " + caja);

            while (rs.next()) {
                int precioVenta = rs.getInt("Precio_Venta");
                int precioProveedor = rs.getInt("Precio_Proveedor");
                int cantidadProducto = rs.getInt("Cantidad_Producto");
                cajaParcial += ((precioVenta-precioProveedor)*cantidadProducto);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cajaParcial;
    }

}

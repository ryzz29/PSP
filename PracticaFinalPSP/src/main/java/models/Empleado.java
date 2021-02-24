package models;

import java.sql.Date;

/**
 * Se trata de la clase Empleado que representa la tabla Empleado de la base de datos
 */
public class Empleado implements java.io.Serializable {
    int ID_Empleado;
    Date Ultima_Sesion;
    Date Fecha_Contratacion;

    /**
     * El constructor de empleado utilizando como parámetros los 3 campos existentes en la BBDD
     * @param ID_Empleado
     * @param Ultima_Sesion
     * @param Fecha_Contratacion
     */
    public Empleado(int ID_Empleado, Date Ultima_Sesion, Date Fecha_Contratacion) {
        this.ID_Empleado = ID_Empleado;
        this.Ultima_Sesion = Ultima_Sesion;
        this.Fecha_Contratacion = Fecha_Contratacion;
    }

    //Los getters y los setters
    public int getID_Empleado() {
        return ID_Empleado;
    }

    public void setID_Empleado(int ID_Empleado) {
        this.ID_Empleado = ID_Empleado;
    }

    public Date getUltima_Sesion() {
        return Ultima_Sesion;
    }

    public void setUltima_Sesion(Date ultima_Sesion) {
        Ultima_Sesion = ultima_Sesion;
    }

    public Date getFecha_Contratacion() {
        return Fecha_Contratacion;
    }

    public void setFecha_Contratacion(Date fecha_Contratacion) {
        Fecha_Contratacion = fecha_Contratacion;
    }
}

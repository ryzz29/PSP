package Utils;

import java.sql.Date;

/**
 * Turbolector es una clase que turbo-genera fechas preparadas para insertar en Sql aa/mm/dd, fechas normales dd/mm/aa y horas h:m
 */
public class Turbofecheador {

    /**
     * Método que genera una fecha preparada para insertarla en Sql con el formato aa/mm/dd, devuelve un Date
     * @return
     */
    public Date generarFechaSql(){
        System.out.println(java.time.LocalDate.now());
        Date date = Date.valueOf(java.time.LocalDate.now().getYear() + "-"
                + java.time.LocalDate.now().getMonthValue() + "-"
                + java.time.LocalDate.now().getDayOfMonth());
        return date;
    }

    /**
     * Método que genera una fecha con formato dd/mm/aa, devuelve un date
     * @return
     */
    public String generarFechaString(){
        String date = java.time.LocalDate.now().getDayOfMonth() + "-"
                + java.time.LocalDate.now().getMonthValue() + "-"
                + java.time.LocalDate.now().getYear();
        return date;
    }

    /**
     * Método que genera una hora con formato h:m, devuelve un string
     * @return
     */
    public String generarHora(){
        String hora = java.time.LocalTime.now().getHour() + ":"
                + java.time.LocalTime.now().getMinute();
        return hora;
    }
}

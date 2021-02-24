package Utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.File;

/**
 * Turbolector es una clase que turbo-lee xml turbo-rápido o si lo prefieres turbofast turbofurious
 */
public class Turbolector {

    /**
     * Este método nos permite cargar directamente el EmailProveedor de nuestro archivo App.config de confianza
     * @return
     */
    public String generarCorreoProveedor (){
        String correo = "";

        try {
            File file = new File("App.config");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            correo = document.getElementsByTagName("EmailProveedor").item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return correo;
    }

}
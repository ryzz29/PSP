package webscrapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main {
	
	public static int minutos = 1;
	public static File archivo = new File("D:\\Bolsa.txt");

	public static void main(String[] args) {
		Document doc;
		
		try {
			while (true) {
			doc = Jsoup.parse(new URL("https://www.bolsamadrid.es/esp/aspx/Mercados/Precios.aspx?indice=ESI100000000&punto=indice"), 5000);
			String cabecera = doc.select("table#ctl00_Contenido_tblÍndice > tbody > tr[align=\"center\"] th").text();
			String datos = doc.select("table#ctl00_Contenido_tblÍndice > tbody > tr[align=\"right\"] td").text();
			
			if (archivo.exists()) {
				System.out.println("El archivo existía, añadiendo datos...");
				BufferedWriter writer = new BufferedWriter(new FileWriter(archivo,true));
				System.out.println(datos);
				writer.write(datos+"\n");
				writer.close();
				
			} else {
				BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
				System.out.println("El archivo no existía, creando y añadiendo datos...");
				System.out.println(cabecera);
				writer.write(cabecera+"\n");
				System.out.println(datos);
				writer.write(datos+"\n");
				writer.close();

			}
			
			Thread.sleep(1000*15*minutos);
			System.out.println("Han pasado 15 segundos...");
			Thread.sleep(1000*15*minutos);
			System.out.println("Han pasado 30 segundos...");
			Thread.sleep(1000*15*minutos);
			System.out.println("Han pasado 45 segundos...");
			Thread.sleep(1000*15*minutos);
			System.out.println("Han pasado 60 segundos, lanzando!");

			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
        
	}

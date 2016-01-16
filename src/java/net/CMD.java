package net;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class CMD
{
	private static Process cmd;
	private static BufferedReader bf;
	public static void executeVisual(String comando)
	{
            try 
            {    
                cmd = Runtime.getRuntime().exec(comando);
                bf = new BufferedReader(new InputStreamReader(cmd.getInputStream()));  

                String linea = null;  	//IMPRIME RESULTADOS:
                while ((linea = bf.readLine()) != null) 
                {  
                    System.out.println(linea);  
                }  
            } catch (IOException e) 
            {
                    System.out.println("Error de ejecucion CMD");
                    e.printStackTrace();
            }
	}
	public static String execute(String comando)
	{
            String respuesta= "";
            try 
            {    
                cmd = Runtime.getRuntime().exec(comando);
                bf = new BufferedReader(new InputStreamReader(cmd.getInputStream()));  

                String linea = null;  	//IMPRIME RESULTADOS:
                while ((linea = bf.readLine()) != null) 
                {  
                        respuesta += linea + ";\n";  
                }  
            } 
            catch (IOException e) 
            {
                    System.out.println("Error de ejecucion CMD:" +comando);
                    e.printStackTrace();
            }
            return respuesta;
	}
        
        public static boolean isWindows()
        {
            boolean respuesta = false;
            
            if(System.getProperty("os.name").toLowerCase().startsWith("windows"))
            {
                respuesta = true;
            }
            
            return respuesta;
        }
        
        public static String dameValorEntre(String contenido,String inicio, String fin)
        {
            String acumulador = "";
            String valorBuscado = "";
            boolean flag = false;
            for(int i = 0 ; i < contenido.length() ; i++)
            {
                char caracterActual = contenido.charAt(i);
                if(acumulador.toLowerCase().endsWith(inicio.toLowerCase()))
                {
                    flag = true;
                    
                }
                else if( acumulador.contains(fin))
                {
                    flag = false;
                }
                
                
                if(flag)
                {
                    valorBuscado += caracterActual;
                }
                acumulador += caracterActual;
                
            }
            
            if(valorBuscado.length() >= fin.length())
            {
                valorBuscado = valorBuscado.substring(0, (valorBuscado.length() - fin.length()));
                
            }
            
            return valorBuscado.trim();
        }
        public static String dameColumna(String contenido,String separador, int columna)
        {
            java.util.ArrayList<String> arr = new java.util.ArrayList<String>();
            
            String acumulador = "";
            
            for(int i = 0 ; i < contenido.length(); i++)
            {
                char caracterActual = contenido.charAt(i);
                
                if(acumulador.endsWith(separador))
                {
                    String actrim = acumulador.trim();
                    if(!actrim.equalsIgnoreCase(""))
                    {
                       arr.add(actrim); 
                    }
                    acumulador = "";
                }
                
                acumulador += caracterActual;
                
                if(i == (contenido.length() -1))
                {
                    String actrim = acumulador.trim();
                    if(!actrim.equalsIgnoreCase(""))
                    {
                       arr.add(actrim); 
                    }
                    acumulador = "";
                }
                
            }
            
            return arr.get(columna-1);
        }

        public static String uptime()
        {
            String salida = "";
            
            if( isWindows() )
            {   
                salida = CMD.dameValorEntre(CMD.execute("systeminfo"), "Tiempo de arranque del sistema:", "Fabricante del sistema:");
                salida = salida.substring(0, salida.length() - 1);
            }
            else
            {
                salida += CMD.dameColumna(CMD.execute("uptime"), " ", 1);
            }
            return salida;
        }
        public static RAM free()
        {
            RAM ram = null;
            if(CMD.isWindows())
            {
                String respuesta = CMD.execute("systeminfo");
                System.out.println(respuesta);
                
                if(respuesta != null)
                {
                    String total = CMD.dameValorEntre(respuesta, "Cantidad total de memoria f¡sica:", "Memoria f¡sica disponible:");
                    total = total.substring(0, total.length() - 3 );
                    System.out.println(total);

                    String free = CMD.dameValorEntre(respuesta, "Memoria f¡sica disponible:", "Memoria virtual: tama¤o m ximo:");
                    free = free.substring(0, free.length() - 3);
                    System.out.println(free);

                    ram = new RAM(Float.parseFloat(free),Float.parseFloat(total));
                }
                
            }
            else
            {
                String respuesta = CMD.execute("free -m");
  
                        
                float total = Float.parseFloat(CMD.dameColumna(CMD.dameValorEntre(respuesta, "Mem:", "-/+ buffers/cache:"), "    ", 1));
                float free = Float.parseFloat(CMD.dameColumna(CMD.dameValorEntre(respuesta, "Mem:", "-/+ buffers/cache:"), "    ", 3));
                
                ram = new RAM(free,total);
            }
            
            return ram;
        }
        
}

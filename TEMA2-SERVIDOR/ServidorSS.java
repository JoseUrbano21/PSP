import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
public class ServidorSS {
    public static void main(String[] args) {
        System.out.println(" APLICACIÓN DE SERVIDOR ");
        System.out.println("----------------------------------");
        try {
            ServerSocket servidor = new ServerSocket();
            // Configuramos en una clase el servidor y el puerto
            InetSocketAddress direccion = new InetSocketAddress("192.168.1.104",2000);
            // .bind para poner el socket en marcha, que se puede lanzar con un .delay, por ejemplo, a 10 segundos
            servidor.bind(direccion);
            System.out.println("Servidor creado y escuchando .... ");
            System.out.println("Dirección IP: " + direccion.getAddress());

            // ----------------------------------------------------------------------- CONEXIÓN CON EL CLIENTE
            Socket enchufeAlCliente = servidor.accept();
            System.out.println("Comunicación entrante");
            InputStream entrada = enchufeAlCliente.getInputStream();
            OutputStream salida = enchufeAlCliente.getOutputStream();
            String texto = "";

            while (!texto.trim().equals("FIN")) {
                /*
                Al poner byte[100], obligamos a que el mensaje tenga 100 caracteres. Si el mensaje final es solo "Hola", los otros
                96 caracteres te los rellena con 0. Si se hace un .trim() ya no habría problema, ya que elimina los espacios al
                principio y al final de la cadena String.
                 */
                byte[] mensaje = new byte[100];
                entrada.read(mensaje);
                texto = new String(mensaje).trim();  //.trim();
                if (texto.trim().equals("FIN")) {
                    salida.write("Hasta pronto, gracias por establecer conexión".getBytes());
                } else {
                    System.out.println("Cliente dice: " + texto);
                    salida.write(("Tu mensaje tiene " + texto.length() + " caracteres").getBytes());  //texto.length()
                }
            }

            entrada.close();
            salida.close();
            enchufeAlCliente.close();
            servidor.close();
            System.out.println("Comunicación cerrada");
            // ------------------------------------------------------------ SE CIERRA LA COMUNICACIÓN CON EL CLIENTE
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;

public class Cliente {

    public static void main(String[] args) {
        try {

            Client client = Client.create();
            WebResource webResource = null;
            ClientResponse response = null;
            int opcion = -1;
            Scanner lectura = new Scanner(System.in);
            System.out.println("Bienvenido sistema gestion empleados");
         
            do {
                System.out.println("1. Listar 2. Crear. 3. Buscar. 4.  Salir");
                opcion = lectura.nextInt();
                switch (opcion) {
                    case 1:
                        webResource = client.resource("http://localhost:8080/myapp/pacientes");

                        response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

                        if (response.getStatus() != 200) {
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }

                        String output = response.getEntity(String.class);
                        System.out.println("Output from Server .... \n");
                        System.out.println(output);
                        break;

                    case 2:
                        webResource = client
                                .resource("http://localhost:8080/myapp/pacientes");

                        ObjectMapper mapper = new ObjectMapper();
                        Paciente paciente = new Paciente();
                        
                        System.out.println("Ingrese el nombre");
                        String nombre = lectura.next();
                        paciente.setfName(nombre);
                        
                        System.out.println("Ingrese el apellido");
                        String apellido = lectura.next();
                        paciente.setsName(apellido);
                        
                        System.out.println("Ingrese la direccion");
                        String direccion = lectura.next();
                        paciente.setAddress(direccion);
                        
                        System.out.println("Ingrese la fecha de nacimiento");
                        String fecha = lectura.next();
                        paciente.setBirth(fecha);
                        
                        System.out.println("Ingrese el telefono");
                        String tel = lectura.next();
                        paciente.setTelephone(tel);
                        
                        System.out.println("Ingrese el numero de medicare");
                        String medic = lectura.next();
                        paciente.setMedicare(medic);
                        
                        System.out.println("Ingrese si es privado o publico");
                        String status = lectura.next();
                        paciente.setStatus(status);
                        
                        String input = mapper.writeValueAsString(paciente);
                        //Luego se utilizara Jackson
                        //String input = "{\"empNo\":\"E11\",\"empName\":\"" + nombre + "\",\"position\":\"Salesman\"}";

                        response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);

                        if (response.getStatus() != 200) {
                            System.out.println(response.toString());
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }

                        output = response.getEntity(String.class);
                        System.out.println(output);
                        break;

                    case 3:
                        System.out.println("Indique el ID del Medicare del paciente");
                        String id = lectura.next();
                        webResource = client.resource("http://localhost:8080/myapp/pacientes/" + id);

                        response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

                        if (response.getStatus() != 200) {
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }
                        String outputBuscar = response.getEntity(String.class);
                        System.out.println("Output from Server .... \n");
                        System.out.println(outputBuscar);

                        break;
                    default:
                        System.out.println("Opcion invalida");

                }

            } while (opcion != 4);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}

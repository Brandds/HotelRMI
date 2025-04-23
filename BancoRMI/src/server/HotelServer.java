package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.Service.UsuarioService;
import server.Service.ServiceImpl.UsuarioSerivceImpl;

public class HotelServer {
	public HotelServer() {
        System.setProperty("java.rmi.server.hostname","192.168.15.15");
        try {
            UsuarioService user = new UsuarioSerivceImpl();
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("UsuarioService", user);
            System.out.println("Servidor rodando");
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    public static void main(String args[]) {
        new HotelServer();
    }

}

package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.Service.UsuarioService;
import server.Service.ServiceImpl.UsuarioSerivceImpl;

public class BancoServer {
	public BancoServer() {
        System.setProperty("java.rmi.server.hostname","192.168.15.7");
        try {
            BancoInterface c = new BancoImpl();
            UsuarioService user = new UsuarioSerivceImpl();
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("BancoService", c);
            reg.rebind("UsuarioService", user);
            System.out.println("Servidor rodando");
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    public static void main(String args[]) {
        new BancoServer();
    }

}

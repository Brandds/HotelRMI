package client;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import server.BancoInterface;
import server.Service.UsuarioService;
import server.model.Quarto;

public class BancoClient {
	public static void main(String[] args) {
		try {
            BancoInterface c = (BancoInterface) Naming.lookup("rmi://192.168.15.7/BancoService");
						UsuarioService user = (UsuarioService) Naming.lookup("rmi://192.168.15.7/UsuarioService");
            
            Scanner s = new Scanner(System.in);
    		System.out.println("1 - Cadastrar Cliente");
    		System.out.println("2 - Consultar Quartos disponveis");
    		System.out.println("3 - Depositar Valor");
    		System.out.println("4 - Sacar Valor");
    		int opcao = s.nextInt();
				s.nextLine();
    		if(opcao == 1) {
					
					System.out.println("Digite seu NOME");
					String nome = s.nextLine();  

					System.out.println("Digite seu CPF");
					String cpf = s.nextLine();  

					System.out.println("Qual é o tipo de usuario");
					System.out.println("1-Administrador, 2-Cliente");
					int intPerfil = s.nextInt();  

				
					String tipoPerfil = (intPerfil == 1) ? "ADMIN" : "USER";

					String message = user.cadastrar(nome, cpf, tipoPerfil);
					System.out.println(message);
					
    		} else if(opcao == 2) {
    			try {
						System.out.println("Digite a data de reserva:");
            String dataStr = s.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = sdf.parse(dataStr); // Converte a string para Date
 
            List<Quarto> list = user.consultarQuartosDisponveis(data);

            for (Quarto quarto : list) {
                System.out.println("Quarto: " + quarto.getNumeroQuarto() + ", Tipo: " + quarto.getTipoQuartoEnum());
            }
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use yyyy-MM-dd.");
        }
    		} else if(opcao == 3) {
    			System.out.println("ID do Cliente:");
    			int id = s.nextInt();
    			System.out.println("Valor depositado:");
    			double valor = s.nextDouble();
    			c.depositar(id, valor);
    			System.out.println(valor+" reais depositados");
    		} else {
    			System.out.println("ID do Cliente:");
    			int id = s.nextInt();
    			System.out.println("Valor sacado:");
    			double valor = s.nextDouble();
    			double saque = c.sacar(id, valor);
    			if(saque == -1) {
    				System.out.println("Saldo insuficiente");
    			} else {
    				System.out.println(saque+" reais sacados");
    			}
    		}
            s.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException re) {
            System.out.println(re);
        }
        
    }
}
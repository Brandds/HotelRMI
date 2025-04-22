package client;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import server.Service.UsuarioService;
import server.model.Quarto;
import server.model.Reserva;

public class HotelClient {

	public static void printWithColor(String text, String colorCode) {
		System.out.print("\033[" + colorCode + "m" + text + "\033[0m");
	}
	public static void limparTela() {
    // Limpa a tela dependendo do sistema operacional
    try {
        if (System.getProperty("os.name").contains("Windows")) {
            // Limpa a tela no Windows
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            // Limpa a tela no Unix/Linux/Mac
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    } catch (Exception e) {
        System.out.println("Erro ao tentar limpar a tela: " + e.getMessage());
    }
}

	public static void main(String[] args) {
		try {
						UsuarioService user = (UsuarioService) Naming.lookup("rmi://192.168.15.7/UsuarioService");
            
            Scanner s = new Scanner(System.in);
						printWithColor("\n*************************************************\n", "1;33");
            printWithColor("           Sistema de Reservas de Quartos\n", "1;32");
            printWithColor("*************************************************\n", "1;33");
            
            // Menu interativo
            printWithColor("\nEscolha uma opção abaixo (Digite o número):\n", "0;36");
            
            printWithColor("\n1 - Cadastrar Cliente\n", "0;34");
            printWithColor("2 - Consultar Quartos Disponíveis\n", "0;34");
            printWithColor("3 - Fazer Reserva\n", "0;34");
            printWithColor("4 - Quartos Disponíveis\n", "0;34");
            printWithColor("5 - Buscar Sua Reserva\n", "0;34");
            printWithColor("6 - Cancelar a Reserva\n", "0;34");
            printWithColor("7 - Cadastrar Novo Quarto\n", "0;34");
            printWithColor("8 - Lista de Reservas\n", "0;34");
            
            printWithColor("\n-------------------------------------------------\n", "1;33");
						int opcao = s.nextInt();
						s.nextLine();

						switch (opcao) {
							case 1:
								System.out.println("\nVocê escolheu: Cadastrar Cliente");

								System.out.println("Digite seu NOME");
								String nome = s.nextLine();  

								System.out.println("Digite seu CPF");
								String cpf = s.nextLine();  

								System.out.println("Qual é o tipo de usuario");
								System.out.println("1-Administrador, 2-Cliente");
								int intPerfil = s.nextInt();  

							
								String tipoPerfil = (intPerfil == 1) ? "ADMIN" : "USER";
								limparTela();
								String message = user.cadastrar(nome, cpf, tipoPerfil);
								System.out.println(message);
								break;
							case 2:
									System.out.println("\nVocê escolheu: Consultar Quartos Disponíveis");

									try {
										System.out.println("Digite a data de reserva:");
										String dataStr = s.nextLine();
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										Date data = sdf.parse(dataStr); // Converte a string para Date
										limparTela();
										List<Quarto> list = user.consultarQuartosDisponveis(data);

										if(!list.isEmpty()){
											for (Quarto quarto : list) {
												System.out.println("Quarto: " + quarto.getNumeroQuarto() + ", Tipo: " + quarto.getTipoQuartoEnum());
											}
										}else {
											System.out.println("Nenhum quarto disponivel.");
										}

									} catch (ParseException e) {
										System.out.println("Formato de data inválido. Use yyyy-MM-dd.");
									}

								break;
							case 3:
									System.out.println("\nVocê escolheu: Fazer Reserva");

									try {
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										System.out.println("Digite o numero do quarto:");
										int numeroQuarto = s.nextInt();
										s.nextLine(); // Consome a quebra de linha deixada pelo nextInt()

										System.out.println("Digite seu CPF:");
										String cpfUsuario = s.nextLine();

										System.out.println("Digite a data de entrada:");
										String dataEntrada = s.nextLine();
										Date dataEntradaConvert = sdf.parse(dataEntrada);

										System.out.println("Digite a data de saída:");
										String dataSaida = s.nextLine();
										Date dataSaidaConvert = sdf.parse(dataSaida);
										limparTela();
										String response = user.fazerReserva(dataEntradaConvert, dataSaidaConvert, cpfUsuario, numeroQuarto);
										System.out.println(response);

									} catch (Exception e) {
										e.printStackTrace();
									}
									break;
							case 4:
							System.out.println("\nVocê escolheu: Consultar Quartos Disponíveis");
							limparTela();
							for(Quarto quarto : user.listarQuartosDisponiveis()){
								System.out.println("Numero quarto: " + " " + quarto.getNumeroQuarto());
							}
									break;
							case 5:
									System.out.println("\nVocê escolheu: Buscar Sua Reserva");

									System.out.println("Digite seu CPF:");
									String userCpf = s.nextLine();
									limparTela();
									System.out.println(user.buscarReserva(userCpf));
									break;
							case 6:
							System.out.println("\nVocê escolheu: Cancelar a Reserva");

							System.out.println("Digite o identificador da reserva para cancelar:");
							String idStr = s.nextLine(); 
							
							try {
									Long id = Long.parseLong(idStr); 
									limparTela();
									String resultado = user.cancelarReserva(id);
									System.out.println(resultado);
							} catch (NumberFormatException e) {
									System.out.println("Erro: O identificador fornecido não é um número válido.");
							}
							
									break;
							case 7:
									System.out.println("\nVocê escolheu: Cadastrar Novo Quarto");
									System.out.println("Digite o numero do quarto:");
									int numeroQuarto = s.nextInt();

									s.nextLine();

									System.out.println("Digite o valor da diaria do quarto:");
									String diariaStr = s.nextLine(); 

									BigDecimal diaria = new BigDecimal(diariaStr);

									System.out.println("Digite o tipo de quarto 1-SIMPLES 2-DUPLO 3-SUITE");
									int tipoQuarto = s.nextInt();
									limparTela();
									System.out.println(user.cadastrarQuarto(numeroQuarto, diaria, tipoQuarto));

									break;
							case 8:
									System.out.println("\nVocê escolheu: Lista de Reservas");
									limparTela();
									List<Reserva> reservas = user.listarReservas();
									for(Reserva reserva : reservas){
										System.out.println("Cliente:" + reserva.getCpfUsuario() + " Data checkin: " + reserva.getDataEntrada() + " Data check-out: " + reserva.getDataSaida() + " Status:" + reserva.getStatus() + " Valor estadia: " + reserva.getValorTotal());
									}
									break;

							default:
									printWithColor("\nOpção inválida! Tente novamente.\n", "1;31");
								break;
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
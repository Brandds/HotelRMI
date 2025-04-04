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

public class BancoClient {
	public static void main(String[] args) {
		try {
						UsuarioService user = (UsuarioService) Naming.lookup("rmi://192.168.15.7/UsuarioService");
            
            Scanner s = new Scanner(System.in);
						System.out.println("1 - Cadastrar Cliente");
						System.out.println("2 - Consultar Quartos disponveis");
						System.out.println("3 - Fazer Reserva");
						System.out.println("4 - Quartos disponiveis");
						System.out.println("5 - Buscar sua reserva");
						System.out.println("6 - Cancelar a reserva");
						System.out.println("7 - Cadastrar novo quarto");
						System.out.println("8 - Lista reservas");
						int opcao = s.nextInt();
						s.nextLine();

						switch (opcao) {
							case 1:
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
								break;
							case 2:
									try {
										System.out.println("Digite a data de reserva:");
										String dataStr = s.nextLine();
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										Date data = sdf.parse(dataStr); // Converte a string para Date
				
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

										String response = user.fazerReserva(dataEntradaConvert, dataSaidaConvert, cpfUsuario, numeroQuarto);
										System.out.println(response);

									} catch (Exception e) {
										e.printStackTrace();
									}
									break;
							case 4:
							for(Quarto quarto : user.listarQuartosDisponiveis()){
								System.out.println("Numero quarto: " + " " + quarto.getNumeroQuarto());
							}
									break;
							case 5:
									System.out.println("Digite seu CPF:");
									String userCpf = s.nextLine();
									System.out.println(user.buscarReserva(userCpf));
									break;
							case 6:
							System.out.println("Digite o identificador da reserva para cancelar:");
							String idStr = s.nextLine(); 
							
							try {
									Long id = Long.parseLong(idStr); 
									
									String resultado = user.cancelarReserva(id);
									System.out.println(resultado);
							} catch (NumberFormatException e) {
									System.out.println("Erro: O identificador fornecido não é um número válido.");
							}
							
									break;
							case 7:
									System.out.println("Digite o numero do quarto:");
									int numeroQuarto = s.nextInt();

									s.nextLine();

									System.out.println("Digite o valor da diaria do quarto:");
									String diariaStr = s.nextLine(); 

									BigDecimal diaria = new BigDecimal(diariaStr);

									System.out.println("Digite o tipo de quarto 1-SIMPLES 2-DUPLO 3-SUITE");
									int tipoQuarto = s.nextInt();

									System.out.println(user.cadastrarQuarto(numeroQuarto, diaria, tipoQuarto));

									break;
								case 8:
									List<Reserva> reservas = user.listarReservas();
									for(Reserva reserva : reservas){
										System.out.println("Cliente:" + reserva.getCpfUsuario() + " Data checkin: " + reserva.getDataEntrada() + " Data check-out: " + reserva.getDataSaida() + " Status:" + reserva.getStatus() + " Valor estadia: " + reserva.getValorTotal());
									}
									break;

									default:
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
package server.Service;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import server.model.Quarto;
import server.model.Reserva;

public interface UsuarioService extends Remote {
  String cadastrar(String nome, String cpf, String tipoPerfil) throws RemoteException;
  List<Quarto> consultarQuartosDisponveis(Date data)throws RemoteException;
  String fazerReserva(Date dataEntrada, Date dataSaida, String cpf, int quarto) throws RemoteException;
  List<Quarto> listarQuartosDisponiveis() throws RemoteException;
  String buscarReserva(String cpf) throws RemoteException;
  String cancelarReserva(Long id) throws RemoteException;
  String cadastrarQuarto(int numeroQuarto, BigDecimal valorDiaria, int tipo) throws RemoteException;
  List<Reserva> listarReservas() throws RemoteException;
}

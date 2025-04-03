package server.Service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import server.model.Quarto;

public interface UsuarioService extends Remote {
  String cadastrar(String nome, String cpf, String tipoPerfil) throws RemoteException;
  List<Quarto> consultarQuartosDisponveis(Date data)throws RemoteException;
  String fazerReserva(Date dataEntrada, Date dataSaida, String cpf, int quarto) throws RemoteException;
}

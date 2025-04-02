package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BancoInterface extends Remote {
	
	public int addCliente() throws RemoteException;
	
	public double obterSaldo(int id) throws RemoteException;
	
	public void depositar(int id, double valor) throws RemoteException;
	
	public double sacar(int id, double valor) throws RemoteException;
}

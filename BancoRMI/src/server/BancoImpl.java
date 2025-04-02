package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class BancoImpl extends UnicastRemoteObject implements BancoInterface 
{	
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, Client> contas;
	
	public BancoImpl() throws RemoteException {
		super();
		this.contas = new HashMap<Integer, Client>();
	}
	
	@Override
	public double obterSaldo(int id) throws RemoteException {
		return this.contas.get(id).getSaldo();
	}

	@Override
	public void depositar(int id, double valor) throws RemoteException {
		double saldo = this.contas.get(id).getSaldo();
		this.contas.get(id).setSaldo(saldo + valor);
	}

	@Override
	public double sacar(int id, double valor) throws RemoteException {
		double saldo = this.contas.get(id).getSaldo();
		if(saldo < valor) {
			return -1;
		}
		else {
			this.contas.get(id).setSaldo(saldo - valor);
			return valor;
		}
	}

	@Override
	public int addCliente() throws RemoteException {
		int numClients = this.contas.size();
		int id = numClients+1;
		this.contas.put(id, new Client(id));
		return id;
	}

}
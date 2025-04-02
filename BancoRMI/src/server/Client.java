package server;

import java.rmi.RemoteException;

public class Client {
	private int numero;
	private double saldo;
	
	public Client(int id) {
		this.numero = id;
		this.saldo = 0;
	}
	
	public double getSaldo() throws RemoteException {                 
		return saldo;
	}
	
	public void setSaldo(double valor) {
		this.saldo = valor;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}

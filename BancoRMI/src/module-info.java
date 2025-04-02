/**
 * 
 */
/**
 * 
 */
module BancoRMI {
	requires java.rmi;
	requires java.sql;
	exports server to java.rmi;
	exports server.Service;
}
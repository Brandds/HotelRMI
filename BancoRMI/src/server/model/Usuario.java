package server.model;

import server.Enum.TipoPerfilEnum;

public class Usuario {

  private static final long serialVersionUID = 1L;
  
  private Long id;
  private String nome;
  private String cpf;
  private TipoPerfilEnum tipoPerfil;

  public Usuario(String nome, String cpf){
    this.nome = nome;
    this.cpf = cpf;
  }

  public String getNome(){
    return this.nome;
  }
}

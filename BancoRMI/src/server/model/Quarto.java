package server.model;

import java.io.Serializable;
import java.math.BigDecimal;

import server.Enum.TipoQuartoEnum;

public class Quarto implements Serializable{

  private static final long serialVersionUID = 1L;
  
  private Long id;
  private Integer numeroQuarto;
  private BigDecimal diaria;
  private TipoQuartoEnum tipo;
  private boolean disponivel;

  public void setNumeroQuarto(Integer numero){
    this.numeroQuarto = numero;
  }
  public void setTipoQuartoEnum(String tipo){
    this.tipo = TipoQuartoEnum.valueOf(tipo.toUpperCase());
  }

  public Integer getNumeroQuarto(){
    return this.numeroQuarto;
  }
  public String getTipoQuartoEnum(){
    return this.tipo.toString();
  }

}

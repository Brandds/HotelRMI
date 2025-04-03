package server.model;

import java.math.BigDecimal;
import java.util.Date;

import server.Enum.StatusReservaEnum;

public class Reserva {
  private static final long serialVersionUID = 1L;
  
  private Long id;
  private String cpf_usuario;
  private Long id_quarto;
  private Date dataEntrada;
  private Date dataSaida;
  private StatusReservaEnum status;
  private BigDecimal valortTotal;
  private Long id_reserva;
}

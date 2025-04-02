package server.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import server.Enum.StatusReservaEnum;

public class Reserva {
  private static final long serialVersionUID = 1L;
  
  private Long id;
  private Usuario usuario;
  private Hotel hotel;
  private List<Quarto> quartos;
  private Date dataEntrada;
  private Date dataSaida;
  private StatusReservaEnum status;
  private BigDecimal valortTotal;
}

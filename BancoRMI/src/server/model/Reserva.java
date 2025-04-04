package server.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import server.Enum.StatusReservaEnum;

public class Reserva  implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String cpf_usuario;
    private Long id_quarto;
    private Date dataEntrada;
    private Date dataSaida;
    private StatusReservaEnum status;
    private BigDecimal valortTotal;
    private Long id_reserva;

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCpfUsuario(String cpf) {
        this.cpf_usuario = cpf;
    }

    public void setIdQuarto(Long idQuarto) {
        this.id_quarto = idQuarto;
    }

    public void setDataEntrada(Date data) {
        this.dataEntrada = data;
    }

    public void setDataSaida(Date data) {
        this.dataSaida = data;
    }

    public void setStatus(StatusReservaEnum status) {
        this.status = status;
    }

    public void setValorTotal(BigDecimal valor) {
        this.valortTotal = valor;
    }

    public void setIdReserva(Long idReserva) {
        this.id_reserva = idReserva;
    }

    // Getters
    public Long getId() {
        return this.id;
    }

    public String getCpfUsuario() {
        return this.cpf_usuario;
    }

    public Long getIdQuarto() {
        return this.id_quarto;
    }

    public Date getDataEntrada() {
        return this.dataEntrada;
    }

    public Date getDataSaida() {
        return this.dataSaida;
    }

    public StatusReservaEnum getStatus() {
        return this.status;
    }

    public BigDecimal getValorTotal() {
        return this.valortTotal;
    }

    public Long getIdReserva() {
        return this.id_reserva;
    }
}

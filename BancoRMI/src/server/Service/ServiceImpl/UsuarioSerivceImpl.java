package server.Service.ServiceImpl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import server.Service.UsuarioService;
import server.model.Quarto;

public class UsuarioSerivceImpl extends UnicastRemoteObject implements UsuarioService {
  private static final long serialVersionUID = 1L;
  private Connection conn;

  public UsuarioSerivceImpl() throws RemoteException, SQLException {
    super();
  
    try {
        // Carrega o driver MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Estabelece a conexão com o banco de dados
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "06022002");
    } catch (ClassNotFoundException e) {
        // Caso o driver não seja encontrado
        e.printStackTrace();
        throw new SQLException("Driver MySQL não encontrado.");
    } catch (SQLException e) {
        e.printStackTrace();
    }

  }

  @Override
  public String cadastrar(String nome, String cpf, String tipoPerfil) {
    PreparedStatement checkStmt = null;
    PreparedStatement stmt = null;
    ResultSet checkRs = null;
    ResultSet rs = null;

    try {
        String checkSql = "SELECT COUNT(*) FROM usuario WHERE cpf = ?";
        checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, cpf);
        checkRs = checkStmt.executeQuery();

        if (checkRs.next() && checkRs.getInt(1) > 0) {
            return "Usuário já existe";
        }

        // Se o usuário não existir, insere o novo usuário
        String sql = "INSERT INTO usuario (nome, cpf, tipoPerfil) VALUES (?, ?, ?)";
        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nome);
        stmt.setString(2, cpf);
        stmt.setString(3, tipoPerfil);
        stmt.executeUpdate();

        rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);  // Obtém o ID gerado
            return "Usuário cadastrado com sucesso. ID: " + id;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return "Erro ao cadastrar usuário: " + e.getMessage();
    } finally {
        try {
            if (checkRs != null) checkRs.close();
            if (rs != null) rs.close();
            if (checkStmt != null) checkStmt.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return "Erro desconhecido";
}

    @Override
    public List<Quarto> consultarQuartosDisponveis(Date data) {
        List<Quarto> quartos = new ArrayList<>();
        String sql = "SELECT q.* " +
                    "FROM quarto q " +
                    "LEFT JOIN reserva r ON q.id = r.id_quarto " +
                    "AND r.status != 'CANCELADA' " +
                    "AND r.dataEntrada <= ? AND r.dataSaida >= ? " +
                    "WHERE q.disponivel = TRUE " +
                    "AND r.id IS NULL";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(data.getTime()));
            stmt.setDate(2, new java.sql.Date(data.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Quarto quarto = new Quarto();
                quarto.setNumeroQuarto(rs.getInt("numeroQuarto")); // ou "numero_quarto" dependendo do nome real
                quarto.setTipoQuartoEnum(rs.getString("tipo"));     // ou "tipo_quarto"
                quartos.add(quarto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quartos;
    }

    @Override
    public String fazerReserva(Date dataEntrada, Date dataSaida, String cpf, int numeroQuarto) throws RemoteException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            String sql = "SELECT q.id, q.diaria  " +
                         "FROM quarto q " +
                         "LEFT JOIN reserva r ON q.id = r.id_quarto " +
                         "AND r.status != 'CANCELADA' " +
                         "AND NOT (r.dataSaida <= ? OR r.dataEntrada >= ?) " +
                         "WHERE q.disponivel = TRUE " +
                         "AND q.numeroQuarto = ? " +
                         "AND r.id IS NULL";
    
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(dataEntrada.getTime())); // data de entrada nova reserva
            stmt.setDate(2, new java.sql.Date(dataSaida.getTime()));   // data de saída nova reserva
            stmt.setInt(3, numeroQuarto);
    
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                int idQuarto = rs.getInt("id");
                BigDecimal diariaTotal = rs.getBigDecimal("diaria");

    
                // Quarto disponível - agora inserir a reserva
                String insertSql = "INSERT INTO reserva (dataEntrada, dataSaida, cpf_usuario , id_quarto, valorTotal, status) " +
                                   "VALUES (?, ?, ?, ?, ?, 'ATIVA')";
    
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setDate(1, new java.sql.Date(dataEntrada.getTime()));
                    insertStmt.setDate(2, new java.sql.Date(dataSaida.getTime()));
                    insertStmt.setString(3, cpf);
                    insertStmt.setInt(4, idQuarto);
                    BigDecimal totalDiaria = valorTotalDiaria(dataEntrada, dataSaida, diariaTotal);
                    insertStmt.setBigDecimal(5, totalDiaria);

                    insertStmt.executeUpdate();
                }
    
                return "Reserva realizada com sucesso para o quarto " + numeroQuarto;
    
            } else {
                return "Quarto não disponível para o período selecionado.";
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao realizar reserva: " + e.getMessage();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    private BigDecimal valorTotalDiaria(Date dataEntrda, Date dataSaida, BigDecimal diaria){
        long diffInMillis = dataSaida.getTime() - dataEntrda.getTime();
        long dias = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        
        BigDecimal total = diaria.multiply(new BigDecimal(dias));

        return total;
    }
    

  
}

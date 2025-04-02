package server.Service.ServiceImpl;

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
        String sql = "INSERT INTO usuario (nome, cpf, tipo_perfil) VALUES (?, ?, ?)";
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
        String sql = "SELECT qrt.numero_quarto , qrt.tipo_quarto FROM " + 
            "reserva rsv " + 
            " JOIN quarto qrt ON rsv.quarto_id = qrt.id " +
            " WHERE rsv.data_saida < ? " + 
            "AND rsv.status = 'CONCLUIDA'";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1,new  java.sql.Date(data.getTime()));
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Quarto quarto = new Quarto();
                quarto.setNumeroQuarto(rs.getInt("numero_quarto"));
                quarto.setTipoQuartoEnum(rs.getString("tipo_quarto"));
                quartos.add(quarto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quartos;
        
    }

  
}

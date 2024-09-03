package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import models.Habilidad;
import models.Mision;
import models.Ninja;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KonohaDatabaseManager {

    public List<Ninja> listarNinjas() {
        List<Ninja> ninjas = new ArrayList<>();
        String sql = "SELECT n.ID, n.Nombre, n.Rango, n.Aldea, h.Nombre as HabilidadNombre, h.Descripcion as HabilidadDescripcion " +
                     "FROM Ninja n LEFT JOIN Habilidad h ON n.ID = h.ID_Ninja";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nombre = resultSet.getString("Nombre");
                String rango = resultSet.getString("Rango");
                String aldea = resultSet.getString("Aldea");

                Ninja ninja = new Ninja(id, nombre, rango, aldea);

                String habilidadNombre = resultSet.getString("HabilidadNombre");
                String habilidadDescripcion = resultSet.getString("HabilidadDescripcion");

                if (habilidadNombre != null) {
                    Habilidad habilidad = new Habilidad(habilidadNombre, habilidadDescripcion);
                    ninja.addHabilidad(habilidad);
                }

                ninjas.add(ninja);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ninjas;
    }

    public Ninja obtenerNinjaPorId(int id) {
        Ninja ninja = null;
        String sql = "SELECT * FROM Ninja WHERE ID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("Nombre");
                    String rango = resultSet.getString("Rango");
                    String aldea = resultSet.getString("Aldea");
                    ninja = new Ninja(id, nombre, rango, aldea);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ninja;
    }

    public List<Mision> misionesParaNinja(Ninja ninja) {
        List<Mision> misiones = new ArrayList<>();
        String sql = "SELECT m.ID, m.Descripcion, m.Rango, m.Recompensa " +
                     "FROM Mision m JOIN MisionNinja mn ON m.ID = mn.ID_Mision " +
                     "WHERE mn.ID_Ninja = ? AND mn.FechaFin IS NULL";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ninja.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String descripcion = resultSet.getString("Descripcion");
                    String rango = resultSet.getString("Rango");
                    double recompensa = resultSet.getDouble("Recompensa");

                    Mision mision = new Mision(id, descripcion, rango, recompensa);
                    misiones.add(mision);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return misiones;
    }

    public List<Mision> misionesCompletadasPorNinja(Ninja ninja) {
        List<Mision> misiones = new ArrayList<>();
        String sql = "SELECT m.ID, m.Descripcion, m.Rango, m.Recompensa " +
                     "FROM Mision m JOIN MisionNinja mn ON m.ID = mn.ID_Mision " +
                     "WHERE mn.ID_Ninja = ? AND mn.FechaFin IS NOT NULL";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ninja.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String descripcion = resultSet.getString("Descripcion");
                    String rango = resultSet.getString("Rango");
                    double recompensa = resultSet.getDouble("Recompensa");

                    Mision mision = new Mision(id, descripcion, rango, recompensa);
                    misiones.add(mision);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return misiones;
    }

    public void asignarMision(int idNinja, int idMision, LocalDate fechaInicio) {
    String consultaExistente = "SELECT COUNT(*) FROM MisionNinja WHERE ID_Ninja = ? AND ID_Mision = ?";
    String insertarMision = "INSERT INTO MisionNinja (ID_Ninja, ID_Mision, FechaInicio) VALUES (?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmtExistente = conn.prepareStatement(consultaExistente);
         PreparedStatement stmtInsertar = conn.prepareStatement(insertarMision)) {

        // Verificar si la misión ya está asignada
        stmtExistente.setInt(1, idNinja);
        stmtExistente.setInt(2, idMision);
        ResultSet rs = stmtExistente.executeQuery();
        rs.next();
        if (rs.getInt(1) > 0) {
            System.out.println("La misión ya está asignada a este ninja.");
            System.out.println("Porfavor intente con otra ID.");
            return;
        }

        // Insertar la nueva asignación
        stmtInsertar.setInt(1, idNinja);
        stmtInsertar.setInt(2, idMision);
        stmtInsertar.setDate(3, Date.valueOf(fechaInicio));
        stmtInsertar.executeUpdate();
        System.out.println("Misión asignada exitosamente.");
        
    } catch (SQLException e) {
        System.err.println("Error al asignar la misión: " + e.getMessage());
    }
}


    public void marcarMisionCompletada(Ninja ninja, Mision mision, LocalDate fechaFin) {
        String sql = "UPDATE MisionNinja SET FechaFin = ? WHERE ID_Ninja = ? AND ID_Mision = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(fechaFin));
            statement.setInt(2, ninja.getId());
            statement.setInt(3, mision.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Mision> obtenerTodasLasMisionesCompletadas() {
        List<Mision> misiones = new ArrayList<>();
        String sql = "SELECT m.descripcion, m.rango, m.recompensa " +
                     "FROM Mision m " +
                     "JOIN MisionNinja mn ON m.ID = mn.ID_Mision " +
                     "WHERE mn.fechaFin IS NOT NULL";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String descripcion = resultSet.getString("descripcion");
                String rango = resultSet.getString("rango");
                double recompensa = resultSet.getDouble("recompensa");

                Mision mision = new Mision(0, descripcion, rango, recompensa); // El ID no es necesario para mostrar
                misiones.add(mision);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return misiones;
    }
}

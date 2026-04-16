package co.edu.poli.examen2_torres.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import co.edu.poli.examen2_torres.modelo.asegurado;
import co.edu.poli.examen2_torres.modelo.seguro;
import co.edu.poli.examen2_torres.modelo.seguroVehiculo;
import co.edu.poli.examen2_torres.modelo.seguroVida;

public class DAOseguro implements CRUD<seguro> {

    @Override
    public String create(seguro s) throws Exception {

        Connection con = ConexionBD.getInstancia().getConexion();
        con.setAutoCommit(false);

        try {

            // =========================
            // INSERT TABLA PADRE
            // =========================
            String SQL_INSERT_SEGURO =
                    "INSERT INTO seguro (numero, fecha_exp, estado, asegurado_id) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(SQL_INSERT_SEGURO);
            ps.setString(1, s.getNumero());
            ps.setString(2, s.getFechaExp());
            ps.setBoolean(3, s.isEstado());
            ps.setString(4, s.getasegurado().getId());
            ps.executeUpdate();

            // =========================
            // INSERT SUBCLASE
            // =========================
            String sql;

            if (s instanceof seguroVida) {
                sql = "INSERT INTO seguro_vida (numero, beneficiario) VALUES (?, ?)";
            } else {
                sql = "INSERT INTO seguro_vehiculo (numero, marca) VALUES (?, ?)";
            }

            ps = con.prepareStatement(sql);
            ps.setString(1, s.getNumero());

            if (s instanceof seguroVida) {
                ps.setString(2, ((seguroVida) s).getBeneficiario());
            } else {
                ps.setString(2, ((seguroVehiculo) s).getMarca());
            }

            ps.executeUpdate();

            con.commit();

            return "✔ " + s.getClass().getSimpleName() +
                    " [" + s.getNumero() + "] guardado correctamente.";

        } catch (Exception e) {

            // 🔥 IMPORTANTE: ver error real
            con.rollback();
            e.printStackTrace();

            throw new RuntimeException("ERROR EN CREATE: " + e.getMessage(), e);

        } finally {
            con.setAutoCommit(true);
        }
    }

    @Override
    public <K> seguro readone(K num) throws Exception {

        Connection con = ConexionBD.getInstancia().getConexion();

        // =========================
        // BUSCAR SEGURO VIDA
        // =========================
        String SQL_VIDA =
                "SELECT s.numero, s.fecha_exp, s.estado, " +
                "a.id AS asegurado_id, a.nombre AS asegurado_nombre, " +
                "v.beneficiario " +
                "FROM seguro_vida v " +
                "INNER JOIN seguro s ON v.numero = s.numero " +
                "INNER JOIN asegurado a ON s.asegurado_id = a.id " +
                "WHERE v.numero = ?";

        PreparedStatement ps = con.prepareStatement(SQL_VIDA);
        ps.setString(1, (String) num);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new seguroVida(
                    rs.getString("numero"),
                    rs.getString("fecha_exp"),
                    rs.getBoolean("estado"),
                    new asegurado(
                            rs.getString("asegurado_id"),
                            rs.getString("asegurado_nombre")
                    ),
                    rs.getString("beneficiario")
            );
        }

        // =========================
        // BUSCAR SEGURO VEHICULO
        // =========================
        String SQL_VEHICULO =
                "SELECT s.numero, s.fecha_exp, s.estado, " +
                "a.id AS asegurado_id, a.nombre AS asegurado_nombre, " +
                "v.marca " +
                "FROM seguro_vehiculo v " +
                "INNER JOIN seguro s ON v.numero = s.numero " +
                "INNER JOIN asegurado a ON s.asegurado_id = a.id " +
                "WHERE v.numero = ?";

        ps = con.prepareStatement(SQL_VEHICULO);
        ps.setString(1, (String) num);
        rs = ps.executeQuery();

        if (rs.next()) {
            return new seguroVehiculo(
                    rs.getString("numero"),
                    rs.getString("fecha_exp"),
                    rs.getBoolean("estado"),
                    new asegurado(
                            rs.getString("asegurado_id"),
                            rs.getString("asegurado_nombre")
                    ),
                    rs.getString("marca")
            );
        }

        return null;
    }

    @Override
    public List<seguro> readall() {
        return null;
    }
}

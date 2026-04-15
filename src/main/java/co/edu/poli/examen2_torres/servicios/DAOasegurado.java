package co.edu.poli.examen2_torres.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.examen2_torres.modelo.asegurado;

public class DAOasegurado implements CRUD<asegurado> {

    @Override
    public String create(asegurado a) throws Exception {

        Connection con = ConexionBD.getInstancia().getConexion();

        String sql = "INSERT INTO asegurado (id, nombre) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, a.getId());
        ps.setString(2, a.getNombre());

        ps.executeUpdate();

        return "Asegurado creado correctamente";
    }

    @Override
    public <K> asegurado readone(K id) throws Exception {

        Connection con = ConexionBD.getInstancia().getConexion();

        String sql = "SELECT id, nombre FROM asegurado WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, (String) id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new asegurado(
                    rs.getString("id"),
                    rs.getString("nombre")
            );
        }

        return null;
    }

    @Override
    public List<asegurado> readall() throws Exception {

        Connection con = ConexionBD.getInstancia().getConexion();
        List<asegurado> lista = new ArrayList<>();

        String sql = "SELECT id, nombre FROM asegurado";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            lista.add(new asegurado(
                    rs.getString("id"),
                    rs.getString("nombre")
            ));
        }

        return lista;
    }
}

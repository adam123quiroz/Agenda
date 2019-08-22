package com.company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBaseContacto extends Database {

    private PreparedStatement selectContacto = null;
    private PreparedStatement insertContacto = null;
    private PreparedStatement selectMaquinaPorCodigo = null;

    public DataBaseContacto() {

        try {

            insertContacto = getConnection().prepareStatement(
                    "INSERT INTO `contacto`(`id`, `nombre`, "
                            + "`apellido`, `email`, `adress`, `numero`) VALUES "
                            + "(? ,? ,? ,? ,? ,? )" );

            selectContacto = getConnection().prepareStatement(
                    "SELECT * FROM `contacto`");

//			deleteMaquina = getConnection().prepareStatement(
//					"DELETE FROM `maquina`"
//					+ "WHERE `maquina`.`COD_MAQUINA` = ?" );

//            selectMaquinaPorCodigo = getConnection().prepareStatement(
//                    "SELECT * FROM `maquina` "
//                            + "WHERE `COD_MAQUINA` = ?");


        } catch (SQLException sqlException) {

            sqlException.printStackTrace();
            System.exit( 1 );
        }
    }//fin del constructor

    public List<Contacto> getAllContactos() {

        List <Contacto>  listContactos = null;
        ResultSet resultSet = null;
        try {

            resultSet = selectContacto.executeQuery();//ejecuta los queries
            listContactos = new ArrayList<>();

            while(resultSet.next()) {
                listContactos.add( new Contacto(
                        resultSet.getString("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("email"),
                        resultSet.getString("adress"),
                        resultSet.getInt("numero")));
            }//fin del while

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }//end try catch

        finally {
            try	{
                Objects.requireNonNull(resultSet).close();
            }catch ( SQLException sqlException ) {
                sqlException.printStackTrace();
                close();
            }	// end try catch
        } // end finally

        return listContactos;
    }

    public Contacto getMaquinaPorCodigo(String cod) {

        Contacto contacto = null;
        ResultSet resultSet = null;
        try {

            selectMaquinaPorCodigo.setString(1, cod);
            resultSet = selectMaquinaPorCodigo.executeQuery();//ejecuta los queries
            contacto = new Contacto();

            while(resultSet.next()) {
                contacto.setId(resultSet.getString("id"));
                contacto.setName(resultSet.getString("nombre"));
                contacto.setApellido(resultSet.getString("apellido"));
                contacto.setEmail(resultSet.getString("email"));
                contacto.setAdress(resultSet.getString("adress"));
                contacto.setNumber(resultSet.getInt("numero"));

            }//fin del while

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }//end try catch

        finally {
            try	{
                Objects.requireNonNull(resultSet).close();
            }catch ( SQLException sqlException ) {
                sqlException.printStackTrace();
                close();
            }	// end try catch
        } // end finally

        return contacto;
    }//fin del metodo

    public void setMaquina(Contacto contacto) {

        int actualizacion = 0;
        try {

            insertContacto.setString(1, contacto.getId());
            insertContacto.setString(2, contacto.getName());
            insertContacto.setString(3, contacto.getApellido());
            insertContacto.setString(4, contacto.getEmail());
            insertContacto.setString(5, contacto.getAdress());
            insertContacto.setInt(6, contacto.getNumber());

            actualizacion = insertContacto.executeUpdate();

        } catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
            close();
        }//fin del ry catch

    }

    private void close() {
        try {
            getConnection().close();
        } catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
        } // end try catch
    } // end method close
}

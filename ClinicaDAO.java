package Dao;
/**
 *
 * @author Jhossen
 */
import Dto.CitasDTO;
import Dto.ConsultasDTO;
import Dto.PagosDTO;
import Dto.PacienteDTO;
import Dto.abonoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ClinicaDAO {
    private Connection conn;
    
    public ClinicaDAO(Connection conn){
        this.conn = conn;
    }
    
    private static final String obtenerPacientes = "select * from pacientes";
    private static final String insertarPacientes = "insert into pacientes values"+"(null,?,?,?,?,?,?)";
    private static final String eliminarPacientes = "DELETE from pacientes where nombre= (?)";
    private static final String actualizarPaciente = "UPDATE pacientes SET nombre=?,presupuesto=?,telefono=?,direccion=?,sexo=?,fecha_nacimiento=? WHERE id_paciente =? ";
    
    private static final String insertarCita = "insert into citas values"+"(null,?,?,?,?,?)";
    private static final String eliminarCita = "DELETE from citas where id_cita = (?)";
    private static final String actualizarCitas = "UPDATE citas SET atendido=? WHERE id_cita=?";
    
    private static final String obtenerConsultas = "select * from tipo_de_consulta";
    private static final String insertarConsulta = "insert into tipo_de_consulta values"+"(null,?,?)";
    private static final String eliminarConsulta ="DELETE from tipo_de_consulta where consulta= (?)";
    private static final String actualizarConsulta="UPDATE tipo_de_consulta SET consulta=?,precio=? WHERE id_consulta=? ";
    
    private static final String insertarPago = "insert into pagos values"+"(null,?,?,?,?,?,?)";
    private static final String eliminarPago = "DELETE from pagos where id_pago= (?)";
    
    private static final String insertarAbono = "insert into abonos values"+"(null,?,?,?)";
    private static final String actualizarAbono = "UPDATE abonos SET fecha_abono=?,cantidad=?,id_cita=? WHERE id_abonos=? ";
    private static final String EliminarAbono ="DELETE from abonos where id_abonos= (?)";
    
    public List<PacienteDTO> getPacientes() throws SQLException{
        List<PacienteDTO> listaPacientes = new ArrayList<PacienteDTO>();
        Statement sentencia;
        ResultSet rs;
        PacienteDTO pacientes;
        sentencia = conn.createStatement();
        rs = sentencia.executeQuery(obtenerPacientes);
        while (rs.next()){
            pacientes = new PacienteDTO();
            pacientes.setIdPaciente(rs.getInt("id_paciente"));
            pacientes.setNombre(rs.getString("nombre"));
            pacientes.setPresupuesto(rs.getFloat("presupuesto"));
            pacientes.setTelefono(rs.getString("telefono"));
            pacientes.setDireccion(rs.getString("direccion"));
            pacientes.setSexo(rs.getString("sexo"));
            pacientes.setFechaN(rs.getString("fecha_nacimiento"));
            listaPacientes.add(pacientes);
        }
        rs.close();
        sentencia.close();        
        return listaPacientes;
    }
    
    public int insertarPacientes(PacienteDTO paciente) throws SQLException{
        PreparedStatement sentencia;
        ResultSet rs;
        int idPaciente = 0;
        sentencia = conn.prepareStatement(insertarPacientes, Statement.RETURN_GENERATED_KEYS);
        sentencia.setString(1, paciente.getNombre());
        sentencia.setFloat(2, paciente.getPresupuesto());
        sentencia.setString(3, paciente.getTelefono());
        sentencia.setString(4, paciente.getDireccion());
        sentencia.setString(5, paciente.getSexo());
        sentencia.setString(6, paciente.getFechaN());
          
        sentencia.executeUpdate();
        rs = sentencia.getGeneratedKeys();
        
        if (rs.next()){
            idPaciente = rs.getInt(1);
        }
        
        return idPaciente;
    }

    public void eliminarPaciente (String cod)throws SQLException{
        PreparedStatement sentencia = conn.prepareStatement(eliminarPacientes);
        sentencia.setString(1, cod);
        sentencia.execute();
        sentencia.close();
    }
    
    public void actualizarPaciente(String cod, String nombre, String presupuesto, String telefono, String direccion, String sexo, String edad)throws SQLException{
        PreparedStatement sentencia = conn.prepareStatement(actualizarPaciente);
        sentencia.setString(1, nombre);
        sentencia.setString(2, presupuesto);
        sentencia.setString(3, telefono);
        sentencia.setString(4, direccion);
        sentencia.setString(5, sexo);
        sentencia.setString(6, edad);
        sentencia.setString(7, String.valueOf(cod));
        
        sentencia.execute();
        sentencia.close();
    }   
    
    public int insertarCita (CitasDTO cita)throws SQLException{
        PreparedStatement sentencia;
        ResultSet rs;
        int idCita = 0;
        sentencia = conn.prepareStatement(insertarCita, Statement.RETURN_GENERATED_KEYS);
        sentencia.setString(1, cita.getFechaCita());
        sentencia.setString(2, cita.getHora());
        sentencia.setString(3, cita.getAtendido());
        sentencia.setInt(4, cita.getIdPaciente());
        sentencia.setInt(5, cita.getIdConsulta());
        
        sentencia.executeUpdate();
        rs = sentencia.getGeneratedKeys();
        
        if (rs.next()){
            idCita = rs.getInt(1);
        }
        
        return idCita;     
    }
    
    public void eliminarCita(String cod)throws SQLException{
        PreparedStatement sentencia = conn.prepareStatement(eliminarCita);
        sentencia.setString(1, cod);
        sentencia.execute();
        sentencia.close();
    }
    
    public void actualizarCita(String cod, String atendido)throws SQLException{
        PreparedStatement sentencia = conn.prepareStatement(actualizarCitas);
        sentencia.setString(1, atendido);
        sentencia.setString(2, String.valueOf(cod));
        
        sentencia.execute();
        sentencia.close();
    }
    
    public List<ConsultasDTO> getConsultas()throws SQLException{
        List<ConsultasDTO> listaConsulta = new ArrayList<ConsultasDTO>();
        Statement sentencia = conn.createStatement();
        ResultSet rs = sentencia.executeQuery(obtenerConsultas);
        ConsultasDTO consulta;
        
        while(rs.next()){
            consulta = new ConsultasDTO();
            
            consulta.setIdConsulta(rs.getInt("id_consulta"));
            consulta.setConsultas(rs.getString("consulta"));
            consulta.setPrecio(rs.getFloat("precio"));
            listaConsulta.add(consulta);
        }
        rs.close();
        sentencia.close();
        return listaConsulta;
    }
    
    public int insertarConsulta(ConsultasDTO consulta)throws SQLException{
        PreparedStatement sentencia;
        ResultSet rs;
        int idConsulta = 0;
        sentencia = conn.prepareStatement(insertarConsulta,Statement.RETURN_GENERATED_KEYS);
        sentencia.setString(1, consulta.getConsultas());
        sentencia.setFloat(2, consulta.getPrecio());
        
        sentencia.executeUpdate();
        rs = sentencia.getGeneratedKeys();
        
        if(rs.next()){
            idConsulta = rs.getInt(1);
        }
        
        return idConsulta;
    }
    
    public void eliminarConsulta(String cod)throws SQLException{
        PreparedStatement sentencia = conn.prepareStatement(eliminarConsulta);
        sentencia.setString(1, cod);
        sentencia.execute();
        sentencia.close();
    }
    
    public void actualizarConsulta(String cod, String consulta, String pago)throws SQLException{
        PreparedStatement sentencia = conn.prepareStatement(actualizarConsulta);
        sentencia.setString(1, consulta);
        sentencia.setString(2, pago);
        sentencia.setString(3, String.valueOf(cod));
        
        sentencia.execute();
        sentencia.close();
    }
    
    
    public int insertarPagos(PagosDTO pago)throws SQLException{
        int idPago =0;
        PreparedStatement sentencia;
        ResultSet rs;
        sentencia = conn.prepareStatement(insertarPago, Statement.RETURN_GENERATED_KEYS);
        sentencia.setInt(1, pago.getIdcita());
        sentencia.setString(2, pago.getDia());
        sentencia.setString(3, pago.getMes());
        sentencia.setString(4, pago.getAño());
        sentencia.setFloat(5, pago.getCantidad());
        sentencia.setString(6, pago.getTipoPago());
        
        sentencia.executeUpdate();
        rs = sentencia.getGeneratedKeys();
        
        if(rs.next()){
            idPago = rs.getInt(1);
        }
        return idPago;
    }
    
    public void eliminarpagos (String cod)throws SQLException{
        PreparedStatement sentencia = conn.prepareStatement(eliminarPago);
        sentencia.setString(1, cod);
        sentencia.execute();
        sentencia.close();
    }
    
    public int insertarAbono(abonoDTO abono)throws SQLException{
        int idAbono = 0;
        PreparedStatement sentencia;
        ResultSet rs;
        sentencia = conn.prepareStatement(insertarAbono, Statement.RETURN_GENERATED_KEYS);
        sentencia.setString(1, abono.getFecha());
        sentencia.setFloat(2, abono.getCantidad());
        sentencia.setInt(3, abono.getIdCita());
        
        sentencia.executeUpdate();
        rs = sentencia.getGeneratedKeys();
        
        if(rs.next()){
            idAbono = rs.getInt(1);
        }
        return idAbono;
    }
    
    public void eliminarAbono (String cod)throws SQLException{
        PreparedStatement sentencia = conn.prepareStatement(EliminarAbono);
        sentencia.setString(1, cod);
        sentencia.execute();
        sentencia.close();
    }
    
    public void ActualizarAbono(String cod, String fecha, String cantidad, String codCita)throws SQLException{
        PreparedStatement sentencia = conn.prepareStatement(actualizarAbono);
        sentencia.setString(1, fecha);
        sentencia.setString(2, cantidad);
        sentencia.setString(3, codCita);
        sentencia.setString(4, String.valueOf(cod));
        
        sentencia.execute();
        sentencia.close();
    }
}

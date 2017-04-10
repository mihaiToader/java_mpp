package persistance.jdbc;

import model.Ticket;
import persistance.IRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Mihai on 14.03.2017.
 */
public class RepositoryTicket implements IRepository<Ticket> {

    private DataBaseManager dbm;

    public RepositoryTicket(DataBaseManager dbm) {
        this.dbm = dbm;
    }

    @Override
    public void add(Ticket e) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("insert into Ticket(idMatch,customerName) values (?,?)")){
            preStmt.setInt(1, e.getIdMatch());
            preStmt.setString(2,e.getCustomerName());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer id) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("delete from Ticket where id=?")){
            preStmt.setInt(1,id);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Ticket e, Integer id) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("update Ticket set idMatch=?, customerName=? where id=? ")){
            preStmt.setInt(1,e.getIdMatch());
            preStmt.setString(2,e.getCustomerName());
            preStmt.setInt(3,id);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public Iterable<Ticket> getAll() {
        ArrayList<Ticket> res = new ArrayList<>();
        try(Statement stmt=dbm.getConn().createStatement()){
            try(ResultSet rs=stmt.executeQuery("select * from Ticket")){
                while(rs.next()){
                    res.add(new Ticket(rs.getInt("id"),rs.getInt("idMatch"), rs.getString("customerName")));
                }
            }
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return res;
    }

    public Integer getTicketsNumberForTheMatch(Integer id){
        Integer count = 0;
        try{
            PreparedStatement stmt=dbm.getConn().prepareStatement("Select * From Ticket Where idMatch=?");
            stmt.setInt(1,id);
            try(ResultSet rs=stmt.executeQuery()){
                while(rs.next()){
                    count++;
                }
            }
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return count;
    }
}

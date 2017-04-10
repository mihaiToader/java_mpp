package persistance.jdbc;


import model.Competition;
import persistance.IRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Mihai on 21.03.2017.
 */
public class RepositoryCompetition implements IRepository<Competition> {
    private DataBaseManager dbm;

    public RepositoryCompetition(DataBaseManager dbm) {
        this.dbm = dbm;
    }

    @Override
    public void add(Competition e) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("insert into Competition(name,seats,ticketCost) values (?,?,?)")){
            preStmt.setString(1, e.getName());
            preStmt.setInt(2,e.getSeats());
            preStmt.setDouble(3,e.getTicketCost());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer id) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("delete from Competition where id=?")){
            preStmt.setInt(1,id);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Competition e, Integer id) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("update Competition set name=?, seats=?, ticketCost=? where id=? ")){
            preStmt.setString(1,e.getName());
            preStmt.setInt(2,e.getSeats());
            preStmt.setDouble(3,e.getTicketCost());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public ArrayList<Competition> getAll() {
        ArrayList<Competition> res = new ArrayList<>();
        try(Statement stmt=dbm.getConn().createStatement()){
            try(ResultSet rs=stmt.executeQuery("select * from Competition")){
                while(rs.next()){
                    res.add(new Competition(rs.getInt("id"), rs.getInt("seats"),rs.getString("name"), rs.getDouble("ticketCost")));
                }
            }
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return res;
    }

    public Competition getCompetition(Integer id){
        try{
            PreparedStatement p = dbm.getConn().prepareStatement("SELECT * FROM Competition Where id=?");
            p.setInt(1,id);
            ResultSet rs = p.executeQuery();
            rs.next();
            return new Competition(rs.getInt("id"),rs.getInt("seats"), rs.getString("name"), rs.getDouble("ticketCost"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

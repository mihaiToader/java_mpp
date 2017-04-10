package persistance.jdbc;

import model.Match;
import persistance.IRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Mihai on 14.03.2017.
 */
public class RepositoryMatch implements IRepository<Match> {
    private DataBaseManager dbm;

    public RepositoryMatch(DataBaseManager dbm) {
        this.dbm = dbm;
    }

    @Override
    public void add(Match e) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("insert into Match(name,idCompetition) values (?,?)")){
            preStmt.setString(1, e.getName());
            preStmt.setInt(2,e.getIdCompetition());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer id) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("delete from Match where id=?")){
            preStmt.setInt(1,id);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Match e, Integer id) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("update Match set name=?, idCompetition=?where id=? ")){
            preStmt.setString(1,e.getName());
            preStmt.setInt(2,e.getIdCompetition());
            preStmt.setInt(3,id);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public Iterable<Match> getAll() {
        ArrayList<Match> res = new ArrayList<>();
        try(Statement stmt=dbm.getConn().createStatement()){
            try(ResultSet rs=stmt.executeQuery("select * from Match")){
                while(rs.next()){
                    res.add(new Match(rs.getInt("id"),rs.getString("name"), rs.getInt("idCompetition")));
                }
            }
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return res;
    }

    public ArrayList<Match> getAllMatchFromCompetition(Integer id) {
        ArrayList<Match> res = new ArrayList<>();
        try{
            PreparedStatement stmt=dbm.getConn().prepareStatement("Select * From Match Where idCompetition=?");
            stmt.setInt(1,id);
            try(ResultSet rs=stmt.executeQuery()){
                while(rs.next()){
                    res.add(new Match(rs.getInt("id"),rs.getString("name"), rs.getInt("idCompetition")));
                }
            }
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return res;
    }
}

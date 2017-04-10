package persistance.jdbc;

import model.User;
import persistance.IRepository;
import persistance.IUserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Mihai on 14.03.2017.
 */
public class RepositoryUser implements IUserRepository {
    private DataBaseManager dbm;

    public RepositoryUser(DataBaseManager dbm) {
        this.dbm = dbm;
    }

    @Override
    public void add(User e) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("insert into User(name,username ,password) values (?,?,?)")){
            preStmt.setString(1, e.getName());
            preStmt.setString(3,e.getUsername());
            preStmt.setString(2,e.getPassword());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer id) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("delete from User where id=?")){
            preStmt.setInt(1,id);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(User e, Integer id) {
        try(PreparedStatement preStmt=dbm.getConn().prepareStatement("update User set name=?, username=?, password=? where id=? ")){
            preStmt.setString(1,e.getName());
            preStmt.setString(2,e.getUsername());
            preStmt.setString(3,e.getPassword());
            preStmt.setInt(4,id);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public Iterable<User> getAll() {
        ArrayList<User> res = new ArrayList<>();
        try(Statement stmt=dbm.getConn().createStatement()){
            try(ResultSet rs=stmt.executeQuery("select * from User")){
                while(rs.next()){
                    res.add(new User(rs.getInt("id"),rs.getString("name"), rs.getString("username"), rs.getString("password")));
                }
            }
        }catch(SQLException ex){
            System.err.println(ex.getSQLState());
            System.err.println(ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return res;
    }

    public User validateUser(String username, String password){
        Integer count = 0;
        User user = null;
        try{
            PreparedStatement preparedStatement = dbm.getConn().prepareStatement("Select * From User Where username=? and password=?");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                count++;
                user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (count == 1) return user;
        return null;
    }
}

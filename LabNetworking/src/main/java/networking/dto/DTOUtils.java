package networking.dto;

import model.Competition;
import model.Match;
import model.User;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 20, 2009
 * Time: 8:07:36 AM
 */
public class DTOUtils {
    public static User getFromDTO(UserDTO usdto){
        Integer id=usdto.getId();
        String pass=usdto.getPasswd();
        String username = usdto.getUsername();
        return new User(id, username, pass);

    }
    public static UserDTO getDTO(User user){
        Integer id=user.getId();
        String pass=user.getPassword();
        String username = user.getUsername();
        return new UserDTO(id, pass, username);
    }

    /*public static CompetitionsListDTO getDTO(ArrayList<Competition> competition){
        return new CompetitionsListDTO(competition);
    }

    public static ArrayList<Competition> getFromDto(CompetitionsListDTO competitionsListDTO){
        return competitionsListDTO.getCompetitions();
    }

    public static MatchesListDTO getDTO(ArrayList<Match> matches){
        return new MatchesListDTO(matches);
    }
    public static ArrayList<Match> getFromDto(MatchesListDTO matchesListDTO){
        return matchesListDTO.getMatches();
    }*/

    public static UnusualDTO getDTO(Integer id, String str, Object obj){
        return new UnusualDTO(id,str,obj);
    }




}

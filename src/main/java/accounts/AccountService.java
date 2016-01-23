package accounts;

import dbService.DbException;
import dbService.DbService;
import dbService.dataSets.UserDataSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandr on 17.01.16.
 */
public class AccountService {
    private DbService dbService;
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService(DbService dbService) {
        this.dbService = dbService;
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile user) throws DbException{
        dbService.addUser(user.getLogin(), user.getPass());
        loginToProfile.put(user.getLogin(), user);
    }

    public UserProfile getUserByLogin(String login) throws DbException{
        UserProfile profile = loginToProfile.get(login);
        if (profile == null) {
            try {
                UserDataSet user = dbService.getUserByName(login);
                profile = new UserProfile(user.getName(), user.getPass());
                loginToProfile.put(login, profile);
                return profile;
            }
            catch (DbException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public UserProfile getUserBySessionId(String sessionId){
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile){
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId){
        sessionIdToProfile.remove(sessionId);
    }
}

package dbService.dataSets;

/**
 * Created by aleksandr on 23.01.16.
 */
public class UserDataSet {
    private long id;
    private String name;
    private String pass;

    public UserDataSet(long id, String name, String pass){
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPass() {return pass;}

    @Override
    public String toString(){
        return "UserDataSet{"+"id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

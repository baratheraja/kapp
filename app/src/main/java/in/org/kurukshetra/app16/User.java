package in.org.kurukshetra.app16;

/**
 * Created by baratheraja on 13/12/15.
 */
public class User {
    private String token;
    private String authentication_token;
    private String id;
    private String kid;
    private String name;
    private String sa_id;
    private String provider;
    public User() {
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public User(String token, String authentication_token, String id, String kid, String name, String sa_id,String provider) {
        this.token = token;
        this.authentication_token = authentication_token;
        this.id = id;
        this.kid = kid;
        this.name = name;
        this.sa_id = sa_id;
        this.provider = provider;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthentication_token() {
        return authentication_token;
    }

    public void setAuthentication_token(String authentication_token) {
        this.authentication_token = authentication_token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSa_id() {
        return sa_id;
    }

    public void setSa_id(String sa_id) {
        this.sa_id = sa_id;
    }

}

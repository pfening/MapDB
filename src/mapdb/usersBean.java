package mapdb;

public class usersBean {
int userid;
String username;
String password;
String language;

public usersBean() {}

public usersBean(int userid, String username, String password, String language) {
this.userid = userid;
this.username = username;
this.password = password;
this.language = language;
}

public int getUserid() {
return userid;
}

public void setUserid(int userid) {
this.userid = userid;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getLanguage() {
return language;
}

public void setLanguage(String language) {
this.language = language;
}

}

package mapdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class usersDAO {

private String selectedItem;

public String getSelectedItem() {
return selectedItem;
}

public void setSelectedItem(String selectedItem) {
this.selectedItem = selectedItem;
}

public List<usersBean> getList() throws SQLException, Exception{
List<usersBean> users = new ArrayList<>();
Connection conn = Database.getInstance().getConnection();
PreparedStatement listStatement = conn.prepareStatement("SELECT * FROM users where userid=? OR username=? OR password=? OR language=?");
listStatement.setString(1, selectedItem);
listStatement.setString(2, selectedItem);
listStatement.setString(3, selectedItem);
listStatement.setString(4, selectedItem);
ResultSet results = listStatement.executeQuery();
while(results.next()) {
usersBean entry = new usersBean();
entry.setUserid(results.getInt("userid"));
entry.setUsername(results.getString("username"));
entry.setPassword(results.getString("password"));
entry.setLanguage(results.getString("language"));
users.add(entry);
}
results.close();
listStatement.close();
return users;
}

public void addData(usersBean data) throws SQLException, Exception {
Connection conn = Database.getInstance().getConnection();
PreparedStatement addStatement = conn.prepareStatement("insert into users (username, password, language) values (?, ?, ?)");
addStatement.setString(1, data.getUsername());
addStatement.setString(2, data.getPassword());
addStatement.setString(3, data.getLanguage());
addStatement.executeUpdate();
addStatement.close();
}
}

package mapdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class languagesDAO {

private String selectedItem;

public String getSelectedItem() {
return selectedItem;
}

public void setSelectedItem(String selectedItem) {
this.selectedItem = selectedItem;
}

public List<languagesBean> getList() throws SQLException, Exception{
List<languagesBean> languages = new ArrayList<>();
Connection conn = Database.getInstance().getConnection();
PreparedStatement listStatement = conn.prepareStatement("SELECT * FROM languages WHERE langid=? OR m_lang=? OR f_lang=?");
listStatement.setString(1, selectedItem);
listStatement.setString(2, selectedItem);
listStatement.setString(3, selectedItem);
ResultSet results = listStatement.executeQuery();
while(results.next()) {
languagesBean entry = new languagesBean();
entry.setLangid(results.getInt("langid"));
entry.setM_lang(results.getString("m_lang"));
entry.setF_lang(results.getString("f_lang"));
languages.add(entry);
}
results.close();
listStatement.close();
return languages;
}

public void addData(languagesBean data) throws SQLException, Exception {
Connection conn = Database.getInstance().getConnection();
PreparedStatement addStatement = conn.prepareStatement("INSERT INTO languages (m_lang, f_lang) VALUES (?, ?)");
addStatement.setString(1, data.getM_lang());
addStatement.setString(2, data.getF_lang());
addStatement.executeUpdate();
addStatement.close();
}

public void updateData(languagesBean data) throws SQLException, Exception {
Connection conn = Database.getInstance().getConnection();
PreparedStatement updateStatement = conn.prepareStatement("UPDATE languages SET m_lang=?, f_lang=? WHERE langid=?");
updateStatement.setString(1, data.getM_lang());
updateStatement.setString(2, data.getF_lang());
updateStatement.setInt(3, data.getLangid());
updateStatement.executeUpdate();
updateStatement.close();
}

public void deleteData(languagesBean data) throws SQLException, Exception {
Connection conn = Database.getInstance().getConnection();
PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM languages WHERE langid=?");
deleteStatement.setInt(1, data.getLangid());
deleteStatement.executeUpdate();
deleteStatement.close();
}

}

package mapdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dictionaryDAO {

private String selectedItem;

public String getSelectedItem() {
return selectedItem;
}

public void setSelectedItem(String selectedItem) {
this.selectedItem = selectedItem;
}

public List<dictionaryBean> getList() throws SQLException, Exception{
List<dictionaryBean> dictionary = new ArrayList<>();
Connection conn = Database.getInstance().getConnection();
PreparedStatement listStatement = conn.prepareStatement("SELECT * FROM dictionary WHERE wordid=? OR userid=? OR langid=? OR m_word=? OR f_word=?");
listStatement.setString(1, selectedItem);
listStatement.setString(2, selectedItem);
listStatement.setString(3, selectedItem);
listStatement.setString(4, selectedItem);
listStatement.setString(5, selectedItem);
ResultSet results = listStatement.executeQuery();
while(results.next()) {
dictionaryBean entry = new dictionaryBean();
entry.setWordid(results.getInt("wordid"));
entry.setUserid(results.getInt("userid"));
entry.setLangid(results.getInt("langid"));
entry.setM_word(results.getString("m_word"));
entry.setF_word(results.getString("f_word"));
dictionary.add(entry);
}
results.close();
listStatement.close();
return dictionary;
}

public void addData(dictionaryBean data) throws SQLException, Exception {
Connection conn = Database.getInstance().getConnection();
PreparedStatement addStatement = conn.prepareStatement("INSERT INTO dictionary (userid, langid, m_word, f_word) VALUES (?, ?, ?, ?)");
addStatement.setInt(1, data.getUserid());
addStatement.setInt(2, data.getLangid());
addStatement.setString(3, data.getM_word());
addStatement.setString(4, data.getF_word());
addStatement.executeUpdate();
addStatement.close();
}

public void updateData(dictionaryBean data) throws SQLException, Exception {
Connection conn = Database.getInstance().getConnection();
PreparedStatement updateStatement = conn.prepareStatement("UPDATE dictionary SET userid=?, langid=?, m_word=?, f_word=? WHERE wordid=?");
updateStatement.setInt(1, data.getUserid());
updateStatement.setInt(2, data.getLangid());
updateStatement.setString(3, data.getM_word());
updateStatement.setString(4, data.getF_word());
updateStatement.setInt(5, data.getWordid());
updateStatement.executeUpdate();
updateStatement.close();
}

public void deleteData(dictionaryBean data) throws SQLException, Exception {
Connection conn = Database.getInstance().getConnection();
PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM dictionary WHERE wordid=?");
deleteStatement.setInt(1, data.getWordid());
deleteStatement.executeUpdate();
deleteStatement.close();
}

}

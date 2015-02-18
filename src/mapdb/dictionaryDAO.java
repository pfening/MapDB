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
PreparedStatement listStatement = conn.prepareStatement("SELECT * FROM dictionary where wordid=? OR userid=? OR langid=? OR m_word=? OR f_word=?");
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
PreparedStatement addStatement = conn.prepareStatement("insert into dictionary (wordid, userid, langid, m_word, f_word) values (?, ?, ?, ?, ?)");
addStatement.setInt(1, data.getWordid());
addStatement.setInt(2, data.getUserid());
addStatement.setInt(3, data.getLangid());
addStatement.setString(4, data.getM_word());
addStatement.setString(5, data.getF_word());
addStatement.executeUpdate();
addStatement.close();
}
}

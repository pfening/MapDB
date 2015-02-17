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
PreparedStatement listStatement = conn.prepareStatement("SELECT * FROM languages where langid=? OR m_lang=? OR f_lang=? OR ");
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

}

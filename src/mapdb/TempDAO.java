package mapdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TempDAO {
    
    private String selectedName;

        public String getSelectedName() {        
            return selectedName;
        }

        public void setSelectedName(String selectedName) {
            this.selectedName = selectedName;       
        }
    
    	public void addData(TempBean data) throws SQLException, Exception {		
		Connection conn = Database.getInstance().getConnection();		
		PreparedStatement p = conn.prepareStatement("insert into test (name, age, place) values (?,?,?)");		
		p.setString(1, data.getName());
		p.setString(3, data.getPlace());
		p.setString(2, data.getAge());                
		p.executeUpdate();		
		p.close();		
	}
        
        public TempBean getUser() throws SQLException, Exception{
            TempBean entry = null;
            Connection conn = Database.getInstance().getConnection();		
		PreparedStatement selectStatement = conn.prepareStatement("SELECT * FROM test where name=?");
		selectStatement.setString(1, selectedName);		
		ResultSet result = selectStatement.executeQuery();		
		while(result.next()) {
			String name = result.getString("name");
			String place = result.getString("place");
			String age = result.getString("age");			
			entry = new TempBean(name,place,age);
		}		
		result.close();
		selectStatement.close();                
                return entry;             
        }
        
        public List<TempBean> getNames() throws SQLException, Exception{
                List<TempBean> names = new ArrayList<>();		
		Connection conn = Database.getInstance().getConnection();
		PreparedStatement listStatement = conn.prepareStatement("select name from test order by name");				
		ResultSet results = listStatement.executeQuery();		
		while(results.next()) {
                    TempBean entry = new TempBean();
                    entry.setName(results.getString("name"));
                    names.add(entry);
		}		
		results.close();
		listStatement.close();
                return names;           
        }
    
        public void updateData(TempBean data) throws Exception{
		Connection conn = Database.getInstance().getConnection();		
		PreparedStatement updateStatement = conn.prepareStatement("update test set place=?,age=? where name=?");		
		updateStatement.setString(1, data.getPlace());
		updateStatement.setString(2, data.getAge());
                updateStatement.setString(3, data.getName());
		updateStatement.executeUpdate();		
		updateStatement.close();
        }
        
        public void deleteData(TempBean data) throws Exception{
                Connection conn = Database.getInstance().getConnection();		
		PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM test WHERE name=?");
                deleteStatement.setString(1, data.getName());
		deleteStatement.executeUpdate();
                deleteStatement.close();
        }
}

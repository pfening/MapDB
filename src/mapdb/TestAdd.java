package mapdb;

import java.sql.SQLException;

public class TestAdd {

    public static void main(String[] args) throws SQLException, Exception {

        String m_lang="italian";
        String f_lang="russian";
        
        Database.getInstance().connect();
        languagesDAO dao=new languagesDAO();
        
        languagesBean data = new languagesBean();

        data.setM_lang(m_lang);
        data.setF_lang(f_lang);
        
        dao.addData(data);
        
        Database.getInstance().disconnect();     
    }    
}
    


package mapdb;

import java.sql.SQLException;

public class TestUpdate {

    public static void main(String[] args) throws SQLException, Exception {
        int langid = 12;
        String m_lang="german";
        String f_lang="hungarian";
        
        Database.getInstance().connect();
        languagesDAO dao=new languagesDAO();
        
        languagesBean data = new languagesBean();

        data.setLangid(langid);
        data.setM_lang(m_lang);
        data.setF_lang(f_lang);
        
        dao.updateData(data);
        
        Database.getInstance().disconnect();     
    }    
}
    


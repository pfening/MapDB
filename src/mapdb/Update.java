/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdb;

import java.sql.SQLException;

/**
 *
 * @author pfg
 */
public class Update {
    
        Update(int langid, String m_lang, String f_lang) throws SQLException, Exception {
        
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

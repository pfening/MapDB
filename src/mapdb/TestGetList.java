package mapdb;

import java.sql.SQLException;
import java.util.List;

public class TestGetList {

    public static void main(String[] args) throws SQLException, Exception {
        String lang="english";
        Database.getInstance().connect();
        languagesDAO d=new languagesDAO();
        d.setSelectedItem(lang);

        
        List<languagesBean> list = (List<languagesBean>) d.getList();
        for (languagesBean n:list){
        System.out.println(n.langid +" - "+ n.getM_lang() +" - "+ n.getF_lang());
        }
        
        Database.getInstance().disconnect();     
    }    
}
    


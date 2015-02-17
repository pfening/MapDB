package mapdb;

public class dictionaryBean {
int wordid;
int userid;
int langid;
String m_word;
String f_word;

public dictionaryBean() {}

public dictionaryBean(int wordid, int userid, int langid, String m_word, String f_word) {
this.wordid = wordid;
this.userid = userid;
this.langid = langid;
this.m_word = m_word;
this.f_word = f_word;
}

public int getWordid() {
return wordid;
}

public void setWordid(int wordid) {
this.wordid = wordid;
}

public int getUserid() {
return userid;
}

public void setUserid(int userid) {
this.userid = userid;
}

public int getLangid() {
return langid;
}

public void setLangid(int langid) {
this.langid = langid;
}

public String getM_word() {
return m_word;
}

public void setM_word(String m_word) {
this.m_word = m_word;
}

public String getF_word() {
return f_word;
}

public void setF_word(String f_word) {
this.f_word = f_word;
}

}

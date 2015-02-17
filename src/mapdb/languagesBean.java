package mapdb;

public class languagesBean {
int langid;
String m_lang;
String f_lang;

public languagesBean() {}

public languagesBean(int langid, String m_lang, String f_lang) {
this.langid = langid;
this.m_lang = m_lang;
this.f_lang = f_lang;
}

public int getLangid() {
return langid;
}

public void setLangid(int langid) {
this.langid = langid;
}

public String getM_lang() {
return m_lang;
}

public void setM_lang(String m_lang) {
this.m_lang = m_lang;
}

public String getF_lang() {
return f_lang;
}

public void setF_lang(String f_lang) {
this.f_lang = f_lang;
}

}

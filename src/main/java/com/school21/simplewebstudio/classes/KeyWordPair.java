package com.school21.simplewebstudio.classes;

public class KeyWordPair {
    public KeyWord openWord;
    public KeyWord closeWord;
    boolean needClose;

    public boolean haveOpen(){
        return openWord != null;
    }

    public boolean haveClose(){
        return closeWord == null;
    }

}

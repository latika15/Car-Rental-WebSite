import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Composer {

    private String cid ;
    private String cmake ;
    private String ctype ;
    private String cmodel ;
    
    public Composer (String cid, String cmake, String ctype, String cmodel) {
        this.cid = cid;
        this.cmake = cmake;
        this.ctype = ctype;
        this.cmodel = cmodel;
    }
    
    public String getCId() {
        return cid;
    }
    
    public String getCMake() {
        return cmake;
    }
    
    public String getCType() {
        return ctype;
    }

    public String getCModel() {
        return cmodel;
    }
}
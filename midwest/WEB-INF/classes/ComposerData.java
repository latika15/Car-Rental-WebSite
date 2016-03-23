import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.AggregationOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

public class ComposerData {
    
    private HashMap composers = new HashMap();
    private static final long serialVersionUID = 1L;
    MongoClient mongo;

    public HashMap getComposers() {
        return composers;
    }

    public ComposerData() {

        mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("CarRentals");
        DBCollection carDetails = db.getCollection("car_details");

        try {
            DBCursor cursor= carDetails.find();
            while(cursor.hasNext())
            {
                BasicDBObject obj= (BasicDBObject)cursor.next();
                composers.put(obj.getString("carid"),new Composer(obj.getString("carid"),obj.getString("carmake"),obj.getString("cartype"),obj.getString("carmodel")));
            }
            //composers.put("3",new Composer("3","maruti","sedan","sumo"));
        }
        catch(MongoException e){
            e.printStackTrace();
        }
    }

}

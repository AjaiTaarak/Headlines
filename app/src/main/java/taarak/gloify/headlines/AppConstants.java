package taarak.gloify.headlines;



import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppConstants {
    public static String toptopics[]={
            "NEWS",
            "SPORTS",
            "BUSINESS",
            "CELEBRITY NEWS",
            "INDIAN PARLIAMENT",
            "INDIAN RECIPES",
            "DESIGN",
            "TECHNOLOGY",
            "INDIAN POLITICS",
            "INDIAN RELIGION",
            "SCIENCE",
            "WEATHER",
            "PHOTOGRAPHY",
            "COMPUTER SCIENCE",
            "STYLE",
            "INDIAN CRICKET",
            "TELEVISION",
            "TRAVEL",
            "CULTURE",
            "MUSIC",
            "FILM",
            "BOLLYWOOD",
            "INVESTING",
            "ADVERTISING",
            "INDIAN ECONOMY",
            "WEARABLE TECH",
            "HEALTH",
            "FINANCE",
            "BOOKS",
            "INDIAN EDUCATION",
            "ARCHITECTURE",
            "PARENTING",
            "ENTREPRENEURSHIP",
            "ENTERTAINMENT",
            "GAMING",
            "FOOD",
            "YOGA",
            "AUTOS",
            "BANKING",
            "FORMULA ONE",
            "UX DESIGN"
    };
    public static String statelist[]={
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal"
    };
    public static boolean isNOTNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }
    public static String APIKEY="your-api-key";
    public static String MEDIANAMES[]={"the-times-of-india","national-geographic","cnn","bbc-news","the-hindu","techradar"};
    public static int MEDIALOGOS[]={R.drawable.toi,R.drawable.natgeo,R.drawable.cnn,R.drawable.bbc,R.drawable.hindu,R.drawable.techradar};

    public static String getDateToTimeFormat(String oldstringDate){

        String isTime = null;
        try {
            oldstringDate=oldstringDate.replace('T',' ');
            oldstringDate=oldstringDate.substring(0,oldstringDate.length()-2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.ENGLISH);
            Date date = sdf.parse(oldstringDate);
            Date date1=new Date();
            date1=sdf.parse(sdf.format(date1));

            long diff = date1.getTime() - date.getTime();

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if(diffDays>0)
                isTime = diffDays+" days ago";
            else if(diffHours>0)
                isTime = diffHours+" hours ago";
            else
                isTime =diffMinutes+" minutes ago";
        } catch (ParseException e) {
            Log.e("APP CONSTANTS",e.getLocalizedMessage());
            e.printStackTrace();
        }

        return isTime;
    }
}

package core;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.stlight.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import core.utils.DateTimeUtils;

public class BottomSheet {
   private  ListView lv_languages;
    private  BottomSheetDialog bottomSheetDialog;
    private  Calendar calendar = Calendar.getInstance();
    private Context ctx;
    private  Calendar todayCalendar = Calendar.getInstance();
    private IDateListener mIDateListener;
    private long lastMonthStartDate=0;
    private long lastMonthEndDate=0;
    private String fromCustomMonth="";
    private String fromCustomYear="";
    private String fromCustomDay="";
    private String ToCustomMonth="";
    private String ToCustomYear="";
    private String ToCustomDay="";
    private  Dialog dialog;
    String[] filtter = new String[] {
            "ALL",
            "TODAY",
            "LAST 7 DAYS",
            "LAST MONTH",
            "CUSTOM DATE"

    };
    public  void ShowBottomSheet(Context ctx,IDateListener mIDateListener) {
        this.ctx=ctx;
        this.mIDateListener=mIDateListener;
        LayoutInflater inflater=  (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_bottom_sheet, null);
        lv_languages = (ListView) view.findViewById(R.id.lv_languages);
        lv_languages.setAdapter(new ArrayAdapter<String>(ctx,android.R.layout.simple_list_item_1,filtter));
        bottomSheetDialog = new BottomSheetDialog(ctx);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        lv_languages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                bottomSheetDialog.dismiss();
                dateFilter(position);




            }
        });
    }


    public  void dateFilter(int position){
        switch (position){
            case 0:
                break;
            case 1:
               // calendar.add(Calendar.DATE, -1);
                break;
            case 2:
                calendar.add(Calendar.DATE, -7);
                break;
            case 3:
                calendar.add(Calendar.MONTH, -1);
                String dateInMills= DateTimeUtils.getDate(calendar.getTimeInMillis());
                dateSplit(dateInMills);

                break;
            case 4:
                customDialog(position);
                break;


        }

         if(position==3&&lastMonthStartDate!=0&&lastMonthEndDate!=0){
               mIDateListener.dateListener(lastMonthStartDate, lastMonthEndDate,position);
           }else if(position==4){




           }else {
             mIDateListener.dateListener(calendar.getTime().getTime(), todayCalendar.getTime().getTime(), position);
         }

    }


    private void dateSplit(String dateInMills){

         String month="";
         String year="";
         String firstDay="1";
        try{
                String[] dateSplit = dateInMills.split("/");
                month = dateSplit[1];
                year = dateSplit[2];
        }catch (Exception e){

        }
        lastMonthStartDate=DateTimeUtils.getDateInMills(firstDay+"/"+month+"/"+year+" 12:00:00 AM");
        lastMonthEndDate=DateTimeUtils.getDateInMills(getLastDay(Integer.parseInt(month),Integer.parseInt(year))+"/"+month+"/"+year+" 11:59:59 PM");


    }

    private String getLastDay(int month,int year){
        if (month==2)
        {
            if ((year % 4 == 0))
            return "29";
            else
                return "28";
        }
        else if(month==4||month==6||month==9||month==11){
        return "30";
        }else{
            return "31";
        }
    }

    private void customDialog(final int position){
        // Create custom dialog object
        dialog = new Dialog(ctx);
        // Include dialog.xml file
        dialog.setContentView(R.layout.layout_dialog);
        // Set dialog title
        dialog.setTitle("Custom Date");
        final EditText fromDate = (EditText) dialog.findViewById(R.id.fromDate);
        final EditText toDate = (EditText) dialog.findViewById(R.id.toDate);
        Button submit = (Button) dialog.findViewById(R.id.submit);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from_Date=fromDate.getText().toString();
                String to_Date=toDate.getText().toString();

                if(from_Date.length()>0 && to_Date.length()>0 && from_Date.contains("/") && to_Date.contains("/")){
                    if(from_Date.contains("/")&&to_Date.contains("/")){
                        customDate(from_Date,to_Date,position);

                    }
                }
                else{
                    Toast.makeText(ctx,"Please enter valid date in DD/MM/YYYY format",Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
        dialog.show();

    }
    void customDate(String from_Date,String to_Date,int position){
        try{
            String[] dateSplit = from_Date.split("/");
            fromCustomDay=dateSplit[0];
            fromCustomMonth = dateSplit[1];
            fromCustomYear = dateSplit[2];
            String[] ToDateSplit = to_Date.split("/");
            ToCustomDay=ToDateSplit[0];
            ToCustomMonth = ToDateSplit[1];
            ToCustomYear = ToDateSplit[2];
        }catch (Exception e){

        }
        if(Integer.parseInt(fromCustomYear)<=Integer.parseInt(ToCustomYear)){
            if( Integer.parseInt(fromCustomYear)==Integer.parseInt(ToCustomYear) &&Integer.parseInt(fromCustomMonth)>Integer.parseInt(ToCustomMonth)){

                Toast.makeText(ctx,"Please enter from valid date",Toast.LENGTH_SHORT).show();
                return;
            }
            lastMonthStartDate=DateTimeUtils.getDateInMills(fromCustomDay+"/"+fromCustomMonth+"/"+fromCustomYear+" 12:00:00 AM");
            lastMonthEndDate=DateTimeUtils.getDateInMills(ToCustomDay+"/"+ToCustomMonth+"/"+ToCustomYear+" 11:59:59 PM");
            mIDateListener.dateListener(lastMonthStartDate, lastMonthEndDate,position);
            dialog.dismiss();

        }
        else{
            Toast.makeText(ctx,"Please enter from valid date",Toast.LENGTH_SHORT).show();
            return;
        }




    }


}
package com.jnu.student.mytestproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    ListView listview;
    Calendar calen = Calendar.getInstance();
    private ArrayList<DayNotes> notesCollection=new ArrayList<DayNotes>(); //全部日记数据
    private ArrayList<DayNotes> MonthNotesCollection=new ArrayList<>();  //指定月份数据
    private ArrayList<DayNotes> MonthTxtNotes=new ArrayList<>();
    private Map<Integer,DayNotes> tempMap=new HashMap<>();   //读取指定月份的数据
    ListViewAdapter theListAdapter;
    ListViewAdapter2 theListAdapter2;
    boolean showView=false;               //选择主界面不同的View
    int currentYear,currentMonth,currentDay;  //当前年月日
    int setYear,setMonth;//选定年份和月份
    int tempi=0;

    private static final int REQUEST_CODE_ADD_ITEM = 10;

    //为listview设置适配器
    public class ListViewAdapter extends BaseAdapter {
        ArrayList<View> itemViews;

        public ListViewAdapter(ArrayList<DayNotes> notesCollection) {     //第一种view的适配器
            itemViews = new ArrayList<View>(notesCollection.size());
            for (int i = 0; i < notesCollection.size(); ++i) {  // 读取日记
                if(null!=notesCollection.get(i).getTxt()){   //有日记记录，显示日记记录
                itemViews.add(makeItemView(notesCollection.get(i).getWeek()
                        , Integer.toString(notesCollection.get(i).getDay())
                        , notesCollection.get(i).getTxt())
                );}
                //没有日记记录，显示圆点
                else if(notesCollection.get(i).getWeek().equals("SUN")){ //星期天显示红色圆点
                    itemViews.add(makeItemViewRed());
                }
                else   //否则显示黑色圆点
                    itemViews.add(makeItemViewBlack());
            }
        }

    /*    public void removeItem(int position){   //删除日记
            DayNotes tNotes;
            tNotes=MonthNotesCollection.get(position);
            for(int i=0;i<notesCollection.size();i++){     //在notsCollection中找到该日记并删除
                if((tNotes.getYear()==notesCollection.get(i).getMonth())&&(tNotes.getMonth()==notesCollection.get(i).getMonth())
                    &&(tNotes.getDay()==notesCollection.get(i).getDay())){
                    notesCollection.remove(i);
                }
            }
            MonthNotesCollection.remove(position);
            NotesCollectionOperater operater=new NotesCollectionOperater();
            operater.save(MainActivity.this.getBaseContext(),notesCollection);
        }*/

        public int getCount() {
            return itemViews.size();
        }

        public View getItem(int position) {
            return itemViews.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        private View makeItemView(String strWeek, String strDay, String strText) {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.listview_item, null);
            // 通过findViewById()方法实例R.layout.item内各组件
            TextView week = (TextView) itemView.findViewById(R.id.item_week);
            week.setText(strWeek);
            if(strWeek.equals("SUN"))
               // week.setTextColor(getResources().getColor(R.color.colorRed));    //星期天字体为红色
                week.setTextColor(Color.parseColor("#A84545"));
            TextView day = (TextView) itemView.findViewById(R.id.item_day);
            day.setText(strDay);
            TextView text = (TextView) itemView.findViewById(R.id.item_Text);
            text.setText(strText);
            return itemView;
        }

        private View makeItemViewBlack(){
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.listview_item2,null);
            ImageView pot=(ImageView)itemView.findViewById(R.id.pot);
            pot.setImageResource(R.drawable.circle_black);
            return  itemView;
        }
        private View makeItemViewRed(){
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.listview_item2,null);
            ImageView pot=(ImageView)itemView.findViewById(R.id.pot);
            pot.setImageResource(R.drawable.circle_red);
            return  itemView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //if (convertView == null)
            return itemViews.get(position);
            //return convertView;
        }
    }

    //设置第一个界面第二种view的适配器
    public class ListViewAdapter2 extends BaseAdapter {
        ArrayList<View> itemViews2;

        public ListViewAdapter2(ArrayList<DayNotes> notesCollection) {
            itemViews2 = new ArrayList<View>(notesCollection.size());
            for (int i = 0; i < notesCollection.size(); ++i) {
                if(null!=notesCollection.get(i).getTxt()){
                    itemViews2.add(makeItemView2(Integer.toString(notesCollection.get(i).getDay())
                            , notesCollection.get(i).getWeek()
                            , notesCollection.get(i).getTxt())
                    );}
            }
        }

        public int getCount() {
            return itemViews2.size();
        }
        public View getItem(int position) {
            return itemViews2.get(position);
        }
        public long getItemId(int position) {
            return position;
        }

        private View makeItemView2(String strDay, String strWeek, String strText){
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.listview_item3, null);
            TextView day = (TextView) itemView.findViewById(R.id.item3_day);
            day.setText(strDay);
            TextView week = (TextView) itemView.findViewById(R.id.item3_week);
            week.setText(strWeek);
            if(strWeek.equals("SUN"))
                week.setTextColor(Color.parseColor("#A84545"));
            TextView text = (TextView) itemView.findViewById(R.id.item3_txt);
            text.setText(strText);
            return itemView;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //if (convertView == null)
            return itemViews2.get(position);
            //return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
        currentYear = Integer.parseInt(sDateFormat.format(new java.util.Date()));
        sDateFormat = new SimpleDateFormat("MM");
        currentMonth = Integer.parseInt(sDateFormat.format(new java.util.Date()));
        sDateFormat = new SimpleDateFormat("dd");
        currentDay = Integer.parseInt(sDateFormat.format(new java.util.Date()));
        setYear=currentYear;
        setMonth=currentMonth;

        listview= (ListView) this.findViewById(R.id.MyListView);

        NotesCollectionOperater operater=new NotesCollectionOperater();
        notesCollection=operater.load(getBaseContext());   //加载数据
        if(notesCollection==null){
            notesCollection=new ArrayList<DayNotes>();
        }

        final TextView monthSeleted=(TextView)findViewById(R.id.month);
        monthSeleted.setText(getMonthALL(setMonth));                      //月份初始设置为当前月份
        monthSeleted.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final AlertDialog monthSelectDialog=new AlertDialog.Builder(MainActivity.this,R.style.MyCommonDialog).create(); //设置浮现在actvity之上
                monthSelectDialog.show();
                WindowManager.LayoutParams p=monthSelectDialog.getWindow().getAttributes();  //获取窗口属性
                //获取屏幕分辨率属性
                DisplayMetrics dm=new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                //保持窗口与屏幕的宽度分辨率相同
                p.width=dm.widthPixels;
                monthSelectDialog.getWindow().setAttributes(p);
                //窗口内容绑定，获取month_select_dialog资源
                monthSelectDialog.getWindow().setContentView(R.layout.month_select_dialog);
                monthSelectDialog.setCancelable(true);  //返回键返回
                monthSelectDialog.getWindow().findViewById(R.id.month_select_dialog)
                        .setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                monthSelectDialog.dismiss();  //选择之后让dialog从屏幕中消失
                            }
                        });
                int [] monthId={R.id.select_Jan,R.id.select_Feb,R.id.select_Mar,R.id.select_Apr,R.id.select_May,R.id.select_Jun
                ,R.id.select_Jul,R.id.select_Aug,R.id.select_Sep,R.id.select_Oct,R.id.select_Nov,R.id.select_Dec};

                //设置当前被选中的选项背景为黑色（在布局中默认为灰色）
                monthSelectDialog.getWindow().findViewById(monthId[setMonth-1]).setBackgroundResource(R.drawable.circle_black);
                //还没有到来的月份设置背景为浅灰色
                if(setYear==currentYear){
                    for(int i=currentMonth;i<12;i++)
                        monthSelectDialog.getWindow().findViewById(monthId[i]).setBackgroundResource(R.drawable.circle_unreach_month);
                }

                if(setYear==currentYear){
                   for(int i=0;i<currentMonth;i++){
                       monthSelectDialog.getWindow().findViewById(monthId[i]).setOnClickListener(new View.OnClickListener(){
                           @Override
                           public void onClick(View view){
                               //设置setMonth为点击的月份
                               for(int j=0;j<12;j++){
                                   if(((TextView)view).getText().toString().equals(getMonthPART(j+1))){
                                       setMonth=++j;
                                       break;
                                   }
                               }
                               showNotes();
                               monthSeleted.setText(getMonthALL(setMonth));

                               if(!showView) {
                                   theListAdapter = new ListViewAdapter(MonthNotesCollection);
                                   listview.setAdapter(theListAdapter);
                                   listview.setOnItemClickListener(new mListViewItemClick());
                                   listview.setOnItemLongClickListener(new deleteListViewItemClick());
                               }
                               else {
                                   theListAdapter2 = new ListViewAdapter2(MonthNotesCollection);
                                   listview.setAdapter(theListAdapter2);
                                   listview.setOnItemClickListener(new mTextViewItemClick());
                                   listview.setOnItemLongClickListener(new deleteTextViewItemClick());
                               }
                               monthSelectDialog.dismiss();
                           }
                       });
                   }

                }else {
                    for (int i = 0; i < 12; i++) {
                        monthSelectDialog.getWindow().findViewById(monthId[i]).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //设置setMonth为点击的月份
                                for (int j = 0; j < 12; j++) {
                                    if (((TextView) view).getText().toString().equals(getMonthPART(j + 1))) {
                                        setMonth = ++j;
                                        break;
                                    }
                                }
                                showNotes();
                                monthSeleted.setText(getMonthALL(setMonth));

                                if (!showView) {
                                    theListAdapter = new ListViewAdapter(MonthNotesCollection);
                                    listview.setAdapter(theListAdapter);
                                    listview.setOnItemClickListener(new mListViewItemClick());
                                    listview.setOnItemLongClickListener(new deleteListViewItemClick());
                                } else {
                                    theListAdapter2 = new ListViewAdapter2(MonthNotesCollection);
                                    listview.setAdapter(theListAdapter2);
                                    listview.setOnItemClickListener(new mTextViewItemClick());
                                    listview.setOnItemLongClickListener(new deleteTextViewItemClick());
                                }
                                monthSelectDialog.dismiss();
                            }
                        });
                    }
                }
            }
        });

        final TextView yearSelected=(TextView)findViewById(R.id.year);
        yearSelected.setText(Integer.toString(setYear));

        yearSelected.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final AlertDialog yearSelectDialog = new AlertDialog.Builder(MainActivity.this, R.style.MyCommonDialog).create(); //设置浮现在actvity之上
                yearSelectDialog.show();
                WindowManager.LayoutParams p = yearSelectDialog.getWindow().getAttributes();
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                p.width = dm.widthPixels;
                yearSelectDialog.getWindow().setAttributes(p);
                yearSelectDialog.getWindow().setContentView(R.layout.year_select_dialog);
                yearSelectDialog.setCancelable(true);
                yearSelectDialog.getWindow().findViewById(R.id.year_select_dialog)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                yearSelectDialog.dismiss();
                            }
                        });
                int [] buttonId={R.id.year_one,R.id.year_two,R.id.year_three,R.id.year_four,R.id.year_five,R.id.year_six,R.id.year_seven,R.id.year_eight,R.id.year_nine};
                ((TextView)yearSelectDialog.getWindow().findViewById(buttonId[setYear-(currentYear-8)])).setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorGray));
                for(int i=0;i<9;i++){
                    int tempyear=currentYear-8+i;
                    ((TextView)yearSelectDialog.getWindow().findViewById(buttonId[i])).setText(Integer.toString(tempyear));
                }
                //各个按钮绑定
                for (int i=0;i<9;i++)
                {
                    yearSelectDialog.getWindow().findViewById(buttonId[i]).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for(int j=0;j<9;j++){
                                if(((TextView)view).getText().toString().equals(Integer.toString((currentYear-8)+j)))
                                {
                                    setYear=(currentYear-8)+j;
                                    break;
                                }
                            }
                            showNotes();
                            yearSelected.setText(Integer.toString(setYear));

                            if(!showView) {
                                theListAdapter = new ListViewAdapter(MonthNotesCollection);
                                listview.setAdapter(theListAdapter);
                                listview.setOnItemClickListener(new mListViewItemClick());
                                listview.setOnItemLongClickListener(new deleteListViewItemClick());
                            }
                            else {
                                theListAdapter2 = new ListViewAdapter2(MonthNotesCollection);
                                listview.setAdapter(theListAdapter2);
                                listview.setOnItemClickListener(new mTextViewItemClick());
                                listview.setOnItemLongClickListener(new deleteTextViewItemClick());
                            }

                            yearSelectDialog.dismiss();
                        }
                    });
                }
            }
        });

        final RelativeLayout editNotes=(RelativeLayout)findViewById(R.id.edit_today_notes);
        editNotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setYear=currentYear;
                setMonth=currentMonth;
                yearSelected.setText(Integer.toString(setYear));
                monthSeleted.setText(getMonthALL(setMonth));
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("year",currentYear);
                bundle.putString("month",getMonthALL(currentMonth));
                bundle.putString("week",stringGetWeek(currentYear,currentMonth,calen.get(Calendar.DATE)));
                bundle.putInt("day",calen.get(Calendar.DATE));

                showNotes();//可能是从不同月份跳转过来的，MonthCollection需要刷新

                if(MonthNotesCollection.get(calen.get(Calendar.DATE)-1).getTxt()==null){
                    bundle.putString("notes",null);
                }
                else
                    bundle.putString("notes",MonthNotesCollection.get(calen.get(Calendar.DATE)-1).getTxt());
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_ADD_ITEM );
            }
        });


        RelativeLayout changeView=(RelativeLayout)findViewById(R.id.change_form) ;
        changeView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!showView) {
                    ListViewAdapter2 theListAdapter2 = new ListViewAdapter2(MonthNotesCollection);
                    listview.setAdapter(theListAdapter2);
                    listview.setOnItemClickListener(new mTextViewItemClick());
                    listview.setOnItemLongClickListener(new deleteTextViewItemClick());
                    showView=true;
                }
                else {
                    ListViewAdapter theListAdapter = new ListViewAdapter(MonthNotesCollection);
                    listview.setAdapter(theListAdapter);
                    listview.setOnItemClickListener(new mListViewItemClick());
                    listview.setOnItemLongClickListener(new deleteListViewItemClick());
                    showView=false;
                }

            }
        });

        final LinearLayout MainView=(LinearLayout)findViewById(R.id.main_view);
        LinearLayout setButton=(LinearLayout)findViewById(R.id.setting_button);
        setButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tempi++;
                if(tempi%4==1){
                    MainView.setBackgroundResource(R.drawable.bg_one);
                }else if(tempi%4==2){
                    MainView.setBackgroundResource(R.drawable.bg_four);
                }else if(tempi%4==3){
                    MainView.setBackgroundResource(R.drawable.bg_five);
                }else
                    MainView.setBackgroundResource(R.drawable.bg);

            }
        });

       // registerForContextMenu(listview);

        showNotes();
        theListAdapter = new ListViewAdapter(MonthNotesCollection);  //默认的显示方式为第一种
        listview.setAdapter(theListAdapter);
        listview.setOnItemClickListener(new mListViewItemClick());
        listview.setOnItemLongClickListener(new deleteListViewItemClick());
    }


    public void showNotes(){
        tempMap=new HashMap<>();
        MonthNotesCollection=new ArrayList<>();
        for(DayNotes tempDayNotes :notesCollection){     //查找出指定月份的数据
            if(tempDayNotes.getYear()==setYear)
                if(tempDayNotes.getMonth()==setMonth)
                    tempMap.put(tempDayNotes.getDay(),tempDayNotes);
        }
        //当前年月
        if((setMonth == currentMonth) && (setYear == currentYear)) {
            for(int i=1;i<=calen.get(Calendar.DAY_OF_MONTH);i++){
                DayNotes tempDayNotes;
                tempDayNotes=tempMap.get(i);
                if(tempDayNotes!=null){
                    MonthNotesCollection.add(tempDayNotes);
                }
                else{      //如果文件中没有之前的记录就初始化记录，设置日期时间
                    DayNotes initNotes=new DayNotes();
                    initNotes.setYear(currentYear);
                    initNotes.setMonth(currentMonth);
                    initNotes.setDay(i);
                    initNotes.setWeek(stringGetWeek(currentYear,currentMonth,i));
                    initNotes.setTxt(null);
                    MonthNotesCollection.add(initNotes);
                }
            }
        }
        //不是当前年月
        else{
            int DayOfMonth=getDaysByYearMonth(setYear,setMonth);  //获取该月份的天数
            for(int i=1;i<=DayOfMonth;i++){
                DayNotes tempDayNotes;
                tempDayNotes=tempMap.get(i);
                if(tempDayNotes!=null){
                    MonthNotesCollection.add(tempDayNotes);
                }
                else{
                    DayNotes initNotes=new DayNotes();
                    initNotes.setYear(setYear);
                    initNotes.setMonth(setMonth);
                    initNotes.setDay(i);
                    initNotes.setWeek(stringGetWeek(setYear,setMonth,i));
                    initNotes.setTxt(null);
                    MonthNotesCollection.add(initNotes);
                }
            }
        }
    }


    class mListViewItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
            Bundle bundle1=new Bundle();
            bundle1.putString("notes",MonthNotesCollection.get(i).getTxt());
            bundle1.putInt("year",setYear);
            bundle1.putString("month",getMonthALL(setMonth));
            bundle1.putString("week",MonthNotesCollection.get(i).getWeek());
            bundle1.putInt("day",MonthNotesCollection.get(i).getDay());
            intent1.putExtras(bundle1);    //A向B传送数据
            startActivityForResult(intent1,REQUEST_CODE_ADD_ITEM);  //启动另一个页面，页面跳转
        }
    }

    class mTextViewItemClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
            MonthTxtNotes=new ArrayList<>();
            for(DayNotes tempDayNotes :MonthNotesCollection){     //查找出日记不为空的记录
                if(tempDayNotes.getTxt()!=null)
                    MonthTxtNotes.add(tempDayNotes);
            }
            DayNotes notes=MonthTxtNotes.get(i);
            Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
            Bundle bundle1=new Bundle();
            bundle1.putString("notes",notes.getTxt());
            bundle1.putInt("year",notes.getYear());
            bundle1.putString("month",getMonthALL(notes.getMonth()));
            bundle1.putString("week",notes.getWeek());
            bundle1.putInt("day",notes.getDay());
            intent1.putExtras(bundle1);
            startActivityForResult(intent1,REQUEST_CODE_ADD_ITEM);
        }
    }

    class deleteListViewItemClick implements AdapterView.OnItemLongClickListener{
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id){
            final DayNotes deleteItem=MonthNotesCollection.get(i);
            if(deleteItem.getTxt()!=null){           //有当天的日记数据才做响应
                final int year2=deleteItem.getYear();
                final int month2=deleteItem.getMonth();
                final int day2=deleteItem.getDay();
                String month1,day1;
                if(month2<10)
                    month1="0"+Integer.toString(month2);
                else
                    month1=Integer.toString(month2);
                if(day2<10)
                    day1="0"+Integer.toString(day2);
                else
                    day1=Integer.toString(day2);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("MyDayGram");
                dialog.setMessage("是否删除"+Integer.toString(year2)+"-"+month1+"-"+day1+"的日记？");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        for(int i=0;i<MonthNotesCollection.size();i++){
                            if(day2==MonthNotesCollection.get(i).getDay()){
                                MonthNotesCollection.get(i).setTxt(null);
                                break;
                            }
                        }
                  /*      for (DayNotes n : MonthNotesCollection) {
                            if(day2==n.getDay()){
                                MonthNotesCollection.remove(n);         //是修改不是删除
                                break;
                            }
                        } */
                        if(null==notesCollection){
                            notesCollection=new ArrayList<DayNotes>();
                        }
                        for(DayNotes n:notesCollection){
                            if((year2==n.getYear())&&(month2==n.getMonth())&&(day2==n.getDay())){
                                notesCollection.remove(n);
                                break;
                            }
                        }
                        NotesCollectionOperater operater = new NotesCollectionOperater();
                        operater.save(MainActivity.this.getBaseContext(), notesCollection);
                        theListAdapter = new ListViewAdapter(MonthNotesCollection);
                        listview.setAdapter(theListAdapter);
                    }
            });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
        }
        return  true;
    }
    };

    class deleteTextViewItemClick implements AdapterView.OnItemLongClickListener{
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id){
            MonthTxtNotes=new ArrayList<>();
            for(DayNotes tempDayNotes :MonthNotesCollection){     //查找出日记不为空的记录
                if(tempDayNotes.getTxt()!=null)
                    MonthTxtNotes.add(tempDayNotes);
            }
            final DayNotes deleteItem=MonthTxtNotes.get(i);
            if(deleteItem.getTxt()!=null){           //有当天的日记数据才做响应
                final int year2=deleteItem.getYear();
                final int month2=deleteItem.getMonth();
                final int day2=deleteItem.getDay();
                String month1,day1;
                if(month2<10)
                    month1="0"+Integer.toString(month2);
                else
                    month1=Integer.toString(month2);
                if(day2<10)
                    day1="0"+Integer.toString(day2);
                else
                    day1=Integer.toString(day2);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("MyDayGram");
                dialog.setMessage("是否删除"+Integer.toString(year2)+"-"+month1+"-"+day1+"的日记？");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        for(int i=0;i<MonthNotesCollection.size();i++){
                            if(day2==MonthNotesCollection.get(i).getDay()){
                                MonthNotesCollection.get(i).setTxt(null);
                                break;
                            }
                        }
                        if(null==notesCollection){
                            notesCollection=new ArrayList<DayNotes>();
                        }
                        for(DayNotes n:notesCollection){
                            if((year2==n.getYear())&&(month2==n.getMonth())&&(day2==n.getDay())){
                                notesCollection.remove(n);
                                break;
                            }
                        }
                        NotesCollectionOperater operater = new NotesCollectionOperater();
                        operater.save(MainActivity.this.getBaseContext(), notesCollection);
                        theListAdapter2 = new ListViewAdapter2(MonthNotesCollection);
                        listview.setAdapter(theListAdapter2);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
            return  true;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD_ITEM:
                if (resultCode == RESULT_OK) {
                    String name = data.getStringExtra("txt");   //获得editactivity传过来的数据
                    String day=data.getStringExtra("day");
                    String week=data.getStringExtra("week");
                    String month=data.getStringExtra("month");
                    String year=data.getStringExtra("year");

                    int intday=Integer.parseInt(day);
                    int intmonth=StringMonthtoInt(month);
                    int intyear=Integer.parseInt(year);

                //    Toast.makeText(getApplicationContext(),"传值："+name+week+intday,Toast.LENGTH_SHORT).show();
                   //这里改一下monthcollextion只修改不保存
                   for(int i=0;i<MonthNotesCollection.size();++i){
                        DayNotes tempnotes=MonthNotesCollection.get(i);
                        if(day.equals(Integer.toString(tempnotes.getDay()))) {
                            if((tempnotes.getTxt()==null)&&(name==null)){
                                break;
                            }else {
                                MonthNotesCollection.get(i).setTxt(name);
                                SaveNotes(intyear,intmonth,intday,name);    //保存日记
                                break;
                            }
                        }
                    }

                    if(!showView) {
                        theListAdapter = new ListViewAdapter(MonthNotesCollection);
                        listview.setAdapter(theListAdapter);
                    }
                    else {
                        theListAdapter2 = new ListViewAdapter2(MonthNotesCollection);
                        listview.setAdapter(theListAdapter2);
                    }
                }
                break;
        }
    }

    //把值添加给notesCollection,注意是添加数据，之前的数据也要保留
    public void SaveNotes(int year,int month,int day,String text){
        int j=0;
        for(int i=0;i<notesCollection.size();++i){                 //如果有数据的话
            if((year==notesCollection.get(i).getYear())&&(month==notesCollection.get(i).getMonth())
                    &&(day==notesCollection.get(i).getDay())) {
                notesCollection.get(i).setTxt(text);
                j=1;
                break;
            }
        }
        if(j==0){   //如果没有数据，添加数据
            DayNotes notes=new DayNotes();
            notes.setYear(year);
            notes.setMonth(month);
            notes.setDay(day);
            notes.setWeek(stringGetWeek(year, month, day));
            notes.setTxt(text);
            notesCollection.add(notes);
        }
        NotesCollectionOperater operater = new NotesCollectionOperater();
        operater.save(MainActivity.this.getBaseContext(), notesCollection);

 /*       Map<Integer,DayNotes> tempMap=new HashMap<>();
        for(DayNotes tempDayNotes :notesCollection){
            tempMap.put(tempDayNotes.getDay(),tempDayNotes);
        }
        for(int i=1;i<=tempMap.size();i++){
            DayNotes Notes=tempMap.get(i);
            if(Notes!=null)
                notesCollection.add(Notes);
            if((Notes.getYear()==year)&&(Notes.getMonth()==month)&&(Notes.getDay()==day)){   //添加日记的日期之前有数据
                Notes.setTxt(text);
            }
        }
        DayNotes Notes=tempMap.get(1);
        if(Notes!=null){

        }else {
            notesCollection.add(addNotes);
        }  */

    }



    public static String stringGetWeek(int year,int month,int day) {       //根据日期获取星期几
        Calendar cal = Calendar.getInstance();
        int i = -1;     //星期几
        String time,month1,day1;
        if(month<10)
            month1="0" + Integer.toString(month);
        else
            month1=Integer.toString(month);
        if(day<10)
            day1="0" + Integer.toString(day);
        else
            day1=Integer.toString(day);
        time=Integer.toString(year)+"-"+month1+"-"+day1;
        String weekday = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   //指定输入参数time的格式
            Date date;
            date = dateFormat.parse(time);
            cal.setTime(date);
            i = cal.get(Calendar.DAY_OF_WEEK);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if(2==i)  weekday="MON"; else if(3==i) weekday="TUE"; else if (4==i) weekday="WED";
        else if (5==i) weekday="THU"; else if (6==i) weekday="FRI"; else if (7==i) weekday="SAT";
        else weekday="SUN";
        return weekday;
    }

    public static int getDaysByYearMonth(int year, int month)     //根据年月获取当月天数
     {
         Calendar a = Calendar.getInstance();
         a.set(Calendar.YEAR, year);
         a.set(Calendar.MONTH, month - 1);
         a.set(Calendar.DATE, 1);
         a.roll(Calendar.DATE, -1);
         int maxDate = a.get(Calendar.DATE);
         return maxDate;
     }

     public static String getMonthALL(int month){
         switch (month) {
             case 1:
                 return "JANUARY";
             case 2:
                 return "FEBRUARY";
             case 3:
                 return "MARCH";
             case 4:
                 return "APRIL";
             case 5:
                 return "MAY";
             case 6:
                 return "JUNE";
             case 7:
                 return "JULY";
             case 8:
                 return "AUGUST";
             case 9:
                 return "SEPTEMBER";
             case 10:
                 return "OCTOBER";
             case 11:
                 return "NOVEMBER";
             case 12:
                 return "DECEMBER";
             default:
                 return null;
         }

     }

     public static String getMonthPART(int month){
         switch (month) {
             case 1:
                 return "JAN";
             case 2:
                 return "FEB";
             case 3:
                 return "MAR";
             case 4:
                 return "APR";
             case 5:
                 return "MAY";
             case 6:
                 return "JUN";
             case 7:
                 return "JUL";
             case 8:
                 return "AUG";
             case 9:
                 return "SEP";
             case 10:
                 return "OCT";
             case 11:
                 return "NOV";
             case 12:
                 return "DEC";
             default:
                 return null;
         }

     }

    public static int StringMonthtoInt(String month) {
        switch (month) {
            case "JANUARY":
                return 1;
            case "FEBRUARY":
                return 2;
            case "MARCH":
                return 3;
            case "APRIL":
                return 4;
            case "MAY":
                return 5;
            case "JUNE":
                return 6;
            case "JULY":
                return 7;
            case "AUGUST":
                return 8;
            case "SEPTEMBER":
                return 9;
            case "OCTOBER":
                return 10;
            case "NOVEMBER":
                return 11;
            case "DECEMBER":
                return 12;
            default:
                return 0;
        }
    }

}
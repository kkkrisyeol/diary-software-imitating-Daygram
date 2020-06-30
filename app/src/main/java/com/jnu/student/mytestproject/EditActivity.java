package com.jnu.student.mytestproject;

/**
 * Created by 王璐 on 2018/11/11.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class EditActivity extends Activity {
    TextView mTextView,textView,weekday,textView2,weekday2,TextName;
    EditText mEditText,mEditText2;
    ImageButton mButton,mButtonClock,mButtonDone;

    public String week,notes,month;
    int day,year;
    public static int tempi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

       // setBgListener();  //背景监听函数

        mTextView=(TextView) findViewById(R.id.mytextview);
        mButton=(ImageButton)findViewById(R.id.image_button);
        mButton.setOnClickListener(new btnclick());  //点击返回主界面

        Bundle bundle1=this.getIntent().getExtras();     //接收主界面的数据
        notes= bundle1.getString("notes");
        month=bundle1.getString("month");
        year=bundle1.getInt("year");
        week=bundle1.getString("week");
        day=bundle1.getInt("day");
        TextName= (TextView) this.findViewById(R.id.mytextview);
        TextName.setText(notes);

        weekday=(TextView)this.findViewById(R.id.weekday);     //获取日期时间的标题
        textView=(TextView)this.findViewById(R.id.title_YMD);
        weekday.setText(week);
        if(week.equals("SUN"))
            weekday.setTextColor(Color.parseColor("#A84545"));
        String a="/"+month+" "+day+"/"+year;
        textView.setText(a);

        mTextView.setOnClickListener(new View.OnClickListener() {       //从阅读模式变为编辑模式
            @Override
            public void onClick(View v) {
                goNextPage();
            }
        });   //点击进入编辑模式
    }

 /*   private  void setBgListener(){
        if(tempi%4==1){
            MainView.setBackgroundResource(R.drawable.bg_one);
        }else if(tempi%4==2){
            MainView.setBackgroundResource(R.drawable.bg_four);
        }else if(tempi%4==3){
            MainView.setBackgroundResource(R.drawable.bg_five);
        }else
            MainView.setBackgroundResource(R.drawable.bg);
    }*/

/*    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("txt",String.valueOf(mEditText.getText()));
        intent.putExtra("day",Integer.toString(day));
        intent.putExtra("week",week);
        intent.putExtra("year",Integer.toString(year));
        intent.putExtra("month",month);
        setResult(RESULT_OK,intent);
        EditActivity.this.finish();
        super.onBackPressed();
        System.out.println("按下了back键   onBackPressed()");
    }  */


    private class btnclick implements View.OnClickListener {      //返回主界面,并向主界面传送数据
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("txt",String.valueOf(TextName.getText()));
            intent.putExtra("day",Integer.toString(day));
            intent.putExtra("week",week);
            intent.putExtra("year",Integer.toString(year));
            intent.putExtra("month",month);
            Log.i("text", String.valueOf(TextName.getText()));
            Log.i("day",Integer.toString(day));
            Log.i("week",week);
            setResult(RESULT_OK,intent);
            EditActivity.this.finish();
        }
    }

    private void goNextPage(){
        setContentView(R.layout.edit_activity2);

        weekday2=(TextView)this.findViewById(R.id.weekday2);     //获取日期时间的标题
        textView2=(TextView)this.findViewById(R.id.title_YMD2);
        weekday2.setText(week);
        if(week.equals("SUN"))
            weekday2.setTextColor(Color.parseColor("#A84545"));
        String a="/"+month+" "+day+"/"+year;
        textView2.setText(a);

        mEditText2=(EditText) findViewById(R.id.myedittext1);
        mEditText=(EditText)this.findViewById(R.id.myedittext1);
        mButtonClock=(ImageButton)findViewById(R.id.button_clock);
        mButtonDone=(ImageButton)findViewById(R.id.button_done);

        mEditText.setText(notes);

        mEditText2.setFocusable(true);    //自动弹出键盘
        mEditText2.setFocusableInTouchMode(true);
        mEditText2.requestFocus();
        Timer timer = new Timer();          //延迟200毫秒弹出键盘
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(mEditText2, 0);
                           }
                       },
                200);

        mButtonClock.setOnClickListener(new View.OnClickListener() {       //在光标位置插入时间
            @Override
            public void onClick(View v) {
                int index=mEditText.getSelectionStart();       //获取光标所在位置
                SimpleDateFormat sDateFormat = new SimpleDateFormat("HH");
                int hour = Integer.parseInt(sDateFormat.format(new java.util.Date()));
                sDateFormat = new SimpleDateFormat(":mm");  //设置格式
                String minute = sDateFormat.format(new java.util.Date());
                Editable tempnotes=mEditText.getEditableText();
                String temptime;
                if(hour<12){
                    temptime="am ";
                }else if(hour==12){
                    temptime="pm ";
                }else{
                    temptime="pm ";
                    hour=hour-12;
                }
                String time=Integer.toString(hour)+minute+temptime;
                if(index<0||index>=tempnotes.length()){  //在光标处插入时间
                    tempnotes.append(time);
                }else{
                    tempnotes.insert(index,time);
                }
            }
        });

        mButtonDone.setOnClickListener(new View.OnClickListener() {       //从编辑模式变为阅读模式
            @Override
            public void onClick(View v) {
                notes=String.valueOf(mEditText.getText());   //把修改后的数据传给notes
                goPrePage();
            }
        });
    }

    private void goPrePage(){
        setContentView(R.layout.edit_activity);

        mButton=(ImageButton)findViewById(R.id.image_button);
        mButton.setOnClickListener(new btnclick());

        weekday=(TextView)this.findViewById(R.id.weekday);     //获取日期时间的标题
        textView=(TextView)this.findViewById(R.id.title_YMD);
        weekday.setText(week);
        if(week.equals("SUN"))
            weekday.setTextColor(Color.parseColor("#A84545"));
        String a="/"+month+" "+day+"/"+year;
        textView.setText(a);

        mTextView=(TextView) findViewById(R.id.mytextview);
        TextName=(TextView) this.findViewById(R.id.mytextview);
        TextName.setText(notes);

        mTextView=(TextView) findViewById(R.id.mytextview);
        mTextView.setOnClickListener(new View.OnClickListener() {         //从阅读模式变为编辑模式
            @Override
            public void onClick(View v) {
                goNextPage();
            }
        });
    }


}
package com.jnu.student.mytestproject;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class NotesCollectionOperater {
    private String file="daynotestest.dat";      //用于保存数据的文件名为goods.dat

    public ArrayList<DayNotes> load(Context context) {       //读取数据
        FileInputStream fis = null;     //文件字节输入流
        ObjectInputStream ois = null;    //对象字节输入流
        try {
            fis = context.openFileInput(file);    //打开文件
            ois = new ObjectInputStream(fis);
            return (ArrayList<DayNotes>) ois.readObject();  //返回数据
        } catch ( FileNotFoundException e ) {
        } catch ( Exception e ) {
            e.printStackTrace();
// 反序列化失败 - 删除缓存文件
            if ( e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();     //关闭文件字节输入流
            } catch ( Exception e ) {
            }
            try {
                fis.close();     //关闭对象字节输入流
            } catch ( Exception e ) {
            }
        }
        return null;
    }

    public boolean save(Context context, Serializable ser)    //保存数据
    {
        FileOutputStream fos = null;    //文件字节输出流
        ObjectOutputStream oos = null;    //对象字节输出流
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);      //写入数据
            oos.flush();           //清空缓存区数据
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

}

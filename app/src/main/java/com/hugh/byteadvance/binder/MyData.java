package com.hugh.byteadvance.binder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenyw on 2020/8/12.
 */
public class MyData implements Parcelable {
    private int data1;
    private int data2;

    public MyData(){

    }

    public MyData(int data1,int data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    protected MyData(Parcel in) {
        data1 = in.readInt();
        data2 = in.readInt();
    }

    public int getData1() {
        return data1;
    }

    public void setData1(int data1) {
        this.data1 = data1;
    }

    public int getData2() {
        return data2;
    }

    public void setData2(int data2) {
        this.data2 = data2;
    }

    public static final Creator<MyData> CREATOR = new Creator<MyData>() {
        @Override
        public MyData createFromParcel(Parcel in) {
            return new MyData(in);
        }

        @Override
        public MyData[] newArray(int size) {
            return new MyData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(data1);
        dest.writeInt(data2);
    }
}

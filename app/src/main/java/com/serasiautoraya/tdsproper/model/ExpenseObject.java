package com.serasiautoraya.tdsproper.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Randi Dwi Nandra on 13/03/2017.
 */

public class ExpenseObject implements Parcelable {

//    private Bitmap bitmapThumbnail = null;
//    private byte[] bitmapThumbnailBytes;

    private Uri realBitmapUri;
    private String jumlahPengeluaran;
    private String tipeExpenses;

    public ExpenseObject(Uri realBitmapUri, String jumlahPengeluaran, String tipeExpenses) {
        this.realBitmapUri = realBitmapUri;
        this.jumlahPengeluaran = jumlahPengeluaran;
        this.tipeExpenses = tipeExpenses;
//        this.contentResolver = contentResolver;
//        generateThumbnail();
    }

    protected ExpenseObject(Parcel in) {
//        in.readByteArray(bitmapThumbnailBytes);
        realBitmapUri = in.readParcelable(Uri.class.getClassLoader());
        jumlahPengeluaran = in.readString();
        tipeExpenses = in.readString();
    }

    public static final Creator<ExpenseObject> CREATOR = new Creator<ExpenseObject>() {
        @Override
        public ExpenseObject createFromParcel(Parcel in) {
            return new ExpenseObject(in);
        }

        @Override
        public ExpenseObject[] newArray(int size) {
            return new ExpenseObject[size];
        }
    };

//    public Bitmap getBitmapThumbnail() {
//        return bitmapThumbnail;
//    }


//    public byte[] getBitmapThumbnailBytes() {
//        return bitmapThumbnailBytes;
//    }

    public Uri getRealBitmapUri() {
        return realBitmapUri;
    }

    public String getJumlahPengeluaran() {
        return jumlahPengeluaran;
    }

    public String getTipeExpenses() {
        return tipeExpenses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeParcelable(bitmapThumbnail, i);
//        parcel.writeByteArray(bitmapThumbnailBytes);
        parcel.writeParcelable(realBitmapUri, i);
        parcel.writeString(jumlahPengeluaran);
        parcel.writeString(tipeExpenses);
    }


}

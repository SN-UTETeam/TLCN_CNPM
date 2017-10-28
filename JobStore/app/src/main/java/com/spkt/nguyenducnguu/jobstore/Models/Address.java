package com.spkt.nguyenducnguu.jobstore.Models;

import android.app.Activity;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class Address {
    private String AddressStr;
    private double Lat;
    private double Lng;

    public Address() {
    }

    public Address(String addressStr) {
        AddressStr = addressStr;
    }

    public static Address getAddressFromLocationName(String addressStr, Activity activity)
    {
        Address address = new Address();
        address.setAddressStr(addressStr);

        Geocoder coder = new Geocoder(activity);
        List<android.location.Address> ad;
        try {
            ad = coder.getFromLocationName(addressStr, 1);
            if (ad == null)
                return null;
            if(ad.size() >= 1) {
                android.location.Address location = ad.get(0);
                address.setLat(location.getLatitude());
                address.setLng(location.getLongitude());
            }
            else
            {
                address.setLat(-1);
                address.setLng(-1);
            }
            return address;
        }
        catch (IOException e)
        {
            return null;
        }
    }
    public static Address getAddressFromLocation(double lat, double lng, Activity activity)
    {
        Address address = new Address();
        address.setLat(lat);
        address.setLng(lng);

        Geocoder coder = new Geocoder(activity);
        List<android.location.Address> ad;
        try {
            ad = coder.getFromLocation(lat,lng, 1);
            if (ad == null)
                return null;
            android.location.Address location=ad.get(0);
            String addressName = "";
            for(int i = 0; i < location.getMaxAddressLineIndex(); i++) {
                addressName += " --- " + location.getAddressLine(i);
            }
            address.setAddressStr(addressName);
            return address;
        }
        catch (IOException e)
        {
            return null;
        }
    }
    public String getAddressStr() {
        return AddressStr;
    }

    public void setAddressStr(String addressStr) {
        AddressStr = addressStr;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    @Override
    public String toString() {
        return "Address: " + AddressStr + "\n"
                + "Lat: " + Lat + "\n"
                + "Lng: " + Lng;
    }
}

package com.example.cagairsidemonitoring;

import com.google.firebase.database.Exclude;

public class Flights {

    private String FlightNo;
    private String mETA;
    private String Bay;
    private String mType;
    private String documentId;


    public Flights(){

    }






    public Flights (String FlightNo, String mETA, String Bay, String mType) {
        this.FlightNo = FlightNo;
        this.mETA = mETA;
        this.mType = mType;
        this.Bay = Bay;

    }

    public String getFlightNo() {
        return FlightNo;
    }

    public String getmETA() {
        return mETA;
    }

    public String getBay() {
        return Bay;
    }

    public String getmType() {
        return mType;
    }

   @Exclude
    public String getDocumentId() { return documentId;}

    public void setDocumentId (String documentId){
        this.documentId = documentId;
    }

}

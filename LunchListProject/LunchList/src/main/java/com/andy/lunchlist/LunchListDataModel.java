package com.andy.lunchlist;

/**
 * Created by Andy56-MSI on 9/7/13.
 */
public class LunchListDataModel {

    private String LocationName;
    private String LocationAddress;
    private String LocationGPSCoordinates;
    private String LocationNotes;
    private String LocationType;
    private int LocationRating;
    private int Position;





    public String getLocationName()
    {
        return this.LocationName;
    }

    public String getLocationAddress()
    {
        return this.LocationAddress;
    }

    public void setLocationName(String LocationName)
    {
        this.LocationName = LocationName;
    }

    public void setLocationAddress(String LocationAddress)
    {
        this.LocationAddress = LocationAddress;

    }

    public void setLocationType(String LocationType)
    {
        this.LocationType = LocationType;
    }

    public String getLocationType()
    {
        return this.LocationType;
    }

    public int getPosition()
    {
        return Position;
    }

    public String toString()
    {
        return getLocationName()+ ", "+ getLocationAddress() + ", " + getLocationType();
    }

    public boolean isTheSameAs(LunchListDataModel dataModel1)
    {
        char[] data1 = this.toString().toCharArray();
        char[] data2 = dataModel1.toString().toCharArray();

        boolean isMatch = false;
        if(data1.length == data2.length)
        {
            for(int y = 0; y<data1.length; y++)
            {
                if(data1[y] == data2[y])
                {
                    isMatch = true;
                }else
                {
                    return false;
                }
            }
        }

        if(isMatch == false)
        {
            return false;
        }
        if(isMatch == true)
        {
            return true;
        }
        return false;
    }

    LunchListDataModel()
    {
          LocationName = "";
          LocationAddress = "";

          LocationType = "";
        //increase position
       // Position = Position + 1;
    }



}



package com.kissansaarthi.kissansaarthi;

/**
 * Created by sonu1212 on 17-03-2016.
 */
public class GetMandiData {

    public String commodity;
    public String variety;
    public String min;
    public String max;
    public String market;

    public GetMandiData(String market,String commodity,String variety,String min,String max)
    {
        this.commodity=commodity;
        this.variety=variety;
        this.min=min;
        this.max=max;
        this.market=market;

    }
}

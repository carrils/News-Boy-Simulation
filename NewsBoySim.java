//Sam Carrillo
//1.27.18
//CSC 318 News Boy Simulation

/*
    This is a simulation program written in Java. Each day the newsboy must
  order the papers for the next day. He buys the paper for 35 cents and generates
  the paper demand (from 15-20 per day) randomly for an observed distribution.
  Over the 1000 day period, the simulation collects profits per day, average profit
  per day, variance, standard deviation, and the average papers demanded per day.

*/

public class NewsBoySim {

    public static void main(String[] args) throws Exception {
        int day, dmd;

        cStats statistics = new cStats();//initiate statistics
        newsBoy joe = new newsBoy();//initiate joe
        demandProc wantPaper = new demandProc();//initiate wantPaper

        //this is the time-step loop
        for (day = 1; day <= 1000; day++) {
            dmd = wantPaper.dmdToday();
            joe.setDemand(dmd);
            statistics.setProfitStatistics(joe.getProfit());//initialize profit statistics
            statistics.setDemandStatistics(joe.getDemand());//initialize demand statistics
            statistics.setSoldStatistics(joe.getSold());//initialize sold statistics
            joe.order();
            //test behavior for 5 days & print
            if (day >= 500 && day <= 505) {
                System.out.println("For day " + day + " demand " + dmd + " sold " + joe.getSold());
                System.out.println("Profit " + joe.getProfit() + " ordered " + joe.getOrdered());
            }
        }//end of timing loop

        //print the statistics at the end of 1000 days
        System.out.println("*************** Statistics for 1000 Days of Sales ***************");
        System.out.println("Average daily papers sold: " + statistics.getAverageSold());
        System.out.println("Average daily papers demanded: "+statistics.getAverageDemanded());
        System.out.println("Average daily profit: " + statistics.getAverage());

        System.out.println("Variance of papers sold daily: "+statistics.getVarianceSold());
        System.out.println("Variance of papers demanded daily: "+statistics.getVarianceDemanded());
        System.out.println("Variance of daily profit: " + statistics.getVariance());
        System.out.println("Days: " + statistics.getCount());
    }//end of main
}//end of newsBoySim

class newsBoy {
    private int demand;//this is the demand for the day
    private int ordered;//this is the amt ordered for the day
    private int bought;//this is the amt bought for the day
    private int sold;//this is the amt sold for the day
    private double profit;//this is the days profit

    //the newsboy constructor
    public newsBoy() {
        demand = 0;
        ordered = 0;
        bought = 0;
        sold = 0;
        profit = 0.0;
    }

    //this is the private policy function for how many the newsboy will order daily
    public int order() {
        //Reordering policy A: Average Profit = 10.302
        //ordered = 16;
        //return ordered;

        //Reordering policy B: Average Profit = 10.638
        //*This is the best reordering policy*
        ordered = demand;
        return ordered;

        //Reordering policy C: Average Profit = 10.362
        //ordered = demand - 1;
        //return ordered;
    }

    //this is the behavior for the newsboy
    public void behavior() {
        //receive the papers ordered yesterday
        bought = ordered;
        //calculate the papers sold
        if (demand >= bought) {
            sold = bought;
        } else {
            sold = demand;
        }
        //calculate profit
        profit = (1 * sold) - (.35 * bought);
        //return unsold papers for 5 cents
        if(bought > sold){
            profit += (0.05*(bought-sold));
        }
        //order for tomorrow
        ordered = order();
    }

    public void setDemand(int x) {
        //give the newsboy an initial demand then let behavior decide the amt
        demand = x;
        //given the demand, activate the behavior for the day
        behavior();
    }

    //***** Utility Functions *****
    public double getProfit() {
        return profit;
    }

    public int getSold() {
        return sold;
    }

    public int getOrdered() {
        return ordered;
    }

    public int getDemand(){
        return demand;
    }

}//end of newsboy class

class cStats {
    //the calculator class
    private double profit;//profit for today
    private double psum;//sum of profit for all days
    private double psum2;//sum squared of profit
    private double average;//average profit
    private double stdev;//standard deviation
    private double variance;//variance
    private int count;//count
    //variables used in maintain statistics
    private double sold;
    private double ssum;//sum of papers sold for all days
    private double ssum2;//sum squared of papers sold
    private double averageSold;//average sold
    private double stdevSold;//st dev of papers sold
    private double varianceSold;//variance of papers sold
    //----------------------------------------------------------------------------
    private double demand;
    private double dsum;//sum of papers demanded for all days
    private double dsum2;//sum squared of papers demanded
    private double averageDemanded;//average demanded
    private double stdevDemanded;//st dev of papers demanded
    private double varianceDemanded;//variance of papers demanded

    //the constructor for cStats
    public cStats() {
        //initialize all to zero
        profit = 0;
        psum = 0;
        psum2 = 0;
        average = 0;
        stdev = 0;
        variance = 0;
        count = 0;
        ssum = 0;
        ssum2 = 0;
        averageSold = 0;
        stdevSold = 0;
        varianceSold = 0;
        dsum = 0;
        dsum2 = 0;
        averageDemanded = 0;
        stdevDemanded = 0;
        varianceDemanded = 0;
    }

    public void setProfitStatistics(double x) {
        //this function sets profit and calculates the stats for the day for profit
        profit = x;
        psum += profit;
        psum2 += profit * profit;
        count++;
        average = psum / count;
        variance = (psum2 / count) - (average * average);
        stdev = Math.sqrt(variance);
        return;
    }

    //The ability of maintain statistics :D

    public void setSoldStatistics(double x){
        //parameter is joe.getSold for use in main
        sold = x;
        ssum += sold;
        ssum2 += sold * sold;
        averageSold = ssum / count;
        varianceSold = (ssum2 / count) - (averageSold * averageSold);
        stdevSold = Math.sqrt(varianceSold);
        return;
    }

    public void setDemandStatistics(double x){
        //parameter is joe.getDemand for use in main
        demand = (int)x;
        dsum += demand;
        dsum2 += demand * demand;
        averageDemanded = dsum / count;
        varianceDemanded = (dsum2 / count) - (averageDemanded * averageDemanded);
        stdevDemanded = Math.sqrt(varianceDemanded);
        return;
    }

    //***** Utility Functions for cStats *****
    public double getProfit() {
        return profit;
    }

    public double getAverage() {
        return average;
    }

    public double getVariance() {
        return variance;
    }

    public double getStDev() {
        return stdev;
    }

    public int getCount() {
        return count;
    }

    public double getAverageSold() {
        return averageSold;
    }

    public double getVarianceSold(){
        return varianceSold;
    }

    public double getAverageDemanded(){
        return averageDemanded;
    }

    public double getVarianceDemanded(){
        return varianceDemanded;
    }

}//end of class cStats

class demandProc {
    //this is the process generator for demand
    private int demand;

    //the constructor for demandProc
    public demandProc() {
        demand = 0;
    }

    public int dmdToday() {
        //this is the process generator for demand today
        int x;
        x = (int) (Math.random() * 100);
        if (x <= 8.3) {
            //(1/12)=.083
            demand = 15;
        } else if (x <= 16.6) {
            //(2/12)=.166
            demand = 16;
        } else if (x <= 58.3) {
            //(7/12)=.583
            demand = 17;
        } else if (x <= 75) {
            //(9/12)=.75
            demand = 18;
        } else if (x <= 91.6) {
            //(11/12)=.916
            demand = 19;
        } else {
            //(12/12)=1
            demand = 20;
        }
        return demand;
    }
}//end of demandProc


import java.util.Arrays;
import java.util.Scanner;

public class Lab1JesperBodin {

    static Scanner sc = new Scanner(System.in);
    static String choice;


    public static void main(String[] args) {
        String[] timeArray = {"00 - 01", "01 - 02", "02 - 03", "03 - 04", "04 - 05", "05 - 06", "06 - 07", "07 - 08",
                "08 - 09", "09 - 10", "10 - 11", "11 - 12", "12 - 13", "13 - 14", "14 - 15",
                "15 - 16", "16 - 17", "17 - 18", "18 - 19", "19 - 20", "20 - 21", "21 - 22", "22 - 23", "23 - 00"};

        int[] priceInput = new int[24];

        HourlyRate[] hourlyRate = new HourlyRate[priceInput.length];
        double sum=0;

        boolean checkProgram = true;

        do {


            StartMenu();
            choice = sc.next();

            if (choice.equals("1")) {
                System.out.println("Input price for every hour span (full öre):");

                for (int i = 0; i < timeArray.length; i++) {
                    System.out.print(timeArray[i] + ": ");
                    hourlyRate[i] = TimePriceInput(timeArray[i], sc.nextInt());
                }

            } else if (choice.equals("2")) {
                HourlyRate[] sortedHourlyRate = HighLowSort(hourlyRate);
                System.out.println("(Lägsta) Mellan kl: " + sortedHourlyRate[0].getTime() + " är priset: " + sortedHourlyRate[0].getPrice() + " öre/kWh.");
                System.out.println("");
                System.out.println("(Högsta) Mellan kl: " + sortedHourlyRate[hourlyRate.length - 1].getTime() + " är priset: " + sortedHourlyRate[sortedHourlyRate.length - 1].getPrice() + " öre/kWh.");
                System.out.println("");
                double average = GetAverage(hourlyRate);
                System.out.println("Medelpriset är: " + average + " öre/kWh.");
                System.out.println("");

            } else if (choice.equals("3")) {
                HourlyRate[] sortedHourlyRate = HighLowSort(hourlyRate);
                for (int i = 0; i < hourlyRate.length; i++) {
                    System.out.println("Mellan kl: " + sortedHourlyRate[i].getTime() + " är priset: " + sortedHourlyRate[i].getPrice() + " öre/kWh.");
                }
            } else if (choice.equals("4")) {
                HourlyRate[] bestChargingRange = calculateBestChargingRange(hourlyRate);
                System.out.println("Bästa tiden att ladda är:");
                for (int i = 0; i < bestChargingRange.length; i++) {
                    System.out.println("Mellan timmarna: " + bestChargingRange[i].getTime() + " är priset: " + bestChargingRange[i].getPrice() + " öre/kWh.");
                    sum += bestChargingRange[i].getPrice();
                }
                double average = sum / 4;
                System.out.println("Totalpriset för laddperioden är: " + sum + " öre");
                System.out.println("Medelpriset för laddperioden är: " + average + " öre/kWh.");

            } else  if (choice.equalsIgnoreCase("e")) {
                System.out.println("Avslutar");
                checkProgram=false;

            } else {
                System.out.println("Välj en giltig input!");
                System.out.println("");
            }
        }while (checkProgram);

    }


    private static double GetAverage(HourlyRate[] priceTime) {
        double sumTwo=0;
        for (HourlyRate hourlyRate : priceTime) {
            sumTwo += hourlyRate.getPrice();
        }
        return sumTwo / priceTime.length;
    }


    private static HourlyRate TimePriceInput(String timeSpan, int price) {
        HourlyRate priceTime = new HourlyRate();
        priceTime.setTime(timeSpan);
        priceTime.setPrice(price);
        return priceTime;
    }


    private static void StartMenu() {
        System.out.println("Elpriser");
        System.out.println("========");
        System.out.println("1. Inmatning");
        System.out.println("2. Min, Max och Medel");
        System.out.println("3. Sortera");
        System.out.println("4. Bästa Laddningstid (4h)");
        System.out.println("e. Avsluta");
    }


    private static HourlyRate[] calculateBestChargingRange(HourlyRate[] hourlyRate) {

        HourlyRate[] bestChargingRange = new HourlyRate[4];
        double priceSum = 0;
        int cheapestRangeFirstIndex = 0;


        for (int i = 0; i < 4; i++) {
            priceSum = priceSum + hourlyRate[i].getPrice();
        }

        for (int i = 1; i < hourlyRate.length - 3; i++) {
            double rangeSum = hourlyRate[i].getPrice();


            for (int j = i + 1; j < i + 4; j++) {
                rangeSum = rangeSum + hourlyRate[j].getPrice();
            }

            if (rangeSum < priceSum) {
                priceSum = rangeSum;
                cheapestRangeFirstIndex = i;
            }
        }

        bestChargingRange[0] = hourlyRate[cheapestRangeFirstIndex];
        bestChargingRange[1] = hourlyRate[cheapestRangeFirstIndex + 1];
        bestChargingRange[2] = hourlyRate[cheapestRangeFirstIndex + 2];
        bestChargingRange[3] = hourlyRate[cheapestRangeFirstIndex + 3];

        return bestChargingRange;
    }

    private static HourlyRate[] HighLowSort(HourlyRate[] hourlyRate) {
        boolean sort = true;
        HourlyRate[] sortedHourlyRate = Arrays.copyOf(hourlyRate, hourlyRate.length);

        while (sort) {
            sort = false;
            for (int i = 0; i < sortedHourlyRate.length - 1; i++) {
                if (sortedHourlyRate[i].getPrice() > sortedHourlyRate[i + 1].getPrice()) {
                    HourlyRate temp = sortedHourlyRate[i + 1];
                    sortedHourlyRate[i + 1] = sortedHourlyRate[i];
                    sortedHourlyRate[i] = temp;
                    sort = true;
                }
            }
        }

        return sortedHourlyRate;
    }
}

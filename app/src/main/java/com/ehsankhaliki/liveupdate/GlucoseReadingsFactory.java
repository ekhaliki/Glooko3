package com.ehsankhaliki.liveupdate;

import android.app.Activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * This class takes a file of readings and parses it into
 * ReadingObjects.  It stores all the readingObjects in
 * the ReadingsController object.
 * Created by ehsan on 4/28/15.
 */
public class GlucoseReadingsFactory {
    private ReadingsController readingsController;
    public GlucoseReadingsFactory(String patientName, Activity app){
        GlucoseReadingModel glucoseReadingModel;
        readingsController = new ReadingsController(patientName);
        String fileName = patientName.replaceAll("\\s","");
        File myFile = new File(fileName.toLowerCase());
        try {
            InputStream input = app.getAssets().open(fileName);
            Scanner scan = new Scanner(input);
            while(scan.hasNextLine()){
                String reading = scan.nextLine();
                glucoseReadingModel = new GlucoseReadingModel(parseValue(reading), parseTime(reading));
                readingsController.addReading(glucoseReadingModel);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int parseValue(String reading){
        int startIndex = reading.indexOf("bg_value")+11;
        int endIndex = reading.indexOf(',');
        return Integer.parseInt(reading.substring(startIndex, endIndex));
    }

    public String parseTime(String reading){
        int startIndex = reading.lastIndexOf('‘')+1;
        int endIndex = reading.lastIndexOf('’');
        return reading.substring(startIndex, endIndex);
    }

    public ReadingsController getReadingsController(){
        return readingsController;
    }
}

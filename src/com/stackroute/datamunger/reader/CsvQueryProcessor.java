package com.stackroute.datamunger.reader;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {

    private String fileNamame = "\"/home/ubuntu/Desktop/DataMunging/DataMungerStep4_Boilerplate/data/ipl.csv\"";


    /*
     * parameterized constructor to initialize filename. As you are trying to
     * perform file reading, hence you need to be ready to handle the IO Exceptions.
     */
    public CsvQueryProcessor(String fileName) throws FileNotFoundException {

        FileReader reader = new FileReader(fileName);
        this.fileNamame = fileName;
    }

    /*
     * implementation of getHeader() method. We will have to extract the headers
     * from the first line of the file.
     */
    @Override
    public Header getHeader() throws IOException {
        // TODO Auto-generated method stub

        BufferedReader reader = new BufferedReader(new FileReader(this.fileNamame));
        String header = reader.readLine();
        if (header != null) {
            String[] splitHeader = header.split(",");
            Header headerO = new Header();
            headerO.setHeaders(splitHeader);
            return headerO;
        } else {
            return null;
        }

    }


    /**
     * This method will be used in the upcoming assignments
     */
    @Override
    public void getDataRow() {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileNamame));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String header = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     * implementation of getColumnType() method. To find out the data types, we will
     * read the first line from the file and extract the field values from it. In
     * the previous assignment, we have tried to convert a specific field value to
     * Integer or Double. However, in this assignment, we are going to use Regular
     * Expression to find the appropriate data type of a field. Integers: should
     * contain only digits without decimal point Double: should contain digits as
     * well as decimal point Date: Dates can be written in many formats in the CSV
     * file. However, in this assignment,we will test for the following date
     * formats('dd/mm/yyyy',
     * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
     */
    @Override
    public DataTypeDefinitions getColumnType() throws IOException {
        // TODO Auto-generated method stub


        BufferedReader reader = new BufferedReader(new FileReader(this.fileNamame));
        String header = reader.readLine();
        String[] headerSplit = header.split(",");
        String line = reader.readLine();
        if (line == null) {
            return new DataTypeDefinitions();
        }

        String[] lineSplit = line.split(",");
        ArrayList<String> datatypes = new ArrayList<>();

        for (String str : lineSplit) {
            // checking for Integer
            if (str.matches("[0-9]*") && !str.contains(".")) {
                datatypes.add("java.lang.Integer");
            }
            // checking for floating point numbers
            else if (str.contains(".") && str.matches("[0-9]*")) {
                datatypes.add("java.lang.Double");
            } else if (str.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") || str.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
                datatypes.add("java.util.Date");
            } else if (str.matches("[0-9]{2}-[a-zA-Z]*-[0-9]{2}") || str.matches("[0-9]{2}/[a-zA-Z]*/[0-9]{4}")) {
                datatypes.add("java.util.Date");
            } else {
                datatypes.add("java.lang.String");
            }
        }
        while ((datatypes.size() < headerSplit.length)) {
            datatypes.add("java.lang.Object");
        }
        DataTypeDefinitions dataDef = new DataTypeDefinitions();
        dataDef.setDataTypes(datatypes.toArray(new String[datatypes.size()]));
        return dataDef;
        // checking for date format dd/mm/yyyy

        // checking for date format mm/dd/yyyy

        // checking for date format dd-mon-yy
        // checking for date format dd-mon-yyyy
        // checking for date format dd-month-yy
        // checking for date format dd-month-yyyy
        // checking for date format yyyy-mm-dd

    }


}

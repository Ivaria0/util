package com.mycompany.util;

import org.kohsuke.args4j.CmdLineException;

import java.io.*;

/**
 *
 * @author vikai
 */
public class FlagProcessing {

    /**
     *
     * @param arguments
     * @param GivenPath
     * @param Prefix
     * @param Adding
     * @param ShortStats
     * @param FullStats
     * @throws IOException
     * @throws CmdLineException
     */
    public static void FileRecording(String[] arguments, String GivenPath, String Prefix, 
            boolean Adding, boolean ShortStats, boolean FullStats) throws IOException, CmdLineException {
        
        StringBuilder IntBuilder = new StringBuilder();
        StringBuilder FloatBuilder = new StringBuilder();
        StringBuilder StrBuilder = new StringBuilder();

        int IntElemNumber = 0;
        int FloatElemNumber = 0;
        int StrElemNumber = 0;
        
        long MinInt = 0;
        long MaxInt = 0;
        long SumInt = 0;
        
        double MinFloat = 0;
        double MaxFloat = 0;
        double SumFloat = 0;
        
        long MinLength = 0;
        long MaxLength = 0;

        for (String argument : arguments) {
            if (new File(argument).exists()) {
                FileReader fr = new FileReader(argument);
                try(BufferedReader reader = new BufferedReader(fr)) {
                    String temp;

                    while ((temp = reader.readLine()) != null) {
                        int StrType = Distribution(temp, IntBuilder, FloatBuilder, StrBuilder);
                        
                        if (StrType == 1) {
                            long IntTemp = Long.parseLong(temp);
                            if (ShortStats || FullStats) {
                                IntElemNumber++;
                            }
                            if (FullStats) {
                                MinInt = Min(MinInt, IntTemp, IntElemNumber);
                                
                                MaxInt = Max(MaxInt, IntTemp, IntElemNumber);
                                
                                SumInt += IntTemp;
                            }
                        }
                        
                        if (StrType == 2) {
                            double FloatTemp = Double.parseDouble(temp);
                            if (ShortStats || FullStats) {
                                FloatElemNumber++;
                            }
                            if (FullStats) {
                                MinFloat = Min(MinFloat, FloatTemp, FloatElemNumber);
                                
                                MaxFloat = Max(MaxFloat, FloatTemp, FloatElemNumber);
                                
                                SumFloat += FloatTemp;
                            }
                        }
                        
                        if (StrType == 3) {
                            if (ShortStats || FullStats) {
                                StrElemNumber++;
                            }
                            if (FullStats) {
                                MinLength = Min(MinLength, temp.length(), StrElemNumber);
                                
                                MaxLength = Max(MaxLength, temp.length(), StrElemNumber);
                            }
                        }
                    }

                } catch(IOException e) {
                    System.out.println("Reading of the file " + argument + " is failed");
                }
                
            } else {
                System.out.println("This file doesn't exist: " + argument);
                throw new IllegalArgumentException("");
            }
        }
        
        if (!IntBuilder.toString().equals("")) {
            File FileInt = new File(OutputFilePath(GivenPath, Prefix, "integers.txt"));
            RecordInFile(FileInt, IntBuilder, Adding);
        }
        if (!FloatBuilder.toString().equals("")) {
            File FileFloat = new File(OutputFilePath(GivenPath, Prefix, "floats.txt"));
            RecordInFile(FileFloat, FloatBuilder, Adding);
        }
        if (!StrBuilder.toString().equals("")) {
            File FileStr = new File(OutputFilePath(GivenPath, Prefix, "strings.txt"));
            RecordInFile(FileStr, StrBuilder, Adding);
        }
        
        PrintStats(ShortStats, FullStats, MinInt, MaxInt, SumInt, IntElemNumber, MinFloat, MaxFloat, SumFloat,
                FloatElemNumber, MinLength, MaxLength, StrElemNumber);
    }
        
    /**
     *
     * @param line
     * @param IntBuilder
     * @param FloatBuilder
     * @param StrBuilder
     * @return
     */
    public static int Distribution(String line, StringBuilder IntBuilder, StringBuilder FloatBuilder, 
            StringBuilder StrBuilder) {
        try {
            Double.parseDouble(line);
            try {
                Long.parseLong(line);
                IntBuilder.append(line).append("\n");
                return 1;
            } catch (NumberFormatException e) {
                FloatBuilder.append(line).append("\n");
                return 2;
            }
        } catch (NumberFormatException e) {
            StrBuilder.append(line).append("\n");
            return 3;
        }
    }
    
    /**
     *
     * @param MinValue
     * @param value
     * @param NumberOfStrings
     * @return
     */
    public static double Min(double MinValue, double value, int NumberOfStrings) {
        if (NumberOfStrings == 1) {
            return value;
        } else {
            if (MinValue < value) {
                return MinValue;
            } else {
                return value;
            }
        }
    }
    
    /**
     *
     * @param MaxValue
     * @param value
     * @param NumberOfStrings
     * @return
     */
    public static double Max(double MaxValue, double value, int NumberOfStrings) {
        if (NumberOfStrings == 1) {
            return value;
        } else {
            if (MaxValue > value) {
                return MaxValue;
            } else {
                return value;
            }
        }
    }
    
    /**
     *
     * @param MinValue
     * @param value
     * @param NumberOfStrings
     * @return
     */
    public static long Min(long MinValue, long value, int NumberOfStrings) {
        if (NumberOfStrings == 1) {
            return value;
        } else {
            if (MinValue < value) {
                return MinValue;
            } else {
                return value;
            }
        }
    }
    
    /**
     *
     * @param MaxValue
     * @param value
     * @param NumberOfStrings
     * @return
     */
    public static long Max(long MaxValue, long value, int NumberOfStrings) {
        if (NumberOfStrings == 1) {
            return value;
        } else {
            if (MaxValue > value) {
                return MaxValue;
            } else {
                return value;
            }
        }
    }

    /**
     *
     * @param GivenPath
     * @param Prefix
     * @param Name
     * @return
     */
    public static String OutputFilePath(String GivenPath, String Prefix, String Name) {
        String path;
        if (GivenPath != null) {
            if (Prefix != null) {
                path = GivenPath + "/" + Prefix + Name;

            } else {
                path = GivenPath + "/" + Name;
            }

        } else {
            if (Prefix != null) {
                path = "./" + Prefix + Name;

            } else {
                path = "./" + Name;
            }
        }
        return path;
    }
    
    /**
     *
     * @param f
     * @param Builder
     * @param Adding
     */
    public static void RecordInFile(File f, StringBuilder Builder, boolean Adding) {
        if (FileCreateOrCheck(f)) {
            try (FileWriter writer = new FileWriter(f, Adding)) {
                writer.write(Builder.toString());
            } catch(IOException e) {
                System.out.println("Writing in the file " + f.getName() + " is failed");
            }
        }
    }

    /**
     *
     * @param f
     * @return
     */
    public static boolean FileCreateOrCheck(File f) {
        if (!f.exists()) {
            try {
                f.createNewFile();
                return true;
            } catch (IOException e) {
                System.out.println("File " + f.getName() + " couldn't be created");
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param ShortStats
     * @param FullStats
     * @param MinInt
     * @param MaxInt
     * @param SumInt
     * @param IntElemNumber
     * @param MinFloat
     * @param MaxFloat
     * @param SumFloat
     * @param FloatElemNumber
     * @param MinLength
     * @param MaxLength
     * @param StrElemNumber
     */
    public static void PrintStats(boolean ShortStats, boolean FullStats, long MinInt, long MaxInt, long SumInt, 
            int IntElemNumber, double MinFloat, double MaxFloat, double SumFloat, int FloatElemNumber, 
            long MinLength, long MaxLength, int StrElemNumber) {
        if (ShortStats) {
            System.out.println("Number of integer elements: " + IntElemNumber);
            System.out.println(" ");
            
            System.out.println("Number of float elements: " + FloatElemNumber);
            System.out.println(" ");
            
            System.out.println("Number of string elements: " + StrElemNumber);
            System.out.println(" ");
        }
        
        if (FullStats) {
            System.out.println("Number of integer elements: " + IntElemNumber);
            
            if (IntElemNumber != 0) {
                System.out.println("Minimum integer number: " + MinInt);
                System.out.println("Maximum integer number: " + MaxInt);
                System.out.println("Sum of integer numbers: " + SumInt);
                System.out.println("Mean integer number: " + SumInt / IntElemNumber);
            }
            System.out.println(" ");
                        
            System.out.println("Number of float elements: " + FloatElemNumber);
            
            if (FloatElemNumber != 0) {
                System.out.println("Minimum float number: " + MinFloat);
                System.out.println("Maximum float number: " + MaxFloat);
                System.out.println("Sum of float numbers: " + SumFloat);
                System.out.println("Mean float number: " + SumFloat / FloatElemNumber);
            }
            System.out.println(" ");
            
            System.out.println("Number of string elements: " + StrElemNumber);
            if (StrElemNumber != 0) {
                System.out.println("Minimum string length: " + MinLength);
                System.out.println("Maximum string length: " + MaxLength);
            }
        }
    }
}
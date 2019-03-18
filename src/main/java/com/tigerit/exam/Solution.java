package com.tigerit.exam;


import static com.tigerit.exam.IO.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {


    @Override
    public void run() {
        // init();
        // if(true) return;
        // your application entry point

        // sample input process
        // String string = readLine();
        //
        // Integer integer = readLineAsInteger();
        //
        // // sample output process
        // printLine(string);
        // printLine(integer);
        int tcase = readLineAsInteger();

        for(int tc=1; tc<=tcase; tc++) {
            printLine("Test: "+tc);
            Database database = new Database();

            database.processInput();

            int totalQuery = readLineAsInteger();

            for(int q=1; q<=totalQuery; q++) {
                Query query = new Query();
                query.processInput(database);
                
                readLine();
                printLine("");
            }
        }
        // printTimeConsumed();

    }

    void print(String str) {
        System.out.print(str);
    }
    void print(int i) {
        print(String.valueOf(i));
    }

    private int[] getIntArrayFromLine(String line) {
        String[] str = line.split(" ");
        int ara[] = new int[str.length];
        for(int i=0; i< str.length; i++) {
            ara[i] = Integer.valueOf(str[i]);
        }
        return ara;
    }

    private String[] getStringArrayFromLine(String line) {
        String[] str = line.split(" ");
        return str;
    }

    private String[] splitByDot(String line) {
        return line.split("\\.");
    }


    class Query {
        private int firstTableId, secondTableId, firstTableColumnId, secondTableColumnId;
        private String firstTableFullName, firstTableShortName, secondTableFullName, secondTableShortName;
        private SelectedColumn selectedColumns[];
        private String queryString[];

        void processInput(Database database) {
            queryString = new String[4];
            for(int i=0; i<4; i++) {
                queryString[i] = readLine();
            }
            processFirstTableInformation(queryString[1], database);
            processSecondTableInformation(queryString[2], database);
            processConditionInformation(queryString[3], database);
            processSelectStatement(queryString[0], database);

            executeQuery(database);
        }


        void executeQuery(Database database) {
            Table firstTable = database.getTable(firstTableId), secondTable = database.getTable(secondTableId);
            ArrayList<ArrayList<Integer> > ans = new ArrayList<ArrayList<Integer> > ();

            for(int i=0; i< selectedColumns.length; i++) {
                assert(selectedColumns[i]!=null);
                assert(database.getTable(selectedColumns[i].getTableId())!=null);
                print(database.getTable(selectedColumns[i].getTableId()).getColumnName(selectedColumns[i].getColumnId()));
                if(i==selectedColumns.length-1)
                    printLine("");
                else
                    print(" ");
            }

            for(int i=0; i< firstTable.getNumberOfRows(); i++) {
                for(int j=0; j< secondTable.getNumberOfRows(); j++) {
                    if(firstTable.getDataAt(i, firstTableColumnId) == secondTable.getDataAt(j, secondTableColumnId)) {
                        ArrayList<Integer> temp = new ArrayList<Integer>();    
                        for(int k=0; k< selectedColumns.length; k++) {
                            assert(selectedColumns[i] != null);
                            selectedColumns[k].getTableId();
                            if(selectedColumns[k].getTableId() == firstTableId) {
                                // print(database.getTable(selectedColumns[k].getTableId()).getDataAt(i, selectedColumns[k].getColumnId()));
                            
                                temp.add(database.getTable(selectedColumns[k].getTableId()).getDataAt(i, selectedColumns[k].getColumnId()));
                            } else {
                                // print(database.getTable(selectedColumns[k].getTableId()).getDataAt(j, selectedColumns[k].getColumnId()));
                                temp.add(database.getTable(selectedColumns[k].getTableId()).getDataAt(j, selectedColumns[k].getColumnId()));
                            }

                            
                        }
                        ans.add(temp);


                    }
                }
            }


            Collections.sort(ans, new Comparator<ArrayList<Integer>>() {    
            @Override
            public int compare(ArrayList<Integer> a, ArrayList<Integer> b) {
                for(int i=0; i<a.size(); i++) {
                    if(a.get(i)!= b.get(i)) {
                        return a.get(i) - b.get(i);
                    }
                }
                return 0;
            }               
            });


            for(int i=0; i< ans.size(); i++) {
                if(ans.get(i).size()>0) {
                    ArrayList<Integer> temp = ans.get(i);

                    for(int j=0; j< temp.size(); j++) {
                        print(temp.get(j));

                        if(j==temp.size()-1)
                            printLine("");
                        else
                            print(" ");
                    }
                }
            }
        }

        void processConditionInformation(String line, Database database) {
            // printLine("processConditionInformation called");
            String firstTableColumnName, secondTableColumnName;
            String str[] = getStringArrayFromLine(line);
            
            
            // printLine("Str array size:"+ str.length);
            // for(int i=0; i<str.length; i++)
            //     printLine(str[i]);
            
            // printLine("Str[1] split array size:"+ str[1].split(".").length);
            // for(int i=0; i<str[1].split(".").length; i++)
            //     printLine(str[1].split(".")[i]);


            firstTableColumnName = splitByDot(str[1])[1];
            secondTableColumnName = splitByDot(str[3])[1];

            Table firstTable = database.getTable(firstTableId);
            for(int i=0; i< firstTable.getNumberOfColumns(); i++) {
                if(firstTable.getColumnName(i).equals(firstTableColumnName)) {
                    firstTableColumnId = i;
                    break;
                }
            }

            Table secondTable = database.getTable(secondTableId);
            for(int i=0; i< secondTable.getNumberOfColumns(); i++) {
                if(secondTable.getColumnName(i).equals(secondTableColumnName)) {
                    secondTableColumnId = i;
                    break;
                }
            }

        }


        void processFirstTableInformation(String line, Database database) {
            String str[] = getStringArrayFromLine(line);
            firstTableFullName = str[1];

            if(str.length>2)
                firstTableShortName = str[2];
            else
                firstTableShortName = "";

            for(int i=0; i< database.getNumberOfTables(); i++) {
                if(database.getTable(i).getName().equals(firstTableFullName)) {
                    firstTableId = i;
                    break;
                }
            }
        }


        void processSecondTableInformation(String line, Database database) {
            String str[] = getStringArrayFromLine(line);
            secondTableFullName = str[1];

            if(str.length>2)
                secondTableShortName = str[2];
            else
                secondTableShortName = "";

            for(int i=0; i< database.getNumberOfTables(); i++) {
                if(database.getTable(i).getName().equals(secondTableFullName)) {
                    secondTableId = i;
                    break;
                }
            }
        }

        void processSelectStatement(String line, Database database) {
            String str[] = getStringArrayFromLine(line);
            if(str[1].equals("*")) {
                Table firstTable = database.getTable(firstTableId);
                Table secondTable = database.getTable(secondTableId);
                int nC = firstTable.getNumberOfColumns()+secondTable.getNumberOfColumns();
                selectedColumns = new SelectedColumn[nC];
                
                for(int i=0; i< firstTable.getNumberOfColumns(); i++) {
                    selectedColumns[i] = new SelectedColumn(firstTableId, i);
                }
                for(int i=firstTable.getNumberOfColumns(), j=0; j< secondTable.getNumberOfColumns(); i++, j++) {
                    selectedColumns[i] = new SelectedColumn(secondTableId, j);
                }


                // printLine("selectedColumns size:"+selectedColumns.length);

                // for(int i=0; i< selectedColumns.length; i++) {
                //     selectedColumns[i].printInformation();
                // }

                return;
            }

            selectedColumns = new SelectedColumn[str.length-1];
            for(int i=1; i< str.length; i++) {
                if(i != str.length-1) {
                    str[i] = str[i].substring(0, str[i].length()-1);
                }
                String temp[] = splitByDot(str[i]);
                int tableId = getFirstOrSecondTableId(temp[0]);
                selectedColumns[i-1] = new SelectedColumn(tableId, getColumnIdFromTable(database.getTable(tableId), temp[1]));
                
            }

    

        }


        int getColumnIdFromTable(Table table, String columnName) {
            for(int i=0; i< table.getNumberOfColumns(); i++) {
                if(table.getColumnName(i).equals(columnName)) {
                    return i;
                }
            }
            return -1;
        }


        int getFirstOrSecondTableId(String tableName) {
            if(tableName.equals(firstTableShortName) || tableName.equals(firstTableFullName)) {
                return firstTableId;
            }
            return secondTableId;
        }

    }

    class SelectedColumn{
        private int tableId= -1, columnId = -1;        
        
        SelectedColumn(){}

        SelectedColumn(int tableId, int columnId) {
            this.tableId = tableId;
            this.columnId = columnId;
        }
        public int getTableId() {
            // assert(tableId!=null);
            return tableId;
        }

        public int getColumnId() {
            return columnId;
        }

        public void printInformation() {
            printLine("tableId: "+tableId+" columnId: "+columnId);
        }
    }


    class Database{
        private int nT;
        private Table tables[];

        Database() {
        }

        void processInput() {
            nT = readLineAsInteger();
            tables = new Table [nT];

            for(int i=0; i<nT; i++) {
                String tableName = readLine();
                int ara[] = getIntArrayFromLine(readLine());
                tables[i] = new Table(tableName, ara[1], ara[0]);
            }
        }

        int getNumberOfTables() {
            return nT;
        }

        Table getTable(int pos) {
            // if(pos>= tables.length)
            //     return null;
            return tables[pos];
        }

    }

    class Table {
        private int rows, cols, data[][];
        private String name, columnNames[];

        Table(String name, int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            this.name = name;
            columnNames = new String[cols];
            data = new int[rows] [cols];

            fillColumnNamesWithInput();
            fillDataWithInput();

            // printTableDetails();
        }

        private void fillColumnNamesWithInput() {
            columnNames = getStringArrayFromLine(readLine());
        }


        private void fillDataWithInput() {
            for(int r=0; r<rows; r++) {
                int ara[] = getIntArrayFromLine(readLine());
                for(int c=0; c<cols; c++) {
                    data[r][c] = ara[c];
                }
            }
        }

        String getName(){
            return name;
        }

        int getNumberOfRows() {
            return rows;
        }

        int getNumberOfColumns() {
            return cols;
        }

        String getColumnName(int pos){
            if(pos>=columnNames.length)
                return "";
            return columnNames[pos];
        }

        int getDataAt(int r, int c) {
            return data[r][c];
        }

        private void printTableDetails() {
            printLine("table name: "+name);
            printLine("rows:"+rows+" cols:"+cols);
            printLine("columnNames:");
            for(int i=0; i<cols; i++)
                printLine(columnNames[i]);

            for(int r=0; r<rows; r++) {
                for(int c=0; c<cols; c++) {
                    System.out.print(data[r][c]+" ");
                }
                printLine("");
            }
        }



    }


}

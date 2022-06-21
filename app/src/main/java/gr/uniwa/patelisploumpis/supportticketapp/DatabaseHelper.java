package gr.uniwa.patelisploumpis.supportticketapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gr.uniwa.patelisploumpis.supportticketapp.Model.SupportTicket;
import gr.uniwa.patelisploumpis.supportticketapp.Model.Technician;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static Cursor cursor;
    private static DatabaseHelper dbInstance;
    private final SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();
    private final SQLiteDatabase sqLiteDatabaseW = getWritableDatabase();

    // Database Info
    private static final String DATABASE_NAME = "support_ticket_app";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_TICKETS = "support_tickets";
    private static final String TABLE_TECHNICIANS = "technicians";

    // Table Columns
    private static final String KEY_TICKET_ID = "ticketID";
    private static final String KEY_TECHNICIAN_NAME = "technicianName";
    private static final String KEY_CLIENT_NAME = "clientName";
    private static final String KEY_CLIENT_ADDRESS = "clientAddress";
    private static final String KEY_CLIENT_PHONE = "clientPhone";
    private static final String KEY_CLIENT_EMAIL = "clientEmail";
    private static final String KEY_LABOR_DATE = "laborDate";
    private static final String KEY_LABOR_TYPE = "laborType";
    private static final String KEY_LABOR_HOURS = "laborHours";
    private static final String KEY_LABOR_DESCRIPTION = "laborDescription";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Singleton Pattern Implementation
    public static synchronized DatabaseHelper getInstance(Context context) {
        if(dbInstance == null){
            dbInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TICKETS_TABLE = "CREATE TABLE " + TABLE_TICKETS + "(" +
                KEY_TICKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                KEY_TECHNICIAN_NAME+ " TEXT NOT NULL," +
                KEY_CLIENT_NAME + " TEXT NOT NULL," +
                KEY_CLIENT_ADDRESS + " TEXT NOT NULL," +
                KEY_CLIENT_PHONE + " TEXT NOT NULL," +
                KEY_CLIENT_EMAIL + " TEXT NOT NULL," +
                KEY_LABOR_DATE + " DATE NOT NULL," +
                KEY_LABOR_TYPE + " TEXT NOT NULL," +
                KEY_LABOR_HOURS + " INTEGER NOT NULL," +
                KEY_LABOR_DESCRIPTION + " TEXT" +
                ")";

        String CREATE_TECHNICIANS_TABLE = "CREATE TABLE " + TABLE_TECHNICIANS + "(" +
                KEY_TECHNICIAN_NAME+ " TEXT PRIMARY KEY NOT NULL" +
                ")";

        sqLiteDatabase.execSQL(CREATE_TICKETS_TABLE);
        sqLiteDatabase.execSQL(CREATE_TECHNICIANS_TABLE);

        String[] defaultTechnicians = {"Vasilis Patelis","Loukas Ploumpis","Technician #3"};
        for (String technician : defaultTechnicians){
            ContentValues cValues = new ContentValues();
            cValues.put(KEY_TECHNICIAN_NAME, technician);
            sqLiteDatabase.insert(TABLE_TECHNICIANS, null, cValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TECHNICIANS);
            onCreate(sqLiteDatabase);
        }
    }

    // Add new SupportTicket entry to DB
    public void addSupportTicket(SupportTicket supportTicket) {
        sqLiteDatabaseW.beginTransaction();
        try{
            ContentValues cValues = new ContentValues();
            cValues.put(KEY_TECHNICIAN_NAME, supportTicket.getTechnicianName());
            cValues.put(KEY_CLIENT_NAME, supportTicket.getClientName());
            cValues.put(KEY_CLIENT_ADDRESS, supportTicket.getClientAddress());
            cValues.put(KEY_CLIENT_PHONE, supportTicket.getClientPhone());
            cValues.put(KEY_CLIENT_EMAIL, supportTicket.getClientEmail());
            cValues.put(KEY_LABOR_DATE, supportTicket.getLaborDate());
            cValues.put(KEY_LABOR_TYPE, supportTicket.getLaborType());
            cValues.put(KEY_LABOR_HOURS, supportTicket.getLaborHours());
            cValues.put(KEY_LABOR_DESCRIPTION, supportTicket.getLaborDescription());

            sqLiteDatabaseW.insert(TABLE_TICKETS, null, cValues);
            sqLiteDatabaseW.setTransactionSuccessful();
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to add ticket to database");
        }finally{
            sqLiteDatabaseW.endTransaction();
        }
    }

    // Retrieve all SupportTicket entries stored in DB
    @SuppressLint("Range") // Suppress error "value must be >0" for get column index
    public List<SupportTicket> getAllTickets() {
        List<SupportTicket> supportTicketList = new ArrayList<>();
        String ALL_TICKETS_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_TICKETS);
        cursor = sqLiteDatabaseR.rawQuery(ALL_TICKETS_SELECT_QUERY, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    SupportTicket supportTicket = new SupportTicket();
                    supportTicket.setTicketID(cursor.getString(cursor.getColumnIndex(KEY_TICKET_ID)));
                    supportTicket.setTechnicianName(cursor.getString(cursor.getColumnIndex(KEY_TECHNICIAN_NAME)));
                    supportTicket.setClientName(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_NAME)));
                    supportTicket.setClientAddress(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_ADDRESS)));
                    supportTicket.setClientPhone(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_PHONE)));
                    supportTicket.setClientEmail(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_EMAIL)));
                    supportTicket.setLaborDate(cursor.getString(cursor.getColumnIndex(KEY_LABOR_DATE)));
                    supportTicket.setLaborType(cursor.getString(cursor.getColumnIndex(KEY_LABOR_TYPE)));
                    supportTicket.setLaborHours(cursor.getInt(cursor.getColumnIndex(KEY_LABOR_HOURS)));
                    supportTicket.setLaborDescription(cursor.getString(cursor.getColumnIndex(KEY_LABOR_DESCRIPTION)));
                    supportTicketList.add(supportTicket);
                }while(cursor.moveToNext());
            }
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to retrieve tickets list from database");
        }finally{
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return supportTicketList;
    }

    // Retrieve SupportTicket by tickedID
    @SuppressLint("Range")
    public SupportTicket getTicketByID(String ticketID) {
        SupportTicket supportTicket = new SupportTicket();
        String TICKET_BY_ID_SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = %s", TABLE_TICKETS, KEY_TICKET_ID, ticketID);
        cursor = sqLiteDatabaseR.rawQuery(TICKET_BY_ID_SELECT_QUERY, null);

        try{
            if(cursor.moveToLast()){
                supportTicket.setTicketID(cursor.getString(cursor.getColumnIndex(KEY_TICKET_ID)));
                supportTicket.setTechnicianName(cursor.getString(cursor.getColumnIndex(KEY_TECHNICIAN_NAME)));
                supportTicket.setClientName(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_NAME)));
                supportTicket.setClientAddress(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_ADDRESS)));
                supportTicket.setClientPhone(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_PHONE)));
                supportTicket.setClientEmail(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_EMAIL)));
                supportTicket.setLaborDate(cursor.getString(cursor.getColumnIndex(KEY_LABOR_DATE)));
                supportTicket.setLaborType(cursor.getString(cursor.getColumnIndex(KEY_LABOR_TYPE)));
                supportTicket.setLaborHours(cursor.getInt(cursor.getColumnIndex(KEY_LABOR_HOURS)));
                supportTicket.setLaborDescription(cursor.getString(cursor.getColumnIndex(KEY_LABOR_DESCRIPTION)));
            }
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to retrieve ticket from database");
        }finally{
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return supportTicket;
    }

    // Retrieve the ticketID of the last SupportTicket stored in DB
    @SuppressLint("Range")
    public int getLastTicketID() {
        int lastTicketID = 0;
        String LAST_TICKET_ID_SELECT_QUERY = String.format("SELECT seq FROM sqlite_sequence WHERE NAME = \"%s\"", TABLE_TICKETS);
        cursor = sqLiteDatabaseR.rawQuery(LAST_TICKET_ID_SELECT_QUERY, null);

        try{
            if(cursor.moveToFirst()){
                lastTicketID = cursor.getInt(cursor.getColumnIndex("seq"));
            }
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to retrieve last ticket's id from database");
        }finally{
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return lastTicketID;
    }

    // Delete SupportTicket entry from DB
    public void deleteSupportTicketByID(String ticketID) {
        String DELETE_TICKET_QUERY = String.format("DELETE FROM %s WHERE %s = %s", TABLE_TICKETS, KEY_TICKET_ID, ticketID);
        sqLiteDatabaseW.beginTransaction();
        try{
            sqLiteDatabaseW.execSQL(DELETE_TICKET_QUERY);
            sqLiteDatabaseW.setTransactionSuccessful();
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to delete ticket from database");
        }finally {
            sqLiteDatabaseW.endTransaction();
        }
    }

    // Add new Technician entry to DB
    public void addTechnician(Technician technician) {
        sqLiteDatabaseW.beginTransaction();
        try{
            ContentValues cValues = new ContentValues();
            cValues.put(KEY_TECHNICIAN_NAME, technician.getName());
            sqLiteDatabaseW.insertOrThrow(TABLE_TECHNICIANS, null, cValues);
            sqLiteDatabaseW.setTransactionSuccessful();
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to add technician to database");
        }finally{
            sqLiteDatabaseW.endTransaction();
        }
    }

    // Retrieve all Technician entries stored in DB
    @SuppressLint("Range")
    public List<Technician> getAllTechnicians() {
        List<Technician> technicianList = new ArrayList<>();
        String ALL_TECHNICIANS_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_TECHNICIANS);
        cursor = sqLiteDatabaseR.rawQuery(ALL_TECHNICIANS_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Technician technician = new Technician();
                    technician.setName(cursor.getString(cursor.getColumnIndex(KEY_TECHNICIAN_NAME)));
                    technicianList.add(technician);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to retrieve technicians list from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return technicianList;
    }

    // Delete Technician entry from DB
    public void deleteTechnician(String technicianName) {
        String DELETE_TECHNICIAN_QUERY = String.format("DELETE FROM %s WHERE %s = \"%s\"", TABLE_TECHNICIANS, KEY_TECHNICIAN_NAME, technicianName);
        sqLiteDatabaseW.beginTransaction();
        try{
            sqLiteDatabaseW.execSQL(DELETE_TECHNICIAN_QUERY);
            sqLiteDatabaseW.setTransactionSuccessful();
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to delete technician from database");
        }finally {
            sqLiteDatabaseW.endTransaction();
        }
    }

}
